package com.example.annotationlibrary.IRetrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * author:lgh on 2019-09-29 14:24
 */
public class IRetrofit {
    //请求地址
    private HttpUrl baseUrl;
    //OKHttpClient唯一接口实现类
    private Call.Factory callFactory;
    //缓存请求的方法
    private final Map<Method,ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    private IRetrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.callFactory = builder.callFactory;
    }

    //对外提供get方法

    public HttpUrl baseUrl() {
        return baseUrl;
    }

    public Call.Factory callFactory() {
        return callFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {

            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                //收集开发者需要请求的接口方法的信息
                ServiceMethod serviceMethod = loadServiceMethod(method);
                return new OkHttpCall(serviceMethod,objects);
            }
        });
    }

    //获取方法所有内容，方法名
    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result!=null){
            return result;
        }
        //线程安全同步锁
        synchronized (serviceMethodCache){
            result = serviceMethodCache.get(method);
            if (result==null){
                result=new ServiceMethod.Builder(this,method).build();
                serviceMethodCache.put(method,result);
            }
        }
        return result;
    }

    public static class Builder {
        //请求地址
        private HttpUrl baseUrl;
        //OKHttpClient唯一接口实现类
        private Call.Factory callFactory;

        //对外提供API
        public Builder baseUrl(String baseUrl) {
            if (baseUrl.isEmpty()) {
                throw new NullPointerException("baseUrl == null");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(HttpUrl baseUrl) {
            if (baseUrl == null) {
                throw new NullPointerException("baseUrl == null");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder callFactory(Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        //构建者模式最终为 .build()  .create()  .
        //属性校验，不为空做出适合操作
        public IRetrofit build() {
            if (this.baseUrl == null) {
                throw new IllegalStateException("Base Url required");
            }
            if (this.callFactory == null) {
                //初始化赋值
                callFactory = new OkHttpClient();
            }
            return new IRetrofit(this);
        }
    }
}
