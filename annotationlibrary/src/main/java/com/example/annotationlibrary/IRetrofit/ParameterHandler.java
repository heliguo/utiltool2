package com.example.annotationlibrary.IRetrofit;

/**
 * author:lgh on 2019-09-29 15:40
 * 保存参数的注解值，参数值，拼接请求
 */
abstract class ParameterHandler {
    /**
     * 抽象方法，外部赋值和调用，自己的内部类实现
     *
     * @param builder 请求构建者（拼装者）
     * @param value   方法的参数值
     */
    abstract void apply(RequestBuilder builder, String value);

    static final class Query extends ParameterHandler {
        private String name;

        Query(String name) {
            if (name.isEmpty()) {
                throw new NullPointerException("");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if (value == null) return;
            builder.addQueryParam(name,value);
        }
    }

    static final class Field extends ParameterHandler {
        private String name;

        Field(String name) {
            if (name.isEmpty()) {
                throw new NullPointerException("");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            if (value == null) return;
            builder.addFormField(name,value);
        }

    }

}
