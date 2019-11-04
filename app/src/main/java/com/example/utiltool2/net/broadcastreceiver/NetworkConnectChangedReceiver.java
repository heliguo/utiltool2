package com.example.utiltool2.net.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.utiltool2.util.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * author:lgh on 2019-10-28 10:13
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkConnectChanged";

    @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用*/
        boolean isConnected = NetUtils.isConnected(context);
        Log.d(TAG, "onReceive: 当前网络 " + isConnected);
        EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }
}
