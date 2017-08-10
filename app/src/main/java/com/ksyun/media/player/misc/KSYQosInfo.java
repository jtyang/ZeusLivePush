package com.ksyun.media.player.misc;

public class KSYQosInfo {
    public static final String AUDIO_BUFFER_BYTE = "audio_buffer_byte";
    public static final String AUDIO_BUFFER_TIME = "audio_buffer_time";
    public static final String AUDIO_TOTAL_DATA_SIZE = "audio_total_data_size";
    public static final String TOTAL_DATA_BYTES = "total_data_bytes";
    public static final String VIDEO_BUFFER_BYTE = "video_buffer_byte";
    public static final String VIDEO_BUFFER_TIME = "video_buffer_time";
    public static final String VIDEO_TOTAL_DATA_SIZE = "video_total_data_size";
    public int audioBufferByteLength;
    public int audioBufferTimeLength;
    public long audioTotalDataSize;
    public long totalDataSize;
    public int videoBufferByteLength;
    public int videoBufferTimeLength;
    public long videoTotalDataSize;

    public KSYQosInfo() {
        this.audioBufferByteLength = 0;
        this.audioBufferTimeLength = 0;
        this.audioTotalDataSize = 0;
        this.videoBufferByteLength = 0;
        this.videoBufferTimeLength = 0;
        this.videoTotalDataSize = 0;
        this.totalDataSize = 0;
    }
}
