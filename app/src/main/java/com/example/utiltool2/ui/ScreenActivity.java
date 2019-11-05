package com.example.utiltool2.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

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
        sceneDrawView.setOnClickListener(this);
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
                        toast = Toast.makeText(this, "1", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(1);
                        break;
                    case 1:
                        toast = Toast.makeText(this, "2", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(2);

                        break;
                    case 2:
                        toast = Toast.makeText(this, "3", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(3);
                        break;
                    case 3:
                        toast = Toast.makeText(this, "4", Toast.LENGTH_SHORT);
                        sceneDrawView.startAnimator(4);
                        break;
                }
                break;

        }
        toast.show();
    }
}
