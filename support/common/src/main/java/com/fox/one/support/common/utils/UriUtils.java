package com.fox.one.support.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * class description here
 *
 * @author simon
 * @version 1.0.0
 * @since 2018-05-31
 */
public class UriUtils {

    /**
     * 获取兼容性的Uri, 当系统target >= 24时，以FileProvider.getUriForFile(..)返回Uri
     * @param context
     * @param filePath
     * @return
     */
    public static Uri getCompatUriFromFile(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", new File(filePath));
        } else {
            return Uri.parse("file://" + filePath);
        }
    }
}
