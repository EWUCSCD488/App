package com.spokanevalley.app;


import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SlidingDrawer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;


@SuppressWarnings("deprecation")
public class MapView extends Activity implements OnMarkerClickListener,  LocationListener{
    
	//bounds for limiting scolling and zooming.
	private final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(47.575693, -117.364063), new LatLng(47.749113, -117.115154));
	private overscrollHandler mOverscrollHandler = new overscrollHandler();
	
	final Context context = this;
	
	GoogleMap map;
	
	Marker lastMarkerSelected = null;
	
    private LatLng locationMain = null;
    private float globalZoom = 0;
	
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Happens when activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
                
        mOverscrollHandler.sendEmptyMessageDelayed(0,100);
        
        
        
        //Initialize Location List
        try{
            LocationList.Create(LocationInflator.inflate(this, R.xml.locations));
        }
        catch (Exception e){
            Log.e("Inflator Error", e.getMessage());
        }
        
        
        
        //location checking...
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), true);
        lm.requestLocationUpdates(provider, 1000, 0, this); //change these
        
    }//end onCreate
    
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Main Map setup and initialization.
     */
    public void initilizeMap(){
    	
    	
    	
    	map = ((MapFragment) getFragmentManager()
               .findFragmentById(R.id.map)).getMap();
        
    	map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
        for(Location location : LocationList.LIST)
        {
            location.setGpsCoord(new LatLng(location.getLatitude(), location.getLongitude()));
            map.addMarker(new MarkerOptions()
                          .title(location.getTitle())
                          .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker128))
                          .position(location.getGpsCoord()));
        }
    	
        
        //set location and camera of where map looks
        map.setMyLocationEnabled(true);
        
        //coordinates of center of Spokane Valley
        LatLng center = new LatLng(47.670568, -117.239437);
        
        if(locationMain == null)
    		locationMain = center; //place where it loads
        
        if(globalZoom == 0){
        	globalZoom = 11.8f; //Scaling when it loads
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMain, globalZoom)); //second number is zoom unit
        
        
        //marker info window listener
        map.setOnMarkerClickListener(this);
        
        //This adds an image over the google map
        GroundOverlay groundOverlay = map.addGroundOverlay(new GroundOverlayOptions()
        .image(BitmapDescriptorFactory.fromResource(R.drawable.test))
        .position(new LatLng(47.670568, -117.239437), 9000)
        .transparency(0.3f));
        
        //Removes all street names
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        
        
    }//end initilizeMap
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    
    @Override
    protected void onPause(){
        
        super.onPause();
        
        VisibleRegion region = map.getProjection().getVisibleRegion();
        LatLngBounds cameraBounds = region.latLngBounds;
        LatLng myLatLng = map.getCameraPosition().target;
        CameraPosition position = map.getCameraPosition();
        globalZoom = position.zoom;
        locationMain = myLatLng;
    }// end onPause
    
    
    
    /**
     * Does this when map page is brought up again after being left.
     */
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
        
    }//end onResume
    
   
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Responds to marker click
     *
     */
	@Override
	public boolean onMarkerClick(Marker arg0) {
        
        //Change title text depending on pin

        arg0.hideInfoWindow();
        
        //MARKER IMAGES AND CENTERING*****************************************************************************************
		//put last pressed marker back to normal
		if(lastMarkerSelected != null && !arg0.equals(lastMarkerSelected))
			lastMarkerSelected.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker128));
		
		if(!arg0.equals(lastMarkerSelected))
			arg0.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker256)); 
        
        lastMarkerSelected = arg0;
        
        //move camera
        CameraPosition position = map.getCameraPosition();
        CameraPosition newPosition = new CameraPosition(arg0.getPosition(), position.zoom, position.tilt, position.bearing);
        map.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition),400, null);
        
        //MARKER IMAGES AND CENTERING*****************************************************************************************
        
        
        //return true so that the stock things don't happen when a marker is pressed.
        //This removes the automatic centering and the info and title boxes
        return false;
	}
	
	
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Returns the correction for Lat and Lng if camera is trying to get outside of visible map
     * @param cameraBounds Current camera bounds
     * @return Latitude and Longitude corrections to get back into bounds.
     */
    private LatLng getLatLngCorrection(LatLngBounds cameraBounds) {
        double latitude=0, longitude=0;
        if(cameraBounds.southwest.latitude < BOUNDS.southwest.latitude) {
            latitude = BOUNDS.southwest.latitude - cameraBounds.southwest.latitude;
        }
        if(cameraBounds.southwest.longitude < BOUNDS.southwest.longitude) {
            longitude = BOUNDS.southwest.longitude - cameraBounds.southwest.longitude;
        }
        if(cameraBounds.northeast.latitude > BOUNDS.northeast.latitude) {
            latitude = BOUNDS.northeast.latitude - cameraBounds.northeast.latitude;
        }
        if(cameraBounds.northeast.longitude > BOUNDS.northeast.longitude) {
            longitude = BOUNDS.northeast.longitude - cameraBounds.northeast.longitude;
        }
        return new LatLng(latitude, longitude);
    }
    
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
	 * Bounds the user to the overlay.
	 */
	private class overscrollHandler extends Handler {
	    @Override
	    public void handleMessage(Message msg) {
	        CameraPosition position = map.getCameraPosition();
	        VisibleRegion region = map.getProjection().getVisibleRegion();
            
	        LatLng correction = getLatLngCorrection(region.latLngBounds);
	        
	        LatLng center = new LatLng(47.670568, -117.239437);
	        
	        
	        if(correction.latitude != 0 || correction.longitude != 0) {
                CameraPosition newPosition = new CameraPosition(center, 11.8f, position.tilt, position.bearing);
                
                map.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition),400, null);
                
	        }
	        /* Recursively call handler every 100ms */
	        sendEmptyMessageDelayed(0,100);
	    }
	}//end overscroll handler
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    
	@Override
	public void onLocationChanged(android.location.Location arg0) {
		
		
		LatLng current = new LatLng(arg0.getLatitude(), arg0.getLongitude());
		
		
		Toast.makeText(getApplicationContext(),
                       "YOU ARE AT" + current.latitude + " and " + current.longitude , Toast.LENGTH_LONG)
        .show();
		
        
	}
    
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
	
    
}//end MapView


