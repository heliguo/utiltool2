package com.example.utiltool2.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.Nullable;

/**
 * 从当前上下文获取Activity
 * author:lgh on 2019-11-18 11:23
 */
public class ActivityUtils {

    @Nullable
    static Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }

    static void finishActivity(Context context) {
        Activity activity = getActivity(context);
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }
}
