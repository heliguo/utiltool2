package com.example.utiltool2.signature;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;
import com.example.utiltool2.pickercolor.ColorPickerView;
import com.example.utiltool2.pickercolor.IColorListener;

/**
 * author:lgh on 2019-12-20 13:49
 */
public class PenActivity extends BaseActivity implements View.OnClickListener {

    private SparseArray<ImageView> imageViewSparseArray;
    private PenStyle mPenStyle;
    private SeekBar seekBar;
    private BrushPreset brushPreset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pencil);
        ColorPickerView colorPickerView = findViewById(R.id.colorPickerView);
        colorPickerView.setiColorListener(new IColorListener() {
            @Override
            public void color(int color) {
                brushPreset.color = color;
                mPenStyle.setmBrushPreset(brushPreset);
                Log.e("``", "color: " + color);
            }
        });
        imageViewSparseArray = new SparseArray<>();
        ImageView maker = findViewById(R.id.sign_maker);
        maker.setOnClickListener(this);
        imageViewSparseArray.put(maker.getId(), maker);
        ImageView brush = findViewById(R.id.sign_brush);
        brush.setOnClickListener(this);
        imageViewSparseArray.put(brush.getId(), brush);
        ImageView pen = findViewById(R.id.sign_pen);
        pen.setOnClickListener(this);
        imageViewSparseArray.put(pen.getId(), pen);
        ImageView pencil = findViewById(R.id.sign_pencil);
        pencil.setOnClickListener(this);
        imageViewSparseArray.put(pencil.getId(), pencil);
        mPenStyle = findViewById(R.id.sign_pen_style);
        brushPreset = new BrushPreset(BrushPreset.MARKER, Color.BLACK);
        mPenStyle.setmBrushPreset(brushPreset);
        maker.setColorFilter(getResources().getColor(R.color.colorAccent));
        seekBar = findViewById(R.id.sign_pen_size);
        seekBar.setMax(40);
        seekBar.setProgress(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brushPreset.size = progress;
                mPenStyle.setmBrushPreset(brushPreset);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        setImageBg(v.getId());
        switch (v.getId()) {
            case R.id.sign_maker:
                brushPreset = new BrushPreset(BrushPreset.MARKER, Color.BLACK);
                mPenStyle.setmBrushPreset(brushPreset);
                seekBar.setMax(40);
                seekBar.setProgress(20);
                break;
            case R.id.sign_brush:
                brushPreset = new BrushPreset(BrushPreset.BRUSH, Color.BLACK);
                mPenStyle.setmBrushPreset(brushPreset);
                seekBar.setMax(25);
                seekBar.setProgress(15);
                break;
            case R.id.sign_pen:
                brushPreset = new BrushPreset(BrushPreset.PEN, Color.BLACK);
                mPenStyle.setmBrushPreset(brushPreset);
                seekBar.setMax(10);
                seekBar.setProgress(2);
                break;
            case R.id.sign_pencil:
                brushPreset = new BrushPreset(BrushPreset.PENCIL, Color.BLACK);
                mPenStyle.setmBrushPreset(brushPreset);
                seekBar.setMax(10);
                seekBar.setProgress(2);
                break;
        }
    }

    private void setImageBg(int id) {
        for (int i = 0; i < imageViewSparseArray.size(); i++) {
            if (id == imageViewSparseArray.keyAt(i)) {
                imageViewSparseArray.get(imageViewSparseArray.keyAt(i)).setColorFilter(getResources().getColor(R.color.colorAccent));
            } else {
                imageViewSparseArray.get(imageViewSparseArray.keyAt(i)).setColorFilter(getResources().getColor(R.color.black));
            }
        }
    }

}
