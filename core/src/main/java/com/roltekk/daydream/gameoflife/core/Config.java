package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class Config {
    private static final String CONFIG_FILE = "config";

    private static final long[] frameTimes = {
        2000, // slow
        1000, // medium (default)
        500, // fast
    };
    private static final int SPEED_INDEX_DEFAULT = 1;

    private static final int[][] cellCount = {
        {54, 96},// many
        {18, 32},// medium (default)
        {9, 16}// few
    };
    private static final int SIZE_INDEX_DEFAULT = 1;

    private static final int COLOUR_DEAD_DEFAULT  = Color.BLACK;
    private static final int COLOUR_ALIVE_DEFAULT = Color.WHITE;

    private static SharedPreferences        mSettings;
    private static SharedPreferences.Editor mEditor;

    private static final String SPEED_KEY              = "speed";
    private static final String SIZE_KEY               = "size";
    private static final String COLOUR_DEAD_KEY        = "colourDead";
    private static final String COLOUR_ALIVE_KEY       = "colourAlive";
    private static final String REPOPULATE_TIMEOUT_KEY = "repopulateTimeout";

    private static final String TEST_SPEED_KEY              = "test_speed";
    private static final String TEST_SIZE_KEY               = "test_size";
    private static final String TEST_COLOUR_DEAD_KEY        = "test_colourDead";
    private static final String TEST_COLOUR_ALIVE_KEY       = "test_colourAlive";
    private static final String TEST_REPOPULATE_TIMEOUT_KEY = "test_repopulateTimeout";

    public static final long REPOPULATE_TIMEOUT_DEFAULT = 10000;

    public static final int COLOUR_DEAD_ITEM  = 0;
    public static final int COLOUR_ALIVE_ITEM = 1;

    public static void saveSpeed(Context ctx, int speedIndex) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(SPEED_KEY, speedIndex);
        mEditor.apply();
    }

    public static int getSpeedIndex(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(SPEED_KEY, SPEED_INDEX_DEFAULT);
    }

    public static int getDefaultSpeedIndex() {
        return SPEED_INDEX_DEFAULT;
    }

    public static long getFrameTime(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        int index = mSettings.getInt(SPEED_KEY, SPEED_INDEX_DEFAULT);
        return frameTimes[index];
    }

    public static void saveSize(Context ctx, int sizeIndex) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(SIZE_KEY, sizeIndex);
        mEditor.apply();
    }

    public static int getSizeIndex(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(SIZE_KEY, SIZE_INDEX_DEFAULT);
    }

    public static int getDefaultSizeIndex() {
        return SIZE_INDEX_DEFAULT;
    }

    public static int[] getCellCount(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        int index = mSettings.getInt(SIZE_KEY, SIZE_INDEX_DEFAULT);
        return cellCount[index];
    }

    public static void saveColourDead(Context ctx, int colourDead) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(COLOUR_DEAD_KEY, colourDead);
        mEditor.apply();
    }

    public static int getColourDead(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(COLOUR_DEAD_KEY, COLOUR_DEAD_DEFAULT);
    }

    public static int getDefaultColourDead() {
        return COLOUR_DEAD_DEFAULT;
    }

    public static void saveColourAlive(Context ctx, int colourAlive) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(COLOUR_ALIVE_KEY, colourAlive);
        mEditor.apply();
    }

    public static int getColourAlive(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(COLOUR_ALIVE_KEY, COLOUR_ALIVE_DEFAULT);
    }

    public static int getDefaultColourAlive() {
        return COLOUR_ALIVE_DEFAULT;
    }

    public static void saveRepopulationTimeout(Context ctx, long timeout) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putLong(REPOPULATE_TIMEOUT_KEY, timeout);
        mEditor.apply();
    }

    public static long getRepopulationTimeout(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getLong(REPOPULATE_TIMEOUT_KEY, REPOPULATE_TIMEOUT_DEFAULT);
    }

    public static long getDefaultRepopulationTimeout() {
        return REPOPULATE_TIMEOUT_DEFAULT;
    }

    public static void saveTestSpeed(Context ctx, int speedIndex) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(TEST_SPEED_KEY, speedIndex);
        mEditor.apply();
    }

    public static long getTestFrameTime(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        int index = mSettings.getInt(TEST_SPEED_KEY, SPEED_INDEX_DEFAULT);
        return frameTimes[index];
    }

    public static void saveTestSize(Context ctx, int sizeIndex) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(TEST_SIZE_KEY, sizeIndex);
        mEditor.apply();
    }

    public static int[] getTestCellCount(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        int index = mSettings.getInt(TEST_SIZE_KEY, SIZE_INDEX_DEFAULT);
        return cellCount[index];
    }

    public static void saveTestColourDead(Context ctx, int colourDead) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(TEST_COLOUR_DEAD_KEY, colourDead);
        mEditor.apply();
    }

    public static int getTestColourDead(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(TEST_COLOUR_DEAD_KEY, COLOUR_DEAD_DEFAULT);
    }

    public static void saveTestColourAlive(Context ctx, int colourAlive) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(TEST_COLOUR_ALIVE_KEY, colourAlive);
        mEditor.apply();
    }

    public static int getTestColourAlive(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(TEST_COLOUR_ALIVE_KEY, COLOUR_ALIVE_DEFAULT);
    }

    public static void saveTestRepopulationTimeout(Context ctx, long timeout) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putLong(TEST_REPOPULATE_TIMEOUT_KEY, timeout);
        mEditor.apply();
    }

    public static long getTestRepopulationTimeout(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getLong(TEST_REPOPULATE_TIMEOUT_KEY, REPOPULATE_TIMEOUT_DEFAULT);
    }
}
