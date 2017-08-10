package com.ksyun.media.streamer.framework;

import java.nio.ByteBuffer;

public class AVBufFrame extends AVFrameBase {
    public long avPacketOpaque;
    public ByteBuffer buf;
}
