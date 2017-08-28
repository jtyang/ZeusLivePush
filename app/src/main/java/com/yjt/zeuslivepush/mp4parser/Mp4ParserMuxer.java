package com.yjt.zeuslivepush.mp4parser;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;

import com.yjt.zeuslivepush.mp4parser.SrsHelper.SrsAvcNaluType;
import com.yjt.zeuslivepush.mp4parser.SrsHelper.SrsEsFrame;
import com.yjt.zeuslivepush.mp4parser.SrsHelper.SrsEsFrameBytes;
import com.yjt.zeuslivepush.mp4parser.SrsHelper.SrsRawH264Stream;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static android.R.attr.orientation;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/8/25
 * ref:
 * https://github.com/DrKLO/Telegram/blob/master/TMessagesProj/src/main/java/org/telegram/messenger/video/Track.java
 * https://github.com/Tourenathan-G5organisation/SiliCompressor/blob/master/silicompressor/src/main/java/com/iceteck/silicompressorr/videocompression/MediaController.java
 */
public class Mp4ParserMuxer {

    private static final String TAG = "Mp4ParserMuxer";

    public final static String VIDEO_MIME_TYPE = "video/avc";
    private static final int VIDEO_TRACK = 100;
    private static final int AUDIO_TRACK = 101;

    private File mRecFile;
//    private RecordHandler mHandler;

    private MediaFormat videoFormat = null;
    private MediaFormat audioFormat = null;

    private SrsRawH264Stream avc = new SrsRawH264Stream();
//    private Mp4Movie mp4Movie = new Mp4Movie();

    private boolean aacSpecConfig = false;
    private ByteBuffer h264_sps = null;
    private ByteBuffer h264_pps = null;
    private ArrayList<byte[]> spsList = new ArrayList<>();
    private ArrayList<byte[]> ppsList = new ArrayList<>();

    private Thread worker;
    private volatile boolean bRecording = false;
    private volatile boolean bPaused = false;
    private volatile boolean needToFindKeyFrame = true;
    private final Object writeLock = new Object();
    private ConcurrentLinkedQueue<SrsEsFrame> frameCache = new ConcurrentLinkedQueue<>();

    private int mOrientation = 0;//手机屏幕方向，用于对视频做旋转处理
    private int videoKeyframeCheckNum = 0;

    private MP4Builder mp4Builder = null;
    private int mpVideoTrack;
    private int mpAudioTrack;

    public boolean record(File outputFile) {

        if (videoFormat == null && audioFormat == null) {
            return false;
        }
        if (outputFile == null) return false;
//        FileUtils.createFile(outputFile.getAbsolutePath());
        if (bRecording) {
            stop();
        }

        mRecFile = outputFile;
        mOrientation = orientation;

        Mp4Movie movie = new Mp4Movie();
//        movie.setCacheFile(cacheFile);
//        movie.setRotation(rotationValue);
//        movie.setSize(resultWidth, resultHeight);

        try {
            mp4Builder = new MP4Builder().createMovie(movie);
            if (!spsList.isEmpty() && !ppsList.isEmpty()) {
//            mp4Movie.addTrack(videoFormat, false);
                mpVideoTrack = mp4Builder.addTrack(videoFormat, false,spsList,ppsList);
            }
            mpAudioTrack = mp4Builder.addTrack(audioFormat, true,spsList,ppsList);

            worker = new Thread(new Runnable() {
                @Override
                public void run() {
                    bRecording = true;
                    while (bRecording) {
                        // Keep at least one audio and video frame in cache to ensure monotonically increasing.
                        while (!frameCache.isEmpty()) {
                            SrsEsFrame frame = frameCache.poll();
                            if (frame != null)
                                writeSampleData(frame.bb, frame.bi, frame.is_audio(AUDIO_TRACK));
                        }
                        // Waiting for next frame
                        synchronized (writeLock) {
                            try {
                                // isEmpty() may take some time, so we set timeout to detect next frame
                                writeLock.wait(10);
                            } catch (InterruptedException ie) {
                                worker.interrupt();
                            }
                        }
                    }
                }
            });
            worker.start();
//            mHandler.notifyRecordStarted(mRecFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void stop() {
        bRecording = false;
        bPaused = false;
        needToFindKeyFrame = true;
        aacSpecConfig = false;
        frameCache.clear();
        videoKeyframeCheckNum = 0;

        if (worker != null) {
            try {
                worker.join(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                worker.interrupt();
            }
            worker = null;

            try {
                if (mp4Builder != null) mp4Builder.finishMovie(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            mHandler.notifyRecordFinished(mRecFile.getPath());
        }
        Log.i(TAG, "NonoMp4Muxer closed");
    }


    public synchronized boolean isRecording() {
        return bRecording;
    }

    /**
     * 判断是否已经写视频数据
     *
     * @return
     */
    public synchronized boolean isStartWriteVideo() {
        return (bRecording && !needToFindKeyFrame);
    }

    /**
     * Adds a track with the specified format.
     *
     * @param format The media format for the track.
     * @return The track index for this newly added track.
     */
    public int addTrack(MediaFormat format) {
        if (format.getString(MediaFormat.KEY_MIME).contentEquals(VIDEO_MIME_TYPE)) {
            videoFormat = format;
            return VIDEO_TRACK;
        } else {
            audioFormat = format;
            return AUDIO_TRACK;
        }
    }

    /**
     * send the annexb frame to SRS over RTMP.
     *
     * @param trackIndex The track index for this sample.
     * @param byteBuf    The encoded sample.
     * @param bufferInfo The buffer information related to this sample.
     */
    public void writeSampleData(int trackIndex, ByteBuffer byteBuf, MediaCodec.BufferInfo bufferInfo) {
        if (VIDEO_TRACK == trackIndex) {
            writeVideoSample(byteBuf, bufferInfo);
        } else {
            writeAudioSample(byteBuf, bufferInfo);
        }
    }


    private void writeVideoSample(final ByteBuffer bb, MediaCodec.BufferInfo bi) {
        int nal_unit_type = bb.get(4) & 0x1f;
        if (nal_unit_type == SrsAvcNaluType.IDR || nal_unit_type == SrsAvcNaluType.NonIDR) {
            writeFrameByte(VIDEO_TRACK, bb, bi, nal_unit_type == SrsAvcNaluType.IDR);
        } else {
            while (bb.position() < bi.size) {
                SrsEsFrameBytes frame = avc.annexb_demux(bb, bi);

                if (avc.is_sps(frame)) {
                    if (!frame.data.equals(h264_sps)) {
                        byte[] sps = new byte[frame.size];
                        frame.data.get(sps);
                        h264_sps = ByteBuffer.wrap(sps);
                        spsList.clear();
                        spsList.add(sps);
                    }
                    continue;
                }

                if (avc.is_pps(frame)) {
                    if (!frame.data.equals(h264_pps)) {
                        byte[] pps = new byte[frame.size];
                        frame.data.get(pps);
                        h264_pps = ByteBuffer.wrap(pps);
                        ppsList.clear();
                        ppsList.add(pps);
                    }
                    continue;
                }
            }
        }
    }

    private void writeAudioSample(final ByteBuffer bb, MediaCodec.BufferInfo bi) {
        if (!aacSpecConfig) {
            aacSpecConfig = true;
        } else {
            writeFrameByte(AUDIO_TRACK, bb, bi, false);
        }
    }

    private void writeFrameByte(int track, ByteBuffer bb, MediaCodec.BufferInfo bi, boolean isKeyFrame) {
        SrsEsFrame frame = new SrsEsFrame();
        frame.bb = bb;
        frame.bi = bi;
        frame.isKeyFrame = isKeyFrame;
        frame.track = track;

        if (bRecording && !bPaused) {
            //check video keyframe
            if (frame.is_video(VIDEO_TRACK)) {
                if (frame.isKeyFrame) {
                    videoKeyframeCheckNum = 0;
                } else {
                    videoKeyframeCheckNum++;
                    if (videoKeyframeCheckNum == 64) {
                        videoKeyframeCheckNum = 100;
//                        if (mHandler != null) {
//                            mHandler.notifyRecordNoKeyframe();
//                        }
                    }
                }
            }

            if (needToFindKeyFrame) {
                if (frame.isKeyFrame) {
                    needToFindKeyFrame = false;
//                    if (mHandler != null) {
//                        mHandler.notifyRecordStartedWriteData();
//                    }
//                    RecordLog.i("key frame=========");
                    frameCache.add(frame);
                    synchronized (writeLock) {
                        writeLock.notifyAll();
                    }
                }
            } else {
                frameCache.add(frame);
                synchronized (writeLock) {
                    writeLock.notifyAll();
                }
            }
        }
    }


    private void writeSampleData(ByteBuffer byteBuf, MediaCodec.BufferInfo bi, boolean isAudio) {
//        int trackIndex = isAudio ? AUDIO_TRACK : VIDEO_TRACK;
//        if (!mp4Movie.getTracks().containsKey(trackIndex)) {
//            return;
//        }
//
//        try {
//            if (mdat.first) {
//                mdat.setContentSize(0);
//                mdat.getBox(fc);
//                mdatOffset = recFileSize;
//                recFileSize += mdat.getHeaderSize();
//                mdat.first = false;
//            }
//
//            mp4Movie.addSample(trackIndex, recFileSize, bi);
//            byteBuf.position(bi.offset + (isAudio ? 0 : 4));
//            byteBuf.limit(bi.offset + bi.size);
//            if (!isAudio) {
//                ByteBuffer size = ByteBuffer.allocateDirect(4);
//                size.position(0);
//                size.putInt(bi.size - 4);
//                size.position(0);
//                recFileSize += fc.write(size);
//            }
//            int writeBytes = fc.write(byteBuf);
//
//            recFileSize += writeBytes;
//            flushBytes += writeBytes;
//            if (flushBytes > 64 * 1024) {
//                fos.flush();
//                flushBytes = 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            mHandler.notifyRecordIOException(e);
//        }

//        int trackIndex = isAudio ? AUDIO_TRACK : VIDEO_TRACK;
        int trackIndex = isAudio ? mpAudioTrack : mpVideoTrack;
        try {
            mp4Builder.writeSampleData(trackIndex, byteBuf, bi, isAudio);
        } catch (Exception e) {
            e.printStackTrace();
//            mHandler.notifyRecordIOException(e);
        }
    }


}
