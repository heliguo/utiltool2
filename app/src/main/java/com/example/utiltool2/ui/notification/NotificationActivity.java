package com.example.utiltool2.ui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.MainActivity;
import com.example.utiltool2.R;

/**
 * author:lgh on 2019-10-24 09:18
 */
public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private NotificationManager notificationManager;

    public NotificationActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        TextView tv_nomal = findViewById(R.id.tv_nomal);
        TextView tv_fold = findViewById(R.id.tv_fold);
        TextView tv_hang = findViewById(R.id.tv_hang);
        radioGroup = findViewById(R.id.rg_all);
        tv_nomal.setOnClickListener(this);
        tv_fold.setOnClickListener(this);
        tv_hang.setOnClickListener(this);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nomal:
                sendNomalNotification();
                break;
            case R.id.tv_fold:
                sendFoldNotification();
                break;

            case R.id.tv_hang:
                sendHangNotification();
                break;
        }
    }

    private void sendNomalNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pi);
        builder.setSmallIcon(R.drawable.lanucher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("普通通知");
        selectNotofovatiomLevel(builder);
        notificationManager.notify(0, builder.build());
    }

    private void sendFoldNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.foldleft);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("折叠式通知");
        selectNotofovatiomLevel(builder);
        //用RemoteViews来创建自定义Notification视图
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_fold);
        Notification notification = builder.build();

        //指定展开时的视图
        notification.bigContentView = remoteViews;
        notificationManager.notify(1, notification);

    }

    private void sendHangNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.foldleft);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        selectNotofovatiomLevel(builder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK版本大于等于21才有悬挂式通知栏
            //设置点击跳转
            Intent hangIntent = new Intent();
            hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            hangIntent.setClass(this, MainActivity.class);//设置弹出内容
            //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
            PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setFullScreenIntent(hangPendingIntent, true);//悬挂式通知  悬挂在手机屏上方
            final String notifyTag = 2 + "jpush";//由于同一条消息  id 一样  ，有针对悬挂式通知打了一个tag；
            notificationManager.notify(notifyTag, 2, builder.build());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);//五秒后悬挂式通知消失
                        notificationManager.cancel(notifyTag, 2);//按tag id 来清除消息
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    private void selectNotofovatiomLevel(Notification.Builder builder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_public:
                    builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                    builder.setContentText("public");
                    break;
                case R.id.rb_private:
                    builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                    builder.setContentText("private");
                    break;
                case R.id.rb_secret:
                    builder.setVisibility(Notification.VISIBILITY_SECRET);
                    builder.setContentText("secret");
                    break;
                default:
                    break;
            }
        }

    }
}
