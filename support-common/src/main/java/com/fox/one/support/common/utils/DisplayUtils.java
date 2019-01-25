package com.fox.one.support.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link DisplayUtils#dp2px(Context, float)}</li>
 * <li>{@link DisplayUtils#px2dp(Context, float)}</li>
 * </ul>
 * 
 * @author liuxiaoming 2014-2-14
 */
public class DisplayUtils {

    @Deprecated
    public static float dp2px(Context context, float dp) {
        if (context == null) {
            return -1f;
        }
        return dp * context.getResources().getDisplayMetrics().density + 0.5f;
    }

    public static float dp2px(float dp) {
        return Resources.getSystem().getDisplayMetrics().density * dp + 0.5f;
    }

    @Deprecated
    public static float px2dp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density + 0.5f;
    }

    public static float px2dp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density + 0.5f;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    @Deprecated
    public static float px2sp(Context context, float pxValue) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float fontScale = dm.scaledDensity;
        return pxValue / fontScale + 0.5f;
    }

    public static float px2sp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    @Deprecated
    public static float sp2px(Context context, float sp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float fontScale = dm.scaledDensity;
        return sp * fontScale + 0.5f;
    }

    public static float sp2px(float sp) {
        return Resources.getSystem().getDisplayMetrics().scaledDensity * sp + 0.5f;
    }

    public static Point getScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }

    public static Point getScreenSize() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }
}
