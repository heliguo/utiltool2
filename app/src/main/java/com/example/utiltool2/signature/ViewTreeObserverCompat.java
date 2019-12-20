package com.example.utiltool2.signature;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.ViewTreeObserver;

/**
 * author:lgh on 2019-12-19 11:46
 */
public class ViewTreeObserverCompat {
    /**
     * Remove a previously installed global layout callback.
     * @param observer the view observer
     * @param victim the victim
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener victim) {
        // Future (API16+)...
        if (Build.VERSION.SDK_INT >= 16) {
            observer.removeOnGlobalLayoutListener(victim);
        }
        // Legacy...
        else {
            observer.removeGlobalOnLayoutListener(victim);
        }
    }
}