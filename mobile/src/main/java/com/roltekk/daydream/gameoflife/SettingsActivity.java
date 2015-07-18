package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.roltekk.daydream.gameoflife.core.Config;

public class SettingsActivity extends Activity {
    private static final String TAG = "SettingsActivity";

    private int mDeadColor, mAliveColor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        // TODO: setup all ui elements


    }

    private void saveSettings() {
        // TODO: save all settings set in UI to shared prefs
        Config.saveColourDead(this, mDeadColor);
        Config.saveColourAlive(this, mAliveColor);
        //...


    }

    private void getCurrentSettings() {
        // TODO: get current settings from shared prefs and set UI to match
        mDeadColor = Config.getColourDead(this);
        mAliveColor = Config.getColourAlive(this);
        //...

    }

    private void getDefaultSettings() {
        // TODO: get default settings and set UI to match
        mDeadColor = Config.getColourDead(this);
        mAliveColor = Config.getColourAlive(this);
        //...


    }

    private void testSettings() {
        // TODO: take settings set in UI and run game with them (could use DevelopmentActivity like setup)

    }
}
