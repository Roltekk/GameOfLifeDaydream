package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.roltekk.daydream.gameoflife.core.DishView;

public class DevelopmentActivity extends Activity {
    private static final String TAG = "DevelopmentActivity";
    private DishView mDishView;
    private int mTestDishWidth  = 18; // 1080 / 60
    private int mTestDishHeight = 32; // 1920 / 60

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
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
