package com.roltekk.daydream.gameoflife;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ColourPickerDialog extends Dialog {
    private static final String TAG = "ColourPickerDialog";
    private ImageView mImgPickedColour;
    private TextView mTxtColourValue;
    private SeekBar mSeekRed, mSeekGreen, mSeekBlue;
    private Button mBtnOK, mBtnCancel;
    private OnColourChangedListener mListener;
    private int mItem;
    private int mInitialColor;

    public interface OnColourChangedListener {
        void colourChanged(int item, int colour);
    }

    public ColourPickerDialog(Context context, OnColourChangedListener listener, int item, int initialColour) {
        super(context);
        Log.d(TAG, "ColourPickerDialog");
        mListener = listener;
        mItem = item;
        mInitialColor = initialColour;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_layout);

        setTitle(getContext().getString(R.string.colour_picker_title));

        // interface widget creation ///////////////////////////////////////////////
        mImgPickedColour = (ImageView) findViewById(R.id.imgPickedColour);
        mTxtColourValue = (TextView) findViewById(R.id.txtColourValue);
        mSeekRed = (SeekBar) findViewById(R.id.seekRed);
        mSeekGreen = (SeekBar) findViewById(R.id.seekGreen);
        mSeekBlue = (SeekBar) findViewById(R.id.seekBlue);
        mBtnOK = (Button) findViewById(R.id.btnColourOK);
        mBtnCancel = (Button) findViewById(R.id.btnColourCancel);

        mImgPickedColour.setBackgroundColor(mInitialColor);

        mTxtColourValue.setText("#0x" + Integer.toHexString(mInitialColor));

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
        mTxtColourValue.setText("#" + Integer.toHexString(mInitialColor));
    }
}
