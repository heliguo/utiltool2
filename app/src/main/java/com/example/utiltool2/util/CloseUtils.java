package com.example.utiltool2.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author lgh on 2020/9/17:17:26
 * @description io close quitly
 */
public class CloseUtils {

    private CloseUtils() {

    }

    public static void closeQuitly(Closeable closeable) {

        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
