package com.example.myfirstapp;

import android.app.Application;
import android.widget.NumberPicker;

/**
 * Created by Jebreiel on 2016-10-11.
 */

public class Globals extends Application {

    private History history = new History(0,0);

    public History getHistory() {
        return history;
    }
    public void setHistory(Integer dur, Integer intv) {
        this.history.setDuration(dur);
        this.history.setInterval(intv);
    }

    private NumberPicker np;
    private NumberPicker np2;

    public NumberPicker getNp() {
        return np;
    }

    public NumberPicker getNp2() {
        return np2;
    }

    public void setNp(NumberPicker np) {
        this.np = np;
    }

    public void setNp2(NumberPicker np2) {
        this.np2 = np2;
    }

    private Boolean durationFin = false;

    public Boolean getDurationFin() { return durationFin; }

    public void setDurationFin(Boolean durationFin) { this.durationFin = durationFin; }

    private Boolean hasHistory = false;
    public Boolean getHasHistory() {
        return hasHistory;
    }
    public void setHasHistory(Boolean history) {
        this.hasHistory = history;
    }

}
