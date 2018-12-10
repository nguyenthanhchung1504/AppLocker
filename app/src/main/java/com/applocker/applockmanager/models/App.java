package com.applocker.applockmanager.models;

import android.graphics.drawable.Drawable;

public class App {


    private Drawable icon;
    private String name;
    private String packageName;
    private int state;
    private int type;

    public App() {
    }

    public App(Drawable icon, String name, String packageName, int type, int state) {
        this.icon = icon;
        this.name = name;
        this.state = state;
        this.type = type;
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
