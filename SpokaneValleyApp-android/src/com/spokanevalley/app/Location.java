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
        
    /**
     * Return marker GPS coordinates
     */
	public LatLng getGpsCoord() {
        return gpsCoord;
    }

    /**
     * Return marker icon image location path.
     */
	public BitmapDescriptor getMarkerImage() {
        return markerImage;
    }
	
    /**
     * Initialize marker icon image location path.
     */
    public void setMarkerImage(String marker) {
    	BitmapDescriptor r = BitmapDescriptorFactory.fromAsset(marker);
        this.markerImage = r;
    }
    
    /**
     * Set the marker GPS coordinates.
     */
    public void setGpsCoord(LatLng gpsCoord) {
        this.gpsCoord = gpsCoord;
    }

    /**
     * Return marker title (name of location).
     */
    public String getTitle() {
        return title;
    }

    /**
     * Initialize marker title (name of location).
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the description of the marker location.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Initialize the description of the marker location.
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Returns the longitude coordinate of the location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Initialize the longitude coordinate of the location.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Return the latitude coordinate of the location.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Initialize the latitude coordinate of the location.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * Returns the ID of the location.
     */
	public String getID() {
		return ID;
	}

    /**
     * Returns the ID of the location.
     */
	public void setID(String ID) {
		this.ID = ID;
	}
	
    /**
     * Return the altitude of the location.
     */
	public double getAltitude() {
		return altitude;
	}

    /**
     * Initialize the latitude of the location.
     */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	//Corner Points Getters/Setters
    /**
     * Return the bottom left latitude coordinate of the location.
     */
    public double getLatitudeBottomLeft() {
		return latitudeBottomLeft;
	}
    
    /**
     * Initialize the bottom left latitude coordinate of the location.
     */
	public void setLatitudeBottomLeft(double latitudeBottomLeft) {
		this.latitudeBottomLeft = latitudeBottomLeft;
	}
	
    /**
     * Return the bottom left longitude coordinate of the location.
     */
	public double getLongitudeBottomLeft() {
		return longitudeBottomLeft;
	}

    /**
     * Initialize the bottom left longitude coordinate of the location.
     */
	public void setLongitudeBottomLeft(double longitudeBottomLeft) {
		this.longitudeBottomLeft = longitudeBottomLeft;
	}

    /**
     * Return the top right latitude coordinate of the location.
     */
	public double getLatitudeTopRight() {
		return latitudeTopRight;
	}
	
    /**
     * Initialize the top right latitude coordinate of the location.
     */
	public void setLatitudeTopRight(double latitudeTopRight) {
		this.latitudeTopRight = latitudeTopRight;
	}
	
    /**
     * Return the top right longitude coordinate of the location.
     */
	public double getLongitudeTopRight() {
		return longitudeTopRight;
	}

    /**
     * Initialize the top right longitude coordinate of the location.
     */
	public void setLongitudeTopRight(double longitudeTopRight) {
		this.longitudeTopRight = longitudeTopRight;
	}
}
