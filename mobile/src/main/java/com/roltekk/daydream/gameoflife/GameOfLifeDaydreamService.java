package com.roltekk.daydream.gameoflife;

import android.service.dreams.DreamService;
import android.util.Log;

public class GameOfLifeDaydreamService extends DreamService {
    private static final String TAG = "GOLDS";

    @Override
    public void onDreamingStarted() {
        Log.d(TAG, "onDreamingStarted");

    }

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();

        setInteractive(false);
        setFullscreen(true);
        setContentView(R.layout.blank_layout);
    }
}
