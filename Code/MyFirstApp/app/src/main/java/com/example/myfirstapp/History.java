package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by robsh on 2016-10-27.
 */

public class History {
    Integer duration;
    Integer interval;
    //String media[] = new String[4];

    public History(Integer duration, Integer interval) {
        this.duration = duration;
        this.interval = interval;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
