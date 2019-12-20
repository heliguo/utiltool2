package com.example.utiltool2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.bigkoo.pickerview.view.TimePickerView;
import com.example.utiltool2.adapter.RecyclerAdapterActivity;
import com.example.utiltool2.annotation.LogRecord;
import com.example.utiltool2.annotation.NetworkCheck;
import com.example.utiltool2.annotation.NoNetworkShow;
import com.example.utiltool2.exam.ExamSystem;
import com.example.utiltool2.examination.TabLayoutActivity;
import com.example.utiltool2.glide.GlideActivity;
import com.example.utiltool2.ipc.client.ClientActivity;
import com.example.utiltool2.signature.SignatureActivity;
import com.example.utiltool2.ui.ScreenActivity;
import com.example.utiltool2.ui.TreeViewActivity;
import com.example.utiltool2.ui.cardview.CardViewActivity;
import com.example.utiltool2.ui.notification.NotificationActivity;
import com.example.utiltool2.ui.recyclerview.RecyclerViewActivity;
import com.example.utiltool2.ui.slideview.ViewSlideActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    TimePickerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);//权限申请
        findViewById(R.id.btn_recyclerview).setOnClickListener(this);
        findViewById(R.id.btn_notification).setOnClickListener(this);
        findViewById(R.id.btn_cardview).setOnClickListener(this);
        findViewById(R.id.btn_screen_draw).setOnClickListener(this);
        findViewById(R.id.btn_slideview).setOnClickListener(this);
        findViewById(R.id.btn_glide).setOnClickListener(this);
        findViewById(R.id.btn_treeview).setOnClickListener(this);
        findViewById(R.id.btn_recyclerview_adapter).setOnClickListener(this);
        findViewById(R.id.btn_ipc).setOnClickListener(this);
    }

    private void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
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
            case R.id.btn_slideview:
                startActivity(new Intent(MainActivity.this, ViewSlideActivity.class));
                break;

            case R.id.btn_screen_draw:
                startActivity(new Intent(MainActivity.this, ScreenActivity.class));
                break;
            case R.id.btn_glide:
                startActivity(new Intent(MainActivity.this, GlideActivity.class));
                break;
            case R.id.btn_treeview:
                startActivity(new Intent(MainActivity.this, TreeViewActivity.class));

                break;
            case R.id.btn_recyclerview_adapter:
                startActivity(new Intent(MainActivity.this, RecyclerAdapterActivity.class));
                break;

            case R.id.btn_ipc:
                startActivity(new Intent(MainActivity.this, ClientActivity.class));
                break;
        }
    }

    @NetworkCheck
    public void hasNetwork() {
        //有网操作
    }

    @NoNetworkShow
    public void noNetwork() {
        //无网操作
    }

    @LogRecord("登陆操作")//日志埋点
    public void doLogin() {

        int processors = Runtime.getRuntime().availableProcessors();

    }

    public void onViewpager(View view) {
        startActivity(new Intent(this, TabLayoutActivity.class));
    }

    public void toolbar(View view) {
        startActivity(new Intent(this, ExamSystem.class));
    }

    public void signature(View view) {
        startActivity(new Intent(this, SignatureActivity.class));
    }
}
