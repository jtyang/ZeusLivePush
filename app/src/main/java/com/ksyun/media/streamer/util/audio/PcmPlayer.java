package com.ksyun.media.streamer.util.audio;

import android.media.AudioTrack;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.ksyun.media.streamer.kit.StreamerConstants;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PcmPlayer {
    private static final int CMD_RELEASE = 4;
    private static final int CMD_STOP = 3;
    private static final int QUEUE_SIZE = 4;
    public static final String TAG = "PcmPlayer";
    private static final boolean VERBOSE = true;
    private BlockingQueue<short[]> mAudioQueue;
    private AudioTrack mAudioTrack;
    private int mChannel;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private int mSampleFormat;
    private int mSampleRate;

    /* renamed from: com.ksyun.media.streamer.util.audio.PcmPlayer.1 */
    class AnonymousClass1 extends Handler {
        final /* synthetic */ PcmPlayer a;

        AnonymousClass1(PcmPlayer pcmPlayer, Looper looper) {
            this.a = pcmPlayer;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case PcmPlayer.CMD_STOP /*3*/:
                    this.a.doStop();
                case PcmPlayer.QUEUE_SIZE /*4*/:
                    this.a.mHandlerThread.quit();
                default:
            }
        }
    }

    /* renamed from: com.ksyun.media.streamer.util.audio.PcmPlayer.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ short[] a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ PcmPlayer d;

        AnonymousClass2(PcmPlayer pcmPlayer, short[] sArr, int i, int i2) {
            this.d = pcmPlayer;
            this.a = sArr;
            this.b = i;
            this.c = i2;
        }

        public void run() {
            if (this.d.mAudioTrack == null) {
                this.d.mAudioTrack = new AudioTrack(PcmPlayer.CMD_STOP, this.d.mSampleRate, this.d.mChannel, this.d.mSampleFormat, AudioTrack.getMinBufferSize(this.d.mSampleRate, this.d.mChannel, this.d.mSampleFormat), 1);
                this.d.mAudioTrack.play();
            }
            this.d.mAudioTrack.write(this.a, this.b, this.c);
            this.d.mAudioQueue.offer(this.a);
        }
    }

    public PcmPlayer() {
        this.mSampleRate = StreamerConstants.DEFAULT_AUDIO_SAMPLE_RATE;
        this.mChannel = QUEUE_SIZE;
        this.mSampleFormat = 2;
        init();
    }

    public PcmPlayer(int i, int i2, int i3) {
        this.mSampleRate = StreamerConstants.DEFAULT_AUDIO_SAMPLE_RATE;
        this.mChannel = QUEUE_SIZE;
        this.mSampleFormat = 2;
        this.mSampleRate = i;
        this.mChannel = i2;
        this.mSampleFormat = i3;
        init();
    }

    private void init() {
        initThread();
        this.mAudioQueue = new ArrayBlockingQueue(QUEUE_SIZE);
        for (int i = 0; i < QUEUE_SIZE; i++) {
            this.mAudioQueue.offer(new short[2048]);
        }
    }

    private void initThread() {
        this.mHandlerThread = new HandlerThread("pcm_player_thread");
        this.mHandlerThread.start();
        this.mHandler = new AnonymousClass1(this, this.mHandlerThread.getLooper());
    }

    private void doStop() {
        if (this.mAudioTrack != null) {
            this.mAudioTrack.pause();
            this.mAudioTrack.flush();
            this.mAudioTrack.stop();
            this.mAudioTrack.release();
            this.mAudioTrack = null;
        }
    }

    private void playInternal(short[] sArr, int i, int i2) {
        this.mHandler.post(new AnonymousClass2(this, sArr, i, i2));
    }

    private short[] getPcmCache(int i) {
        short[] sArr = (short[]) this.mAudioQueue.poll();
        if (sArr == null) {
            Log.w(TAG, "Dropped " + (i * 2) + " bytes pcm data");
            return null;
        }
        int length = sArr.length;
        while (length < i) {
            length *= 2;
        }
        if (sArr.length >= length) {
            return sArr;
        }
        Log.d(TAG, "realloc pcm size from " + sArr.length + " to " + length);
        return new short[length];
    }

    public void play(short[] sArr, int i, int i2) {
        if (sArr != null && sArr.length >= i + i2) {
            Object pcmCache = getPcmCache(i2);
            if (pcmCache != null) {
                System.arraycopy(sArr, i, pcmCache, 0, i2);
                playInternal(pcmCache, 0, i2);
            }
        }
    }

    public void play(short[] sArr) {
        play(sArr, 0, sArr.length);
    }

    public void play(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            int limit = byteBuffer.limit() / 2;
            short[] pcmCache = getPcmCache(limit);
            if (pcmCache != null) {
                ShortBuffer asShortBuffer = byteBuffer.asShortBuffer();
                asShortBuffer.limit(limit);
                asShortBuffer.get(pcmCache, 0, limit);
                playInternal(pcmCache, 0, limit);
            }
        }
    }

    public void stop() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.sendEmptyMessage(CMD_STOP);
    }

    public void release() {
        if (this.mHandlerThread != null) {
            stop();
            this.mHandler.sendEmptyMessage(QUEUE_SIZE);
            try {
                this.mHandlerThread.join();
            } catch (InterruptedException e) {
                Log.d(TAG, "Pcm Player Thread Interrupted!");
            }
            this.mHandlerThread = null;
            this.mHandler = null;
        }
        this.mAudioQueue.clear();
    }
}
