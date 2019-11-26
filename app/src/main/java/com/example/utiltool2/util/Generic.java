package com.example.utiltool2.util;

/**
 * author:lgh on 2019-11-26 09:11
 * 比较大小
 */
public class Generic {

    //返回max
    public static <T extends Comparable<T>> T max(T[] arr) {
        T max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[1].compareTo(max) > 0) {
                max = arr[i];
            }
        }
        return max;
    }

    //返回min
    public static <T extends Comparable<T>> T min(T[] arr) {
        T min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[1].compareTo(min) < 0) {
                min = arr[i];
            }
        }
        return min;
    }

}
