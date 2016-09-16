package com.geekyint.instaadmin.Model;

import java.io.Serializable;

/**
 * Created by geekyint on 2/8/16.
 */
public class Data implements Serializable {
    private String title, gner;
    private int dat;

    public Data(String title, String gner, int dat) {
        this.title = title;
        this.gner = gner;
        this.dat = dat;

    }

    public String getGner() {
        return gner;
    }

    public int getDat() {
        return dat;
    }

    public void setDat(int dat) {
        this.dat = dat;
    }

    public void setGner(String gner) {
        this.gner = gner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}