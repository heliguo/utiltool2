package com.example.utiltool2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.utiltool2.annotation.LogRecord;
import com.example.utiltool2.annotation.NetworkCheck;
import com.example.utiltool2.annotation.NoNetworkShow;
import com.example.utiltool2.ui.ScreenActivity;
import com.example.utiltool2.ui.cardview.CardViewActivity;
import com.example.utiltool2.ui.notification.NotificationActivity;
import com.example.utiltool2.ui.recyclerview.RecyclerViewActivity;
import com.example.utiltool2.ui.slideview.ViewSlideActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_recyclerview).setOnClickListener(this);
        findViewById(R.id.btn_notification).setOnClickListener(this);
        findViewById(R.id.btn_cardview).setOnClickListener(this);
//        findViewById(R.id.btn_slideview).setOnClickListener(this);
//        findViewById(R.id.btn_screen_draw).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recyclerview:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.btn_notification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;
            case R.id.btn_cardview:
                startActivity(new Intent(MainActivity.this, CardViewActivity.class));
                break;
//            case R.id.btn_slideview:
//                startActivity(new Intent(MainActivity.this, ViewSlideActivity.class));
//                break;
//
//            case R.id.btn_screen_draw:
//                startActivity(new Intent(MainActivity.this, ScreenActivity.class));
//                break;

        }
    }
    @NetworkCheck
    public void hasNetwork(){
        //有网操作
    }
    @NoNetworkShow
    public void noNetwork(){
        //无网操作
    }

    @LogRecord("登陆操作")//日志埋点
    public void doLogin(){

    }
}
