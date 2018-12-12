package com.applocker.applockmanager.models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Theme {
    private int id;
    private int image;
    private boolean isSelectedButton;


    public Theme() {
    }

    public Theme(int id, int image) {
        this.id = id;
        this.image = image;
    }

    public boolean isSelectedButton() {
        return isSelectedButton;
    }

    public void setSelectedButton(boolean selectedButton) {
        isSelectedButton = selectedButton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
