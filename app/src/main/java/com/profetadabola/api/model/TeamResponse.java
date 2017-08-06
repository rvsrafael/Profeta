package com.profetadabola.api.model;

/**
 * Created by rafa on 19/07/17.
 */

public class TeamResponse {
    private String name;
    private String icon;
    private String goal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
