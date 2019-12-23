package com.example.utiltool2.signature;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.utiltool2.R;
import com.example.utiltool2.pickercolor.ColorPickerView;
import com.example.utiltool2.pickercolor.IColorListener;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * author:lgh on 2019-12-20 16:32
 */
public class PenPopup implements View.OnClickListener {

    private Context context;
    private PopupWindow popupWindow;

    private SparseArray<ImageView> imageViewSparseArray;
    private PenStyle mPenStyle;
    private SeekBar seekBar;
    private BrushPreset brushPreset;
    private IBrushPresetListener iBrushPresetListener;

    public PenPopup(Context context) {
        this.context = context;
    }

    public void setiBrushPresetListener(IBrushPresetListener iBrushPresetListener) {
        this.iBrushPresetListener = iBrushPresetListener;
    }

    public void show(View parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return;
        View view = inflater.inflate(R.layout.pencil, null);
        TextView cancel = view.findViewById(R.id.popup_cancel);
        cancel.setOnClickListener(this);
        TextView confirm = view.findViewById(R.id.popup_confirm);
        confirm.setOnClickListener(this);
        ColorPickerView colorPickerView = view.findViewById(R.id.colorPickerView);
        colorPickerView.setiColorListener(new IColorListener() {
            @Override
            public void color(int color) {
                brushPreset.color = color;
                mPenStyle.setmBrushPreset(brushPreset);
                Log.e("``", "color: " + color);
            }
        });
        imageViewSparseArray = new SparseArray<>();
        ImageView maker = view.findViewById(R.id.sign_maker);
        maker.setOnClickListener(this);
        imageViewSparseArray.put(maker.getId(), maker);
        ImageView brush = view.findViewById(R.id.sign_brush);
        brush.setOnClickListener(this);
        imageViewSparseArray.put(brush.getId(), brush);
        ImageView pen = view.findViewById(R.id.sign_pen);
        pen.setOnClickListener(this);
        imageViewSparseArray.put(pen.getId(), pen);
        ImageView pencil = view.findViewById(R.id.sign_pencil);
        pencil.setOnClickListener(this);
        imageViewSparseArray.put(pencil.getId(), pencil);
        mPenStyle = view.findViewById(R.id.sign_pen_style);
        brushPreset = new BrushPreset(BrushPreset.MARKER, Color.BLACK);
        mPenStyle.setmBrushPreset(brushPreset);
        maker.setColorFilter(context.getResources().getColor(R.color.colorAccent));
        seekBar = view.findViewById(R.id.sign_pen_size);
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
        popupWindow = new PopupWindow(view, 1000,
                1000);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(false);

        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(10.0f);
        }

        popupWindow.setAnimationStyle(R.style.TopDefaultsViewColorPickerPopupAnimation);
        if (parent == null) parent = view;
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);


    }

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
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
            case R.id.popup_cancel:
                dismiss();
                break;

            case R.id.popup_confirm:
                if (iBrushPresetListener != null) {
                    iBrushPresetListener.brushPreset(mPenStyle.getmBrushPrest());
                }
                dismiss();
                break;
        }
    }

    private void setImageBg(int id) {
        for (int i = 0; i < imageViewSparseArray.size(); i++) {
            if (id == imageViewSparseArray.keyAt(i)) {
                imageViewSparseArray.get(imageViewSparseArray.keyAt(i)).setColorFilter(context.getResources().getColor(R.color.colorAccent));
            } else {
                imageViewSparseArray.get(imageViewSparseArray.keyAt(i)).setColorFilter(context.getResources().getColor(R.color.black));
            }
        }
    }
}
