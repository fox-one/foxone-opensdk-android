package com.fox.one.support.common.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyinghao on 2018/7/26.
 */
public class FoxOneViewModelFactory implements ViewModelProvider.Factory {

    private volatile static FoxOneViewModelFactory sInstance;

    private final Map<Class<? extends ViewModel>, Object> cache = new HashMap<>();

    public static FoxOneViewModelFactory getInstance() {
        if (sInstance == null) {
            synchronized (FoxOneViewModelFactory.class) {
                if (sInstance == null) {
                    sInstance = new FoxOneViewModelFactory();
                }
            }
        }
        return sInstance;
    }

    private FoxOneViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (cache.containsKey(modelClass)) {
            if (cache.get(modelClass) == null) {
                cache.remove(modelClass);
                return create(modelClass);
            }
            return (T) cache.get(modelClass);
        } else {
            try {
                T model = modelClass.newInstance();
                cache.put(modelClass, model);
                return model;
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
    }

    public void clearCache() {
        cache.clear();
        System.gc();
    }
}
