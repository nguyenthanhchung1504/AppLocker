package com.applocker.applockmanager.models;

/**
 * Created by user on 16-Oct-17.
 */

public class Been
{
    private String AppName;

    private Integer Pin;

    public Been() {}

    public Been(String AppName)
    {
        this.AppName = AppName;
    }


    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Integer getPin() {
        return Pin;
    }

    public void setPin(Integer pin) {
        Pin = pin;
    }

}
