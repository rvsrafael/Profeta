package com.profetadabola.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GameResponse {

    @SerializedName("teamA")
    private TeamResponse teamA;
    @SerializedName("teamB")
    private TeamResponse teamb;
    private String stage;
    private String date;
    private String stadium;
    private String address;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TeamResponse getTeamA() {
        return teamA;
    }

    public void setTeamA(TeamResponse teamA) {
        this.teamA = teamA;
    }

    public TeamResponse getTeamb() {
        return teamb;
    }

    public void setTeamb(TeamResponse teamb) {
        this.teamb = teamb;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
