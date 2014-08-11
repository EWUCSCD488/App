package com.spokanevalley.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.spokanevalley.app.Location;
import com.spokanevalley.bankStore.NameHolder;
import com.spokanevalley.bankStore.ThumbNailFactory;
import com.spokanevalley.bankStore.gameModel;
import com.spokanevalley.bankStore.poolLocation;

/**
 * Create custom access to original database
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */

public class DatabaseCustomAccess extends NameHolder {

	public static final String TAG = DatabaseCustomAccess.class.getName();

	private static DatabaseCustomAccess databaseInterface; // implement
															// singleton class

	private static ArrayList<Location> LIST = null; // list of Location
	private static ArrayList<gameModel> SCORE_LIST = null; // list of max scores
	private static ArrayList<poolLocation> POOL_LIST = null; // list of pool
																// locations and
																// coupons
	private int totalScore = 0; // back up totalScore

	private SpokaneValleyDatabaseHelper helper; // original database class

	public static DatabaseCustomAccess Create(Context context) { // implement
																	// single
																	// creation
																	// in entire
																	// Android
																	// app life
		if (databaseInterface == null) {
			databaseInterface = new DatabaseCustomAccess(context);
		}
		return databaseInterface;
	}

	/**
	 * Private constructor save pool to database if database is empty save total
	 * score to database if database is empty save max score to database if
	 * database is empty
	 * 
	 * @param context
	 */

	private DatabaseCustomAccess(Context context) {
		helper = new SpokaneValleyDatabaseHelper(context);

		saveInitialPoolLocation(); // save initial pools into database
		saveInitialTotalScore(); // create total score in database
		LoadingDatabaseTotalScore();
		LoadingDatabaseScores();
	}

	/**
	 * register total score to database as 0 if total score table is empty
	 */

	private void saveInitialTotalScore() {
		helper.insertTotalScoreData(TotalScoretableName, totalScoreID, "0");
	}

	/**
	 * Return original database access
	 * 
	 */
	
	public SpokaneValleyDatabaseHelper getDatabase() {
		return helper;
	}

	/**
	 * filter and return all pool locations in pool locations table
	 * 
	 * note : pool locations table contains coupons and pool locations
	 * 
	 * @return array List of pool locations 
	 */
	
	public ArrayList<poolLocation> getPoolList() {

		LoadingDatabasePoolLocation();

		ArrayList<poolLocation> tempLocationList = new ArrayList<poolLocation>();
		// remove coupons
		for (poolLocation location : POOL_LIST) {
			if ((location.getTitle().equals(pool1ID)
					|| location.getTitle().equals(pool2ID) || location
					.getTitle().equals(pool3ID))
					&& location.getIsCouponUsed() == false)					// only return any pool without buying a coupon
				tempLocationList.add(location);
		}

		return tempLocationList;

		// return POOL_LIST;
	}

	/**
	 * filter and return coupon list 
	 * 
	 * note :pool locations table contains coupons and pool locations
	 * 
	 * @return arrayList of coupon list
	 */
	
	public ArrayList<poolLocation> getCouponList() {

		LoadingDatabasePoolLocation();

		ArrayList<poolLocation> temp_CouponList = new ArrayList<poolLocation>();
		// remove coupons
		for (poolLocation location : POOL_LIST) {
			if ((location.getTitle().equals(coupon1ID)
					|| location.getTitle().equals(coupon2ID) || location
					.getTitle().equals(coupon3ID))
					&& location.getIsCouponUsed() == true)						// only show coupons which are bought from the mall
				temp_CouponList.add(location);
		}

		return temp_CouponList;
		// return POOL_LIST;
	}

	/**
	 * return arrayList of max scores for games
	 * @return
	 */
	
	public ArrayList<gameModel> getScoreList() {
		LoadingDatabaseScores();
		return SCORE_LIST;
	}
	
	/**
	 * 
	 * load total score from database,store to backup value and return it
	 * 
	 * @return total score for redeeming coupons
	 */
	
	public int getTotalScore() {
		LoadingDatabaseTotalScore();
		return totalScore;
	}

	/**
	 * load inital pool locations and coupons if database pool location table is empty
	 */
	
	private void saveInitialPoolLocation() {

		POOL_LIST = new ArrayList<poolLocation>();

		for (int i = 0; i < NumPool; i++) {
			addNewPool(getPoolLocation(i));
		}

		for (int i = 0; i < NumCoupon; i++) {
			addNewPool(getCouponLocation(i));
		}
	}

	/**
	 * 
	 * create poolLocation based on ID,generate thumbnail for each pool locations using ThumbNailFactory 
	 * 
	 * @param ID of pool location
	 * @return poolLocation
	 */
	
	private poolLocation getPoolLocation(int ID) {
		poolLocation pool = null;
		if (ID == 0) {
			pool = new poolLocation(pool1ID, pool1Description, false,
					ThumbNailFactory.create().getThumbNail(pool1ID));
		} else if (ID == 1) {
			pool = new poolLocation(pool2ID, pool2Description, false,
					ThumbNailFactory.create().getThumbNail(pool2ID));
		} else if (ID == 2) {
			pool = new poolLocation(pool3ID, pool3Descrption, false,
					ThumbNailFactory.create().getThumbNail(pool3ID));
		}
		return pool;
	}

	/**
	 * create coupons based on ID,generate thumbnail for each coupons using ThumbNailFactory
	 * for convenience, coupons use poolLocation class aslo  
	 * 
	 * @param ID of pool location
	 * @return poolLocation
	 */
	
	private poolLocation getCouponLocation(int ID) {
		poolLocation coupon = null;
		if (ID == 0) {
			coupon = new poolLocation(coupon1ID, Coupon1Description, false,
					ThumbNailFactory.create().getThumbNail(coupon1ID));
		} else if (ID == 1) {
			coupon = new poolLocation(coupon2ID, Coupon2Description, false,
					ThumbNailFactory.create().getThumbNail(coupon2ID));
		} else if (ID == 2) {
			coupon = new poolLocation(coupon3ID, Coupon3Description, false,
					ThumbNailFactory.create().getThumbNail(coupon3ID));
		}
		return coupon;
	}

	/**
	 * Custom access to pool location database table : add new pool locations or coupons to pool location table
	 * @param pool location
	 */

	public void addNewPool(poolLocation pool) {

		// add to database here
		Cursor checking_avalability = helper.getPoolLocationData(PooltableName,
				pool.getTitle());
		if (checking_avalability == null) {				

			POOL_LIST.add(pool);

			long RowIds = helper.insertPoolLocation(PooltableName,
					pool.getTitle(), pool.getDescription(),
					convertToString(pool.getIsCouponUsed()));
			//if (RowIds == -1)
				//Log.d(TAG, "Error on inserting columns");
		} else if (checking_avalability.getCount() == 0) { 				// checking twice to make sure it will not miss 

			POOL_LIST.add(pool);

			long RowIds = helper.insertPoolLocation(PooltableName,
					pool.getTitle(), pool.getDescription(),
					convertToString(pool.getIsCouponUsed()));
			//if (RowIds == -1)
				//Log.d(TAG, "Error on inserting columns");
		}

	}

	/**
	 * convert boolean to string 0 or 1
	 * 0 	:	not used
	 * 1	: 	used
	 * 
	 * @param isUsed
	 * @return
	 */
	
	private String convertToString(boolean isUsed) {
		if (isUsed)
			return "1";
		return "0";
	}

	/**
	 * load total score from database
	 *  
	 */
	
	private void LoadingDatabaseTotalScore() {

		Cursor cursor = helper.getDataAll(TotalScoretableName);

		while (cursor.moveToNext()) {
			// loading each element from database
			String ID = cursor.getString(ID_TOTAL_SCORE_COLUMN);
			String totalPoint = cursor.getString(POINT_TOTAL_SCORE_COLUMN);
			
			totalScore = Integer.parseInt(totalPoint);					// store total score from database to backup value
			
		} // travel to database result

	}

	/**
	 * load max score table to SCORE_LIST array list.
	 * create new arrayList every time it is called to make sure list is up-to-date
	 * limit call due to memory cost
	 */
	
	private void LoadingDatabaseScores() {

		Cursor cursor = helper.getDataAll(ScoretableName);

		// id_counter = cursor.getCount();
		SCORE_LIST = new ArrayList<gameModel>();
		while (cursor.moveToNext()) {
			// loading each element from database
			String ID = cursor.getString(ID_MAXSCORE_COLUMN);
			String Score = cursor.getString(SCORE_MAXSCORE_COLUMN);

			SCORE_LIST.add(new gameModel(ID, Integer.parseInt(Score),
					ThumbNailFactory.create().getThumbNail(ID)));

		} // travel to database result

	}

	/**
	 * load pool locations and coupons to LOOK_LIST array list.
	 * create new arrayList every time it is called to make sure list is up-to-date
	 * limit call due to memory cost
	 */
	
	private void LoadingDatabasePoolLocation() {

		Cursor cursor = helper.getDataAll(PooltableName);

		// id_counter = cursor.getCount();

		POOL_LIST.clear();

		while (cursor.moveToNext()) {
			// loading each element from database
			String ID = cursor.getString(ID_POOL_LCOATION_COLUMN);
			String Description = cursor
					.getString(DESCRIPTION_POOL_LCOATION_COLUMN);
			String isCouponUsed = cursor.getString(COUPON_IS_USED_COLUMN);

			POOL_LIST.add(new poolLocation(ID, Description,
					isUsed(isCouponUsed), ThumbNailFactory.create()
							.getThumbNail(ID)));

		} // travel to database result

	}

	/**
	 * convert boolean isUsed in database from String back to boolean
	 * @param couponType
	 * @return boolean true(1) , false(0)
	 */
	
	private boolean isUsed(String couponType) { // 0 = not used , 1 = used
		if (1 == Integer.parseInt(couponType)) // used
			return true;
		return false;
	}

	/**
	 * register max score for apple game in database
	 * @param score
	 */
	
	public void saveInitialScoretoDatabase_AppleGame(int score) {
		saveInitialScoretoDatabase(score, AppleID);
	}

	/**
	 * register max score for discovery game in database
	 * @param score
	 */
	
	public void saveInitialScoretoDatabase_DiscoveryGame(int score) {
		saveInitialScoretoDatabase(score, DiscoveryID);
	}

	/**
	 * register max score for plantes ferry game in database
	 * @param score
	 */
	
	public void saveInitialScoretoDatabase_PlantesFerryGame(int score) {
		saveInitialScoretoDatabase(score, PlantesFerryID);
	}
	
	/**
	 * register max score for greenacres game in database
	 * @param score
	 */

	public void saveInitialScoretoDatabase_GreenacresGame(int score) {
		saveInitialScoretoDatabase(score, GreenacresID);
	}
	
	/**
	 * register max score for ski game in database
	 * @param score
	 */

	public void saveInitialScoretoDatabase_SkiGame(int score) {
		saveInitialScoretoDatabase(score, SkiID);
	}
	
	/**
	 * update max score for apple game in database
	 * @param score
	 */

	public int saveMaxScore_AppleGame(int score) {
		return saveMaxScore(score, AppleID);
	}

	/**
	 * update max score for discovery game in database
	 * @param score
	 */
	
	public int saveMaxScore_DiscoveryGame(int score) {
		return saveMaxScore(score, DiscoveryID);
	}
	
	/**
	 * update max score for plantes ferry game in database
	 * @param score
	 */

	public int saveMaxScore_PlantesFerryGame(int score) {
		return saveMaxScore(score, PlantesFerryID);
	}
	
	/**
	 * update max score for greenacres game in database
	 * @param score
	 */

	public int saveMaxScore_GreenacresGame(int score) {
		return saveMaxScore(score, GreenacresID);
	}

	/**
	 * update max score for ski game in database
	 * @param score
	 */
	
	public int saveMaxScore_SkiGame(int score) {
		return saveMaxScore(score, SkiID);
	}

	/**
	 * Custom access to database : register score of games into database
	 * 
	 * @param score
	 * @param id of game
	 */
	
	private void saveInitialScoretoDatabase(int score, String id) {
		// save max score to database
		Cursor checking_avalability = getDatabase().getScoreData(
				ScoretableName, id);
		if (checking_avalability == null) {
			long RowIds = getDatabase().insertScoreData(ScoretableName, id,
					String.valueOf(score));
			//if (RowIds == -1)
				//Log.d(TAG, "Error on inserting columns");
		} else if (checking_avalability.getCount() == 0) {
			long RowIds = getDatabase().insertScoreData(ScoretableName, id,
					String.valueOf(score));
			//if (RowIds == -1)
				//Log.d(TAG, "Error on inserting columns");
		}
	}

	/**
	 * compare max score in database and current score , save max score between those two scores
	 * @param CurrentScore
	 * @param id of game
	 * @return max score between max score in database and current score
	 */
	
	private int saveMaxScore(int CurrentScore, String id) {
		Cursor cursor = getDatabase().getScoreData(ScoretableName, id);

		int MaxScore = 0;
		while (cursor.moveToNext()) {
			// loading each element from database
			String ID = cursor.getString(ID_MAXSCORE_COLUMN);
			int MaxScore_temp = Integer.parseInt(cursor
					.getString(SCORE_MAXSCORE_COLUMN));
			if (MaxScore_temp > MaxScore)
				MaxScore = MaxScore_temp;
			//Log.d(TAG, "MaxScore is" + MaxScore_temp + " with ID : " + ID);
		} // travel to database result

		addUpTotalScore(CurrentScore);

		if (MaxScore < CurrentScore) {
			long RowIds = getDatabase().updateScoreTable(ScoretableName, id,
					String.valueOf(CurrentScore));
			//if (RowIds == -1)
				//Log.d(TAG, "Error on inserting columns");

			return CurrentScore;
		}

		return MaxScore;

	}

	/**
	 * add current score of each play in game to total score for redeeming coupons
	 * 
	 * @param CurrentScore
	 */
	
	public void addUpTotalScore(int CurrentScore) {
		Cursor cursor = getDatabase().getTotalScoreData(TotalScoretableName,
				totalScoreID);

		int currentTotalScore = 0;
		while (cursor.moveToNext()) {
			// loading each element from database
			String ID = cursor.getString(ID_TOTAL_SCORE_COLUMN);
			int MaxScore_temp = Integer.parseInt(cursor
					.getString(POINT_TOTAL_SCORE_COLUMN));
			if (MaxScore_temp > currentTotalScore)
				currentTotalScore = MaxScore_temp;
			//Log.d(TAG, "total score is" + MaxScore_temp + " with ID : "
					//+ totalScoreID);
		} // travel to database result

		// if(MaxScore < CurrentScore){
		long RowIds = getDatabase().updateTotalScoreTable(TotalScoretableName,
				totalScoreID, String.valueOf(CurrentScore + currentTotalScore));
		//if (RowIds == -1)
			//Log.d(TAG, "Error on inserting columns");
	}

	/**
	 * update pool location after users buy coupon from this pool location
	 * 
	 * @param ID
	 * @param isgteCoupon
	 */

	public void updatePoolwithBoughtCoupon(String ID, boolean isgteCoupon) {
		helper.updatePoolLocationTable(PooltableName, ID,
				convertToString(isgteCoupon));
	}

	/**
	 * update coupon after users users use the coupon
	 * 
	 * @param ID
	 * @param isgetCoupon
	 */
	
	public void updateCouponwithBoughtCoupon(String ID, boolean isgetCoupon) {
		helper.updatePoolLocationTable(PooltableName, ID,
				convertToString(isgetCoupon));
	}

}