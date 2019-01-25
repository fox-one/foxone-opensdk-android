package com.fox.one.support.common.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangyinghao on 2018/6/6.
 */
public class TimeUtils {

    private volatile static DateFormat sEnDateFormat;
    private volatile static DateFormat sZhDateFormat;
    private volatile static DateFormat sEnDateTimeFormat;
    private volatile static DateFormat sZhDateTimeFormat;

    synchronized public static DateFormat getEnDateFormat() {
        if (sEnDateFormat == null) {
            synchronized (TimeUtils.class) {
                sEnDateFormat = new SimpleDateFormat("MMM d,yyyy", Locale.US);
            }
        }
        return sEnDateFormat;
    }

    synchronized public static DateFormat getEnDateFormatWithTime() {
        if (sEnDateTimeFormat == null) {
            synchronized (TimeUtils.class) {
                sEnDateTimeFormat = new SimpleDateFormat("MMM d,yyyy HH:mm:ss", Locale.US);
            }
        }
        return sEnDateTimeFormat;
    }


    synchronized public static DateFormat getZhDateFormat() {
        if (sZhDateFormat == null) {
            synchronized (TimeUtils.class) {
                sZhDateFormat = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
            }
        }
        return sZhDateFormat;
    }
    synchronized public static DateFormat getZhDateFormatWithTime() {
        if (sZhDateTimeFormat == null) {
            synchronized (TimeUtils.class) {
                sZhDateTimeFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss", Locale.CHINA);
            }
        }
        return sZhDateTimeFormat;
    }
}
