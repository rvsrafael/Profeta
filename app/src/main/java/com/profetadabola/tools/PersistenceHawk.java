package com.profetadabola.tools;

import com.orhanobut.hawk.Hawk;
import com.profetadabola.api.model.EighthGamesResponse;
import com.profetadabola.main.games.GameStep;

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

    public static void setSteps(GameStep key, EighthGamesResponse games) {
        Hawk.put(String.valueOf(key),games);
    }

    public static EighthGamesResponse getSteps(GameStep key) {
        return Hawk.get(String.valueOf(key));
    }
}
