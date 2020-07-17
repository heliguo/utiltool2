package com.example.utiltool2.ui.ImageLoad;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utiltool2.R;

import java.io.InputStream;

/**
 * author:lgh on 2019-09-17 15:42
 */
public class ImageLoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageload);
        BigView bigView = findViewById(R.id.big_view);
        InputStream in = null;
        try {
            in = getAssets().open("qingmingshanghetu.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
        bigView.setImage(in);
    }
}
