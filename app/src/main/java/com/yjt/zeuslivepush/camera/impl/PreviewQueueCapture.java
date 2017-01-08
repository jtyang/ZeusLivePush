package com.yjt.zeuslivepush.camera.impl;

import android.hardware.Camera;
import android.util.Log;

import com.yjt.zeuslivepush.buffer.ByteArrayHolder;
import com.yjt.zeuslivepush.camera.CaptureOutput;
import com.yjt.zeuslivepush.camera.PreviewQueue;
import com.yjt.zeuslivepush.camera.SessionRequest;

import java.util.ArrayDeque;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class PreviewQueueCapture extends CaptureOutput implements Camera.PreviewCallback, PreviewQueue.OnBufferAvailableListener {
    private PreviewQueue _PreviewQueue;
    private SessionRequest mConfig;
    private ArrayDeque<ByteArrayHolder> _Queue = new ArrayDeque();

    public PreviewQueueCapture(Camera camera, PreviewQueue preview_queue, SessionRequest config) {
        super(camera);
        this._PreviewQueue = preview_queue;
        this.mConfig = config;
    }

    public boolean configure() {
        this.mCamera.setPreviewCallbackWithBuffer(this);
        this._PreviewQueue.setBuffersGeometry(this.mConfig.previewWidth, this.mConfig.previewHeight, this.mConfig.previewFormat);
        this._PreviewQueue.setOnBufferAvailableListener(this);
        this.fillPreviewQueue();
        return true;
    }

    public boolean close() {
        this._PreviewQueue.setOnBufferAvailableListener((PreviewQueue.OnBufferAvailableListener) null);
        this.mCamera.setPreviewCallbackWithBuffer((Camera.PreviewCallback) null);
        this.flushPreviewQueue();
        return true;
    }

    public void onPreviewFrame(byte[] bytes, Camera camera) {
        ByteArrayHolder first = (ByteArrayHolder) this._Queue.getFirst();
        if (first.getByteArray() != bytes) {
            Log.w(TAG, bytes + " does not map to current sample, ignored");
        } else {
            this._Queue.removeFirst();
            this._PreviewQueue.enqueueBuffer(first);
            this.fillPreviewQueue();
        }
    }

    private void fillPreviewQueue() {
        while (true) {
            ByteArrayHolder holder = this._PreviewQueue.dequeueBuffer();
            if (holder == null) {
                return;
            }

            this._Queue.addLast(holder);
            this.mCamera.addCallbackBuffer(holder.getByteArray());
        }
    }

    public void onBufferAvailable(PreviewQueue queue) {
        this.fillPreviewQueue();
    }

    private void flushPreviewQueue() {
        for (ByteArrayHolder holder : this._Queue) {
            this._PreviewQueue.cancelBuffer(holder);
        }
        this._Queue.clear();
    }
}
