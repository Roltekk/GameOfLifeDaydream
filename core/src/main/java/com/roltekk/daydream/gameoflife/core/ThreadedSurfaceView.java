package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class ThreadedSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private static final String TAG = "TSView";
    protected Thread            mThread;
    private boolean             mStopThread = false;
    private boolean             mIsPaused   = false;
    protected Paint             mPaint;
    private   long              mFrameTime, mSleepMinTime, mSleepMaxTime;
    private final float SLEEP_MIN_TIME_RATIO = 0.2f;
    private final float SLEEP_MAX_TIME_RATIO = 0.5f;
    protected SurfaceHolder mSurfaceHolder;

    // animate and draw callbacks
    private OnAnimateListener mAnimateCallback;
    private OnDrawListener    mDrawCallback;

    public interface OnAnimateListener { public void OnAnimate(); }
    public interface OnDrawListener { public void OnDraw(); }
    protected void setCallback(ThreadedSurfaceView threadedSurfaceView) {
        mAnimateCallback = (OnAnimateListener) threadedSurfaceView;
        mDrawCallback = (OnDrawListener) threadedSurfaceView;
    }

    public ThreadedSurfaceView(Context context) {
        super(context);
        mPaint = new Paint();
        mThread = new Thread(this);
        mSurfaceHolder = getHolder();
    }

    protected void setFrameTime(long frameTime) {
        mFrameTime = frameTime;
        mSleepMinTime = (long) (mFrameTime * SLEEP_MIN_TIME_RATIO);
        mSleepMaxTime = (long) (mFrameTime * SLEEP_MAX_TIME_RATIO);
    }

    // start and stop thread
    public synchronized void StartViewThread() {
        mStopThread = false;
        if (!mThread.isAlive()) {
            mThread.start();
        }
    }

    public synchronized void requestStop() { mStopThread = true; }

    // Pausing and resuming thread
    public void Pause() { mIsPaused = true; }
    public synchronized void Resume() { notify(); }

    @Override
    public void run() {
        Log.d(TAG, "run");
        Timer.Start();
        while (!mStopThread) {
            // pausing section
            if (mIsPaused) {
                synchronized (this) {
                    // wait for Resume() to be called
                    try {
                        wait();
                        mIsPaused = false;
                        // reset the timer to try to stop game objects from moving when paused
                        Timer.Reset();
                    } catch (InterruptedException e) { e.printStackTrace(); }
                }
            }

            // working section
            try {
                Timer.CalcElapsed();
                if (Timer.GetElapsed() >= mFrameTime) {
                    Timer.Reset();
                    // animate
                    mAnimateCallback.OnAnimate();
                    // repaint
                    mDrawCallback.OnDraw();
                }

                try {
                    // Sleep for a while
                    Thread.sleep(Math.max(mSleepMaxTime - Timer.GetElapsed(), mSleepMinTime));
                } catch (InterruptedException e) { e.printStackTrace(); }
            } catch (Exception e) { e.printStackTrace(); }
        }
        Log.d(TAG, mThread.getName() + " Stopped");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
    }
}
