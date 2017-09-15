package com.profetadabola.api;

import rx.Observable;

import com.profetadabola.api.model.EighthGamesResponse;
import com.profetadabola.api.model.User;

import retrofit2.http.GET;

public interface API {

    @GET("profeta/games.json")
    Observable<EighthGamesResponse> getAllGames();

    @GET("58b9b1740f0000b614f09d2f")
    Observable<User> syncUser();

}
