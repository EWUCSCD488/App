package com.spokanevalley.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
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
import com.spokanevalley.bankStore.BankActivity;
import com.spokanevalley.database.DatabaseInterface;
import com.spokanevalley.discoveryGame.DiscoveryActivity;
import com.spokanevalley.farm.FarmGameLauncher;
import com.spokanevalley.minigames.plantesferry.GameSetup;

@SuppressWarnings("deprecation")
public class MapView extends Activity implements OnMarkerClickListener,
		LocationListener, OnInfoWindowClickListener {

	public static final String TAG = MapView.class.getName();

	//NEED TO CONFIGURE BOUNDS AFTER WE PUT IN ALL OUR LOCATIONS!*****
	// bounds for limiting scolling and zooming.
	private final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(47.594413,
			-117.399280), new LatLng(47.750088, -117.035187));
	private overscrollHandler mOverscrollHandler = new overscrollHandler();
	final Context context = this;
	private GoogleMap map;
	private Marker lastMarkerSelected = null;
	private LatLng LastLegitLocation = null;
	private LatLng locationMain = null;
	private float globalZoom = 0;
	
	//temp values
	/*private Location location1;
	private Location location2;
	private Location location3;*/
	
	public static final int REQUEST_CODE = 1;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Happens when activity is created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);

		mOverscrollHandler.sendEmptyMessageDelayed(0, 100);
		DatabaseInterface.Create(context);
		// Initialize Location List
		try {
			LocationList
					.Create(LocationInflator.inflate(this, R.xml.locations));
		} catch (Exception e) {
			Log.e("Inflator Error", e.getMessage());
		}

		// location checking...
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = lm.getBestProvider(new Criteria(), true);
		lm.requestLocationUpdates(provider, 1000, 0, this); // change these
		
		// for testing only
				/*location1 = new Location("ID1", "Terace View Park",
						"Awesome place 1", 47.636221, -117.222319);
				location2 = new Location("ID2", "Plantes Ferry Park",
						"Awesome place 2", 47.697924, -117.241588);
				
				location3 = new Location("ID3", "Discovery Park",
						"Awesome place 3", 47.678063, -117.224036);
				
				(DatabaseInterface.Create(context)).addNewLocation(location1);
				(DatabaseInterface.Create(context)).addNewLocation(location2);
				(DatabaseInterface.Create(context)).addNewLocation(location3);*/
  
	}// end onCreate

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Main Map setup and initialization.
	 */
	public void initilizeMap() {

		//Setting fragment to map
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		//Makes a clear map
		map.setMapType(GoogleMap.MAP_TYPE_NONE); 
		
		//Removes zoom buttons
		map.getUiSettings().setZoomControlsEnabled(false);
		
		//Removes zoom gestures (pinch, double tap, etc.)
		map.getUiSettings().setZoomGesturesEnabled(false);
		
		// Add different markers when reading through the location list
		for (Location location : LocationList.LIST) {
			Log.d(TAG, "location "+ location.getTitle());
			location.setGpsCoord(new LatLng(location.getLatitude(), location.getLongitude()));
			map.addMarker(new MarkerOptions()
					.title(location.getTitle())
					.snippet(location.getInfo())
					.icon(location.getMarkerImage())
					.position(location.getGpsCoord()));
					map.setOnInfoWindowClickListener(this);
					// Setting a custom info window adapter for the google map
					map.setInfoWindowAdapter(new InfoWindowAdapter() {
						 
			            // Use default InfoWindow frame
				        @Override
				        public View getInfoContents(Marker marker) {
				            if (marker != null && marker.isInfoWindowShown()) {
				                marker.hideInfoWindow();
				                marker.showInfoWindow();
				            }
				            return null;
				        }
			 
			            // Defines the contents of the InfoWindow (Have to use this one to use custom info-bubble)
			            @Override
			            public View getInfoWindow(Marker arg0) {
			 
			                // Getting view from the layout file info_window_layout
			                View view = getLayoutInflater().inflate(R.layout.info_window_layout, null);
			               
			                //setting title
		                	TextView titleUi = (TextView) view.findViewById(R.id.custom_title);
			                if (arg0.getTitle() != null && arg0 != null)
			                	titleUi.setText(arg0.getTitle());
			                else
			                	titleUi.setText("");
			                
			                //setting info
			                TextView snippetUi = ((TextView) view.findViewById(R.id.custom_snippet));
			                if (arg0.getSnippet() != null && arg0 != null)
			                	snippetUi.setText(arg0.getSnippet());
			                else
			                	snippetUi.setText("");
			 
			                //Setting click to play option
			                TextView clickUi = ((TextView) view.findViewById(R.id.custom_click));
			                clickUi.setText("Click to Play!");
			       
			                // Returning the view containing InfoWindow contents
			                return view;
			 
			            }
			        });
		}

		map.setPadding(0, 0, 30, 0);

		// set location and camera of where map looks
		map.setMyLocationEnabled(false);

		// coordinates of center of Spokane Valley
		LatLng center = new LatLng(47.717924, -117.221588);

		if (LastLegitLocation == null)
			LastLegitLocation = center;

		if (locationMain == null) {
			locationMain = center; // place where it loads
		}
		if (globalZoom == 0) {
			globalZoom = 12.4f; // Scaling when it loads *****
		}
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMain,
				globalZoom)); // second number is zoom unit

		// marker info window listener
		map.setOnMarkerClickListener(this);

		// This adds an image over the google map
		GroundOverlay groundOverlay = map
				.addGroundOverlay(new GroundOverlayOptions()
						.image(BitmapDescriptorFactory
								.fromResource(R.drawable.newmapbg))
						.position(new LatLng(47.670568, -117.239437), 39000)
						.transparency(0.0f));

		// Removes all street names
		map.getUiSettings().setCompassEnabled(false);
		map.getUiSettings().setRotateGesturesEnabled(false);
		map.getUiSettings().setTiltGesturesEnabled(false);
	
	}// end initilizeMap

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Override
	protected void onPause() {

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

	}// end onResume

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void onInfoWindowClick(Marker marker) 
    {
    	Log.d(TAG, marker.getTitle());
		// Check for Plantes Ferry Park

					if(marker.getTitle().equals("Bank")){
						Intent intent = new Intent(MapView.this, BankActivity.class);
						startActivityForResult(intent, REQUEST_CODE);
					}else if(marker.getTitle().equals("Terrace View Park And Pool")){
						// INITIALIZE GAME
						Intent intent = new Intent(MapView.this, GameLauncher.class);
						startActivityForResult(intent, REQUEST_CODE);
					}else if(marker.getTitle().equals("Discovery Park")){
						// INITIALIZE GAME
						Intent intent = new Intent(MapView.this, DiscoveryActivity.class);
						startActivityForResult(intent, REQUEST_CODE);
					}else if(marker.getTitle().equals("Plantes Ferry Park")){
						Intent intent = new Intent(MapView.this, PlantesFerryActivity.class);
						startActivityForResult(intent, REQUEST_CODE);
					}else if(marker.getTitle().equals("Greenacres Park")){
					Intent intent = new Intent(MapView.this, FarmGameLauncher.class);
					startActivityForResult(intent, REQUEST_CODE);
				}
    }
	/**
	 * Responds to marker click
	 * 
	 */
	@Override
	public boolean onMarkerClick(Marker arg0) {
		
		// Check if there is an open info window
        if (lastMarkerSelected != null) {
            // Close the info window
            lastMarkerSelected.hideInfoWindow();

            // Is the marker the same marker that was already open
            if (lastMarkerSelected.equals(arg0)) {
                // Nullify the lastOpened object
                lastMarkerSelected = null;
                // Return so that the info window isn't opened again
                return true;
            } 
        }

        // Open the info window for the marker
        arg0.showInfoWindow();
        // Re-assign the last opened such that we can close it later
        lastMarkerSelected = arg0;

        // Get the markers current position
        LatLng curMarkerPos = arg0.getPosition();

        // Use the markers position to get a new latlng to move the camera to such that it adjusts appropriately to your infowindows height (might be more or less then 0.3 and might need to subtract vs add this is just an example)
        LatLng camMove = new LatLng(curMarkerPos.latitude + 0.02, curMarkerPos.longitude);

        // Create a camera update with the new latlng to move to            
        CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(camMove);
        // Move the map to this position
        map.animateCamera(camUpdate);

		// return true so that the stock things don't happen when a marker is
		// pressed.
		// This removes the automatic centering and the info and title boxes
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);

			if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
				String requiredValue = data.getStringExtra("Key");
			}
			
		} catch (Exception ex) {
			Toast.makeText(MapView.this, ex.toString(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Returns the correction for Lat and Lng if camera is trying to get outside
	 * of visible map
	 * 
	 * @param cameraBounds
	 *            Current camera bounds
	 * @return Latitude and Longitude corrections to get back into bounds.
	 */
	/*
	 * private LatLng getLatLngCorrection(LatLngBounds cameraBounds) { double
	 * latitude=0, longitude=0; if(cameraBounds.southwest.latitude <
	 * BOUNDS.southwest.latitude) { latitude = BOUNDS.southwest.latitude -
	 * cameraBounds.southwest.latitude; } if(cameraBounds.southwest.longitude <
	 * BOUNDS.southwest.longitude) { longitude = BOUNDS.southwest.longitude -
	 * cameraBounds.southwest.longitude; } if(cameraBounds.northeast.latitude >
	 * BOUNDS.northeast.latitude) { latitude = BOUNDS.northeast.latitude -
	 * cameraBounds.northeast.latitude; } if(cameraBounds.northeast.longitude >
	 * BOUNDS.northeast.longitude) { longitude = BOUNDS.northeast.longitude -
	 * cameraBounds.northeast.longitude; } return new LatLng(latitude,
	 * longitude); }
	 */

	private int getLatLngCorrection(LatLngBounds cameraBounds) {
		// double latitude=0, longitude=0;
		int counter = 0;
		if (cameraBounds.southwest.latitude < BOUNDS.southwest.latitude) {
			// latitude = BOUNDS.southwest.latitude -
			// cameraBounds.southwest.latitude;
			counter++;
			// return false;
		}
		if (cameraBounds.southwest.longitude < BOUNDS.southwest.longitude) {
			// longitude = BOUNDS.southwest.longitude -
			// cameraBounds.southwest.longitude;
			counter++;
			// return false;
		}
		if (cameraBounds.northeast.latitude > BOUNDS.northeast.latitude) {
			// latitude = BOUNDS.northeast.latitude -
			// cameraBounds.northeast.latitude;
			// map.getUiSettings().setZoomGesturesEnabled(false);
			counter++;
			// return false;
		}
		if (cameraBounds.northeast.longitude > BOUNDS.northeast.longitude) {
			// longitude = BOUNDS.northeast.longitude -
			// cameraBounds.northeast.longitude;
			counter++;
			// return false;
		}
		return counter;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Bounds the user to the overlay.
	 */
	private class overscrollHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			CameraPosition position = map.getCameraPosition();
			VisibleRegion region = map.getProjection().getVisibleRegion();

			/*
			 * LatLng correction = getLatLngCorrection(region.latLngBounds);
			 * 
			 * LatLng center = new LatLng(47.670568, -117.239437);
			 * 
			 * 
			 * if(correction.latitude != 0 || correction.longitude != 0) {
			 * CameraPosition newPosition = new CameraPosition(center, 11.8f,
			 * position.tilt, position.bearing);
			 * 
			 * map.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition
			 * ),100, null);
			 * 
			 * }
			 */
			int counter = getLatLngCorrection(region.latLngBounds);
			if (position != null) {

				if (counter == 0) {

					LastLegitLocation = new LatLng(position.target.latitude,
							position.target.longitude);

					//Log.d(TAG, "center of screen is : " + LastLegitLocation);
				} else if (counter >= 1) {
					if (counter > 2) {
						CameraPosition newPosition = new CameraPosition(
								locationMain, 11.8f, position.tilt,
								position.bearing);
						map.animateCamera(CameraUpdateFactory
								.newCameraPosition(newPosition), 100, null);
					} else {
						CameraPosition newPosition = new CameraPosition(
								LastLegitLocation, position.zoom,
								position.tilt, position.bearing);

						map.animateCamera(CameraUpdateFactory
								.newCameraPosition(newPosition), 100, null);
					}

					// LastLegitLocation = new LatLng(position.target.latitude,
					// position.target.longitude);
					// map.moveCamera(CameraUpdateFactory.newLatLngZoom(LastLegitLocation,
					// position.zoom));
				}

			}

			sendEmptyMessageDelayed(0, 100);
		}
	}

	// end overscroll handler
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@Override
	public void onLocationChanged(android.location.Location arg0) {

		LatLng current = new LatLng(arg0.getLatitude(), arg0.getLongitude());

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

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}// end MapView

