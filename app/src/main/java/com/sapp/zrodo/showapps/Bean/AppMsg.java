package com.sapp.zrodo.showapps.Bean;

import android.graphics.drawable.Drawable;

public class AppMsg {
    private String Appname;
    private Drawable Appimg;

    public String getAppname() {
        return Appname;
    }

    public AppMsg() {
    }

    public AppMsg(String appname, Drawable appimg) {

        Appname = appname;
        Appimg = appimg;
    }

    public void setAppname(String appname) {
        Appname = appname;
    }

    public Drawable getAppimg() {
        return Appimg;
    }

    public void setAppimg(Drawable appimg) {
        Appimg = appimg;
    }
}
