package com.example.utiltool2.net.broadcastreceiver;

/**
 * author:lgh on 2019-10-28 10:20
 */
public class NetworkChangeEvent {
    public boolean isConnected; //是否存在网络

    public NetworkChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
