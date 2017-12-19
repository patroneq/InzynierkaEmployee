package com.client.inzynierkaemployee.Utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {

    private static Gson gson;
    public static Gson getGsonInstance() {
        if ( gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        }
        return gson;
    }
}
