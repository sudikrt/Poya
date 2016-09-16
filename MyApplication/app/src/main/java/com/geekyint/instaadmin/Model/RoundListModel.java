package com.geekyint.instaadmin.Model;

/**
 * Created by geekyint on 5/8/16.
 */
public class RoundListModel {
    String key;
    int dat;
    public RoundListModel (String key, int dat)
    {
        this.key = key;
        this.dat = dat;
     }

    public void setDat(int dat) {
        this.dat = dat;
    }

    public int getDat() {
        return dat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
