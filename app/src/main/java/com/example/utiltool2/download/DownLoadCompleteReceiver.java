package com.example.utiltool2.download;

/**
 * @author lgh on 2020/10/21:19:42
 * @description
 */

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

/**
 * 下载完成广播
 */
public class DownLoadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("=======", "onReceive: " + id);
            DownloadManager.Query query = new DownloadManager.Query();
            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            query.setFilterById(id);
            Cursor c = dm.query(query);
            if (c != null) {
                try {
                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            ContentResolver contentResolver = context.getContentResolver();
                            String filePath;
//                            contentResolver.openFileDescriptor()
                            String url = c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_URI));
                            Log.e("url", "onReceive: "+url );
                            filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                            Log.e("======", "onReceive: " + filePath);
                            InstallApkUtils.install(context, "update.apk");
                        }
                        if (status == DownloadManager.STATUS_FAILED) {
                            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    c.close();
                }

            }
        }
    }

}
