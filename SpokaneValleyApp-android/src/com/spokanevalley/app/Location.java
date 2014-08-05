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
    private double altitude;
    private BitmapDescriptor markerImage;
	private double latitudeBottomLeft;
    private double longitudeBottomLeft;
    private double latitudeTopRight;
    private double longitudeTopRight;

        public Location(String ID,String title,String info,double latitude,double longitude){
        	this.ID = ID;
        	this.title = title;
        	this.info = info;
        	this.latitude = latitude;
        	this.longitude = longitude;
        	this.setAltitude(0);
        }
        
        public Location(String ID,String title,String info,double latitude,double longitude,double attitude){
        	this.ID = ID;
        	this.title = title;
        	this.info = info;
        	this.latitude = latitude;
        	this.longitude = longitude;
        	this.setAltitude(attitude);
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

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	//Corner Points Getters/Setters
    public double getLatitudeBottomLeft() {
		return latitudeBottomLeft;
	}

	public void setLatitudeBottomLeft(double latitudeBottomLeft) {
		this.latitudeBottomLeft = latitudeBottomLeft;
	}

	public double getLongitudeBottomLeft() {
		return longitudeBottomLeft;
	}

	public void setLongitudeBottomLeft(double longitudeBottomLeft) {
		this.longitudeBottomLeft = longitudeBottomLeft;
	}

	public double getLatitudeTopRight() {
		return latitudeTopRight;
	}

	public void setLatitudeTopRight(double latitudeTopRight) {
		this.latitudeTopRight = latitudeTopRight;
	}

	public double getLongitudeTopRight() {
		return longitudeTopRight;
	}

	public void setLongitudeTopRight(double longitudeTopRight) {
		this.longitudeTopRight = longitudeTopRight;
	}
}
