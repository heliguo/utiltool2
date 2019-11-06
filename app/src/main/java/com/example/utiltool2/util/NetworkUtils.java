package com.example.utiltool2.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

/**
 * author:lgh on 2019-11-06 09:15
 */
public class NetworkUtils {

    public NetworkUtils() {
        throw new UnsupportedOperationException("不能初始化");
    }

    //判断网络是否打开
    public static boolean isNetworkAvailable(Context context) {

        boolean isAvailable = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            isAvailable = true;
        }
        return isAvailable;
    }

    //跳转网络设置界面
    public static void goNetworkSetting(Context context){

        Intent intent = null;
        if (Build.VERSION.SDK_INT>10){//必须大于3.0
            intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        }else {
            intent = new Intent();
            intent.setClassName("com.android.settings",
                    "com.android.settings.WirelessSettings");
        }
        context.startActivity(intent);

    }
}
