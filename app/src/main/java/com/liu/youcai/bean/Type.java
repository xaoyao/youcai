package com.liu.youcai.bean;

import java.io.Serializable;

/**
 * Created by liu on 2016/4/25 0025.
 */
public class Type implements Serializable{
    private int id;
    private String name;
    private int icon;

    public Type() {
    }

    public Type(int id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
