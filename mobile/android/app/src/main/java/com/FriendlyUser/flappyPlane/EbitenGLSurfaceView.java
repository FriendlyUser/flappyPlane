package com.FriendlyUser.flappyPlane;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.FriendlyUser.flappyPlane.mobile.*;

// added classes

import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class EbitenGLSurfaceView extends GLSurfaceView {

    private class EbitenRenderer implements Renderer {

        private boolean mErrored;

        @Override
        public void onDrawFrame(GL10 gl) {
            if (mErrored) {
                return;
            }
            try {
                Mobile.update();
            } catch (Exception e) {
                Log.e("Go Error", e.toString());
                mErrored = true;
            }
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
        }
    }

    private double mDeviceScale = 0.0;

    public EbitenGLSurfaceView(Context context) {
        super(context);
        initialize();
    }

    public EbitenGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        setRenderer(new EbitenRenderer());
    }

    private double pxToDp(double x) {
        if (mDeviceScale == 0.0) {
            mDeviceScale = getResources().getDisplayMetrics().density;
        }
        return x / mDeviceScale;
    }

    public double getScaleInPx() {
        View parent = (View)getParent();
        return Math.max(1,
                Math.min(parent.getWidth() / (double)Mobile.ScreenWidth,
                        parent.getHeight() / (double)Mobile.ScreenHeight));
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int oldWidth = getLayoutParams().width;
        int oldHeight = getLayoutParams().height;
        double scaleInPx = getScaleInPx();
        int newWidth = (int)(Mobile.ScreenWidth * scaleInPx);
        int newHeight = (int)(Mobile.ScreenHeight * scaleInPx);
        if (oldWidth != newWidth || oldHeight != newHeight) {
            getLayoutParams().width = newWidth;
            getLayoutParams().height = newHeight;
            post(new Runnable() {
                @Override
                public void run() {
                    requestLayout();
                }
            });
        }
        try {
            if (!Mobile.isRunning()) {
                Configuration configuration = getResources().getConfiguration();
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                // px= dp*(dpi/160)
                // compute dpi
                int densityDpi = (int)(metrics.density * 160f);
                int screenWidthDp = configuration.screenWidthDp;
                int screenHeightDp = configuration.screenHeightDp;
                int screenWidth = (int) densityDpi*screenWidthDp / 160;
                int screenHeight = (int) densityDpi*screenHeightDp / 160;
                // int smallestScreenWidthDp = configuration.smallestScreenWidthDp;
                //Mobile.start(pxToDp(getScaleInPx()), screenWidth, screenHeight);
                Mobile.start(pxToDp(getScaleInPx()));
            }
        } catch (Exception e) {
            Log.e("Go Error", e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        for (int i = 0; i < e.getPointerCount(); i++) {
            int id = e.getPointerId(i);
            int x = (int)e.getX(i);
            int y = (int)e.getY(i);
            Mobile.updateTouchesOnAndroid(e.getActionMasked(), id, (int)pxToDp(x), (int)pxToDp(y));
        }
        return true;
    }
}