package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

public class DishView extends ThreadedView implements ThreadedView.OnAnimateListener {
    public Dish mDish;

    public DishView(Context context, int width, int height) {
        super(context);
        mThread.setName("generationThread");
        super.setCallback(this);
        mDish = new Dish(width, height, Color.parseColor("#FFFFFF"), Color.parseColor("#000000"));
    }

    @Override
    public void OnAnimate() {
        mDish.Animate();
    }

    protected void onDraw(Canvas canvas) {
        mDish.Draw(canvas);
    }
}
