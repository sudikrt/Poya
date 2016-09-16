package com.geekyint.instaadmin.Model;

/**
 * Created by geekyint on 4/8/16.
 */
public class Round_obj {
    String key;
    Object object;

    public Round_obj(String key, Object object) {
        this.key = key;
        this.object = object;
    }
    public void setKey (String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
    public void  setObject (Object object) {
        this.object = object;
    }

    public Object getObject () {
        return this.object;
    }
}
