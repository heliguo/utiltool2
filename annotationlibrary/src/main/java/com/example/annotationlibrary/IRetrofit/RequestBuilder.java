package com.example.annotationlibrary.IRetrofit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * author:lgh on 2019-09-29 15:41
 */
public class RequestBuilder {

    private final String httpMethod;
    private final HttpUrl baseUrl;
    private String relativeUrl;
    private HttpUrl.Builder urlBuider;
    private FormBody.Builder formBuidler;
    private final Request.Builder requestBuidler;

    public RequestBuilder(String httpMethod, HttpUrl baseUrl, String relativeUrl, boolean hasBody) {
        this.httpMethod = httpMethod;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;
        requestBuidler = new Request.Builder();
        if (hasBody) {
            formBuidler = new FormBody.Builder();
        }
    }

    //拼接get
    void addQueryParam(String name, String value) {
        if (relativeUrl != null) {
            urlBuider = baseUrl.newBuilder(relativeUrl);
            relativeUrl = null;
        }
        urlBuider.addQueryParameter(name, value);
    }


    void addFormField(String name, String value) {
        formBuidler.add(name, value);
    }

    Request build() {
        HttpUrl url;
        if (urlBuider != null) {
            url = urlBuider.build();
        } else {
            url = baseUrl.resolve(relativeUrl);
            if (url == null) {
                throw new IllegalStateException("");

            }
        }
        RequestBody body = null;
        if (formBuidler != null) {
            body = formBuidler.build();
        }

        return requestBuidler
                .url(url)
                .method(httpMethod, body)
                .build();
    }
}
