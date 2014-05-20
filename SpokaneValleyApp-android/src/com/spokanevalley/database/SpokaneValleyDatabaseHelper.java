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
		if (!table_name.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return 0;
		else {
			SQLiteDatabase db = databaseContent.getWritableDatabase();

			// return int of how many rows effected , if 0 , there is no change
			// on the table
			// make sure you have right name for database
			return db.delete(privateDatabaseContent.MAXSCORE_TABLE_NAME, null,
					null);
		}

	}

	/*
	 * THIS METHOD UPDATE ROWS IN TABLE MAXSCORE AT GIVEN ID WITH NEW MAX SCORE
	 * 
	 * update (name of table , ContentValues : colums will be changed
	 * values,whereClase , whereArgs)
	 */
	public int updateTable(String tableName, String ID, String score) {

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
	 * INSERT NEW ROW ON DATABASE , null VALUES WILL NOT BE CREATED UNLESS USING numColumnHack in insert method
	 */
	public long insertData(String tableName, String ID, String Maxscore) {
	
		// CHECK IF WE HAVE INSERTING IN RIGHT TABLE
		if (!tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return -1;
		else {
			// CREATE VALUES FOR A ROW
			ContentValues contentvalue = new ContentValues();
			contentvalue.put(privateDatabaseContent.MAXSCORE_ID, ID);
			contentvalue.put(privateDatabaseContent.MAXSCORE_SCORE,Maxscore);
			
			// PERFORM INSERTING
			SQLiteDatabase db = databaseContent.getWritableDatabase();
			
			
			long RowID = db.insert(privateDatabaseContent.MAXSCORE_TABLE_NAME, null,
					contentvalue);
			// if RowID is -1 , operation fails
			return RowID;
		}
	}

	// query (String tablename, String[] columns, String slection, String[]
	// selectionArgs , String groupBy , String having, String orderBy)

	public Cursor getDataAll(String tableName) {
		
		if (!tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return null;
		else {
		
			SQLiteDatabase db = databaseContent.getWritableDatabase();
			String table = privateDatabaseContent.MAXSCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.MAXSCORE_ID,
					privateDatabaseContent.MAXSCORE_SCORE};
	
			Cursor cursor = db.query(table, columns, null, null, null, null, null);
			return cursor;
		}
	}

	public Cursor getData(String tableName,String ID) {
		
		if (!tableName.equals(privateDatabaseContent.MAXSCORE_TABLE_NAME))
			return null;
		else {
		
			SQLiteDatabase db = databaseContent.getWritableDatabase();
	
			String table = privateDatabaseContent.MAXSCORE_TABLE_NAME;
			String[] columns = { privateDatabaseContent.MAXSCORE_ID,privateDatabaseContent.MAXSCORE_SCORE };
			String[] selectionArgs = { ID };
	
			Cursor cursor = db.query(table, columns,
					privateDatabaseContent.MAXSCORE_ID + " = ?", selectionArgs, null,
					null, null);
	
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

		// Name of table we have in database, we can have multiple tables in one
		// database
		private static final String MAXSCORE_TABLE_NAME = "GameScoreTable";

		// Name of columns in each table, name starts with name of table , then
		// name of columns
		private static final String MAXSCORE_ID = "ID";
		private static final String MAXSCORE_SCORE = "MaxScore";

		// Query to create table , we can have multiple queries to create
		// multiple tables
		private static final String CREATE_QUERY_MAXSCORE = "CREATE TABLE "
				+ MAXSCORE_TABLE_NAME + " (" + MAXSCORE_ID
				+ " VARCHAR(255) PRIMARY KEY ," + MAXSCORE_SCORE
				+ " VARCHAR(255));";

		private static final String DROP_TABLE_MAXSCORE = "DROP TABLE IF EXISTS "
				+ MAXSCORE_TABLE_NAME;

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
				db.execSQL(CREATE_QUERY_MAXSCORE);
			} catch (SQLException e) {
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE_MAXSCORE);
			onCreate(db);
		}

	}// privateDatabaseContent

}
