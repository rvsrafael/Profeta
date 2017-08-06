package com.profetadabola.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rafa on 22/07/17.
 */

public class EighthGamesResponse {

    @SerializedName("eighthGames")
    private List<GameResponse> travel;

    public List<GameResponse> getGames() {
        return travel;
    }

    public void setTravel(List<GameResponse> travel) {
        this.travel = travel;
    }
}
