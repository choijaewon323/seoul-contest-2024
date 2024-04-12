package com.jaewon.seoul.util;

import lombok.Getter;

@Getter
public class LatLng {
    private final double lat;
    private final double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
