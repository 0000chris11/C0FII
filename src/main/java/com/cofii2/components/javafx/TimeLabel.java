package com.cofii2.components.javafx;

import javafx.scene.control.Label;

public class TimeLabel extends Label {

    private int msu = 0;
    private int su = 0;
    private int sd = 0;
    private int mu = 0;

    public void setTimer() {
        msu++;
        // MILISECOND
        if (msu == 10) {
            su++;
            msu = 0;
        }
        // SECOND U
        if (su == 10) {
            sd++;
            su = 0;
        }
        // SECOND D
        if (sd == 6) {
            mu++;
            sd = 0;
        }
        // MINUTE U
        if (mu == 10) {
            msu = 0;
            su = 0;
            sd = 0;
            mu = 0;
        }
        setText((mu) + ":" + (sd) + (su) + ":" + (msu));
    }
    public void resetTimer(){
        setText("0:00:0");
    }
}
