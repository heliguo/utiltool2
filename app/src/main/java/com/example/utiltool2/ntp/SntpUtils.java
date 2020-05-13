package com.example.utiltool2.ntp;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author:lgh on 2020-05-13 10:05
 * 同步服务器时间
 */
public class SntpUtils {

    /**
     * 注意时区问题
     * @return
     */
    public static Date getUTCDate() {
        long nowAsPerDeviceTimeZone = getUTCTimestamp();
        return new Date(nowAsPerDeviceTimeZone);
    }

    /**
     *获取服务器时间
     * @return
     */
    public static long getUTCTimestamp() {

        long nowAsPerDeviceTimeZone = -1L;

        SntpClient sntpClient = new SntpClient();

        boolean success = sntpClient.requestTime("time.windows.com", 30000);

        if (success) {
            nowAsPerDeviceTimeZone = sntpClient.getNtpTime();
            Calendar cal = Calendar.getInstance();
            TimeZone timeZoneInDevice = cal.getTimeZone();
            int differentialOfTimeZones = timeZoneInDevice.getOffset(System.currentTimeMillis());
            nowAsPerDeviceTimeZone -= differentialOfTimeZones;
        }

        return nowAsPerDeviceTimeZone;
    }
}
