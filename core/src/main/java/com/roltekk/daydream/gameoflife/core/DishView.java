package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class DishView extends ThreadedSurfaceView implements ThreadedSurfaceView.OnAnimateListener, ThreadedSurfaceView.OnDrawListener {
    private Dish             mDish;
    private int[]            mCellCount;
    private int              mCellWidth, mCellHeight;
    private int              mViewWidth, mViewHeight;
    private static final int DISH_WIDTH_INDEX  = 0;
    private static final int DISH_HEIGHT_INDEX = 1;

    private static final boolean DRAW_FPS = false;
    private long time1, time2, elapsed = 0;
    private float mLastFPS = 0.0f;
    private String mFPSText = "";
    private float mFPSTextWidth;
    private Paint mPaintFPS;

    private static final boolean DRAW_GEN_CALC_ELAPSED = false;
    private static final boolean DRAW_PAINT_ELAPSED = false;
    private long time1Test, time2Test, mLastElapsed_ns, mLastPaintElapsed_ns;
    private String mElapsedText = "";
    private float mElapsedTextWidth;
    private Paint mPaintElapsed;

    private Canvas mCanvas;

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

        mPaintFPS = new Paint();
        mPaintFPS.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mPaintElapsed = new Paint();
        mPaintElapsed.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        time1 = System.currentTimeMillis();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        if (w > h) {
            mCellWidth = h / mCellCount[DISH_WIDTH_INDEX];
            mCellHeight = w / mCellCount[DISH_HEIGHT_INDEX];
            mDish.setDrawMode(false);
            mPaintFPS.setTextSize(w / 20);
            mPaintFPS.setStrokeWidth(w / 300);
            mPaintElapsed.setTextSize(w / 20);
            mPaintElapsed.setStrokeWidth(w / 300);
        } else {
            mCellWidth = w / mCellCount[DISH_WIDTH_INDEX];
            mCellHeight = h / mCellCount[DISH_HEIGHT_INDEX];
            mDish.setDrawMode(true);
            mPaintFPS.setTextSize(h / 20);
            mPaintFPS.setStrokeWidth(h / 300);
            mPaintElapsed.setTextSize(h / 20);
            mPaintElapsed.setStrokeWidth(h / 300);
        }
        mDish.setCellDimensions(mCellWidth, mCellHeight);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void OnAnimate() {
        if (DRAW_FPS) {
            // calc last real fps
            time2 = System.currentTimeMillis();
            elapsed = time2 - time1;
            time1 = time2;
            mLastFPS = (elapsed == 0L ? 0.0f : 1000.0f / (float) elapsed);
        }

        if (DRAW_GEN_CALC_ELAPSED) {
            time1Test = System.nanoTime();
        }

        // old way
//        mDish.calcNextGenOrig();

        // new way
        mDish.calcNextGenOrigFlipFlop();

        if (DRAW_GEN_CALC_ELAPSED) {
            time2Test = System.nanoTime();
            mLastElapsed_ns = time2Test - time1Test;
        }
    }

    // surface view way
    @Override
    public void OnDraw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            if (DRAW_PAINT_ELAPSED) {
                time1Test = System.nanoTime();
            }

            // draw dish
            mDish.Draw(mCanvas);

            if (DRAW_PAINT_ELAPSED) {
                time2Test = System.nanoTime();
                mLastPaintElapsed_ns = time2Test - time1Test;
            }

            // draw stats
            if (DRAW_FPS) {
                mFPSText = String.format("%.2f", mLastFPS) + " fps";

                mPaintFPS.setColor(Color.BLACK);
                mPaintFPS.setStyle(Paint.Style.FILL);
                mFPSTextWidth = mPaintFPS.measureText(mFPSText);
                mCanvas.drawText(mFPSText, mViewWidth - mFPSTextWidth, 100, mPaintFPS);

                mPaintFPS.setColor(Color.WHITE);
                mPaintFPS.setStyle(Paint.Style.STROKE);
                mFPSTextWidth = mPaintFPS.measureText(mFPSText);
                mCanvas.drawText(mFPSText, mViewWidth - mFPSTextWidth, 100, mPaintFPS);
            }

            if (DRAW_GEN_CALC_ELAPSED) {
                mElapsedText = String.format("%.4f", (float) mLastElapsed_ns / 1000000f) + " ms a";

                mPaintElapsed.setColor(Color.BLACK);
                mPaintElapsed.setStyle(Paint.Style.FILL);
                mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
                mCanvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 200, mPaintElapsed);

                mPaintElapsed.setColor(Color.WHITE);
                mPaintElapsed.setStyle(Paint.Style.STROKE);
                mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
                mCanvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 200, mPaintElapsed);
            }

            if (DRAW_PAINT_ELAPSED) {
                mElapsedText = String.format("%.4f", (float) mLastPaintElapsed_ns / 1000000f) + " ms p";

                mPaintElapsed.setColor(Color.BLACK);
                mPaintElapsed.setStyle(Paint.Style.FILL);
                mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
                mCanvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 300, mPaintElapsed);

                mPaintElapsed.setColor(Color.WHITE);
                mPaintElapsed.setStyle(Paint.Style.STROKE);
                mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
                mCanvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 300, mPaintElapsed);
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    // view way
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (DRAW_PAINT_ELAPSED) {
//            time1Test = System.nanoTime();
//        }
//
//        // draw dish
//        mDish.Draw(canvas);
//
//        if (DRAW_PAINT_ELAPSED) {
//            time2Test = System.nanoTime();
//            mLastPaintElapsed_ns = time2Test - time1Test;
//        }
//
//        // draw stats
//        if (DRAW_FPS) {
//            mFPSText = String.format("%.2f", mLastFPS) + " fps";
//
//            mPaintFPS.setColor(Color.BLACK);
//            mPaintFPS.setStyle(Paint.Style.FILL);
//            mFPSTextWidth = mPaintFPS.measureText(mFPSText);
//            canvas.drawText(mFPSText, mViewWidth - mFPSTextWidth, 100, mPaintFPS);
//
//            mPaintFPS.setColor(Color.WHITE);
//            mPaintFPS.setStyle(Paint.Style.STROKE);
//            mFPSTextWidth = mPaintFPS.measureText(mFPSText);
//            canvas.drawText(mFPSText, mViewWidth - mFPSTextWidth, 100, mPaintFPS);
//        }
//
//        if (DRAW_GEN_CALC_ELAPSED) {
//            mElapsedText = String.format("%.4f", (float) mLastElapsed_ns / 1000000f) + " ms a";
//
//            mPaintElapsed.setColor(Color.BLACK);
//            mPaintElapsed.setStyle(Paint.Style.FILL);
//            mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
//            canvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 200, mPaintElapsed);
//
//            mPaintElapsed.setColor(Color.WHITE);
//            mPaintElapsed.setStyle(Paint.Style.STROKE);
//            mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
//            canvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 200, mPaintElapsed);
//        }
//
//        if (DRAW_PAINT_ELAPSED) {
//            mElapsedText = String.format("%.4f", (float) mLastPaintElapsed_ns / 1000000f) + " ms p";
//
//            mPaintElapsed.setColor(Color.BLACK);
//            mPaintElapsed.setStyle(Paint.Style.FILL);
//            mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
//            canvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 300, mPaintElapsed);
//
//            mPaintElapsed.setColor(Color.WHITE);
//            mPaintElapsed.setStyle(Paint.Style.STROKE);
//            mElapsedTextWidth = mPaintElapsed.measureText(mElapsedText);
//            canvas.drawText(mElapsedText, mViewWidth - mElapsedTextWidth, 300, mPaintElapsed);
//        }
//    }

    public void randomPopulateDish() {
        mDish.randomPopulateDish();
    }
}
