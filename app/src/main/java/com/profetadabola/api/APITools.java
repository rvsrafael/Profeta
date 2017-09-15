package com.profetadabola.api;

public class APITools {

    public static final String BASE_URL_GAMES = "https://sites.google.com/site/profetadabolabr/";
    public static final String BASE_URL_SYNC = "http://www.mocky.io/v2/";

    public static API getAPI() {
        return RetrofitClient.getClient(BASE_URL_GAMES).create(API.class);
    }

    public static API syncUser() {
        return RetrofitClient.getClient(BASE_URL_SYNC).create(API.class);
    }

}
