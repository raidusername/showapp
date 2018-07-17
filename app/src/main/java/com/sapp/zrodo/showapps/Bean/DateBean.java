package com.sapp.zrodo.showapps.Bean;

import java.io.Serializable;
import java.util.List;

public class DateBean implements Serializable {
    private List<AppMsg> ls;

    public List<AppMsg> getLs() {
        return ls;
    }

    public void setLs(List<AppMsg> ls) {
        this.ls = ls;
    }
}
