package com.example.utiltool2.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.util.Map;

/**
 * @author lgh on 2020/10/21:19:27
 * @description
 */
public class DownloadUtil {

    public static void downloadApk(Context context, String url, Map<String, String> params) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        if (params != null && params.size() != 0)
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request.addRequestHeader(entry.getKey(), entry.getValue());
            }
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏的标题
        request.setTitle("更新");
        //设置通知栏的message
        request.setDescription("正在下载" + context.getString(com.example.rvlibrary.R.string.app_name));
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //获取系统服务
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //进行下载
        long id = downloadManager.enqueue(request);
        Log.e("========", "downloadApk: " + id);

    }

}
