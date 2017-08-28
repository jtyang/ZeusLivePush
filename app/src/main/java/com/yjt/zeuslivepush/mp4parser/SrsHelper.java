package com.yjt.zeuslivepush.mp4parser;

import android.media.MediaCodec;
import android.util.Log;

import java.nio.ByteBuffer;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/8/25
 */
public class SrsHelper {
    private static final String TAG = "SrsHelper";
    /**
     * Table 7-1 – NAL unit type codes, syntax element categories, and NAL unit type classes
     * H.264-AVC-ISO_IEC_14496-10-2012.pdf, page 83.
     */
    public class SrsAvcNaluType {
        // Unspecified
        public final static int Reserved = 0;

        // Coded slice of a non-IDR picture slice_layer_without_partitioning_rbsp( )
        public final static int NonIDR = 1;
        // Coded slice data partition A slice_data_partition_a_layer_rbsp( )
        public final static int DataPartitionA = 2;
        // Coded slice data partition B slice_data_partition_b_layer_rbsp( )
        public final static int DataPartitionB = 3;
        // Coded slice data partition C slice_data_partition_c_layer_rbsp( )
        public final static int DataPartitionC = 4;
        // Coded slice of an IDR picture slice_layer_without_partitioning_rbsp( )
        public final static int IDR = 5;
        // Supplemental enhancement information (SEI) sei_rbsp( )
        public final static int SEI = 6;
        // Sequence parameter set seq_parameter_set_rbsp( )
        public final static int SPS = 7;
        // Picture parameter set pic_parameter_set_rbsp( )
        public final static int PPS = 8;
        // Access unit delimiter access_unit_delimiter_rbsp( )
        public final static int AccessUnitDelimiter = 9;
        // End of sequence end_of_seq_rbsp( )
        public final static int EOSequence = 10;
        // End of stream end_of_stream_rbsp( )
        public final static int EOStream = 11;
        // Filler data filler_data_rbsp( )
        public final static int FilterData = 12;
        // Sequence parameter set extension seq_parameter_set_extension_rbsp( )
        public final static int SPSExt = 13;
        // Prefix NAL unit prefix_nal_unit_rbsp( )
        public final static int PrefixNALU = 14;
        // Subset sequence parameter set subset_seq_parameter_set_rbsp( )
        public final static int SubsetSPS = 15;
        // Coded slice of an auxiliary coded picture without partitioning slice_layer_without_partitioning_rbsp( )
        public final static int LayerWithoutPartition = 19;
        // Coded slice extension slice_layer_extension_rbsp( )
        public final static int CodedSliceExt = 20;
    }


    /**
     * the search result for annexb.
     */
    public class SrsAnnexbSearch {
        public int nb_start_code = 0;
        public boolean match = false;
    }

    /**
     * the demuxed tag frame.
     */
    public class SrsEsFrameBytes {
        public ByteBuffer data;
        public int size;
    }

    /**
     * the AV frame.
     */
    public static class SrsEsFrame {
        public ByteBuffer bb;
        public MediaCodec.BufferInfo bi;
        public int track;
        public boolean isKeyFrame;

        public boolean is_video(int VIDEO_TRACK) {
            return track == VIDEO_TRACK;
        }

        public boolean is_audio(int AUDIO_TRACK) {
            return track == AUDIO_TRACK;
        }
    }

    /**
     * the raw h.264 stream, in annexb.
     */
    public static class SrsRawH264Stream {
        public boolean is_sps(SrsEsFrameBytes frame) {
            if (frame.size < 1) {
                return false;
            }

            return (frame.data.get(0) & 0x1f) == SrsAvcNaluType.SPS;
        }

        public boolean is_pps(SrsEsFrameBytes frame) {
            if (frame.size < 1) {
                return false;
            }
            return (frame.data.get(0) & 0x1f) == SrsAvcNaluType.PPS;
        }

        public SrsAnnexbSearch srs_avc_startswith_annexb(ByteBuffer bb, MediaCodec.BufferInfo bi) {
            SrsAnnexbSearch as = new SrsAnnexbSearch();
            as.match = false;

            int pos = bb.position();
            while (pos < bi.size - 3) {
                // not match.
                if (bb.get(pos) != 0x00 || bb.get(pos + 1) != 0x00) {
                    break;
                }

                // match N[00] 00 00 01, where N>=0
                if (bb.get(pos + 2) == 0x01) {
                    as.match = true;
                    as.nb_start_code = pos + 3 - bb.position();
                    break;
                }

                pos++;
            }

            return as;
        }

        public SrsEsFrameBytes annexb_demux(ByteBuffer bb, MediaCodec.BufferInfo bi) {
            SrsEsFrameBytes tbb = new SrsEsFrameBytes();

            while (bb.position() < bi.size) {
                // each frame must prefixed by annexb format.
                // about annexb, @see H.264-AVC-ISO_IEC_14496-10.pdf, page 211.
                SrsAnnexbSearch tbbsc = srs_avc_startswith_annexb(bb, bi);
                if (!tbbsc.match || tbbsc.nb_start_code < 3) {
                    Log.e(TAG, "annexb not match.");
//                    mHandler.notifyRecordIllegalArgumentException(new IllegalArgumentException(
//                            String.format("annexb not match for %dB, pos=%d", bi.size, bb.position())));
                }

                // the start codes.
                ByteBuffer tbbs = bb.slice();
                for (int i = 0; i < tbbsc.nb_start_code; i++) {
                    bb.get();
                }

                // find out the frame size.
                tbb.data = bb.slice();
                int pos = bb.position();
                while (bb.position() < bi.size) {
                    SrsAnnexbSearch bsc = srs_avc_startswith_annexb(bb, bi);
                    if (bsc.match) {
                        break;
                    }
                    bb.get();
                }

                tbb.size = bb.position() - pos;
                break;
            }

            return tbb;
        }
    }
}
