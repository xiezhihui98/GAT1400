package com.cz.viid.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonCommon {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static String toJson(Object source) {
        return gson.toJson(source);
    }
}
