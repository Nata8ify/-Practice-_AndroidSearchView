package com.example.pnattawut.androidsearchview_practice;

import com.google.gson.annotations.SerializedName;

/**
 * Created by PNattawut on 28-Aug-17.
 */

public class Color {

    @SerializedName("color")
    private String color;

    @SerializedName("value")
    private String value;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getColor();
    }
}
