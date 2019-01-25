package com.fox.one.support.common.utils;

import android.content.ContentValues;

/**
 * class description here
 *
 * @author simon
 * @version 1.0.0
 * @since 2017-06-01
 */
public class ContentValueBuilder {

    private ContentValues mContentValues;

    private ContentValueBuilder() {
        mContentValues = new ContentValues();
    }

    public static ContentValueBuilder create() {
        return new ContentValueBuilder();
    }

    public ContentValueBuilder put(String key, Boolean value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, Byte value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, byte[] value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, Double value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, Float value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, Integer value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, Long value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, Short value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder put(String key, String value) {
        mContentValues.put(key, value);
        return this;
    }

    public ContentValueBuilder putAll(ContentValues other) {
        mContentValues.putAll(other);
        return this;
    }

    public ContentValueBuilder putNull(String key) {
        mContentValues.putNull(key);
        return this;
    }

    public ContentValues build() {
        return mContentValues;
    }
}
