package com.spokanevalley.app;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    private String title;
    private String info;
    private double latitude;
    private double longitude;
    private LatLng gpsCoord;

    public LatLng getGpsCoord() {
        return gpsCoord;
    }

    public void setGpsCoord(LatLng gpsCoord) {
        this.gpsCoord = gpsCoord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
