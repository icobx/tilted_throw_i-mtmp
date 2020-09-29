package com.example.tiltedthrow.utility;

public class Point {
    private float x, y, t;

    public Point(){}

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(float x, float y, float t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getT() {
        return t;
    }

    public String getXasString() {
        return String.valueOf(x);
    }

    public String getYasString() {
        return String.valueOf(y);
    }

    public String getTasString() {
        return String.valueOf(t);
    }
}
