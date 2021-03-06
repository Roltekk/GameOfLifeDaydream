package com.roltekk.daydream.gameoflife;

import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import static android.content.Context.UI_MODE_SERVICE;

public class ColourPickerDialog extends Dialog {
    private static final String TAG = "ColourPickerDialog";
    private ImageView mImgPickedColour;
    private SeekBar mSeekRed, mSeekGreen, mSeekBlue;
    private Button mBtnOK, mBtnCancel;
    private OnColourChangedListener mListener;
    private int mItem;
    private int mInitialColor;
    private Context mCtx;

    public interface OnColourChangedListener {
        void colourChanged(int item, int colour);
    }

    public ColourPickerDialog(Context context, OnColourChangedListener listener, int item, int initialColour) {
        super(context);
        mCtx = context;
        Log.d(TAG, "ColourPickerDialog");
        mListener = listener;
        mItem = item;
        mInitialColor = initialColour;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        UiModeManager uiModeManager = (UiModeManager) mCtx.getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            mCtx.setTheme(R.style.SettingsThemeTV);
            setContentView(R.layout.color_picker_layout_tv);
        } else {
            mCtx.setTheme(R.style.SettingsThemeMobile);
            setContentView(R.layout.color_picker_layout_mobile);
        }

        // interface widget creation ///////////////////////////////////////////////
        mImgPickedColour = (ImageView) findViewById(R.id.imgPickedColour);
        mSeekRed = (SeekBar) findViewById(R.id.seekRed);
        mSeekGreen = (SeekBar) findViewById(R.id.seekGreen);
        mSeekBlue = (SeekBar) findViewById(R.id.seekBlue);
        mBtnOK = (Button) findViewById(R.id.btnColourOK);
        mBtnCancel = (Button) findViewById(R.id.btnColourCancel);

        mImgPickedColour.setBackgroundColor(mInitialColor);

        mSeekRed.setMax(0xFF);
        mSeekGreen.setMax(0xFF);
        mSeekBlue.setMax(0xFF);

        mSeekRed.setProgress(((mInitialColor & Color.RED) - 0xFF000000) >> 16);
        mSeekGreen.setProgress(((mInitialColor & Color.GREEN) - 0xFF000000) >> 8);
        mSeekBlue.setProgress((mInitialColor & Color.BLUE) - 0xFF000000);

        // interface listeners /////////////////////////////////////////////////////
        mBtnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.colourChanged(mItem, mInitialColor);
                dismiss();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        mSeekRed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setChosenColour();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mSeekGreen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setChosenColour();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mSeekBlue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setChosenColour();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setChosenColour() {
        mInitialColor = 0xFF000000 + (mSeekRed.getProgress() << 16) + (mSeekGreen.getProgress() << 8) + mSeekBlue.getProgress();
        mImgPickedColour.setBackgroundColor(mInitialColor);
    }
}
