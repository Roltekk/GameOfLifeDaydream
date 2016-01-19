package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.roltekk.daydream.gameoflife.core.Config;
import com.roltekk.daydream.gameoflife.core.DishView;

public class TestSettingsActivity extends Activity {
    private static final String TAG = "TSA";
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
                    mHandler.sendMessageDelayed(nextMsg, Config.getRepopulationTimeout(TestSettingsActivity.this));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDishView = new DishView(this, true);
        mDishView.randomPopulateDish();
        setContentView(mDishView);
        // set initial timeout to repopulate dish
        Message msg = mHandler.obtainMessage(REPOPULATE_MSG);
        mHandler.sendMessageDelayed(msg, Config.getTestRepopulationTimeout(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        mHandler.removeMessages(REPOPULATE_MSG);
    }
}
