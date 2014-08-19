package com.spokanevalley.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Define important database tables and create access to database
 * 
 * @author : Quyen Ha
 * Eastern Washington University
 */

public class SpokaneValleyDatabaseHelper {

	// USE THIS TO DEBUG THE CODE
	public static final String TAG = SpokaneValleyDatabaseHelper.class
			.getName();

	privateDatabaseContent databaseContent;
	Context context;

	/**
	 * Constructor takes in context of Android application and activate database
	 * @param context
	 */
	
	public SpokaneValleyDatabaseHelper(Context context) {

		databaseContent = new privateDatabaseContent(context);

		// call this to make effect on database , onCreate will be called and
		// create tables for database
		SQLiteDatabase db = databaseContent.getWritableDatabase();

		// import context to avoid duplicate values
		this.context = context;
	}

	/**
	 * THIS METHODS DELETE ALL ROWS IN THE TABLE , THE TABLE AND COLUMNS ARE
	 * STILL THERE
	 * 
	 * @param context
	 * @return how many lines it deletes in table 
	 */
	public int deleteTable(String table_name) {

		// If we have multiple tables, there will be more IF statement
		if (table_name.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME)) {
			
			SQLiteDatabase db = databaseContent.getWritableDatabase();
			// return int of how many rows effected , if 0 , there is no change
			// on the table
			// make sure you have right name for database
			return db.delete(privateDatabaseContent.MAXSCORE_TABLE_NAME, null,null);
			
		} else if (table_name.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME)) {

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			return db.delete(privateDatabaseContent.POOL_LOCATION_TABLE_NAME, null,null);
			
		}  else if (table_name.equals(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME)) {

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			return db.delete(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME, null,null);
			
		} else if (table_name.equals(privateDatabaseContent.GAME_LOCATION_TABLE_NAME)) {

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			return db.delete(privateDatabaseContent.GAME_LOCATION_TABLE_NAME, null,null);
			
		}else {
			return 0;
		}

	}  
 
	/**
	 * THIS METHOD UPDATE ROWS IN TABLE MAXSCORE AT GIVEN ID WITH NEW MAX SCORE
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
	 * 
	 * @param tableName
	 * @param ID of row  to update
	 * @param score value to update
	 * @return how many rows it updates in database
	 */
	public int updateScoreTable(String tableName, String ID, String score) {

		// If we have multiple tables, there will be more IF statement
		if (!tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return 0;
		else {

			SQLiteDatabase db = databaseContent.getReadableDatabase();

			// VALUES TO BE CHANGED
			ContentValues values = new ContentValues();
			values.put(privateDatabaseContent.MAXSCORE_SCORE, score);

			// CONDITION WHERE IT SHOULD BE CHANGED
			String[] whereArgs = { ID };

			return db.update(privateDatabaseContent.MAXSCORE_TABLE_NAME,
					values, privateDatabaseContent.MAXSCORE_ID + " = ?",
					whereArgs);
		}

	}
	
	/**
	 * THIS METHOD UPDATE ROWS IN TOTAL SCORE WITH ID AND TOTAL POINT
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
	 * 
	 * @param table Name
	 * @param ID of total score
	 * @param score point
	 */
	public int updateTotalScoreTable(String tableName,String ID,  String point) {

		// If we have multiple tables, there will be more IF statement
		if (!tableName.equals(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME))
			return 0;
		else {

			SQLiteDatabase db = databaseContent.getReadableDatabase();

			// VALUES TO BE CHANGED
			ContentValues values = new ContentValues();
			values.put(privateDatabaseContent.TOTAL_SCORE_POINT, point);
			
			// CONDITION WHERE IT SHOULD BE CHANGED
			String[] whereArgs = { ID };

			return db.update(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME,
					values, privateDatabaseContent.TOTAL_SCORE_ID + " = ?",
					whereArgs);
		}//ELSE

	}

	/**
	 * THIS METHOD UPDATE ROWS IN TABLE LOCATION WITH ID ,isUsed boolean AS String
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
	 * 
	 * @param table Name
	 * @param ID of locations
	 * @param ID is used(1) or not(0)
	 * @return how many rows changed
	 */
	public int updatePoolLocationTable(String tableName,String ID, String isUsed) {

		// If we have multiple tables, there will be more IF statement
		if (!tableName.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME))
			return 0;
		else {

			SQLiteDatabase db = databaseContent.getReadableDatabase();

			// VALUES TO BE CHANGED
			ContentValues values = new ContentValues();
			values.put(privateDatabaseContent.POOL_IS_VISITED_USED, isUsed);
			
			// CONDITION WHERE IT SHOULD BE CHANGED
			String[] whereArgs = { ID };

			return db.update(privateDatabaseContent.POOL_LOCATION_TABLE_NAME,
					values, privateDatabaseContent.POOL_LOCATION_ID + " = ?",
					whereArgs);
		}//ELSE

	}
	
	/**
	 * THIS METHOD UPDATE ROWS IN TABLE GAME LOCATION WITH ID ,isUsed boolean AS String
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
	 * 
	 * @param table Name
	 * @param ID of game location
	 * @param ID is used(1) or not(0)
	 * @return how many rows changed
	 */
	public int updateGameLocationTable(String tableName,String ID, String isUsed) {

		// If we have multiple tables, there will be more IF statement
		if (!tableName.equals(privateDatabaseContent.GAME_LOCATION_TABLE_NAME))
			return 0;
		else {

			SQLiteDatabase db = databaseContent.getReadableDatabase();

			// VALUES TO BE CHANGED
			ContentValues values = new ContentValues();
			values.put(privateDatabaseContent.GAME_LOCATION_IS_VISITED_USED, isUsed);
			
			// CONDITION WHERE IT SHOULD BE CHANGED
			String[] whereArgs = { ID };

			return db.update(privateDatabaseContent.GAME_LOCATION_TABLE_NAME,
					values, privateDatabaseContent.GAME_LOCATION_ID + " = ?",
					whereArgs);
		}//ELSE

	}

	/**
	 * INSERT NEW ROW ON SCORE TABLE , null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
	 * 
	 * @param table Name
	 * @param ID of game
	 * @param max score of game
	 * @return how many row changed
	 */
	public long insertScoreData(String tableName, String ID, String Maxscore) {

		// CHECK IF WE HAVE INSERTING IN RIGHT TABLE
		if (!tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return -1;
		else {
			// CREATE VALUES FOR A ROW
			ContentValues contentvalue = new ContentValues();
			contentvalue.put(privateDatabaseContent.MAXSCORE_ID, ID);
			contentvalue.put(privateDatabaseContent.MAXSCORE_SCORE, Maxscore);

			// PERFORM INSERTING
			SQLiteDatabase db = databaseContent.getWritableDatabase();

			long RowID = db.insert(privateDatabaseContent.MAXSCORE_TABLE_NAME,
					null, contentvalue);
			// if RowID is -1 , operation fails
			return RowID;
		}
	}
	
	/** 
	 * INSERT NEW ROW ON Pool Location table , null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
	 * 
	 * @param table Name
	 * @param ID location
	 * @param description about location
	 * @param isUsed boolean is used(1) or not(0)
	 * @retun how many row changed
	 */
	public long insertPoolLocation(String tableName, String ID, String description,String isCouponUsed) {

		// CHECK IF WE HAVE INSERTING IN RIGHT TABLE
		if (!tableName.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME))
			return -1;
		else {
			// CREATE VALUES FOR A ROW
			ContentValues contentvalue = new ContentValues();
			contentvalue.put(privateDatabaseContent.POOL_LOCATION_ID, ID);
			contentvalue.put(privateDatabaseContent.POOL_LOCATION_DESCRIPTION, description);
			contentvalue.put(privateDatabaseContent.POOL_IS_VISITED_USED, isCouponUsed);

			// PERFORM INSERTING
			SQLiteDatabase db = databaseContent.getWritableDatabase();

			long RowID = db.insert(privateDatabaseContent.POOL_LOCATION_TABLE_NAME,
					null, contentvalue);
			// if RowID is -1 , operation fails
			return RowID;
		}
	}
	
	/** 
	 * INSERT NEW ROW ON game Location table , null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
	 * 
	 * @param table Name
	 * @param ID location
	 * @param description about location
	 * @param isUsed boolean is used(1) or not(0)
	 * @retun how many row changed
	 */
	public long insertGameLocation(String tableName, String ID, String description,String isCouponUsed) {

		// CHECK IF WE HAVE INSERTING IN RIGHT TABLE
		if (!tableName.equals(privateDatabaseContent.GAME_LOCATION_TABLE_NAME))
			return -1;
		else {
			// CREATE VALUES FOR A ROW
			ContentValues contentvalue = new ContentValues();
			contentvalue.put(privateDatabaseContent.GAME_LOCATION_ID, ID);
			contentvalue.put(privateDatabaseContent.GAME_LOCATION_DESCRIPTION, description);
			contentvalue.put(privateDatabaseContent.GAME_LOCATION_IS_VISITED_USED, isCouponUsed);

			// PERFORM INSERTING
			SQLiteDatabase db = databaseContent.getWritableDatabase();

			long RowID = db.insert(privateDatabaseContent.GAME_LOCATION_TABLE_NAME,
					null, contentvalue);
			// if RowID is -1 , operation fails
			return RowID;
		}
	}
	
	/**
	 * INSERT NEW new total score to database
	 * null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
	 * 
	 * @param table Name
	 * @param ID of location
	 * @param total point
	 * @return how many rows changed
	 */
	public long insertTotalScoreData(String tableName, String ID ,String point) {

		// CHECK IF WE HAVE INSERTING IN RIGHT TABLE
		if (!tableName.equals(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME))
			return -1;
		else {
			// CREATE VALUES FOR A ROW
			ContentValues contentvalue = new ContentValues();
			contentvalue.put(privateDatabaseContent.TOTAL_SCORE_ID, ID);
			contentvalue.put(privateDatabaseContent.TOTAL_SCORE_POINT, point);

			// PERFORM INSERTING
			SQLiteDatabase db = databaseContent.getWritableDatabase();

			long RowID = db.insert(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME,
					null, contentvalue);
			// if RowID is -1 , operation fails
			return RowID;
		}
	}
	
	/**
	 * GET ALL DATA FROM TABLENAME GIVEN AS INPUT 
	 * query (String tablename, String[] columns, String slection, String[]
	 * selectionArgs , String groupBy , String having, String orderBy)
	 * 
	 * @param table name
	 * @return Cursor of temporary in-memory table contains result of all data in table
	 */
	public Cursor getDataAll(String tableName) {
		if (tableName.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME)){				// get all data in  pool location table

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.POOL_LOCATION_TABLE_NAME;
			String[] columns = { privateDatabaseContent.POOL_LOCATION_ID,
					privateDatabaseContent.POOL_LOCATION_DESCRIPTION,
					privateDatabaseContent.POOL_IS_VISITED_USED};

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else if (tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME)){			// get all data in max score table

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.MAXSCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.MAXSCORE_ID,
					privateDatabaseContent.MAXSCORE_SCORE };

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else if (tableName.equals(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME)){			// get all data in total score table
			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.TOTAL_SCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.TOTAL_SCORE_ID,
					privateDatabaseContent.TOTAL_SCORE_POINT};

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else if (tableName.equals(privateDatabaseContent.GAME_LOCATION_TABLE_NAME)){			// get all data in total score table
			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.GAME_LOCATION_TABLE_NAME;
			String[] columns = { privateDatabaseContent.GAME_LOCATION_ID,
					privateDatabaseContent.GAME_LOCATION_DESCRIPTION,
					privateDatabaseContent.GAME_LOCATION_IS_VISITED_USED};

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else{
			return null;
		}
	}
	
	/**
	 * GET THE MAX SCORE COMES WITH GIVEN ID IN MAXSCORE TABLE
	 * 
	 * @param tableName
	 * @param ID of game
	 * @return cursor contains result
	 */
	
	public Cursor getScoreData(String tableName, String ID) {

		if (!tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return null;
		else {

			SQLiteDatabase db = databaseContent.getWritableDatabase();

			String table = privateDatabaseContent.MAXSCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.MAXSCORE_ID,
					privateDatabaseContent.MAXSCORE_SCORE };
			String[] selectionArgs = { ID };

			Cursor cursor = db.query(table, columns,
					privateDatabaseContent.MAXSCORE_ID + " = ?", selectionArgs,
					null, null, null);

			return cursor;
		}
	}
	
	/**
	 * GET total score data based on ID
	 * 
	 * @param table Name
	 * @param ID of total score row
	 * @return Cursor contains result
	 */
	
	public Cursor getTotalScoreData(String tableName,String ID) {

		if (!tableName.equals(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME))
			return null;
		else {

			SQLiteDatabase db = databaseContent.getWritableDatabase();

			String table = privateDatabaseContent.TOTAL_SCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.TOTAL_SCORE_ID,
					privateDatabaseContent.TOTAL_SCORE_POINT};
			
			String[] selectionArgs = { ID };

			Cursor cursor = db.query(table, columns,
					privateDatabaseContent.TOTAL_SCORE_ID + " = ?", selectionArgs,
					null, null, null);

			return cursor;
		}
	}
	
	/**
	 * GET POOL LOCATION DATA BASED ON ID
	 * 
	 * @param
	 */
	
	public Cursor getPoolLocationData(String tableName,String ID) {

		if (!tableName.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME))
			return null;
		else {

			SQLiteDatabase db = databaseContent.getWritableDatabase();

			String table = privateDatabaseContent.POOL_LOCATION_TABLE_NAME;
			String[] columns = { privateDatabaseContent.POOL_LOCATION_ID,
					privateDatabaseContent.POOL_LOCATION_DESCRIPTION,
					privateDatabaseContent.POOL_IS_VISITED_USED};
			String[] selectionArgs = { ID };

			Cursor cursor = db.query(table, columns,
					privateDatabaseContent.POOL_LOCATION_ID + " = ?", selectionArgs,
					null, null, null);

			return cursor;
		}
	}

	/**
	 * GET GAME LOCATION DATA BASED ON ID
	 * 
	 * @param
	 */
	
	public Cursor getGameLocationData(String tableName,String ID) {

		if (!tableName.equals(privateDatabaseContent.GAME_LOCATION_TABLE_NAME))
			return null;
		else {

			SQLiteDatabase db = databaseContent.getWritableDatabase();

			String table = privateDatabaseContent.GAME_LOCATION_TABLE_NAME;
			String[] columns = { privateDatabaseContent.GAME_LOCATION_ID,
					privateDatabaseContent.GAME_LOCATION_DESCRIPTION,
					privateDatabaseContent.GAME_LOCATION_IS_VISITED_USED};
			String[] selectionArgs = { ID };

			Cursor cursor = db.query(table, columns,
					privateDatabaseContent.GAME_LOCATION_ID + " = ?", selectionArgs,
					null, null, null);

			return cursor;
		}
	}
	

	/**
	 * THIS CLASS CONTAINS VALUES OF DATABASE . IF CHANGING ANY INFORMATION
	 * BELOW, DATABASE WILL CRASH
	 * 
	 * NOTE  : POOL LOCATIONS TABLE IS USED TO STORE POOL LOCATIONS AND COUPONS BECAUSE THEY HAVE SAME SET OF ATTRIBUTES
	 * 
	 * @version 1.0
	 * 
	 * @author  Quyen Ha
	 * 
	 */

	private class privateDatabaseContent extends SQLiteOpenHelper {

		private Context context;

		// Handle database name and VERSION, NEVER CHANGE THIS
		private static final String DATABASE_NAME = "SpokaneValleyDatabase";
		private static final int DATABASE_VERSION = 1;

		/*
		 * *******************************************************************************************
		 * VARIABLES FOR POOL POOL LOCATION TABLE
		 * ****************************************
		 * ***************************************************
		 */

		// Name of table we have in database, we can have multiple tables in one
		// database
		private static final String POOL_LOCATION_TABLE_NAME = "PoolLocationTable";

		// Name of columns in each table, name starts with name of table , then
		// name of columns
		private static final String POOL_LOCATION_ID = "POOL_ID";
		private static final String POOL_LOCATION_DESCRIPTION = "POOL_Description";
		private static final String POOL_IS_VISITED_USED = "POOL_Visited";

		// Query to create table , we can have multiple queries to create
		// multiple tables
		private static final String CREATE_QUERY_POOL_TABLE = "CREATE TABLE "
				+ POOL_LOCATION_TABLE_NAME + " (" + POOL_LOCATION_ID
				+ " VARCHAR(255) PRIMARY KEY ," + POOL_LOCATION_DESCRIPTION
				+ " VARCHAR(255) ," + POOL_IS_VISITED_USED
				+ " VARCHAR(255));";

		private static final String DROP_TABLE_POOL_TABLE = "DROP TABLE IF EXISTS "
				+ POOL_LOCATION_TABLE_NAME;
		
		/*
		 * *******************************************************************************************
		 * VARIABLES FOR MAX SCORE TABLE
		 * ****************************************
		 * ***************************************************
		 */

		// Name of table we have in database, we can have multiple tables in one
		// database
		private static final String MAXSCORE_TABLE_NAME = "GameScoreTable";

		// Name of columns in each table, name starts with name of table , then
		// name of columns
		private static final String MAXSCORE_ID = "MAX_ID";
		private static final String MAXSCORE_SCORE = "MaxScore";

		// Query to create table , we can have multiple queries to create
		// multiple tables
		private static final String CREATE_QUERY_MAXSCORE = "CREATE TABLE "
				+ MAXSCORE_TABLE_NAME + " (" + MAXSCORE_ID
				+ " VARCHAR(255) PRIMARY KEY ," + MAXSCORE_SCORE
				+ " VARCHAR(255));";

		private static final String DROP_TABLE_MAXSCORE = "DROP TABLE IF EXISTS "
				+ MAXSCORE_TABLE_NAME;

		/*
		 * *******************************************************************************************
		 * VARIABLES FOR TOTAL SCORE TABLE
		 * *****************************************
		 * **************************************************
		 */

		// Name of table we have in database, we can have multiple tables in one
		// database
		private static final String TOTAL_SCORE_TABLE_NAME = "totalScoreTable";

		// Name of columns in each table, name starts with name of table , then
		// name of columns
		private static final String TOTAL_SCORE_ID = "scoreID";
		private static final String  TOTAL_SCORE_POINT = "total";
		

		// Query to create table , we can have multiple queries to create
		// multiple tables
		private static final String CREATE_QUERY_TOTAL_SCORE = "CREATE TABLE "
				+ TOTAL_SCORE_TABLE_NAME 	+ " (" 
				+ TOTAL_SCORE_ID 			+ " VARCHAR(255) PRIMARY KEY ," 
				+ TOTAL_SCORE_POINT 		+ " VARCHAR(255));";

		private static final String DROP_TABLE_TOTAL_SCORE = "DROP TABLE IF EXISTS "
				+ TOTAL_SCORE_TABLE_NAME;


		/*
		 * *******************************************************************************************
		 * VARIABLES FOR GAME LOCATION TABLE
		 * *****************************************
		 * **************************************************
		 */

		// Name of table we have in database, we can have multiple tables in one
		// database
		private static final String GAME_LOCATION_TABLE_NAME = "gameLocationTable";

		// Name of columns in each table, name starts with name of table , then
		// name of columns
		private static final String GAME_LOCATION_ID = "gameLocationID";
		private static final String GAME_LOCATION_DESCRIPTION = "GAMELOCATION_Description";
		private static final String GAME_LOCATION_IS_VISITED_USED = "GAMELOCATION_Visited";
		

		// Query to create table , we can have multiple queries to create
		// multiple tables
		private static final String CREATE_QUERY_GAME_LOCATION = "CREATE TABLE "
				+ GAME_LOCATION_TABLE_NAME 		+ " (" 
				+ GAME_LOCATION_ID 				+ " VARCHAR(255) PRIMARY KEY ," 
				+ GAME_LOCATION_DESCRIPTION 	+ " VARCHAR(255),"
				+ GAME_LOCATION_IS_VISITED_USED + "	VARCHAR(255));";

		private static final String DROP_TABLE_GAME_LOCATION = "DROP TABLE IF EXISTS "
				+ GAME_LOCATION_TABLE_NAME;
		
		
		/*
		 * *******************************************************************************************
		 * CONTRUCTORS
		 * **********************************************************
		 * *********************************
		 */

		public privateDatabaseContent(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			// context , database name, create custom cursor , version of
			// database

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				Log.d(TAG, "Created database"); // DELETE LATER
				// CREATE TABLE FOR MAXSCORE AND LOCATION
				db.execSQL(CREATE_QUERY_MAXSCORE);
				db.execSQL(CREATE_QUERY_TOTAL_SCORE);
				db.execSQL(CREATE_QUERY_POOL_TABLE);
				db.execSQL(CREATE_QUERY_GAME_LOCATION);
			} catch (SQLException e) {
				Log.d(TAG, "Created database FAILED"); // DELETE LATER
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE_MAXSCORE);
			db.execSQL(DROP_TABLE_TOTAL_SCORE);
			db.execSQL(DROP_TABLE_POOL_TABLE);
			db.execSQL(DROP_TABLE_GAME_LOCATION);
			onCreate(db);
		}

	}// privateDatabaseContent

}
