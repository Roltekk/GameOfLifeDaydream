package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.graphics.Canvas;

public class DishView extends ThreadedView implements ThreadedView.OnAnimateListener {
    public Dish mDish;

    public DishView(Context context, int width, int height) {
        super(context);
        mThread.setName("generationThread");
        super.setCallback(this);
        mDish = new Dish(width, height, Config.getColourDead(this.getContext()), Config.getColourAlive(this.getContext()));
    }

    @Override
    public void OnAnimate() {
        mDish.calcNextGeneration();
    }

    protected void onDraw(Canvas canvas) {
        mDish.Draw(canvas);
    }
}
