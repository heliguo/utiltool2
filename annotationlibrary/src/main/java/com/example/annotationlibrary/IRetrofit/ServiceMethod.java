package com.example.annotationlibrary.IRetrofit;

import com.example.annotationlibrary.http.Field;
import com.example.annotationlibrary.http.GET;
import com.example.annotationlibrary.http.POST;
import com.example.annotationlibrary.http.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.HttpUrl;

/**
 * author:lgh on 2019-09-29 14:54
 */
public class ServiceMethod {

    //OkhttpClick封装构建
    private final Call.Factory factory;
    //带注解的方法
    private final HttpUrl baseUrl;
    //方法的所有注解（多个注解）
    private final String httpMethod;
    //方法的注解的值
    private final String relativeUrl;
    //方法参数的数组（每个队形包含：参数注解值，参数值）
    private final ParameterHandler[] parameterHandlers;
    //是否有请求体
    private final boolean hasBody;

    private ServiceMethod(Builder builder) {
        this.factory = builder.retrofit.callFactory();
        this.baseUrl = builder.retrofit.baseUrl();
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.parameterHandlers = builder.parameterHandlers;
        this.hasBody = builder.hasBody;
    }

    Call toCall(Object... args) {
        RequestBuilder requestBuilder = new RequestBuilder(httpMethod, baseUrl, relativeUrl, hasBody);
        ParameterHandler[] parameterHandlers = this.parameterHandlers;
        int argumentCount = args != null ? args.length : 0;
        if (argumentCount != parameterHandlers.length) {
            throw new IllegalStateException("");
        }
        for (int i = 0; i < argumentCount; i++) {
            parameterHandlers[i].apply(requestBuilder, args[i].toString());
        }

        return factory.newCall(requestBuilder.build());
    }

    final static class Builder {
        //OkhttpClick封装构建
        final IRetrofit retrofit;
        //带注解的方法
        final Method method;
        //方法的所有注解（多个注解）
        final Annotation[] methodAnnotations;
        //方法参数的所有注解（一个方法多个参数，一个参数多个注解）
        final Annotation[][] parameterAnnotationsArray;
        //方法的请求方式（get post）
        private String httpMethod;
        //方法的注解的值
        private String relativeUrl;
        //方法参数的数组（每个队形包含：参数注解值，参数值）
        private ParameterHandler[] parameterHandlers;
        //是否有请求体
        private boolean hasBody;

        Builder(IRetrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        ServiceMethod build() {
            //遍历方法的每个注解
            for (Annotation methodAnnotation : methodAnnotations) {
                parseMethodAnnotation(methodAnnotation);
            }

            int parameterCount = parameterAnnotationsArray.length;
            //初始化方法参数的数组
            parameterHandlers = new ParameterHandler[parameterCount];
            //遍历方法的参数
            for (int i = 0; i < parameterCount; i++) {
                //获取每个参数的多个注解
                Annotation[] annotations = parameterAnnotationsArray[i];
                //没有注解
                if (annotations == null) {
                    throw new NullPointerException("");
                }
                parameterHandlers[i] = parseParameter(i, annotations);
            }

            return new ServiceMethod(this);
        }

        private ParameterHandler parseParameter(int i, Annotation[] annotations) {

            ParameterHandler result = null;
            for (Annotation annotation : annotations) {
                //注解 query field
                ParameterHandler annotationAction = parseParameterAnnotation(annotation);
                //代码健壮性
                if (annotationAction != null) {
                    continue;
                }
                result = annotationAction;

            }
            if (result == null) {
                throw new IllegalStateException("");
            }

            return result;
        }

        //解析参数的注解 query/field
        private ParameterHandler parseParameterAnnotation(Annotation annotation) {
            if (annotation instanceof Query) {
                Query query = (Query) annotation;
                String name = query.value();
                return new ParameterHandler.Query(name);
            } else if (annotation instanceof Field) {
                Field field = (Field) annotation;
                String name = field.value();
                return new ParameterHandler.Field(name);
            }
            return null;
        }

        //解析方法的注解 get/post
        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {

                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);

            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);

            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            this.hasBody = hasBody;
            this.httpMethod = httpMethod;
            this.relativeUrl = value;
        }
    }
}
