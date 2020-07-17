package com.example.utiltool2.annotations;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.annotationlibrary.annotations.ContentView;
import com.example.annotationlibrary.annotations.InjectView;
import com.example.annotationlibrary.annotations.OnClick;
import com.example.annotationlibrary.annotations.OnLongClick;
import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

@ContentView(R.layout.activity_annotation)
public class AnnotationActivity extends BaseActivity {

    @InjectView(R.id.btn_onclick)
    Button clickBtn;

    @InjectView(R.id.btn_onlongclick)
    Button longClickBtn;

    @OnClick(R.id.btn_onclick)
    public void clickShow(View view) {
        Toast.makeText(mContext, "this is onClick...", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.btn_onlongclick)
    private boolean longClickShow(View view) {
        Toast.makeText(mContext, "this is onLongClick...", Toast.LENGTH_SHORT).show();
        return true;
    }

}