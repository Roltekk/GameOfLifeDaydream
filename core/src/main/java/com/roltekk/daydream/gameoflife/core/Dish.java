package com.roltekk.daydream.gameoflife.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Dish {
    private static final String TAG = "Dish";
    private Random              mRand;
    private int                 mDishWidth, mDishHeight;
    // TODO: change this to [2][][]byte arrays for multi state cell (dying, dead, alive, born)
    private boolean[][]         mCells, mNextGeneration;
    private int                 mDeadColour;
    private Paint               mPaintAlive, mPaintDead;
    private int                 mCellWidth, mCellHeight;
    private int                 mNeighbourCount;
    private int                 mIIndex, mJIndex;
    private boolean             mDrawPortrait;
    private static final boolean DRAW_DEAD = false; // used for testing performance

    private boolean[][][] mCellsFlipFlop;
    private static final int FLIP_INDEX = 0;
    private static final int FLOP_INDEX = 1;
    private int mFlipFlopCurrentIndex = FLIP_INDEX;
    private int mFlipFlopNextIndex = FLOP_INDEX;
    private int a = 0;

    public Dish(int width, int height, int deadColour, int aliveColour) {
        Log.d(TAG, "w/h = " + width + "/" + height);
        mRand = new Random();
        mPaintAlive = new Paint();
        mPaintAlive.setColor(aliveColour);
        mPaintAlive.setStyle(Paint.Style.FILL);
        mPaintDead = new Paint();
        mPaintDead.setColor(deadColour);
        mPaintDead.setStyle(Paint.Style.FILL);
        mDishWidth = width;
        mDishHeight = height;
        mDeadColour = deadColour;
        mCells = new boolean[mDishWidth][mDishHeight];
        mNextGeneration = new boolean[mDishWidth][mDishHeight];
        mCellsFlipFlop = new boolean[2][mDishWidth][mDishHeight];
    }

    public void randomPopulateDish() {
        for (int i = 0; i < mDishWidth; i++) {
            for (int j = 0; j < mDishHeight; j++) {
                mCells[i][j] = mRand.nextBoolean();
                mCellsFlipFlop[mFlipFlopCurrentIndex][i][j] = mRand.nextBoolean();
            }
        }
    }

    public void calcNextGenOrig() {
        // rules:
        // Any live cell with fewer than two live neighbours dies, as if caused by under-population.
        // Any live cell with two or three live neighbours lives on to the next generation.
        // Any live cell with more than three live neighbours dies, as if by overcrowding.
        // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

        // loop through cell array calculating new births and deaths
        for (int i = 0; i < mDishWidth; i++) {
            for (int j = 0; j < mDishHeight; j++) {
                // check cells around this one and increment count
                mNeighbourCount = 0;

                // set indexes to top left of this cell
                mIIndex = (0 == i) ? mDishWidth - 1 : i - 1;
                mJIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check left 3 column
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }

                // move mIIndex 1 to the right
                mIIndex++;
                if (mIIndex == mDishWidth) { mIIndex = 0; }
                // reset mJIndex to above this cell
                mJIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check middle 2 column
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }

                // move mIIndex 1 to the right
                mIIndex++;
                if (mIIndex == mDishWidth) { mIIndex = 0; }
                // reset mJIndex to above this cell
                mJIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check right 3 column
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCells[mIIndex][mJIndex]) { mNeighbourCount++; }

                // determine this cell's new state depending on it's current state and neighbours
                if (mCells[i][j]) { mNextGeneration[i][j] = (mNeighbourCount > 1 && mNeighbourCount < 4); }
                else { mNextGeneration[i][j] = (mNeighbourCount == 3); }
            }
        }

        // copy next generation to current cells for drawing
        for (int i = 0; i < mCells.length; i++) {
            System.arraycopy(mNextGeneration[i], 0, mCells[i], 0, mNextGeneration[i].length);
        }
    }

    public void calcNextGenOrigFlipFlop() {
        // rules:
        // Any live cell with fewer than two live neighbours dies, as if caused by under-population.
        // Any live cell with two or three live neighbours lives on to the next generation.
        // Any live cell with more than three live neighbours dies, as if by overcrowding.
        // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

        // loop through cell array calculating new births and deaths
        for (int i = 0; i < mDishWidth; i++) {
            for (int j = 0; j < mDishHeight; j++) {
                // check cells around this one and increment count
                mNeighbourCount = 0;

                // set indexes to top left of this cell
                mIIndex = (0 == i) ? mDishWidth - 1 : i - 1;
                mJIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check left 3 column
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }

                // move mIIndex 1 to the right
                mIIndex++;
                if (mIIndex == mDishWidth) { mIIndex = 0; }
                // reset mJIndex to above this cell
                mJIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check middle 2 column
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }

                // move mIIndex 1 to the right
                mIIndex++;
                if (mIIndex == mDishWidth) { mIIndex = 0; }
                // reset mJIndex to above this cell
                mJIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check right 3 column
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }
                mJIndex++;
                if (mJIndex == mDishHeight) { mJIndex = 0; }
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][mIIndex][mJIndex]) { mNeighbourCount++; }

                // determine this cell's new state depending on it's current state and neighbours
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][i][j]) { mCellsFlipFlop[mFlipFlopNextIndex][i][j] = (mNeighbourCount > 1 && mNeighbourCount < 4); }
                else { mCellsFlipFlop[mFlipFlopNextIndex][i][j] = (mNeighbourCount == 3); }
            }
        }

        // toggle buffer indices
        mFlipFlopCurrentIndex = mFlipFlopCurrentIndex == FLIP_INDEX ? FLOP_INDEX : FLIP_INDEX;
        mFlipFlopNextIndex = mFlipFlopNextIndex == FLIP_INDEX ? FLOP_INDEX : FLIP_INDEX;
    }

    public void Draw(Canvas canvas) {
        // TODO: offset to center on view
//        canvas.drawColor(Color.MAGENTA); // used to test background bleeding
        canvas.drawColor(mDeadColour);
        for (int i = 0; i < mDishWidth; i++) {
            for (int j = 0; j < mDishHeight; j++) {
                // old way
//                if (mCells[i][j]) {
//                    if (mDrawPortrait) {
//                        canvas.drawRect(i * mCellWidth, j * mCellHeight, (i + 1) * mCellWidth, (j + 1) * mCellHeight, mPaintAlive);
//                    } else {
//                        canvas.drawRect(j * mCellHeight, i * mCellWidth, (j + 1) * mCellHeight, (i + 1) * mCellWidth, mPaintAlive);
//                    }
//                } else { // drawing dead to make it the same every time
//                    if (DRAW_DEAD) {
//                        if (mDrawPortrait) {
//                            canvas.drawRect(i * mCellWidth, j * mCellHeight, (i + 1) * mCellWidth, (j + 1) * mCellHeight, mPaintDead);
//                        } else {
//                            canvas.drawRect(j * mCellHeight, i * mCellWidth, (j + 1) * mCellHeight, (i + 1) * mCellWidth, mPaintDead);
//                        }
//                    }
//                }

                // new way
                if (mCellsFlipFlop[mFlipFlopCurrentIndex][i][j]) {
                    if (mDrawPortrait) {
                        canvas.drawRect(i * mCellWidth, j * mCellHeight, (i + 1) * mCellWidth, (j + 1) * mCellHeight, mPaintAlive);
                    } else {
                        canvas.drawRect(j * mCellHeight, i * mCellWidth, (j + 1) * mCellHeight, (i + 1) * mCellWidth, mPaintAlive);
                    }
                } else { // drawing dead to make it the same every time
                    if (DRAW_DEAD) {
                        if (mDrawPortrait) {
                            canvas.drawRect(i * mCellWidth, j * mCellHeight, (i + 1) * mCellWidth, (j + 1) * mCellHeight, mPaintDead);
                        } else {
                            canvas.drawRect(j * mCellHeight, i * mCellWidth, (j + 1) * mCellHeight, (i + 1) * mCellWidth, mPaintDead);
                        }
                    }
                }

                // no draw to test min time
//                if (mCellsFlipFlop[mFlipFlopCurrentIndex][i][j]) {
//                    a++;
//                } else {
//                    if (DRAW_DEAD) {
//                        a++;
//                    }
//                }
            }
        }
    }

    public void setCellDimensions(int cellWidth, int cellHeight) {
        mCellWidth = cellWidth;
        mCellHeight = cellHeight;
    }

    public void setDrawMode(boolean drawPortrait) {
        mDrawPortrait = drawPortrait;
    }
}
