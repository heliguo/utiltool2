package com.example.utiltool2.aspect;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.utiltool2.annotation.NoNetworkShow;
import com.example.utiltool2.util.NetworkUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * author:lgh on 2019-11-06 09:27
 */
@Aspect
public class NetworkCheckApspect {

    //进行一个切点
    @Pointcut("execution(@com.example.utiltool2.annotation.NetworkCheck * *(..))")
    public void pointActionMethod() {
    }

    //切面处理
    @Around("pointActionMethod()")
    public void aProceeedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //拿到环境
        Context context = null;//初始化Context ，兼容性处理

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
        //判断是否有网络
        if (NetworkUtils.isNetworkAvailable(context)) {
            //网络正常，保证方法执行
            proceedingJoinPoint.proceed();
        } else {
            //操作1
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            //操作2 @NoNetworkShow
            Class<?> aClass = object.getClass();
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                declaredMethod.setAccessible(true);//获取private 方法
                //符合@NoNetworkShow注解
                boolean annotationPresent = declaredMethod.isAnnotationPresent(NoNetworkShow.class);
                if (annotationPresent) {
                    declaredMethod.invoke(object);
                }
            }
            //操作3 跳转网络设置
//            NetworkUtils.goNetworkSetting(context);
        }
        //是否使用@NetworkCheck 注解

    }

}
