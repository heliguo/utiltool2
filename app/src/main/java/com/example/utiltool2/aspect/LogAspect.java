package com.example.utiltool2.aspect;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.utiltool2.annotation.LogRecord;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author:lgh on 2019-11-06 10:21
 * 日志埋点
 */
@Aspect
public class LogAspect {

    private final String TAG = LogAspect.class.getSimpleName();

    @Pointcut("execution(@com.example.utiltool2.annotation.LogRecord * *(...)) && @annotation(log)")
    public void pointAnctionMethod(LogRecord log) {

    }

    @Around("pointAnctionMethod(log)")
    public void aProceeedingJoinPoint(final ProceedingJoinPoint proceedingJoinPoint, LogRecord log) throws Throwable {
        //初始化
        Context context = null;
        final Object object = proceedingJoinPoint.getThis();

        //activity、fragment、view
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        }

        if (context == null) {
            throw new IllegalAccessException("context is null");
        }
        //日志文件保存
        String value = log.value();
        Log.d(TAG, "aProceeedingJoinPoint: " + value);
    }
}
