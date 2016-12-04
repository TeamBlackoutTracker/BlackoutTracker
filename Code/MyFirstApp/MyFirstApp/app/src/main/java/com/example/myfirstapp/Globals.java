package com.example.myfirstapp;

import android.app.Application;

/**
 * Created by Jebreiel on 2016-10-11.
 */

public class Globals extends Application {
    private Boolean hasHistory = false;

    public Boolean getHasHistory() {
        return hasHistory;
    }

    public void setHasHistory(Boolean history) {
        this.hasHistory = history;
    }
}
