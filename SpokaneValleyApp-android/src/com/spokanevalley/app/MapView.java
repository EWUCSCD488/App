package com.spokanevalley.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.spokanevalley.addScoreGPS.addScoreGPSLauncher;
import com.spokanevalley.apples.AppleActivity;
import com.spokanevalley.bankStore.BankActivity;
import com.spokanevalley.bankStore.ButtonSoundFactory;
import com.spokanevalley.bankStore.MallActivity;
import com.spokanevalley.database.DatabaseCustomAccess;
import com.spokanevalley.discoveryGame.DiscoveryActivity;
import com.spokanevalley.farm.FarmGameLauncher;
import com.spokanevalley.plantesferry.PlantesFerryActivity;
import com.spokanevalley.ski.SkiActivity;

public class MapView extends Activity implements OnMarkerClickListener,
		LocationListener, OnInfoWindowClickListener {

	public static final String TAG = MapView.class.getName();

	// bounds for limiting scolling and zooming.
	private final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(47.594413,-117.399280), new LatLng(47.750088, -117.035187));
	private overscrollHandler mOverscrollHandler = new overscrollHandler();
	final Context context = this;
	private GoogleMap map;
	private Marker lastMarkerSelected = null;
	private LatLng LastLegitLocation = null;
	private LatLng locationMain = null;
	private float globalZoom = 0;
	private LocationManager locationManager;
	private ButtonSoundFactory sounds;	
	public static final int REQUEST_CODE = 1;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Happens when activity is created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);
		
		//Play the sounds
		sounds = new ButtonSoundFactory(context);
		sounds.playBackground();
		
		mOverscrollHandler.sendEmptyMessageDelayed(0, 100);
		DatabaseCustomAccess.Create(context);
		
		// Initialize Location List from the locations.XML file
		try {
			LocationList.Create(LocationInflator.inflate(this, R.xml.locations));
		} catch (Exception e) {
			Log.e("Inflator Error", e.getMessage());
		}

		// location checking...
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = lm.getBestProvider(new Criteria(), true);
		//Default location checking values
		lm.requestLocationUpdates(provider, 1000, 0, this);
		
		//Changing font of score in the corner to our apps font
		TextView tv = (TextView) findViewById(R.id.scoreOnTopOfMap);
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Bubblegum.otf");
		tv.setTypeface(face);
		
		// LOCATION ***********************************************************************
		locationManager = (LocationManager) this.getSystemService(context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
					
			@Override
			public void onLocationChanged(android.location.Location location) {
				//TODO: Once database is done also add "if has not been visited" into the "if" statement
				for (Location location2 : LocationList.LIST) {
								
								//Testing
								Log.i("MyActivity", "*********latitude bottom left: " + location2.getLatitudeBottomLeft());
								Log.i("MyActivity", "*********longitude bottom left: " + location2.getLongitudeBottomLeft());
								Log.i("MyActivity", "*********latitude top right: " + location2.getLatitudeTopRight());
								Log.i("MyActivity", "*********longitude top right: " + location2.getLongitudeTopRight());
								Log.i("MyActivity", "*********lattitude: " + location.getLatitude());
								Log.i("MyActivity", "*********longitude: " + location.getLongitude());
								
					double temp1 = location.getLatitude();
					double temp2 = location.getLongitude();
					
				/*// --TESTING--------------------------------------------------------------------------
					System.out.println(location2.getID());
					if(DatabaseCustomAccess.Create(context).getGameLocationVisitedOrNot(location2.getID()))
						System.out.println("true");
					
					if(!DatabaseCustomAccess.Create(context).getGameLocationVisitedOrNot(location2.getID()))
						System.out.println("false");
				// --TESTING--------------------------------------------------------------------------
				*/	
					if(location2.getLatitudeBottomLeft() <= temp1 && 
							location2.getLongitudeBottomLeft() <= temp2 &&
							location2.getLatitudeTopRight() >= temp1 &&
							location2.getLongitudeTopRight() >= temp2 &&
							!DatabaseCustomAccess.Create(context).getGameLocationVisitedOrNot(location2.getID())){
						Intent intent = new Intent(MapView.this, addScoreGPSLauncher.class);
						startActivityForResult(intent, REQUEST_CODE);
						/* visited = true
						 * not visited = false
						 */
						DatabaseCustomAccess.Create(context).updateGameLocationWithVisitOrNot(location2.getID(), true);
						}
					}
				}
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			            
			@Override
			public void onProviderEnabled(String provider) {
			
			}
			            
			@Override
			public void onProviderDisabled(String provider) {
			
			}
		};
		
		//Request for updates on GPS every so often time and every so often feet moved
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, locationListener);
		        
		// END LOCATION *******************************************************************  
	}// end onCreate

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Main Map setup and initialization.
	 */
	public void initilizeMap() {

		//Setting fragment to map
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

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
			                
			                //Change title font
			        		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/PhatRave.ttf");
			        		titleUi.setTypeface(face);
			                
			                //setting info
			                TextView snippetUi = ((TextView) view.findViewById(R.id.custom_snippet));
			                if (arg0.getSnippet() != null && arg0 != null)
			                	snippetUi.setText(arg0.getSnippet());
			                else
			                	snippetUi.setText("");
			                
			                //MaxScore
			                TextView maxScoreUi = ((TextView) view.findViewById(R.id.custom_maxScore));
			                if (arg0 != null){
				                int maxScore = getMaxScoreForWindow(arg0.getTitle());
				                if(maxScore >= 0)
				                	maxScoreUi.setText("Max Score: " + maxScore);
			                }
			                else
			                	snippetUi.setText("");
			 
			                //Setting click to play option
			                TextView clickUi = ((TextView) view.findViewById(R.id.custom_click));
			                clickUi.setText("Click to Play!");
			                
			                //Change click to play font
			                clickUi.setTypeface(face);
			                
			                // Returning the view containing InfoWindow contents
			                return view;
			            }//end getInfoWindow

			            //Factory for recieving maxScore for each specific location. Checks the name of the marker title and based on the title calls a specific database method that correlates to that location. Return -1 if this location should not have a maxScore. **If you add a location you need to add a specific case here**
						private int getMaxScoreForWindow(String title) {
							switch (title) {
					         case "Discovery Playground":
					 			return DatabaseCustomAccess.Create(context).saveMaxScore_DiscoveryGame(0);
					         case "Terrace View Park and Pool":
					        	 return DatabaseCustomAccess.Create(context).saveMaxScore_AppleGame(0);
					         case "Plantes Ferry Park":
					        	 return DatabaseCustomAccess.Create(context).saveMaxScore_PlantesFerryGame(0);
					         case "Bank":
					        	 return -1;
					         case "Greenacres Park":
					        	 return DatabaseCustomAccess.Create(context).saveMaxScore_GreenacresGame(0);
					         case "The Mall":
					        	 return -1;
					         case "Ski!":
					        	 return DatabaseCustomAccess.Create(context).saveMaxScore_SkiGame(0);
					         default:
					        	 return -1;
						}}
			        });
		}

		map.setPadding(0, 0, 30, 0);

		// set location and camera of where map looks
		map.setMyLocationEnabled(false);

		// coordinates of center of Spokane Valley
		LatLng center = new LatLng(47.633430, -117.226525);

		if (LastLegitLocation == null)
			LastLegitLocation = center;
		
		// Place where the map loads on startup
		if (locationMain == null) {
			locationMain = center; 
		}
		
		// Scaling when it loads (how zoomed in the map is)
		if (globalZoom == 0) {
			globalZoom = 12.9f; 
		}
		
		//Move the view to center adn specified zoom
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMain, globalZoom));

		// Marker info window listener
		map.setOnMarkerClickListener(this);

		//This adds padding on top
		GroundOverlay groundOverlayPaddingTop = map
				.addGroundOverlay(new GroundOverlayOptions()
				.image(BitmapDescriptorFactory
				.fromResource(R.drawable.mapsky))
				.position(new LatLng(47.823297, -117.239437), 32000)
				.transparency(0.0f));
		
		//This adds padding on Bottom
		GroundOverlay groundOverlayPaddingBottom = map
				.addGroundOverlay(new GroundOverlayOptions()
				.image(BitmapDescriptorFactory
				.fromResource(R.drawable.groundmap))
				.position(new LatLng(47.520112, -117.239437), 32000)
				.transparency(0.0f));
		
		//This adds padding on Right Side
		GroundOverlay groundOverlayPaddingSide = map
				.addGroundOverlay(new GroundOverlayOptions()
				.image(BitmapDescriptorFactory
				.fromResource(R.drawable.groundmap))
				.position(new LatLng(47.670568, -116.824730), 32000)
				.transparency(0.0f));
		
		GroundOverlay groundOverlayPaddingSideLeft = map
				.addGroundOverlay(new GroundOverlayOptions()
				.image(BitmapDescriptorFactory
				.fromResource(R.drawable.groundmap))
				.position(new LatLng(47.670568, -117.432411), 32000)
				.transparency(0.0f));
		
		// This adds an image over the google map
		GroundOverlay groundOverlay = map
				.addGroundOverlay(new GroundOverlayOptions()
				.image(BitmapDescriptorFactory
				.fromResource(R.drawable.mapbg))
				.position(new LatLng(47.670568, -117.239437), 32000)
				.transparency(0.0f));
		
		//Remove un-needed functionality
		map.getUiSettings().setCompassEnabled(false);
		map.getUiSettings().setRotateGesturesEnabled(false);
		map.getUiSettings().setTiltGesturesEnabled(false);
		
		//Update the score in the corner of the map every time the map loads
		updateCornerScoreDisplay();
	
	}// end initilizeMap

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * Does this when map page is paused.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		sounds.stopbackground();
		VisibleRegion region = map.getProjection().getVisibleRegion();
		LatLngBounds cameraBounds = region.latLngBounds;
		LatLng myLatLng = map.getCameraPosition().target;
		CameraPosition position = map.getCameraPosition();
		globalZoom = position.zoom;
		locationMain = myLatLng;
	}// end onPause

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Does this when map page is brought up again after being left.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
		sounds.playBackground();
	}// end onResume

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void onInfoWindowClick(Marker marker) 
    {
    	// Play Menu Button Sound
    	sounds.playsound1(); 
    	Log.d(TAG, marker.getTitle());
    	
    	// Initializing game Factory. Reads the title of the marker and plays the specific game that correlates to that marker. **If you add location then make sure to link your game to the marker location in here**
		if(marker.getTitle().equals("Bank")){
			Intent intent = new Intent(MapView.this, BankActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if(marker.getTitle().equals("Terrace View Park and Pool")){
			// INITIALIZE GAME
			Intent intent = new Intent(MapView.this, AppleActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if(marker.getTitle().equals("Discovery Playground")){
			// INITIALIZE GAME
			Intent intent = new Intent(MapView.this, DiscoveryActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if(marker.getTitle().equals("Plantes Ferry Park")){
			Intent intent = new Intent(MapView.this, PlantesFerryActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if(marker.getTitle().equals("Greenacres Park")){
			Intent intent = new Intent(MapView.this, FarmGameLauncher.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if(marker.getTitle().equals("The Mall")){
			Intent intent = new Intent(MapView.this, MallActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}else if(marker.getTitle().equals("Ski!")){
			Intent intent = new Intent(MapView.this, SkiActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
		}
		
		//Closing the info window after the game has been clicked so that the maxScore can get updated once they come back to the map again (otherwise when they come back to the map the info window will still be open and the maxScore in the infoWindow might not be up to date)
		if(lastMarkerSelected != null){
			lastMarkerSelected.hideInfoWindow();
			lastMarkerSelected = null;
		}
    }

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Updates the total score in the database which in turn updates the score in the corner of the map as well
	 */
	private void updateCornerScoreDisplay() {
		EditText text = (EditText) findViewById(R.id.scoreOnTopOfMap);	
		String totalScore  = String.valueOf( DatabaseCustomAccess.Create(context).getTotalScore());
		text.setText(totalScore);
		Log.d(TAG, "total score  : " + totalScore );
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Responds to marker click
	 * 
	 */
	@Override
	public boolean onMarkerClick(Marker arg0) {
		
		// Play Menu Button Sound
		sounds.playsound1(); 
		
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
        LatLng camMove = new LatLng(curMarkerPos.latitude + 0.0175, curMarkerPos.longitude);

        // Create a camera update with the new latlng to move to            
        CameraUpdate camUpdate = CameraUpdateFactory.newLatLng(camMove);
        
        // Move the map to this position
        map.animateCamera(camUpdate);

		// return true so that the stock things don't happen when a marker is
		// pressed.
		// This removes the automatic centering and the info and title boxes
		return true;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Runs after an activity/game has been run
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);

			if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			}
			
		} catch (Exception ex) {

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
			int counter = getLatLngCorrection(region.latLngBounds);
			if (position != null) {

				if (counter == 0) {
					LastLegitLocation = new LatLng(position.target.latitude, position.target.longitude);
				} 
				else if (counter >= 1) {
					if (counter > 2) {
						CameraPosition newPosition = new CameraPosition(
								locationMain, 11.8f, position.tilt,
								position.bearing);
						map.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition), 250, null);
					} 
					else {
						CameraPosition newPosition = new CameraPosition(
								LastLegitLocation, position.zoom,
								position.tilt, position.bearing);
						map.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition), 250, null);
					}
				}
			}
			sendEmptyMessageDelayed(0, 100);
		}
	} // overscrollHandler

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

