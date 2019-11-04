package com.example.utiltool2.ui.slideview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

public class ViewSlideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slide);
        CustomView mCustomView = (CustomView) this.findViewById(R.id.customview);
        mCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewSlideActivity.this, "23333", Toast.LENGTH_SHORT).show();

            }
        });
        //使用属性动画使view滑动
//        ObjectAnimator.ofFloat(mCustomView,"translationX",0,300).setDuration(1000).start();
        //使用View动画使view滑动
//      mCustomView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));

        //使用Scroll来进行平滑移动
        mCustomView.smoothScrollTo(-400, -100, 2000);
    }


}
