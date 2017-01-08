package com.yjt.zeuslivepush.media;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.yjt.zeuslivepush.annotations.GuardedBy;
import com.yjt.zeuslivepush.utils.Assert;
import com.yjt.zeuslivepush.utils.ThreadUtil;


/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public abstract class ACaptureDevice {
    public static final int EVENT_SETUP = 1;
    public static final int EVENT_CREATE = 2;
    public static final int EVENT_START = 3;
    public static final int EVENT_STOP = 5;
    public static final int EVENT_DESTROY = 6;
    public static final int EVENT_ERROR = 7;
    private static final String TAG = "ACaptureDevice";
    private final HandlerThread _Thread;
    private final Handler _Handler;
    private Callback _Callback;


    public ACaptureDevice(Looper looper, String name) {
        if (name == null) {
            name = getClass().getSimpleName();
            if (name == null) {
                name = getClass().getName();
            }
            name = name + "@" + System.identityHashCode(this);
        }

        if (looper == null) {
            this._Thread = new HandlerThread(name);
            this._Thread.start();
            looper = this._Thread.getLooper();
        } else {
            this._Thread = null;
        }

        this._Handler = new Handler(looper, this._HandlerCallback);
        this._Handler.sendEmptyMessage(1);
    }


    public final Looper getLooper() {
        return this._Handler.getLooper();

    }

    public final void setCallback(Callback callback) {
        this._Callback = callback;

    }

    private static enum State {
        IDLE,
        CREATED,
        STARTED;
    }


    @GuardedBy("_HandlerCallback")
    private State _RequestedState = State.IDLE;
    @GuardedBy("_HandlerCallback")
    private State _CurrentState = State.IDLE;

    private boolean _StateChanging = false;
    private static final int WHAT_STATE_CHANGE_REQUEST = 0;

    private void requestStateChange_l() {
        if ((this._CurrentState != this._RequestedState) && (!this._Handler.hasMessages(0))) {
            sendMessage(0);
        }
    }

    public boolean create() {
        Assert.assertEquals(State.IDLE, this._RequestedState);
        synchronized (this._HandlerCallback) {
            this._RequestedState = State.CREATED;
            requestStateChange_l();
        }
        return true;
    }

    public boolean start() {
        Assert.assertEquals(State.CREATED, this._RequestedState);
        synchronized (this._HandlerCallback) {
            this._RequestedState = State.STARTED;
            requestStateChange_l();
        }
        return true;
    }

    public void stop() {
        Assert.assertEquals(State.STARTED, this._RequestedState);
        synchronized (this._HandlerCallback) {
            this._RequestedState = State.CREATED;
            requestStateChange_l();
            while (((this._CurrentState != State.CREATED) && (this._CurrentState != State.IDLE)) || (this._StateChanging)) {
                ThreadUtil.wait(this._HandlerCallback);
            }
        }
    }

    public void destroy() {
        Assert.assertEquals(State.CREATED, this._RequestedState);
        synchronized (this._HandlerCallback) {
            this._RequestedState = State.IDLE;
            requestStateChange_l();
            while ((this._CurrentState != State.IDLE) || (this._StateChanging)) {
                ThreadUtil.wait(this._HandlerCallback);
            }
        }
    }

    public void release() {
        Assert.assertEquals(State.IDLE, this._CurrentState);
        Assert.assertEquals(State.IDLE, this._RequestedState);
        if (this._Thread == null) {
            return;
        }
        this._Handler.sendEmptyMessage(5);
        ThreadUtil.join(this._Thread);
    }


    protected void notifyConfigurationChange() {
        if (!this._Handler.hasMessages(4)) {
            this._Handler.sendEmptyMessage(4);
        }
    }


    protected abstract boolean onSetup();

    protected abstract boolean onCreate();

    protected abstract boolean onStart();

    protected abstract void onStop();

    protected abstract void onDestroy();

    private boolean doCreate() {
        if (!onSetup()) {
            return false;
        }
        if (this._Callback != null) {
            this._Callback.onCallback(this, 1);
        }
        boolean result = onCreate();
        if ((result) &&
                (this._Callback != null)) {
            this._Callback.onCallback(this, 2);
        }
        return result;
    }


    private boolean doStart() {
        boolean result = onStart();
        if ((result) &&
                (this._Callback != null)) {
            this._Callback.onCallback(this, 3);
        }
        return result;
    }

    private void doStop() {
        onStop();
        if (this._Callback != null) {
            this._Callback.onCallback(this, 5);
        }
    }

    private void doDestroy() {
        onDestroy();
        if (this._Callback != null) {
            this._Callback.onCallback(this, 6);
        }
    }

    protected void onConfigurationChange() {
        beginStateChange();
        switch (this._CurrentState) {
            case IDLE:
                break;
            case CREATED:
                doDestroy();
                break;
            case STARTED:
                doStop();
                doDestroy();
        }
        endStateChange(State.IDLE);
    }


    private State beginStateChange() {
        synchronized (this._HandlerCallback) {
            Assert.assertFalse(Boolean.valueOf(this._StateChanging));
            this._StateChanging = true;
            return this._RequestedState;
        }
    }

    private void endStateChange(State state) {
        synchronized (this._HandlerCallback) {
            Assert.assertTrue(Boolean.valueOf(this._StateChanging));
            this._StateChanging = false;
            if (state == null) {
                Log.w("ACaptureDevice", "state not changed: " + this._CurrentState);
            } else {
                this._CurrentState = state;
                requestStateChange_l();
            }
            this._HandlerCallback.notifyAll();
        }
    }


    private void onStateChangeRequest() {
        State requested_state = beginStateChange();
        State state = null;
        switch (requested_state) {
            case IDLE:
                switch (this._CurrentState) {
                    case IDLE:
                        break;
                    case CREATED:
                        onDestroy();
                        state = State.IDLE;
                        break;
                    case STARTED:
                        onStop();
                        state = State.CREATED;
                }
                break;
            case CREATED:
                switch (this._CurrentState) {
                    case IDLE:
                        if (doCreate()) {
                            state = State.CREATED;
                        }
                        break;
                    case CREATED:
                        break;
                    case STARTED:
                        doStop();
                        state = State.CREATED;
                        break;
                }
                break;
            case STARTED:
                switch (this._CurrentState) {
                    case IDLE:
                        if (doCreate()) {
                            state = State.CREATED;
                        }
                        break;
                    case CREATED:
                        if (doStart()) {
                            state = State.STARTED;
                        }
                        break;
                }
                break;

        }
        endStateChange(state);
    }

    private void sendMessage(int what) {
        this._Handler.sendEmptyMessage(what);
    }

    private static final int WHAT_INIT = 1;
    private static final int WHAT_CONFIGURATION_CHANGE = 4;
    private static final int WHAT_RELEASE = 5;
    private final Handler.Callback _HandlerCallback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_INIT:
                    Process.setThreadPriority(-1);
                    break;
                case 0:
                    ACaptureDevice.this.onStateChangeRequest();
                    break;
                case WHAT_CONFIGURATION_CHANGE:
                    ACaptureDevice.this.onConfigurationChange();
                    break;
                case WHAT_RELEASE:
                    ACaptureDevice.this._Thread.quit();
                    break;
            }
            return true;
        }
    };

    public interface Callback {
        void onCallback(ACaptureDevice paramACaptureDevice, int paramInt);
    }
}
