package com.example.utiltool2.glide;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author:lgh on 2019-11-12 13:22
 */
public class ProgressInterceptor implements Interceptor {

    static final HashMap<String, ProgressListener> listenerMap = new HashMap<>();

    public static void addListener(String url, ProgressListener listener) {
        listenerMap.put(url, listener);
    }

    public static void removeListener(String url) {
        listenerMap.remove(url);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        System.out.println("intercept:1111 " + url);
        Log.d("=======>", "intercept:1111 " + url);
        ResponseBody responseBody = new ProgressResponseBody(url, body);
        Log.d("=======>", "intercept: " + responseBody.contentLength());
        Response newResponse = response.newBuilder().body(responseBody).build();
        return newResponse;
    }
}
