package com.roltekk.daydream.gameoflife.core;

public class Timer {
    private static long time1, time2, elapsed = 0;
    private static float secPerFrame = 0.0f;

    // unctor
    private Timer() {}

    public static synchronized void Start() {
        time1 = System.currentTimeMillis();
    }

    public static synchronized void Stop() {
        ;
    }

    public static synchronized void Reset() {
        time1 = System.currentTimeMillis();
    }

    public static synchronized void CalcElapsed() {
        time2 = System.currentTimeMillis();
        elapsed = time2 - time1;
        secPerFrame = (float) elapsed * 0.001f;
    }

    public static synchronized long GetElapsed() {
        return elapsed;
    }

    public static synchronized float GetSecPerFrame() {
        return secPerFrame;
    }
}
