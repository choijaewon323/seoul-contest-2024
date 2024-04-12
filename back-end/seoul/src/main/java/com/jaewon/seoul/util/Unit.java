package com.jaewon.seoul.util;

public enum Unit {
    LAT(0.0113d), LNG(0.0091d);

    private double unit;

    Unit(double unit) {
        this.unit = unit;
    }

    public double getUnit() {
        return unit;
    }
}
