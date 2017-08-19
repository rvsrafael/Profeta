package com.profetadabola.tools;

import com.orhanobut.hawk.Hawk;
import com.profetadabola.api.model.EighthGamesResponse;

public class PersistenceHawk {

    public static void setValueString(String value) {
        Hawk.put("Hawk", value);
    }

    public static void setValueString(String key, String value) {
        Hawk.put(key, value);
    }

    public static String getValueString(String key) {
        return Hawk.get(key);
    }

    public static void setSteps(String key, EighthGamesResponse games) {
        Hawk.put(key, games);
    }

    public static EighthGamesResponse getSteps(String key) {
        return Hawk.get(key);
    }
}
