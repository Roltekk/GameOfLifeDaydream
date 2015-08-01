package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.os.Bundle;

import com.roltekk.daydream.gameoflife.core.DishView;

public class TestSettingsActivity extends Activity {
    private DishView mDishView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDishView = new DishView(this, true);
        mDishView.randomPopulateDish();
        setContentView(mDishView);
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
    }
}
