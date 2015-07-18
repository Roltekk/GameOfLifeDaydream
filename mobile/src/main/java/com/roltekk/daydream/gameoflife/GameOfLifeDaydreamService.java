package com.roltekk.daydream.gameoflife;

import android.os.Handler;
import android.os.Message;
import android.service.dreams.DreamService;
import android.util.Log;

import com.roltekk.daydream.gameoflife.core.Config;
import com.roltekk.daydream.gameoflife.core.DishView;

public class GameOfLifeDaydreamService extends DreamService {
    private static final String TAG = "GOLDS";
    private static final int    REPOPULATE_MSG = 4158;
    private DishView            mDishView;

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

    @Override
    public void onDreamingStarted() {
        Log.d(TAG, "onDreamingStarted");
        super.onDreamingStarted();
        mDishView.StartViewThread();
        mDishView.Resume();
        // set initial timeout to repopulate dish
        Message msg = mHandler.obtainMessage(REPOPULATE_MSG);
        mHandler.sendMessageDelayed(msg, Config.REPOPULATE_TIMEOUT);
    }

    @Override
    public void onDreamingStopped() {
        Log.d(TAG, "onDreamingStopped");
        super.onDreamingStopped();
        mDishView.requestStop();
    }

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();

        setInteractive(false);
        setFullscreen(true);
        mDishView = new DishView(this, false);
        mDishView.randomPopulateDish();
        setContentView(mDishView);
    }
}
