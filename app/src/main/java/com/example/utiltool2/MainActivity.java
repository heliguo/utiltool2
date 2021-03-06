package com.example.utiltool2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bigkoo.pickerview.view.TimePickerView;
import com.example.utiltool2.adapter.RViewAdapterActivity;
import com.example.utiltool2.annotations.AnnotationActivity;
import com.example.utiltool2.aspectjannotation.LogRecord;
import com.example.utiltool2.aspectjannotation.NetworkCheck;
import com.example.utiltool2.aspectjannotation.NoNetworkShow;
import com.example.utiltool2.aspectjannotation.PermissionDenied;
import com.example.utiltool2.aspectjannotation.PermissionDeniedForever;
import com.example.utiltool2.aspectjannotation.PermissionNeed;
import com.example.utiltool2.decorator.DecoratorActivity;
import com.example.utiltool2.download.DownLoadCompleteReceiver;
import com.example.utiltool2.download.DownloadUtil;
import com.example.utiltool2.download.InstallApkUtils;
import com.example.utiltool2.exam.ExamSystem;
import com.example.utiltool2.examination.TabLayoutActivity;
import com.example.utiltool2.glide.GlideActivity;
import com.example.utiltool2.ipc.client.ClientActivity;
import com.example.utiltool2.permission.PermissionSettingUtil;
import com.example.utiltool2.signature.SignatureActivity;
import com.example.utiltool2.ui.ImageLoad.ImageLoadActivity;
import com.example.utiltool2.ui.ScrollTable.listview.ScrollTableActivity;
import com.example.utiltool2.ui.ScrollTable.recyclerview.ScrolltableActivity;
import com.example.utiltool2.ui.SelfImageView;
import com.example.utiltool2.ui.TreeViewActivity;
import com.example.utiltool2.ui.WeChatNavigation.WeChatBottomNavigationActivity;
import com.example.utiltool2.ui.cardview.CardViewActivity;
import com.example.utiltool2.ui.explosion.Impl.ExplosionActivity;
import com.example.utiltool2.ui.huawei_loading.LoadingDialog;
import com.example.utiltool2.ui.notification.NotificationActivity;
import com.example.utiltool2.ui.recyclerview.RecyclerViewActivity;
import com.example.utiltool2.ui.slideview.ViewSlideActivity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import timemonitor.TimeMonitorConfig;
import timemonitor.TimeMonitorManager;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    TimePickerView view;
    private DownLoadCompleteReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).
                recordingTimeTag("MainActivity-onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_recyclerview).setOnClickListener(this);
        findViewById(R.id.btn_notification).setOnClickListener(this);
        findViewById(R.id.btn_cardview).setOnClickListener(this);
        findViewById(R.id.btn_screen_draw).setOnClickListener(this);
        findViewById(R.id.btn_slideview).setOnClickListener(this);
        findViewById(R.id.btn_glide).setOnClickListener(this);
        findViewById(R.id.btn_treeview).setOnClickListener(this);
        findViewById(R.id.btn_recyclerview_adapter).setOnClickListener(this);
        findViewById(R.id.btn_ipc).setOnClickListener(this);
        findViewById(R.id.btn_choose_picture).setOnClickListener(this);
        findViewById(R.id.btn_WeChat).setOnClickListener(this);
        findViewById(R.id.btn_scrolltable).setOnClickListener(this);
        findViewById(R.id.btn_rv_scrolltable).setOnClickListener(this);
        findViewById(R.id.btn_annotation).setOnClickListener(this);
        findViewById(R.id.btn_explosion).setOnClickListener(this);
        findViewById(R.id.btn_check_update).setOnClickListener(this);
        //        findViewById(R.id.btn_decorator).setOnClickListener(this);
        SelfImageView iv = findViewById(R.id.self_iv);
        //        Glide.with(this).load(R.drawable.kcb_picker_pic_call_add).into(iv);
        iv.setBackgroundResource(R.drawable.kcb_picker_pic_call_add);
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START)
                .recordingTimeTag("MainActivity-onCreate-Over");
        int scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Log.e("scaledTouchSlop", "onCreate: " + scaledTouchSlop);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).
                end("MainActivity-onStart", false);

    }

    private boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);


        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        return permission == PackageManager.PERMISSION_GRANTED;
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
                startActivity(new Intent(MainActivity.this, ImageLoadActivity.class));
                break;
            case R.id.btn_glide:
                startActivity(new Intent(MainActivity.this, GlideActivity.class));
                break;
            case R.id.btn_treeview:
                startActivity(new Intent(MainActivity.this, TreeViewActivity.class));

                break;
            case R.id.btn_recyclerview_adapter:
                startActivity(new Intent(MainActivity.this, RViewAdapterActivity.class));
                break;

            case R.id.btn_ipc:
                startActivity(new Intent(MainActivity.this, ClientActivity.class));
                break;
            case R.id.btn_choose_picture:
                testPermission();
                break;
            case R.id.btn_scrolltable:
                startActivity(new Intent(this, ScrollTableActivity.class));
                break;
            case R.id.btn_WeChat:
                weChat(v);
                break;
            case R.id.btn_rv_scrolltable:
                startActivity(new Intent(this, ScrolltableActivity.class));
                break;
            case R.id.btn_annotation:
                startActivity(new Intent(this, AnnotationActivity.class));
                break;
            case R.id.btn_explosion:

                startActivity(new Intent(this, ExplosionActivity.class));
                break;

            case R.id.btn_check_update:

                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );

                int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "程序缺少权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                File file = new File(getExternalFilesDir("Download").getAbsolutePath()
                        + File.separator + "update.apk");
                if (file.exists()) {
                    InstallApkUtils.install(this, "update.apk");
                } else {
                    DownloadUtil.downloadApk(this, "http://192.168.199.198:8866/examples/app.apk", null);
                    mReceiver = new DownLoadCompleteReceiver();
                    IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                    registerReceiver(mReceiver, filter);
                }


                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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


    @PermissionNeed(
            permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_CALENDAR,
                    Manifest.permission.CAMERA, Manifest.permission.BODY_SENSORS}, requestCode = 1)
    private void testPermission() {
        Log.e("Permission", "Activity:权限获取成功: ");
    }

    @PermissionDenied
    private void permissionDenied(int requestCode) {
        switch (requestCode) {
            case 1:
                Log.e("Permission", "Activity:权限被拒绝: ");
                break;
            default:
                break;
        }
    }

    @PermissionDeniedForever
    private void permissionDeniedForever(int requestCode) {
        switch (requestCode) {
            case 1:
                Log.e("Permission", "Activity:权限被永久拒绝: ");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("权限不够");
                builder.setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionSettingUtil.gotoPermission(MainActivity.this);
                    }
                });
                builder.show();

                break;
            default:
                break;
        }
    }

    public void decorator(View view) {
        startActivity(new Intent(this, DecoratorActivity.class));
    }

    public void weChat(View view) {
        startActivity(new Intent(this, WeChatBottomNavigationActivity.class));

    }

    public void loading(View view) {
        //        startActivity(new Intent(this, LoadingActivity.class));
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.setBackgroundColor(R.color.transparent);
        loadingDialog.show();
        try {
            FileInputStream fileInputStream = new FileInputStream("");
            FileDescriptor fd = fileInputStream.getFD();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
