package com.example.utiltool2.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;
import com.example.utiltool2.util.BitmapUtil;

import java.io.InputStream;


/**
 * author:lgh on 2019-11-04 16:22
 */
public class ScreenActivity extends BaseActivity implements View.OnClickListener {
    SceneDrawView sceneDrawView;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        sceneDrawView = this.findViewById(R.id.screen_view);
        InputStream in = null;
        try {
            in = getAssets().open("back_brake.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sceneDrawView.setImage(in);
        sceneDrawView.setOnClickListener(this);
        sceneDrawView.setText1("3号闸");
        sceneDrawView.setText2("4号闸");
        sceneDrawView.setText3("1号闸");
        sceneDrawView.setText4("2号闸");
        sceneDrawView.setOffset(40);
        sceneDrawView.setShowTG(true);
    }

    @Override
    public void onClick(View v) {
        if (toast != null) {
            toast.cancel();
        }
        switch (v.getId()) {
            case R.id.screen_view:
                switch ((int) v.getTag(v.getId())) {
                    case 0:
                        toast = Toast.makeText(ScreenActivity.this, "1", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(1);
                        break;
                    case 1:
                        toast = Toast.makeText(ScreenActivity.this, "2", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(2);

                        break;
                    case 2:
                        toast = Toast.makeText(ScreenActivity.this, "3", Toast.LENGTH_SHORT);
//                        sceneDrawView.startAnimator(3);
                        sceneDrawView.reset(1);
                        sceneDrawView.reset(2);
                        sceneDrawView.reset(4);
                        break;
                    case 3:
                        toast = Toast.makeText(ScreenActivity.this, "4", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(4);
                        break;
                }
                break;

        }
        toast.show();
    }
}
