package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.roltekk.daydream.gameoflife.core.Config;

public class SettingsActivity extends Activity implements ColourPickerDialog.OnColourChangedListener {
    private TextView mTxtVersion, mTxtTitleSpeed, mTxtTitleSize, mTxtTitleRepopulationTime;
    private SeekBar  mSkSpeed, mSkSize, mSkRepopulationTimeout;
    private ImageView mImgColourDead, mImgColourAlive;
    private Button mBtnDefault, mBtnTest, mBtnCancel, mBtnSave;

    private int mSkSpeedIndex, mSkSizeIndex;
    private int mDeadColor, mAliveColor;
    private long mRepopulationTimeout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            setTheme(R.style.SettingsThemeTV);
            setContentView(R.layout.settings_tv);
        } else {
            setTheme(R.style.SettingsThemeMobile);
            setContentView(R.layout.settings_mobile);
        }

        mTxtVersion = (TextView) findViewById(R.id.txtVersion);
        mTxtTitleSpeed = (TextView) findViewById(R.id.txtTitleSpeed);
        mTxtTitleSize = (TextView) findViewById(R.id.txtTitleCellSize);
        mTxtTitleRepopulationTime = (TextView) findViewById(R.id.txtTitleRepopulationTime);
        mSkSpeed = (SeekBar) findViewById(R.id.skSpeed);
        mSkSize = (SeekBar) findViewById(R.id.skSize);
        mSkRepopulationTimeout = (SeekBar) findViewById(R.id.skRepopulationTime);
        mImgColourDead = (ImageView) findViewById(R.id.imgDeadColour);
        mImgColourAlive = (ImageView) findViewById(R.id.imgAliveColour);
        mBtnDefault = (Button) findViewById(R.id.btnDefault);
        mBtnTest = (Button) findViewById(R.id.btnTest);
        mBtnCancel = (Button)findViewById(R.id.btnCancel);
        mBtnSave = (Button)findViewById(R.id.btnSave);

        String version = "";
        try { version = "v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName; }
        catch (Exception e) { e.printStackTrace(); }
        mTxtVersion.setText(version);

        mSkSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                mSkSpeedIndex = progressValue;
                mTxtTitleSpeed.setText(getResources().getString(R.string.settings_title_speed) + " - " + getResources().getStringArray(R.array.fps_rates)[mSkSpeedIndex] + " fps");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mSkSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                mSkSizeIndex = progressValue;
                mTxtTitleSize.setText(getResources().getString(R.string.settings_title_cell_size) + " - " + getResources().getStringArray(R.array.cell_sizes)[progressValue]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mSkRepopulationTimeout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                mRepopulationTimeout = (progressValue + 10) * 1000;
                mTxtTitleRepopulationTime.setText(getResources().getString(R.string.settings_title_repopulation_time) + " - " + (int)(mRepopulationTimeout / 1000) + " seconds");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        mImgColourDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColourPickerDialog(SettingsActivity.this, SettingsActivity.this, Config.COLOUR_DEAD_ITEM, mDeadColor).show();
            }
        });

        mImgColourAlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColourPickerDialog(SettingsActivity.this, SettingsActivity.this, Config.COLOUR_ALIVE_ITEM, mAliveColor).show();
            }
        });

        mBtnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDefaultSettings();
            }
        });

        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSettings();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                SettingsActivity.this.finish();
            }
        });

        getCurrentSettings();
    }

    private void saveSettings() {
        Config.saveSpeed(this, mSkSpeedIndex);
        Config.saveSize(this, mSkSizeIndex);
        Config.saveColourDead(this, mDeadColor);
        Config.saveColourAlive(this, mAliveColor);
        Config.saveRepopulationTimeout(this, mRepopulationTimeout);
    }

    private void getCurrentSettings() {
        mSkSpeedIndex = Config.getSpeedIndex(this);
        mSkSizeIndex = Config.getSizeIndex(this);
        mDeadColor = Config.getColourDead(this);
        mAliveColor = Config.getColourAlive(this);
        mRepopulationTimeout = Config.getRepopulationTimeout(this);
        updateUI();
    }

    private void getDefaultSettings() {
        mSkSpeedIndex = Config.getDefaultSpeedIndex();
        mSkSizeIndex = Config.getDefaultSizeIndex();
        mDeadColor = Config.getDefaultColourDead();
        mAliveColor = Config.getDefaultColourAlive();
        mRepopulationTimeout = Config.getDefaultRepopulationTimeout();
        updateUI();
    }

    private void updateUI() {
        mSkSpeed.setProgress(mSkSpeedIndex);
        mSkSize.setProgress(mSkSizeIndex);
        mImgColourDead.setBackgroundColor(mDeadColor);
        mImgColourAlive.setBackgroundColor(mAliveColor);
        mSkRepopulationTimeout.setProgress((int)((mRepopulationTimeout / 1000)) - 10);
        mTxtTitleSpeed.setText(getResources().getString(R.string.settings_title_speed) + " - " + getResources().getStringArray(R.array.fps_rates)[mSkSpeedIndex] + " fps");
        mTxtTitleSize.setText(getResources().getString(R.string.settings_title_cell_size) + " - " + getResources().getStringArray(R.array.cell_sizes)[mSkSizeIndex]);
        mTxtTitleRepopulationTime.setText(getResources().getString(R.string.settings_title_repopulation_time) + " - " + (int)(mRepopulationTimeout / 1000) + " seconds");
    }

    private void testSettings() {
        Config.saveTestSpeed(this, mSkSpeedIndex);
        Config.saveTestSize(this, mSkSizeIndex);
        Config.saveTestColourDead(this, mDeadColor);
        Config.saveTestColourAlive(this, mAliveColor);
        Config.saveTestRepopulationTimeout(this, mRepopulationTimeout);
        Intent intent = new Intent(this, TestSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void colourChanged(int item, int colour) {
        switch (item) {
            case Config.COLOUR_DEAD_ITEM:
                mDeadColor = colour;
                mImgColourDead.setBackgroundColor(mDeadColor);
                break;
            case Config.COLOUR_ALIVE_ITEM:
                mAliveColor = colour;
                mImgColourAlive.setBackgroundColor(mAliveColor);
                break;
        }
    }
}
