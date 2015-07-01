package com.roltekk.daydream.gameoflife;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

public class Dish {
    private Random      rand;
    private int         mDishWidth, mDishHeight;
    private boolean[][] cells, nextGeneration;
    private int         mDeadColor;
    private Paint       mPaintAlive, mPaintDead;
    private int         mCellWidth, mCellHeight;
    private int         mNeighbourCount;
    private int         iIndex, jIndex;

    public Dish(int width, int height, int aliveColor, int deadColor) {
        rand = new Random();
        mPaintAlive = new Paint();
        mPaintAlive.setColor(aliveColor);
        mPaintAlive.setStyle(Paint.Style.FILL);
        mPaintDead = new Paint();
        mPaintDead.setColor(deadColor);
        mPaintDead.setStyle(Paint.Style.FILL);
        mDishWidth = width;
        mDishHeight = height;
        mDeadColor = deadColor;
        cells = new boolean[mDishWidth][mDishHeight];
        nextGeneration = new boolean[mDishWidth][mDishHeight];

        // randomly populate dish
        for (int i = 0; i < mDishWidth; i++) {
            for (int j = 0; j < mDishHeight; j++) {
                cells[i][j] = rand.nextBoolean();
            }
        }
    }

    public void Animate() {
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
                iIndex = (0 == i) ? mDishWidth - 1 : i - 1;
                jIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check left 3 column
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }
                jIndex++;
                if (jIndex == mDishHeight) { jIndex = 0; }
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }
                jIndex++;
                if (jIndex == mDishHeight) { jIndex = 0; }
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }

                // move iIndex 1 to the right
                iIndex++;
                if (iIndex == mDishWidth) { iIndex = 0; }
                // reset jIndex to above this cell
                jIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check middle 2 column
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }
                jIndex++;
                if (jIndex == mDishHeight) { jIndex = 0; }
                jIndex++;
                if (jIndex == mDishHeight) { jIndex = 0; }
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }

                // move iIndex 1 to the right
                iIndex++;
                if (iIndex == mDishWidth) { iIndex = 0; }
                // reset jIndex to above this cell
                jIndex = (0 == j) ? mDishHeight - 1 : j - 1;

                // check right 3 column
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }
                jIndex++;
                if (jIndex == mDishHeight) { jIndex = 0; }
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }
                jIndex++;
                if (jIndex == mDishHeight) { jIndex = 0; }
                if (cellIsAlive(iIndex, jIndex)) { mNeighbourCount++; }

                // determine this cell's new state depending on it's current state and neighbours
                if (cellIsAlive(i, j)) { nextGeneration[i][j] = (mNeighbourCount > 1 && mNeighbourCount < 4); }
                else { nextGeneration[i][j] = (mNeighbourCount == 3); }
            }
        }

        // copy next generation to current cells for drawing
        for (int i = 0; i < cells.length; i++) {
            System.arraycopy(nextGeneration[i], 0, cells[i], 0, nextGeneration[i].length);
        }
    }

    public void Draw(Canvas canvas) {
        canvas.drawColor(mDeadColor);
        mCellWidth = canvas.getWidth() / mDishWidth;
        mCellHeight = canvas.getHeight() / mDishHeight;
        for (int i = 0; i < mDishWidth; i++) {
            for (int j = 0; j < mDishHeight; j++) {
                if (cells[i][j]) {
                    canvas.drawRect(i * mCellWidth, j * mCellHeight, (i + 1) * mCellWidth, (j + 1) * mCellHeight, mPaintAlive);
                }
            }
        }
    }

    private boolean cellIsAlive(int iIndex, int jIndex) {
        return cells[iIndex][jIndex];
    }
}
