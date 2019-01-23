/*
 * PreferenceHelper      2016-04-27
 * Copyright (c) 2016 hujiang Co.Ltd. All right reserved(http://www.hujiang.com).
 * 
 */
package com.fox.one.support.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 轻量Preference工具，优化preference调用性能，默认使用应用默认的preference文件进行存储，可以自定义preference文件，支持多进程访问。
 *
 * @author simon
 * @version 1.0.0
 * @since 2016-04-27
 */
public class FoxPreference {

    private static FoxPreference sPreferenceHelper;
    private Context mContext;

    public static FoxPreference instance(Context context) {
        if (sPreferenceHelper == null) {
            synchronized (FoxPreference.class) {
                if (sPreferenceHelper == null) {
                    sPreferenceHelper = new FoxPreference(context);
                }
            }
        }

        return sPreferenceHelper;
    }

    private FoxPreference(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public boolean contains(String key) {
        return contains(null, key);
    }

    public boolean contains(String preferenceName, String key) {
        return PreferenceCore.instance(mContext).contains(preferenceName, key);
    }

    public int getInt(String key, int def) {
        return getInt(null, key, def);
    }

    public int getInt(String preferenceName, String key, int defValue) {
        return PreferenceCore.instance(mContext).getInt(preferenceName, key, defValue);
    }

    public long getLong(String key, long def) {
        return getLong(null, key, def);
    }

    public long getLong(String preferenceName, String key, long def) {
        return PreferenceCore.instance(mContext).getLong(preferenceName, key, def);
    }

    public String getString(String key, String def) {
        return getString(null, key, def);
    }

    public String getString(String preferenceName, String key, String defValue) {
        return PreferenceCore.instance(mContext).getString(preferenceName, key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> def) {
        return getStringSet(null, key, def);
    }

    public Set<String> getStringSet(String preferenceName, String key, Set<String> def) {
        return PreferenceCore.instance(mContext).getStringSet(preferenceName, key, def);
    }

    public boolean getBoolean(String key, boolean def) {
        return getBoolean(null, key, def);
    }

    public boolean getBoolean(String preferenceName, String key, boolean def) {
        return PreferenceCore.instance(mContext).getBoolean(preferenceName, key, def);
    }

    public float getFloat(String key, float def) {
        return getFloat(null, key, def);
    }

    public float getFloat(String preferenceName, String key, float def) {
        return PreferenceCore.instance(mContext).getFloat(preferenceName, key, def);
    }

    public void putInt(String key, int value) {
        putInt(null, key, value);
    }

    public void putInt(String preferenceName, String key, int value) {
        PreferenceCore.instance(mContext).putInt(preferenceName, key, value);
    }

    public void putLong(String key, long value) {
        putLong(null, key, value);
    }

    public void putLong(String preferenceName, String key, long value) {
        PreferenceCore.instance(mContext).putLong(preferenceName, key, value);
    }

    public void putString(String key, String value) {
        putString(null, key, value);
    }

    public void putString(String preferenceName, String key, String value) {
        PreferenceCore.instance(mContext).putString(preferenceName, key, value);
    }

    public void putStringSet(String key, Set<String> value) {
        putStringSet(null, key, value);
    }

    public void putStringSet(String preferenceName, String key, Set<String> value) {
        PreferenceCore.instance(mContext).putStringSet(preferenceName, key, value);
    }

    public void putBoolean(String key, boolean value) {
        putBoolean(null, key, value);
    }

    public void putBoolean(String preferenceName, String key, boolean value) {
        PreferenceCore.instance(mContext).putBoolean(preferenceName, key, value);
    }

    public void putFloat(String key, float value) {
        putFloat(null, key, value);
    }

    public void putFloat(String preferenceName, String key, float value) {
        PreferenceCore.instance(mContext).putFloat(preferenceName, key, value);
    }

    public void remove(String key){
        remove(null, key);
    }

    public void remove(String preferenceName, String key) {
        PreferenceCore.instance(mContext).remove(preferenceName, key);
    }

    public void clear(){
        clear(null);
    }

    public void clear(String preferenceName) {
        PreferenceCore.instance(mContext).clear(preferenceName);
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        registerOnSharedPreferenceChangeListener(null, listener);
    }

    public void registerOnSharedPreferenceChangeListener(String preferenceName, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        PreferenceCore.instance(mContext).registerOnSharedPreferenceChangeListener(preferenceName, listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        unregisterOnSharedPreferenceChangeListener(null, listener);
    }
    public void unregisterOnSharedPreferenceChangeListener(String preferenceName, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        PreferenceCore.instance(mContext).unregisterOnSharedPreferenceChangeListener(preferenceName, listener);
    }

    /**
     * 关闭指定的配置入口
     * <p></p>
     * 当使用的配置信息只在某些特定的模块里使用时，建议在模块不使用时调用close关闭该模块相关的配置入口，特定模块的配置入口没有必要在全局一直处于开启状态。否则相关配置信息会一直占用系统内存。
     * @param preferenceName
     */
    public void close(String preferenceName) {
        PreferenceCore.instance(mContext).close(preferenceName);
    }

    private static class PreferenceWrapper {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        public PreferenceWrapper(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
            this.sharedPreferences = sharedPreferences;
            this.editor = editor;
        }
    }

    private static class PreferenceCore {

        private static PreferenceCore sPreferenceCore;
        private Context mContext;
        private PreferenceWrapper mDefaultPreferenceWrapper;
        private Map<String, PreferenceWrapper> mPreferences = new ConcurrentHashMap<>();

        public static PreferenceCore instance(Context context) {
            if (sPreferenceCore == null) {
                synchronized (FoxPreference.class) {
                    if (sPreferenceCore == null) {
                        sPreferenceCore = new PreferenceCore(context);
                    }
                }
            }

            return sPreferenceCore;
        }

        private PreferenceCore(@NonNull Context context) {
            mContext = context;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            mDefaultPreferenceWrapper = new PreferenceWrapper(sharedPreferences, sharedPreferences.edit());
        }

        private PreferenceWrapper getPreferenceWrapper(String preferenceName) {
            if (TextUtils.isEmpty(preferenceName)) {
                return mDefaultPreferenceWrapper;
            }

            PreferenceWrapper preferenceWrapper = mPreferences.get(preferenceName);

            if (preferenceWrapper == null) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
                preferenceWrapper = new PreferenceWrapper(sharedPreferences, sharedPreferences.edit());
                mPreferences.put(preferenceName, preferenceWrapper);
            }

            return preferenceWrapper;
        }

        public boolean contains(String key) {
            return contains(null, key);
        }

        public boolean contains(String preferenceName, String key) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.contains(key);
        }

        public int getInt(String key, int def) {
            return getInt(null, key, def);
        }

        public int getInt(String preferenceName, String key, int defValue) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.getInt(key, defValue);
        }

        public long getLong(String key, long def) {
            return getLong(null, key, def);
        }

        public long getLong(String preferenceName, String key, long def) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.getLong(key, def);
        }

        public String getString(String key, String def) {
            return getString(null, key, def);
        }

        public String getString(String preferenceName, String key, String defValue) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.getString(key, defValue);
        }

        public Set<String> getStringSet(String key, Set<String> def) {
            return getStringSet(null, key, def);
        }

        public Set<String> getStringSet(String preferenceName, String key, Set<String> def) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.getStringSet(key, def);
        }

        public boolean getBoolean(String key, boolean def) {
            return getBoolean(null, key, def);
        }

        public boolean getBoolean(String preferenceName, String key, boolean def) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.getBoolean(key, def);
        }

        public float getFloat(String key, float def) {
            return getFloat(null, key, def);
        }

        public float getFloat(String preferenceName, String key, float def) {
            return getPreferenceWrapper(preferenceName).sharedPreferences.getFloat(key, def);
        }

        public void putInt(String key, int value) {
            putInt(null, key, value);
        }

        public void putInt(String preferenceName, String key, int value) {
            getPreferenceWrapper(preferenceName).editor.putInt(key, value).apply();
        }

        public void putLong(String key, long value) {
            putLong(null, key, value);
        }

        public void putLong(String preferenceName, String key, long value) {
            getPreferenceWrapper(preferenceName).editor.putLong(key, value).apply();
        }

        public void putString(String key, String value) {
            putString(null, key, value);
        }

        public void putString(String preferenceName, String key, String value) {
            getPreferenceWrapper(preferenceName).editor.putString(key, value).apply();
        }

        public void putStringSet(String key, Set<String> value) {
            putStringSet(null, key, value);
        }

        public void putStringSet(String preferenceName, String key, Set<String> value) {
            getPreferenceWrapper(preferenceName).editor.putStringSet(key, value).apply();
        }

        public void putBoolean(String key, boolean value) {
            putBoolean(null, key, value);
        }

        public void putBoolean(String preferenceName, String key, boolean value) {
            getPreferenceWrapper(preferenceName).editor.putBoolean(key, value).apply();
        }

        public void putFloat(String key, float value) {
            putFloat(null, key, value);
        }

        public void putFloat(String preferenceName, String key, float value) {
            getPreferenceWrapper(preferenceName).editor.putFloat(key, value).apply();
        }

        public void remove(String key){
            remove(null, key);
        }

        public void remove(String preferenceName, String key) {
            getPreferenceWrapper(preferenceName).editor.remove(key).apply();
        }

        public void clear(){
            clear(null);
        }

        public void clear(String preferenceName) {
            getPreferenceWrapper(preferenceName).editor.clear().apply();
        }

        public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            registerOnSharedPreferenceChangeListener(null, listener);
        }

        public void registerOnSharedPreferenceChangeListener(String preferenceName, SharedPreferences.OnSharedPreferenceChangeListener listener) {
            getPreferenceWrapper(preferenceName).sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        }

        public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            unregisterOnSharedPreferenceChangeListener(null, listener);
        }
        public void unregisterOnSharedPreferenceChangeListener(String preferenceName, SharedPreferences.OnSharedPreferenceChangeListener listener) {
            getPreferenceWrapper(preferenceName).sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
        }

        /**
         * 关闭指定的配置入口
         * <p></p>
         * 当使用的配置信息只在某些特定的模块里使用时，建议在模块不使用时调用close关闭该模块相关的配置入口，特定模块的配置入口没有必要在全局一直处于开启状态。否则相关配置信息会一直占用系统内存。
         * @param preferenceName
         */
        public void close(String preferenceName) {
            if (!TextUtils.isEmpty(preferenceName)) {
                mPreferences.remove(preferenceName);
            }
        }
    }
}