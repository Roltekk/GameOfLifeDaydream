package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.graphics.Canvas;

public class DishView extends ThreadedView implements ThreadedView.OnAnimateListener {
    private Dish             mDish;
    private int[]            mCellCount;
    private int              mCellWidth, mCellHeight;
    private static final int DISH_WIDTH_INDEX  = 0;
    private static final int DISH_HEIGHT_INDEX = 1;

    public DishView(Context context, boolean testSettings) {
        super(context);
        mThread.setName("generationThread");
        super.setCallback(this);
        if (testSettings) {
            setFrameTime(Config.getTestFrameTime(context));
            mCellCount = Config.getTestCellCount(context);
            mDish = new Dish(mCellCount[0], mCellCount[1], Config.getTestColourDead(context), Config.getTestColourAlive(context));
        } else {
            setFrameTime(Config.getFrameTime(context));
            mCellCount = Config.getCellCount(context);
            mDish = new Dish(mCellCount[0], mCellCount[1], Config.getColourDead(context), Config.getColourAlive(context));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > h) {
            mCellWidth = h / mCellCount[DISH_WIDTH_INDEX];
            mCellHeight = w / mCellCount[DISH_HEIGHT_INDEX];
            mDish.setDrawMode(false);
        } else {
            mCellWidth = w / mCellCount[DISH_WIDTH_INDEX];
            mCellHeight = h / mCellCount[DISH_HEIGHT_INDEX];
            mDish.setDrawMode(true);
        }
        mDish.setCellDimensions(mCellWidth, mCellHeight);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void OnAnimate() {
        mDish.calcNextGeneration();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDish.Draw(canvas);
    }

    public void randomPopulateDish() {
        mDish.randomPopulateDish();
    }
}
