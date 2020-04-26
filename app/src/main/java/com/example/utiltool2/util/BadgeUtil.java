package com.example.utiltool2.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.utiltool2.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * author:lgh on 2020-04-10 15:24
 * <p> xiaomi 需 Notification 图标
 * <p>
 * <!--for android-->
 * <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS"/>
 * <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS"/>
 * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
 * <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
 * <p>
 * <!--  for HuaWei  -->
 * <uses-permission android:name="com.huawei.android.launcher.permission.CHANGE_BADGE" />
 * <uses-permission android:name="com.huawei.android.launcher.permission.READ_SETTINGS" />
 * <uses-permission android:name="com.huawei.android.launcher.permission.WRITE_SETTINGS" />
 * <uses-permission android:name="android.permission.WRITE_APP_BADGE" />
 * <p>
 * <!--for Samsung-->
 * <uses-permission android:name="com.sec.android.provider.badge.permission.READ" />
 * <uses-permission android:name="com.sec.android.provider.badge.permission.WRITE" />
 * <p>
 * <!--for htc-->
 * <uses-permission android:name="com.htc.launcher.permission.READ_SETTINGS" />
 * <uses-permission android:name="com.htc.launcher.permission.UPDATE_SHORTCUT" />
 * <p>
 * <!--for sony-->
 * <uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE" />
 * <uses-permission android:name="com.sonymobile.home.permission.PROVIDER_INSERT_BADGE" />
 * <p>
 * <!--for apex-->
 * <uses-permission android:name="com.anddoes.launcher.permission.UPDATE_COUNT" />
 * <p>
 * <!--for solid-->
 * <uses-permission android:name="com.majeur.launcher.permission.UPDATE_BADGE" />
 * <p>
 * <!--for ZUK-->
 * <uses-permission android:name="android.permission.READ_APP_BADGE" />
 * <p>
 * <!--for OPPO-->
 * <uses-permission android:name="com.oppo.launcher.permission.READ_SETTINGS" />
 * <uses-permission android:name="com.oppo.launcher.permission.WRITE_SETTINGS" />
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 * <p>
 * <!--for EvMe-->
 * <uses-permission android:name="me.everything.badger.permission.BADGE_COUNT_READ" />
 * <uses-permission android:name="me.everything.badger.permission.BADGE_COUNT_WRITE" />
 */


public class BadgeUtil {

    private BadgeUtil() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    /**
     * 设置Badge 目前支持Launcher
     */
    public static void setBadgeCount(Context context, int count) {
        if (count <= 0) {
            count = 0;
        } else {
            count = Math.max(0, Math.min(count, 99));
        }

        //制造商
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
            setBadgeOfMIUI(context, count, R.drawable.lanucher);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
            setBadgeOfSony(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("samsung") ||
                Build.MANUFACTURER.equalsIgnoreCase("lg")) {
            setBadgeOfSumsung(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("htc")) {
            setBadgeOfHTC(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("nova")) {
            setBadgeOfNova(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {//oppo
            setBadgeOfOPPO(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("LeMobile")) {//乐视

        } else if (Build.MANUFACTURER.equalsIgnoreCase("vivo")) {
            setBadgeOfVIVO(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") ||
                Build.BRAND.equalsIgnoreCase("HONOR")) {//华为
            setHuaweiBadge(context, count);
//        } else if (Build.MANUFACTURER.equalsIgnoreCase("")) {//魅族
//
//        } else if (Build.MANUFACTURER.equalsIgnoreCase("")) {//金立
//
//        } else if (Build.MANUFACTURER.equalsIgnoreCase("")) {//锤子
        } else {
            Log.e("Badge", "can not find manufacturer");
        }
    }

    /**
     * 设置MIUI的Badge
     */
    private static void setBadgeOfMIUI(Context context, int count, int iconResId) {

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.app_name))
                .setContentText("账号信息需要更新").setSmallIcon(iconResId);
        Notification notification = builder.build();
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNotificationManager.notify(0, notification);
    }

    /**
     * 设置索尼的Badge
     */
    private static void setBadgeOfSony(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        boolean isShow = true;
        if (count == 0) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());//包名
        context.sendBroadcast(localIntent);
    }

    /**
     * 设置三星的Badge\设置LG的Badge
     */
    private static void setBadgeOfSumsung(Context context, int count) {
        // 获取你当前的应用
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    /**
     * 设置HTC的Badge
     */
    private static void setBadgeOfHTC(Context context, int count) {
        Intent intentNotification = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
        ComponentName localComponentName = new ComponentName(context.getPackageName(), getLauncherClassName(context));
        intentNotification.putExtra("com.htc.launcher.extra.COMPONENT", localComponentName.flattenToShortString());
        intentNotification.putExtra("com.htc.launcher.extra.COUNT", count);
        context.sendBroadcast(intentNotification);

        Intent intentShortcut = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
        intentShortcut.putExtra("packagename", context.getPackageName());
        intentShortcut.putExtra("count", count);
        context.sendBroadcast(intentShortcut);
    }

    /**
     * 设置Nova的Badge
     */
    private static void setBadgeOfNova(Context context, int count) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tag", context.getPackageName() + "/" + getLauncherClassName(context));
        contentValues.put("count", count);
        context.getContentResolver().insert(Uri.parse("content://com.teslacoilsw.notifier/unread_count"),
                contentValues);
    }

    /**
     * 设置vivo的Badge :vivoXplay5 vivo x7无效果
     */
    private static void setBadgeOfVIVO(Context context, int count) {
        try {
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            intent.putExtra("className", launchClassName);
            intent.putExtra("notificationNum", count);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置oppo的Badge :oppo角标提醒目前只针对内部软件还有微信、QQ开放，其他的暂时无法提供
     */
    private static void setBadgeOfOPPO(Context context, int count) {
        try {

            Intent intent = new Intent("com.oppo.unsettledevent");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("number", count);
            intent.putExtra("upgradeNumber", count);
            if (canResolveBroadcast(context, intent)) {
                context.sendBroadcast(intent);
            } else {
                try {
                    Bundle extras = new Bundle();
                    extras.putInt("app_badge_count", count);
                    context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"),
                            "setAppBadgeCount", null, extras);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

//            Bundle extras = new Bundle();
//            extras.putInt("app_badge_count", count);
//            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", String.valueOf(count), extras);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean canResolveBroadcast(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
        return receivers != null && receivers.size() > 0;
    }

    /**
     * 设置华为的Badge :mate8 和华为 p7,honor畅玩系列可以,honor6 plus 无效果
     * 华为手机更新应用桌面角标需要的权限
     * <uses-permission android:name="com.huawei.android.launcher.permission.CHANGE_BADGE"/>
     * <uses-permission android:name="com.huawei.android.launcher.permission.READ_SETTINGS"/>
     * <uses-permission android:name="com.huawei.android.launcher.permission.WRITE_SETTINGS"/>
     */
    private static void setHuaweiBadge(Context context, int count) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("package", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            bundle.putString("class", launchClassName);
            bundle.putInt("badgenumber", count);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setBadgeOfMadMode(Context context, int count, String packageName, String className) {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", packageName);
        intent.putExtra("badge_count_class_name", className);
        context.sendBroadcast(intent);
    }

    private static void setBadgeOfMadMode(Context context, int count) {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name",
                context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName());
        context.sendBroadcast(intent);
    }


    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        return info.activityInfo.name;
    }
}
