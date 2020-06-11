package com.example.annotationlibrary;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * author:lgh on 2019-09-18 15:48
 */
public class ListenerInvocationHandler implements InvocationHandler {

    //拦截对象本应执行的方法
    private Object target;
    //键值对 key: 本应执行的方法 value:用户自定义的方法
    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (target != null) {
            String name = method.getName();
            method = methodHashMap.get(name);//如果集合中有需要拦截的方法,直接赋值
            if (method != null) {
                return method.invoke(target, objects);
            }
        }
        return null;
    }

    /**
     * 拦截的方法和替换执行的方法
     *
     * @param methodName 拦截的方法名如：onClick()
     * @param method     执行自定义的方法：onShow()
     */
    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }
}
