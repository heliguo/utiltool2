package com.example.utiltool2.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author lgh on 2020/10/22:14:18
 * @description
 */
public class InstallApkUtils {

    /**
     * 使用系统下载，默认路径
     *
     * @param context context
     * @param appName name
     */
    public static void install(Context context, String appName) {
        install(context, context.getExternalFilesDir("Download").getAbsolutePath(), appName);
    }

    public static void install(Context context, String filePath, String appName) {

        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File fileName = new File(filePath + File.separator + appName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".FileProvider", fileName);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(uri,
                    "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(Uri.parse("file://" + fileName),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(install);
        android.os.Process.killProcess(android.os.Process.myPid());

    }

}
