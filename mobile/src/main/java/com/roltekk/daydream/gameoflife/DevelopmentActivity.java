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
                    mHandler.sendMessageDelayed(nextMsg, Config.getRepopulationTimeout(DevelopmentActivity.this));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDishView = new DishView(this, false);
        mDishView.randomPopulateDish();
        setContentView(mDishView);
        // set initial timeout to repopulate dish
        Message msg = mHandler.obtainMessage(REPOPULATE_MSG);
        mHandler.sendMessageDelayed(msg, Config.getRepopulationTimeout(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
