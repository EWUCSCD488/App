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
import com.spokanevalley.bankStore.gameModel;

public class DatabaseInterface {

	public static final String TAG = DatabaseInterface.class.getName();
	private static DatabaseInterface databaseInterface;

	private static ArrayList<Location> LIST = null;
	private static ArrayList<gameModel> SCORE_LIST = null;
	private SpokaneValleyDatabaseHelper helper;
	
	// the tableName need to match with name of table in actual database
	private static final String LocationtableName = "LocationTable";
	private static final String ScoretableName = "GameScoreTable";
	private static final String AppleID = "Apple Game";
	
	private static final int ID_LOCATION_COLUMN = 0 ;
	private static final int LATITUDE_LOCATION_COLUMN = 1 ;
	private static final int LONGITUDE_LOCATION_COLUMN = 2 ;
	private static final int TITLE_LOCATION_COLUMN = 4 ;
	private static final int INFO_LOCATION_COLUMN = 5 ;
	
	private static final int ID_MAXSCORE_COLUMN = 0;
	private static final int SCORE_MAXSCORE_COLUMN = 1;
	
	
	public static DatabaseInterface Create(Context context) {
		if (databaseInterface == null) {
			databaseInterface = new DatabaseInterface(
					context);
		}
		return databaseInterface;
	}

	private DatabaseInterface(Context context) {
		
		
		helper = new SpokaneValleyDatabaseHelper(context);
		LoadingDatabaseLocations();
		LoadingDatabaseScores();
		LoadingAllGamesInitial();

	}

	private void LoadingAllGamesInitial() {
		helper.insertScoreData(ScoretableName, AppleID, "0");
		// add here for more games
	}

	public SpokaneValleyDatabaseHelper getDatabase() {
		return helper;
	}

	public ArrayList<Location> getLocationList() {
		LoadingDatabaseLocations();
		return LIST;
	}
	
	public ArrayList<gameModel> getScoreList() {
		LoadingDatabaseScores();
		return SCORE_LIST;
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
		LIST = new ArrayList<Location>();
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
	
	private void LoadingDatabaseScores() {

		Cursor cursor = helper.getDataAll(ScoretableName);
		
		//id_counter = cursor.getCount();
		SCORE_LIST = new ArrayList<gameModel>();
		while (cursor.moveToNext()) {
			// loading each element from database
			String ID  = cursor.getString(ID_LOCATION_COLUMN);
			String Score = cursor.getString(LATITUDE_LOCATION_COLUMN);
			
			SCORE_LIST.add(new gameModel(ID, Integer.parseInt(Score),"pharrell"));

		} // travel to database result

	}

}
