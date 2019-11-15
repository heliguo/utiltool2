package com.example.utiltool2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:lgh on 2019-11-06 08:54
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //面向切面编程
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });

    }
}
