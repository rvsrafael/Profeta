package com.profetadabola.api;

import rx.Observable;

import com.profetadabola.api.model.EighthGamesResponse;

import retrofit2.http.GET;

public interface API {

    @GET("profeta/games.json")
    Observable<EighthGamesResponse> getAllGames();

}
