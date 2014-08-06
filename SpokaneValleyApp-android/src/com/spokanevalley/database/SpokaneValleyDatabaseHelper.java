package com.spokanevalley.database;

import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SpokaneValleyDatabaseHelper {

	// USE THIS TO DEBUG THE CODE
	public static final String TAG = SpokaneValleyDatabaseHelper.class
			.getName();

	privateDatabaseContent databaseContent;
	Context context;

	public SpokaneValleyDatabaseHelper(Context context) {

		databaseContent = new privateDatabaseContent(context);

		// call this to make effect on database , onCreate will be called and
		// create tables for database
		SQLiteDatabase db = databaseContent.getWritableDatabase();

		// import context to avoid duplicate values
		this.context = context;
	}

	/*
	 * THIS METHODS DELETE ALL ROWS IN THE TABLE , THE TABLE AND COLUMNS ARE
	 * STILL THERE
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
			
		}else {
			return 0;
		}

	}  
 
	/*
	 * THIS METHOD UPDATE ROWS IN TABLE MAXSCORE AT GIVEN ID WITH NEW MAX SCORE
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
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
	
	/*
	 * THIS METHOD UPDATE ROWS IN TABLE LOCATION WITH ID , LATITUDE,LONGITUDE ONLY , DEFAULT ATTITUDE 0
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
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

	/*
	 * THIS METHOD UPDATE ROWS IN TABLE LOCATION WITH ID , LATITUDE,LONGITUDE AND ATTITUDE
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
	 */
	public int updatePoolLocationTable(String tableName,String ID, String isgetCoupon) {

		// If we have multiple tables, there will be more IF statement
		if (!tableName.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME))
			return 0;
		else {

			SQLiteDatabase db = databaseContent.getReadableDatabase();

			// VALUES TO BE CHANGED
			ContentValues values = new ContentValues();
			values.put(privateDatabaseContent.POOL_IS_VISITED_USED, isgetCoupon);
			
			// CONDITION WHERE IT SHOULD BE CHANGED
			String[] whereArgs = { ID };

			return db.update(privateDatabaseContent.POOL_LOCATION_TABLE_NAME,
					values, privateDatabaseContent.POOL_LOCATION_ID + " = ?",
					whereArgs);
		}//ELSE

	}

	/*
	 * INSERT NEW ROW ON SCORE TABLE , null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
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
	
	/* INSERT NEW ROW ON Pool Location table , null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
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
	
	
	/*
	 * INSERT NEW new total score to database
	 * null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
	 */
	public long insertLocationData(String tableName, String ID ,String point) {

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
	
	/*
	 * INSERT NEW ROW ON LOCATION TABLE , ROW CONTAINS ID,LATITUDE,LONGITUDE, AND ATTITUDE
	 * null VALUES WILL NOT BE CREATED UNLESS USING
	 * numColumnHack in insert method
	 */
	/*public long insertLocationData(String tableName, String ID ,String Latitude, String longitude,String Attitude,String title,String info) {

		// CHECK IF WE HAVE INSERTING IN RIGHT TABLE
		if (!tableName.equals(privateDatabaseContent.LOCATION_TABLE_NAME))
			return -1;
		else {
			// CREATE VALUES FOR A ROW
			ContentValues contentvalue = new ContentValues();
			contentvalue.put(privateDatabaseContent.LOCATION_ID, ID);
			contentvalue.put(privateDatabaseContent.LOCATION_LATITUDE, Latitude);
			contentvalue.put(privateDatabaseContent.LOCATION_LONGITUDE, longitude);
			contentvalue.put(privateDatabaseContent.LOCATION_ATTITUDE, Latitude);
			contentvalue.put(privateDatabaseContent.LOCATION_TITLE, title);
			contentvalue.put(privateDatabaseContent.LOCATION_INFO, info);

			// PERFORM INSERTING
			SQLiteDatabase db = databaseContent.getWritableDatabase();

			long RowID = db.insert(privateDatabaseContent.LOCATION_TABLE_NAME,
					null, contentvalue);
			// if RowID is -1 , operation fails
			return RowID;
		}
	}*/
	
	/*
	 * GET ALL DATA FROM TABLENAME GIVEN AS INPUT 
	 * query (String tablename, String[] columns, String slection, String[]
	 * selectionArgs , String groupBy , String having, String orderBy)
	 */
	public Cursor getDataAll(String tableName) {
		if (tableName.equals(privateDatabaseContent.POOL_LOCATION_TABLE_NAME)){

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.POOL_LOCATION_TABLE_NAME;
			String[] columns = { privateDatabaseContent.POOL_LOCATION_ID,
					privateDatabaseContent.POOL_LOCATION_DESCRIPTION,
					privateDatabaseContent.POOL_IS_VISITED_USED};

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else if (tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME)){

			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.MAXSCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.MAXSCORE_ID,
					privateDatabaseContent.MAXSCORE_SCORE };

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else if (tableName.equals(privateDatabaseContent.TOTAL_SCORE_TABLE_NAME)){
			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.TOTAL_SCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.TOTAL_SCORE_ID,
					privateDatabaseContent.TOTAL_SCORE_POINT};

			Cursor cursor = db.query(table, columns, null, null, null, null,
					null);
			return cursor;
		}else{
			return null;
		}
	}
	
	/*
	 * GET THE MAX SCORE COMES WITH GIVEN ID IN MAXSCORE TABLE
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
	
	/*
	 * GET total score data based on ID
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
	
	/*
	 * GET THE LATITUDE AND LONGITUDE AND ATTITUDE COME WITH GIVEN ID IN LOCATION TABLE
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


	/*
	 * THIS CLASS CONTAINS VALUES OF DATABASE . IF CHANGING ANY INFORMATION
	 * BELOW, DATABASE WILL CRASH
	 */

	private class privateDatabaseContent extends SQLiteOpenHelper {

		private Context context;

		// Handle database name and VERSION, NEVER CHANGE THIS
		private static final String DATABASE_NAME = "SpokaneValleyDatabase";
		private static final int DATABASE_VERSION = 1;

		/*
		 * *******************************************************************************************
		 * VARIABLES FOR POOL LOCATION TABLE
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
		 * VARIABLES FOR LOCATION TABLE
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
			} catch (SQLException e) {
				Log.d(TAG, "Created database FAILED"); // DELETE LATER
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE_MAXSCORE);
			db.execSQL(DROP_TABLE_TOTAL_SCORE);
			db.execSQL(DROP_TABLE_POOL_TABLE);
			onCreate(db);
		}

	}// privateDatabaseContent

}
