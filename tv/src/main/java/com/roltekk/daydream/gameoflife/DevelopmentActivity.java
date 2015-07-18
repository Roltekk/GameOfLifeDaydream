package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.roltekk.daydream.gameoflife.core.DishView;

public class DevelopmentActivity extends Activity {
    private static final String TAG = "DevelopmentActivity";
    private DishView mDishView;

    // 144 cells (lowest possible for these dimensions)
//    private int mTestDishWidth = 16; // 1920 / 120px
//    private int mTestDishHeight  = 9; // 1080 / 120px

    // 576 cells (low cpu usage)
//    private int mTestDishWidth = 32; // 1920 / 60px
//    private int mTestDishHeight  = 18; // 1080 / 60px

//    // 2,304 cells
//    private int mTestDishWidth  = 64; // 1920 / 30px
//    private int mTestDishHeight = 36; // 1080 / 30px

    // 5,184 cells (good cpu usage, probably best upper limit)
    private int mTestDishWidth  = 96; // 1920 / 20px
    private int mTestDishHeight = 54; // 1080 / 20px

    // 9,216 cells (cpu usage getting a bit spiky at start)
//    private int mTestDishWidth  = 128; // 1920 / 15px
//    private int mTestDishHeight = 72; // 1080 / 15px

    // 20,736 cells (good upper limit, cpu usage spiky throughout)
//    private int mTestDishWidth = 192; // 1920 / 10px
//    private int mTestDishHeight  = 108; // 1080 / 10px

    // 82,944 cells (upper limit, probably too much)
//    private int mTestDishWidth = 384; // 1920 / 5px
//    private int mTestDishHeight  = 216; // 1080 / 5px

    // 2,073,600 cells (too much)
//    private int mTestDishWidth = 1920;
//    private int mTestDishHeight  = 1080;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mDishView = new DishView(this, false);
        setContentView(mDishView);
        mDishView.StartViewThread();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mDishView.Resume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        mDishView.Pause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mDishView.requestStop();
    }
}
