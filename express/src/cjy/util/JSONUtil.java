package cjy.util;

import com.google.gson.Gson;

public class JSONUtil {

    private static Gson gson = new Gson();

    public static String toJSON(Object o){
        return gson.toJson(o);
    }
}
