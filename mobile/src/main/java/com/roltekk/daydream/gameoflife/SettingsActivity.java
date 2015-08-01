package com.roltekk.daydream.gameoflife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.roltekk.daydream.gameoflife.core.Config;

public class SettingsActivity extends Activity implements ColourPickerDialog.OnColourChangedListener {
    private TextView mTxtVersion;
    private SeekBar  mSkSpeed, mSkSize;
    private ImageView mImgColourDead, mImgColourAlive;
    private Button mBtnDefault, mBtnTest, mBtnCancel, mBtnSave;

    private int mSkSpeedIndex, mSkSizeIndex;
    private int mDeadColor, mAliveColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        mTxtVersion = (TextView) findViewById(R.id.txtVersion);
        mSkSpeed = (SeekBar) findViewById(R.id.skSpeed);
        mSkSize = (SeekBar) findViewById(R.id.skSize);
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
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) { mSkSpeedIndex = progresValue; }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mSkSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) { mSkSizeIndex = progresValue; }

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
    }

    private void getCurrentSettings() {
        mSkSpeedIndex = Config.getSpeedIndex(this);
        mSkSizeIndex = Config.getSizeIndex(this);
        mDeadColor = Config.getColourDead(this);
        mAliveColor = Config.getColourAlive(this);
        updateUI();
    }

    private void getDefaultSettings() {
        mSkSpeedIndex = Config.getDefaultSpeedIndex();
        mSkSizeIndex = Config.getDefaultSizeIndex();
        mDeadColor = Config.getDefaultColourDead();
        mAliveColor = Config.getDefaultColourAlive();
        updateUI();
    }

    private void updateUI() {
        mSkSpeed.setProgress(mSkSpeedIndex);
        mSkSize.setProgress(mSkSizeIndex);
        mImgColourDead.setBackgroundColor(mDeadColor);
        mImgColourAlive.setBackgroundColor(mAliveColor);
    }

    private void testSettings() {
        Config.saveTestSpeed(this, mSkSpeedIndex);
        Config.saveTestSize(this, mSkSizeIndex);
        Config.saveTestColourDead(this, mDeadColor);
        Config.saveTestColourAlive(this, mAliveColor);
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
