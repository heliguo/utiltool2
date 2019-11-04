package com.example.utiltool2.ui;

import android.os.Bundle;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

/**
 * author:lgh on 2019-11-04 16:22
 */
public class ScreenActivity extends BaseActivity {
    SceneDrawView sceneDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        sceneDrawView = findViewById(R.id.screen_view);

    }
}
