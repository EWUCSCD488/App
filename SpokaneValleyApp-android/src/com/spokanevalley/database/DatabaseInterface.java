package com.spokanevalley.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.spokanevalley.app.Location;
import com.spokanevalley.app.LocationList;

public class DatabaseInterface {

	public static final String TAG = DatabaseInterface.class.getName();
	private static DatabaseInterface databaseInterface;

	private static ArrayList<Location> LIST = null;
	private SpokaneValleyDatabaseHelper helper;
	private static final String LocationtableName = "LocationTable";

	private static final int ID_LOCATION_COLUMN = 0 ;
	private static final int LATITUDE_LOCATION_COLUMN = 1 ;
	private static final int LONGITUDE_LOCATION_COLUMN = 2 ;
	private static final int TITLE_LOCATION_COLUMN = 4 ;
	private static final int INFO_LOCATION_COLUMN = 5 ;
	
	
	public static DatabaseInterface Create(Context context) {
		if (databaseInterface == null) {
			databaseInterface = new DatabaseInterface(
					context);
		}
		return databaseInterface;
	}

	private DatabaseInterface(Context context) {
		LIST = new ArrayList<Location>();
		helper = new SpokaneValleyDatabaseHelper(context);
		LoadingDatabaseLocations();

	}

	public SpokaneValleyDatabaseHelper getDatabase() {
		return helper;
	}

	public ArrayList<Location> getLocationList() {
		return LIST;
	}

	public void addNewLocation(Location location) {
		
		// add to database here
		Cursor checking_avalability = helper.getLocationData(LocationtableName,
				location.getID());
		if (checking_avalability == null) {
			
			LIST.add(location);
			
			long RowIds = helper.insertLocationData(LocationtableName,
					location.getID(), String.valueOf(location.getLatitude()),
					String.valueOf(location.getLongitude()),
					location.getTitle(), location.getInfo());
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");
		} else if (checking_avalability.getCount() == 0) {
			
			LIST.add(location);
			
			long RowIds = helper.insertLocationData(LocationtableName,
					location.getID(), String.valueOf(location.getLatitude()),
					String.valueOf(location.getLongitude()),
					location.getTitle(), location.getInfo());
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");
		}

	}

	public Location getLocation(String title) {
		return null;
	}

	private void LoadingDatabaseLocations() {

		Cursor cursor = helper.getDataAll(LocationtableName);

		//id_counter = cursor.getCount();

		while (cursor.moveToNext()) {
			// loading each element from database
			String ID  = cursor.getString(ID_LOCATION_COLUMN);
			String Latitude = cursor.getString(LATITUDE_LOCATION_COLUMN);
			String Longitude = cursor.getString(LONGITUDE_LOCATION_COLUMN);
			String Title = cursor.getString(TITLE_LOCATION_COLUMN);
			String Info = cursor.getString(INFO_LOCATION_COLUMN);
			
			LIST.add(new Location(ID,Title,Info,Double.parseDouble(Latitude),Double.parseDouble(Longitude)));

		} // travel to database result

	}

}
