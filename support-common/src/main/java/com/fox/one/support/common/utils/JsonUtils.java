package com.fox.one.support.common.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by zhangyinghao on 2018/4/16.
 */
public class JsonUtils {
    private static Gson mGson;

    /**
     * get Gson.
     * @return Gson.
     */
    public static Gson getGson() {
        if (mGson == null) {
            mGson = newGson();
        }

        return mGson;
    }

    /**
     * new Gson.
     * @return Gson.
     */
    public static Gson newGson() {
        return new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                if (aDouble == aDouble.longValue()) {
                    return new JsonPrimitive(aDouble.longValue());
                }
                return new JsonPrimitive(aDouble);
            }
        }).create();
    }

    /**
     * convert json String to Object.
     * @param jsonString json string
     * @param clazz Object class
     * @param <T> Object Class Type
     * @return Object
     */
    public static <T> T fromJsonThrowEx(String jsonString, Class<T> clazz) throws JsonSyntaxException {
        return getGson().fromJson(jsonString, clazz);
    }

    public static <T> T optFromJson(String jsonString, Class<T> clazz) {
        try {
            return getGson().fromJson(jsonString, clazz);
        } catch (Throwable var3) {
            var3.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        return optFromJson(jsonString, clazz);
    }

    /**
     * convert json string to the Object of the specified Type.
     * @param json json string
     * @param typeOfT Type
     * @param <T> Type
     * @return Object of T
     */
    public static <T> T fromJsonThrowEx(String json, Type typeOfT) throws JsonSyntaxException {
        return getGson().fromJson(json, typeOfT);
    }

    public static <T> T optFromJson(String json, Type typeOfT) {
        try {
            return getGson().fromJson(json, typeOfT);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static <T> T fromJson(String json, Type typeOfT) {
        return optFromJson(json, typeOfT);
    }

    @Deprecated
    public static JSONObject fromInputStream(InputStream inputStream) {
        return optFromInputStream(inputStream);
    }

    public static JSONObject fromInputStreamThrowEx(InputStream inputStream) throws JSONException {
        return new JSONObject(StringUtils.INSTANCE.fromInputStream(inputStream));
    }

    public static JSONObject optFromInputStream(InputStream inputStream) {
        try {
            return new JSONObject(StringUtils.INSTANCE.fromInputStream(inputStream));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T optFromInputStream(InputStream inputStream, Class<T> clazz) {
        try {
            return getGson().fromJson(StringUtils.INSTANCE.fromInputStream(inputStream), clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * vonvert Object to json string.
     * @param object Object
     * @return json string.
     */
    @Deprecated
    public static String toJson(Object object) {
        return optToJson(object);
    }

    public static String optToJson(Object object) {
        try {
            return getGson().toJson(object);
        } catch (Throwable var2) {
            var2.printStackTrace();
            return "";
        }
    }

    public static <T> T optfrom(JsonElement element, Type typeOfT) {
        try {
            return getGson().fromJson(element, typeOfT);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T optFrom(JsonElement element, Class<T> clazz) {
        try {
            return getGson().fromJson(element, clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T optfrom(Reader reader, Type typeOfT) {
        try {
            return getGson().fromJson(reader, typeOfT);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T optFrom(Reader reader, Class<T> clazz) {
        try {
            return getGson().fromJson(reader, clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T optfrom(JsonReader jsonReader, Type typeOfT) {
        try {
            return getGson().fromJson(jsonReader, typeOfT);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T optFrom(JsonReader jsonReader, Class<T> clazz) {
        try {
            return getGson().fromJson(jsonReader, clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

}
