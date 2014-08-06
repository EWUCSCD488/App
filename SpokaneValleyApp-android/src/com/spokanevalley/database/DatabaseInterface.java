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
import com.spokanevalley.bankStore.poolLocationFactory;

public class DatabaseInterface extends NameHolder{

	public static final String TAG = DatabaseInterface.class.getName();
	private static DatabaseInterface databaseInterface;

	private static ArrayList<Location> LIST = null;
	private static ArrayList<gameModel> SCORE_LIST = null;
	private static ArrayList<poolLocation> POOL_LIST = null;
	private int totalScore = 0;
	
	private SpokaneValleyDatabaseHelper helper;
	
	public static DatabaseInterface Create(Context context) {
		if (databaseInterface == null) {
			databaseInterface = new DatabaseInterface(
					context);
		}
		return databaseInterface;
	}

	private DatabaseInterface(Context context) {
		helper = new SpokaneValleyDatabaseHelper(context);

		saveInitialPoolLocation();				// save initial pools into database
		saveInitialTotalScore();				// create total score in database
		LoadingDatabaseTotalScore();
		LoadingDatabaseScores();
	}

	private void saveInitialTotalScore() {
		helper.insertLocationData(TotalScoretableName, totalScoreID, "0");
	}

	private void LoadingAllGamesInitial() {
		helper.insertScoreData(ScoretableName, AppleID, "0");
		// add here for more games
	}

	public SpokaneValleyDatabaseHelper getDatabase() {
		return helper;
	}

	public ArrayList<poolLocation> getPoolList() {
		

		LoadingDatabasePoolLocation();
		
		ArrayList<poolLocation> tempLocationList = new ArrayList<poolLocation>();
		// remove coupons
		for(poolLocation location : POOL_LIST){
			if((	location.getTitle().equals(pool1ID) 	|| 
					location.getTitle().equals(pool2ID) 	||
					location.getTitle().equals(pool3ID)) 	&& 
					location.getIsCouponUsed() == false		)
				tempLocationList.add(location);
		}
		
		return tempLocationList;
		
		//return POOL_LIST;
	}
	
	public ArrayList<poolLocation> getCouponList() {
		

		LoadingDatabasePoolLocation();
		
		
		
		ArrayList<poolLocation> temp_CouponList = new ArrayList<poolLocation>();
		// remove coupons
		for(poolLocation location : POOL_LIST){
			if((	location.getTitle().equals(coupon1ID) 	|| 
					location.getTitle().equals(coupon2ID) 	||
					location.getTitle().equals(coupon3ID)) 	&& 
					location.getIsCouponUsed() == true		)
					temp_CouponList.add(location);
		}
		
		return temp_CouponList;
		//return POOL_LIST;
	}
	
	public int getTotalScore(){
		LoadingDatabaseTotalScore();
		return totalScore;
	}
	
	private void saveInitialPoolLocation(){
		
		POOL_LIST = new ArrayList<poolLocation>();
		
		for(int i = 0 ; i < NumPool ; i++){
			addNewPool(getPoolLocation(i));
		}
		
		for(int i = 0 ; i < NumCoupon ; i++){
			addNewPool(getCouponLocation(i));
		}
	}
	
	private poolLocation getPoolLocation(int ID){
		poolLocation pool = null;
		if(ID == 0){
			pool = new poolLocation(pool1ID, pool1Address, false, ThumbNailFactory.create().getThumbNail(pool1ID)); 
		}else if(ID == 1){
			pool = new poolLocation(pool2ID, pool2Address, false, ThumbNailFactory.create().getThumbNail(pool2ID)); 
		}else if(ID == 2){
			pool = new poolLocation(pool3ID, pool3Address, false, ThumbNailFactory.create().getThumbNail(pool3ID)); 
		}
		return pool;
	}
	
	private poolLocation getCouponLocation(int ID){
		poolLocation coupon = null;
		if(ID == 0){
			coupon = new poolLocation(coupon1ID, Coupon1Description, false, ThumbNailFactory.create().getThumbNail(coupon1ID)); 
		}else if(ID == 1){
			coupon = new poolLocation(coupon2ID, Coupon2Description, false, ThumbNailFactory.create().getThumbNail(coupon2ID)); 
		}else if(ID == 2){
			coupon = new poolLocation(coupon3ID, Coupon3Description, false, ThumbNailFactory.create().getThumbNail(coupon3ID)); 
		}
		return coupon;
	}
	
	public ArrayList<gameModel> getScoreList() {
		LoadingDatabaseScores();
		return SCORE_LIST;
	}

	public void addNewPool(poolLocation pool) {
		
		// add to database here
		Cursor checking_avalability = helper.getPoolLocationData(PooltableName,
				pool.getTitle());
		if (checking_avalability == null) {
			
			POOL_LIST.add(pool);
			
			long RowIds = helper.insertPoolLocation(PooltableName,
					pool.getTitle(), pool.getDescription(),
					convertToString(pool.getIsCouponUsed()) );
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");
		} else if (checking_avalability.getCount() == 0) {
			
			POOL_LIST.add(pool);
			
			long RowIds = helper.insertPoolLocation(PooltableName,
					pool.getTitle(), pool.getDescription(),
					convertToString(pool.getIsCouponUsed()) );
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");
		}

	}

	private String convertToString(boolean isUsed){
		if(isUsed)
			return "1";
		return "0";
	}
	
	public Location getLocation(String title) {
		return null;
	}

	private void LoadingDatabaseTotalScore() {

		Cursor cursor = helper.getDataAll(TotalScoretableName);

		while (cursor.moveToNext()) {
			// loading each element from database
			String ID  = cursor.getString(ID_TOTAL_SCORE_COLUMN);
			String totalPoint = cursor.getString(POINT_TOTAL_SCORE_COLUMN);
			totalScore = Integer.parseInt(totalPoint);
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
			
			SCORE_LIST.add(new gameModel(ID, Integer.parseInt(Score),ThumbNailFactory.create().getThumbNail(ID)));

		} // travel to database result

	}
	
	private void LoadingDatabasePoolLocation() {

		Cursor cursor = helper.getDataAll(PooltableName);
		
		//id_counter = cursor.getCount();
		
		POOL_LIST.clear();
		
		while (cursor.moveToNext()) {
			// loading each element from database
			String ID  = cursor.getString(ID_POOL_LCOATION_COLUMN);
			String Description = cursor.getString(DESCRIPTION_POOL_LCOATION_COLUMN);
			String isCouponUsed = cursor.getString(COUPON_IS_USED_COLUMN);
			
			POOL_LIST.add(new poolLocation(ID, Description, isUsed(isCouponUsed) ,ThumbNailFactory.create().getThumbNail(ID)));
			Log.d(TAG, " pool : " + ID);

		} // travel to database result

	}
	
	private boolean isUsed(String couponType){		// 0 = not used , 1 = used
		if(1 == Integer.parseInt(couponType))			// used
			return true;
		return false;
	}
	
	public void saveInitialScoretoDatabase_AppleGame(int score){
		saveInitialScoretoDatabase(score,AppleID);
	}
	
	public void saveInitialScoretoDatabase_DiscoveryGame(int score){
		saveInitialScoretoDatabase(score,DiscoveryID);
	}
	
	public void saveInitialScoretoDatabase_PlantesFerryGame(int score){
		saveInitialScoretoDatabase(score,PlantesFerryID);
	}
	
	public void saveInitialScoretoDatabase_GreenacresGame(int score){
		saveInitialScoretoDatabase(score,GreenacresID);
	}
	
	public void saveInitialScoretoDatabase_SkiGame(int score){
		saveInitialScoretoDatabase(score,SkiID);
	}
	
	
	
	public int saveMaxScore_AppleGame(int score){
		return saveMaxScore(score,AppleID);
	}
	
	public int saveMaxScore_DiscoveryGame(int score){
		return saveMaxScore(score,DiscoveryID);
	}
	
	public int saveMaxScore_PlantesFerryGame(int score){
		return saveMaxScore(score,PlantesFerryID);
	}
	
	public int saveMaxScore_GreenacresGame(int score){
		return saveMaxScore(score,GreenacresID);
	}
	
	public int saveMaxScore_SkiGame(int score){
		return saveMaxScore(score,SkiID);
	}
	
	private void saveInitialScoretoDatabase(int score,String id){
			// save max score to database
				Cursor checking_avalability = getDatabase().getScoreData(ScoretableName, id);
				if( checking_avalability == null  ){
						long RowIds = getDatabase().insertScoreData(ScoretableName, id, String.valueOf(score));
						if (RowIds == -1)
							Log.d(TAG, "Error on inserting columns");
				}
				else if (checking_avalability.getCount() == 0){
					long RowIds = getDatabase().insertScoreData(ScoretableName, id, String.valueOf(score));
					if (RowIds == -1)
						Log.d(TAG, "Error on inserting columns");
				}
	}

	private int saveMaxScore(int CurrentScore,String id) {
		Cursor cursor= getDatabase().getScoreData(ScoretableName,id);
		
		int MaxScore = 0;
		while(cursor.moveToNext()){
			//loading each element from database
			String ID = cursor.getString(ID_MAXSCORE_COLUMN);
			int MaxScore_temp =  Integer.parseInt(cursor.getString(SCORE_MAXSCORE_COLUMN));
				if(MaxScore_temp > MaxScore)
					MaxScore = MaxScore_temp ;
				Log.d(TAG,"MaxScore is"+ MaxScore_temp+ " with ID : " + ID);
		} // travel to database result
		
		addUpTotalScore(CurrentScore);
		
		if(MaxScore < CurrentScore){
			long RowIds = getDatabase().updateScoreTable(ScoretableName, id, String.valueOf(CurrentScore));
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");

			return CurrentScore;
		}

		return MaxScore;
		
	}
	
	public void addUpTotalScore(int CurrentScore) {
		Cursor cursor= getDatabase().getTotalScoreData(TotalScoretableName,totalScoreID);
		
		int currentTotalScore = 0;
		while(cursor.moveToNext()){
			//loading each element from database
			String ID = cursor.getString(ID_TOTAL_SCORE_COLUMN);
			int MaxScore_temp =  Integer.parseInt(cursor.getString(POINT_TOTAL_SCORE_COLUMN));
				if(MaxScore_temp > currentTotalScore)
					currentTotalScore = MaxScore_temp ;
				Log.d(TAG,"total score is"+ MaxScore_temp+ " with ID : " + totalScoreID);
		} // travel to database result
		
		//if(MaxScore < CurrentScore){
			long RowIds = getDatabase().updateTotalScoreTable(TotalScoretableName, totalScoreID, String.valueOf(CurrentScore + currentTotalScore));
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");
	}		
	
	
	public int AddDatabaseScores() {

		Cursor cursor = helper.getDataAll(ScoretableName);
		int totalScore =0;
		while (cursor.moveToNext()) {
			// loading each element from database
			String ID  = cursor.getString(ID_LOCATION_COLUMN);
			String Score = cursor.getString(LATITUDE_LOCATION_COLUMN);
			int score_temp = Integer.parseInt(Score);
			totalScore = totalScore + score_temp;
			

		} // travel to database result
		return totalScore;
	}
	
	public void updatePoolwithBoughtCoupon(String ID,boolean isgteCoupon){
		helper.updatePoolLocationTable(PooltableName, ID, convertToString(isgteCoupon));
	}
	
	public void updateCouponwithBoughtCoupon(String ID,boolean isgetCoupon){
		helper.updatePoolLocationTable(PooltableName, ID, convertToString(isgetCoupon));
	}
	
	
}
