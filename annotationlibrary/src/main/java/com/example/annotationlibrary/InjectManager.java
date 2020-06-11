package com.example.annotationlibrary;

import android.app.Activity;
import android.view.View;

import com.example.annotationlibrary.annotations.ContentView;
import com.example.annotationlibrary.annotations.EventsBase;
import com.example.annotationlibrary.annotations.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author:lgh on 2019-09-18 11:19
 */
public class InjectManager {

    public static void inject(Activity activity) {
        //布局注入
        injectLayout(activity);
        injectViews(activity);
        injectEvent(activity);
    }

    /**
     * 布局
     *
     * @param activity
     */
    public static void injectLayout(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类之上的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            //获取注解的值
            int layoutId = contentView.value();
            try {
                //获取setContentView()方法
                Method method = clazz.getMethod("setContentView", int.class);
                //执行方法
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 属性
     *
     * @param activity
     */
    private static void injectViews(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有属性（包含私有属性）
        Field[] fields = clazz.getDeclaredFields();
        //遍历处理每个属性
        for (Field field : fields) {
            //筛选含有InjectView注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                int viewId = injectView.value();
                Method method = null;
                try {
                    method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    field.setAccessible(true);//将私有属性设置可以访问
                    //设置该属性的返回值，对应 btn = findViewById(R.id.btn);
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }


    /**
     * 事件
     *
     * @param activity
     */
    private static void injectEvent(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        //遍历方法
        for (Method method : methods) {
            //获取注解
            Annotation[] annotations = method.getDeclaredAnnotations();
            //遍历注解
            for (Annotation annotation : annotations) {
                //获取注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    EventsBase eventsBase = annotationType.getAnnotation(EventsBase.class);
                    if (eventsBase != null) {
                        //获取事件的三个信息

                        //方法名 setOnClickListener
                        String listenerSetter = eventsBase.listenerSetter();
                        //事件类 view.OnClickListener.class
                        Class<?> listenerType = eventsBase.listenerType();
                        //回调事件 onClick
                        String callBackListener = eventsBase.callBackListener();
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);//得到属性数组

                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callBackListener,method);

                            //代理方式，注解是什么就匹配对应的监听方法和回调,拦截onclick方法执行用户自定义方法
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                            //控件没有赋值,不能 btn.setOnClickListener
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                setter.invoke(view,listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }
}
