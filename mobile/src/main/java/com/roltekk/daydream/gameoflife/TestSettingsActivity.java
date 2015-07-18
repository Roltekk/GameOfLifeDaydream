package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.roltekk.daydream.gameoflife.core.DishView;

public class TestSettingsActivity extends Activity {
    private static final String TAG = "TestSettingsActivity";
    private int mScreenWidth;
    private int mScreenHeight;

    private DishView mDishView;
    private int mTestDishWidth  = 18; // 1080 / 60px
    private int mTestDishHeight = 32; // 1920 / 60px

    // upper limit test
//    private int mTestDishWidth  = 54; // 1080 / 20px
//    private int mTestDishHeight = 96; // 1920 / 20px

    // lower limit test
//    private int mTestDishWidth  = 9; // 1080 / 120px
//    private int mTestDishHeight = 16; // 1920 / 120px

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mDishView = new DishView(this, mTestDishWidth, mTestDishHeight);
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
