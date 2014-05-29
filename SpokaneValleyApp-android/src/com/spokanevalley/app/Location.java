package com.spokanevalley.app;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class Location {
	private String ID;
    private String title;
    private String info;
    private double latitude;
    private double longitude;
    private LatLng gpsCoord;
    private double attitude;
    private BitmapDescriptor markerImage;

        public Location(String ID,String title,String info,double latitude,double longitude){
        	this.ID = ID;
        	this.title = title;
        	this.info = info;
        	this.latitude = latitude;
        	this.longitude = longitude;
        	this.attitude = 0;
        }
        
        public Location(String ID,String title,String info,double latitude,double longitude,double attitude){
        	this.ID = ID;
        	this.title = title;
        	this.info = info;
        	this.latitude = latitude;
        	this.longitude = longitude;
        	this.attitude = attitude;
        }

        public Location() {
	    	this.title = null;
	    	this.info = null;
	    	this.latitude = 0.00;
	    	this.longitude = 0.00;
	    	this.markerImage = null;
    	}

	public LatLng getGpsCoord() {
        return gpsCoord;
    }

	public BitmapDescriptor getMarkerImage() {
        return markerImage;
    }
	
    public void setMarkerImage(String marker) {
    	BitmapDescriptor r = BitmapDescriptorFactory.fromAsset(marker);
        this.markerImage = r;
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
    
	public String getID() {
		return ID;
	}
}
