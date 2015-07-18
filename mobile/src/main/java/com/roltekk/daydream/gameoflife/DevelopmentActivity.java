package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.roltekk.daydream.gameoflife.core.Config;
import com.roltekk.daydream.gameoflife.core.DishView;

public class DevelopmentActivity extends Activity {
    private static final String TAG            = "DevelopmentActivity";
    private static final int    REPOPULATE_MSG = 4158;
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

    // setup internal message handler used for repopullating the Dish
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REPOPULATE_MSG:
                    mDishView.randomPopulateDish();
                    Message nextMsg = mHandler.obtainMessage(REPOPULATE_MSG);
                    mHandler.sendMessageDelayed(nextMsg, Config.REPOPULATE_TIMEOUT);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mDishView = new DishView(this, mTestDishWidth, mTestDishHeight);
        mDishView.randomPopulateDish();
        setContentView(mDishView);

        // set initial timeout to repopulate dish
        Message msg = mHandler.obtainMessage(REPOPULATE_MSG);
        mHandler.sendMessageDelayed(msg, Config.REPOPULATE_TIMEOUT);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mDishView.StartViewThread();
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
