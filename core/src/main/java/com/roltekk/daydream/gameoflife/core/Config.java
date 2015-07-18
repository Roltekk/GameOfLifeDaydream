package com.roltekk.daydream.gameoflife.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class Config {
    private static final String CONFIG_FILE = "config";

    private static final int COLOUR_DEAD_DEFAULT  = Color.BLACK;
    private static final int COLOUR_ALIVE_DEFAULT = Color.WHITE;

    private static final int FRAME_TIME_FAST    = 500;
    private static final int FRAME_TIME_MEDIUM  = 1000;
    private static final int FRAME_TIME_SLOW    = 2000;
    private static final int FRAME_TIME_DEFAULT = FRAME_TIME_MEDIUM;

    private static SharedPreferences        mSettings;
    private static SharedPreferences.Editor mEditor;

    private static final String COLOUR_DEAD_KEY   = "colourDead";
    private static final String COLOUR_ALIVE_KEY  = "colourAlive";
    private static final String FRAME_TIME_KEY    = "frameTime";

    public static final long REPOPULATE_TIMEOUT = 10000;

    public static void saveColourDead(Context ctx, int colourDead) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(COLOUR_DEAD_KEY, colourDead);
        mEditor.commit();
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
        mEditor.commit();
    }

    public static int getColourAlive(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(COLOUR_ALIVE_KEY, COLOUR_ALIVE_DEFAULT);
    }

    public static int getDefaultColourAlive() {
        return COLOUR_DEAD_DEFAULT;
    }

    public static void saveFrameTime(Context ctx, int frameTime) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        mEditor = mSettings.edit();
        mEditor.putInt(FRAME_TIME_KEY, frameTime);
        mEditor.commit();
    }

    public static int getFrameTime(Context ctx) {
        mSettings = ctx.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        return mSettings.getInt(FRAME_TIME_KEY, FRAME_TIME_DEFAULT);
    }
}
