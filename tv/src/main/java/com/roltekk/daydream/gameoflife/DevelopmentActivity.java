package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.roltekk.daydream.gameoflife.core.Config;
import com.roltekk.daydream.gameoflife.core.DishView;

public class DevelopmentActivity extends Activity {
    private static final int    REPOPULATE_MSG = 4158;
    private DishView mDishView;

    // setup internal message handler used for repopulating the Dish
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
//    private int mTestDishWidth  = 96; // 1920 / 20px
//    private int mTestDishHeight = 54; // 1080 / 20px

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
        super.onCreate(savedInstanceState);
        mDishView = new DishView(this, false);
        mDishView.randomPopulateDish();
        setContentView(mDishView);
        // set initial timeout to repopulate dish
        Message msg = mHandler.obtainMessage(REPOPULATE_MSG);
        mHandler.sendMessageDelayed(msg, Config.REPOPULATE_TIMEOUT);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mDishView.StartViewThread();
        mDishView.Resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDishView.Pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDishView.requestStop();
    }
}
//
//package com.roltekk.daydream.gameoflife;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//
//import com.roltekk.daydream.gameoflife.core.Config;
//import com.roltekk.daydream.gameoflife.core.DishView;
//
//public class DevelopmentActivity extends Activity {
//    private static final int    REPOPULATE_MSG = 4158;
//    private DishView mDishView;
//
//    // setup internal message handler used for repopulating the Dish
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case REPOPULATE_MSG:
//                    mDishView.randomPopulateDish();
//                    Message nextMsg = mHandler.obtainMessage(REPOPULATE_MSG);
//                    mHandler.sendMessageDelayed(nextMsg, Config.REPOPULATE_TIMEOUT);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mDishView = new DishView(this, false);
//        mDishView.randomPopulateDish();
//        setContentView(mDishView);
//        // set initial timeout to repopulate dish
//        Message msg = mHandler.obtainMessage(REPOPULATE_MSG);
//        mHandler.sendMessageDelayed(msg, Config.REPOPULATE_TIMEOUT);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        mDishView.StartViewThread();
//        mDishView.Resume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mDishView.Pause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mDishView.requestStop();
//    }
//}

