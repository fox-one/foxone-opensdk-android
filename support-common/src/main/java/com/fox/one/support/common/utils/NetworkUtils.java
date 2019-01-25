package com.fox.one.support.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxiaoming on 2014/11/4.
 */
public class NetworkUtils {

    /**
     * 未知网络
     */
    public static final int NETWORK_UNKNOWN = -2;
    /**
     * 无网络
     */
    public static final int NETWORK_INVALID = -1;
    /**
     * wap网络
     */
    public static final int NETWORK_WAP = 0;
    /**
     * 2G网络
     */
    public static final int NETWORK_2G = 1;
    /**
     * 3G网络
     */
    public static final int NETWORK_3G = 2;
    /**
     * 4G网络
     */
    public static final int NETWORK_4G = 3;
    /**
     * 5G网络或以上网络
     */
    public static final int NETWORK_5G = 5;
    /**
     * wifi网络
     */
    public static final int NETWORK_WIFI = 10;

    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN = 18;
    private static final int NETWORK_TYPE_LTE_CA = 19;
    private static final Map<Integer, Integer> NETWORK_TYPE_MAP = new HashMap<>();
    static {
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_UNKNOWN, NETWORK_UNKNOWN);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_GPRS, NETWORK_2G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_EDGE, NETWORK_2G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_UMTS, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_CDMA, NETWORK_2G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_EVDO_0, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_EVDO_A, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_EVDO_B, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_1xRTT, NETWORK_2G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_HSDPA, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_HSUPA, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_HSPA, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_IDEN, NETWORK_2G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_EHRPD, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_HSPAP, NETWORK_3G);
        NETWORK_TYPE_MAP.put(NETWORK_TYPE_GSM, NETWORK_2G);
        NETWORK_TYPE_MAP.put(NETWORK_TYPE_TD_SCDMA, NETWORK_3G);
        NETWORK_TYPE_MAP.put(TelephonyManager.NETWORK_TYPE_LTE, NETWORK_4G);
        NETWORK_TYPE_MAP.put(NETWORK_TYPE_IWLAN, NETWORK_4G);
        NETWORK_TYPE_MAP.put(NETWORK_TYPE_LTE_CA, NETWORK_4G);
    }

    /**
     * 获取网络类型
     *
     * @return 网络类型
     */
    public static int type(Context context) {
        int networkType = NETWORK_UNKNOWN;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!networkConnected(networkInfo)) {
            networkType = NETWORK_INVALID;
        } else if (isWifiNetwork(networkInfo)) {
            networkType = NETWORK_WIFI;
        } else if (isWapNetwork(networkInfo)) {
            networkType = NETWORK_WAP;
        } else if (isMobileNetwork(networkInfo)) {
            Integer type = NETWORK_TYPE_MAP.get(telephonyNetworkType(networkInfo));
            return type == null ? NETWORK_UNKNOWN : type;
        }

        return networkType;
    }

    /**
     * 直接从系统函数里得到的network type
     *
     * @param networkInfo NetworkInfo
     * @return net type
     */
    private static int telephonyNetworkType(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return TelephonyManager.NETWORK_TYPE_UNKNOWN;
        }

        return networkInfo.getSubtype();
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * 是否存在有效的网络连接.
     *
     * @return 存在有效的网络连接返回true，否则返回false
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return networkConnected(connectivityManager.getActiveNetworkInfo());
    }

    private static boolean networkConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isConnected();
    }

    private static boolean isMobileNetwork(NetworkInfo networkInfo) {
        return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    @SuppressWarnings("deprecation")
    private static boolean isWapNetwork(NetworkInfo networkInfo) {
        return isMobileNetwork(networkInfo) && !TextUtils.isEmpty(android.net.Proxy.getDefaultHost());
    }

    public static boolean isWifiNetwork(NetworkInfo networkInfo) {
        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
