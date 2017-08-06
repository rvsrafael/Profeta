package com.profetadabola.api;

public class APITools {

    public static final String BASE_URL_GAMES = "https://sites.google.com/site/profetadabolabr/";

    public static API getAPI() {
        return RetrofitClient.getClient(BASE_URL_GAMES).create(API.class);
    }

}
