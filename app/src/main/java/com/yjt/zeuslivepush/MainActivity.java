package com.yjt.zeuslivepush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yjt.zeuslivepush.live.ZeusLiveManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ZeusLiveManager zeusLiveManager;
    SurfaceView _CameraSurface;
    Surface mPreviewSurface;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zeusLiveManager = new ZeusLiveManager();
        zeusLiveManager.init(this);


        String pushUrl = "rtmp://";
        zeusLiveManager.startRecord(pushUrl);

        _CameraSurface = (SurfaceView) findViewById(R.id.camera_surface);
        _CameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.e("camera","surfacwcreated");
                holder.setKeepScreenOn(true);
                mPreviewSurface = holder.getSurface();

                Map<String, Object> mConfigure = new HashMap<>();
                zeusLiveManager.prepare(mConfigure, mPreviewSurface);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                zeusLiveManager.setPreviewSize(width,height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mPreviewSurface = null;
                zeusLiveManager.stopRecord();
                zeusLiveManager.reset();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zeusLiveManager.release();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
