package com.profetadabola.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EighthGamesResponse {

    @SerializedName("eighthGames")
    private List<GameResponse> games;

    public List<GameResponse> getGames() {
        return games;
    }

    public void setTravel(List<GameResponse> games) {
        this.games = games;
    }
}
