package com.roltekk.daydream.gameoflife;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public abstract class ThreadedView extends View implements Runnable {
    private static final String TAG = "ThreadedView";
    protected Thread mThread;
    private boolean mStopThread = false;
    private boolean mIsPaused = false;
    protected Paint mPaint;
    protected static int FRAME_TIME = 1000; // 1 frame per second
    public static int SLEEP_MIN_TIME = 200;
    public static int SLEEP_MAX_TIME = 500;
    // use for non-boring test
//    protected static int FRAME_TIME = 100;
//    public static int SLEEP_MIN_TIME = 20;
//    public static int SLEEP_MAX_TIME = 50;

    // animate callback
    private OnAnimateListener mAnimateCallback;
    public interface OnAnimateListener { public void OnAnimate(); }
    protected void setCallback(ThreadedView threadedView) { mAnimateCallback = (OnAnimateListener) threadedView; }

    public ThreadedView(Context context) {
        super(context);
        mPaint = new Paint();
        mThread = new Thread(this);
    }

    // start and stop thread
    public synchronized void StartViewThread() {
        mStopThread = false;
        if (!mThread.isAlive()) { mThread.start(); }
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
                if (Timer.GetElapsed() >= FRAME_TIME) {
                    Timer.Reset();
                    // animate
                    mAnimateCallback.OnAnimate();
                    // repaint
                    postInvalidate();
                }

                try {
                    // Sleep for a while
                    Thread.sleep(Math.max(SLEEP_MAX_TIME - Timer.GetElapsed(), SLEEP_MIN_TIME));
                } catch (java.lang.InterruptedException e) { e.printStackTrace(); }
            } catch (Exception e) { e.printStackTrace(); }
        }
        Log.d(TAG, mThread.getName() + " Stopped");
    }
}
