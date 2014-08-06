package com.spokanevalley.bankStore;

public class NameHolder {	
	protected static final String TotalScoretableName = "totalScoreTable";
	protected static final String ScoretableName = "GameScoreTable";
	protected static final String PooltableName = "PoolLocationTable";
	
	protected static final String AppleID = "Apple Game";
	protected static final String DiscoveryID = "Discovery Game";
	protected static final String PlantesFerryID = "Plantes Ferry Game";
	protected static final String GreenacresID = "Greenacres Game";
	protected static final String SkiID = "Ski Game";
	protected static final String totalScoreID = "totalAwesomeScore";
	
	protected static final String pool1ID = "Park Road Pool";
	protected static final String pool2ID = "Terrace View";
	protected static final String pool3ID = "Valley Mission Pool";
	
	protected static final String coupon1ID = "Park Road Pool Coupon";
	protected static final String coupon2ID = "Terrace View Coupon";
	protected static final String coupon3ID = "Valley Mission Pool Coupon ";
	
	
	
	protected static final String Coupon1Description = "awesome coupon 1 ";
	protected static final String Coupon2Description = "awesome coupon 2";
	protected static final String Coupon3Description = "awesome coupon 3";
		
	protected static final int Coupon1Cost = 100;
	protected static final int Coupon2Cost = 200;
	protected static final int Coupon3Cost = 300;
	
	protected static final String pool1Address = "Buy coupon cost : "+  Coupon1Cost;
	protected static final String pool2Address = "Buy coupon cost : "+ Coupon2Cost;
	protected static final String pool3Address = "Buy coupon cost : " + Coupon3Cost;
	
	protected static final int NumPool = 3;
	
	protected static final int NumCoupon = 3;
	
	protected static final int ID_LOCATION_COLUMN = 0 ;
	protected static final int LATITUDE_LOCATION_COLUMN = 1 ;
	protected static final int LONGITUDE_LOCATION_COLUMN = 2 ;
	protected static final int TITLE_LOCATION_COLUMN = 4 ;
	protected static final int INFO_LOCATION_COLUMN = 5 ;
	
	protected static final int ID_MAXSCORE_COLUMN = 0;
	protected static final int SCORE_MAXSCORE_COLUMN = 1;
	
	protected static final int ID_POOL_LCOATION_COLUMN = 0;
	protected static final int DESCRIPTION_POOL_LCOATION_COLUMN = 1;
	protected static final int COUPON_IS_USED_COLUMN = 2;
	
	protected static final int ID_TOTAL_SCORE_COLUMN = 0;
	protected static final int POINT_TOTAL_SCORE_COLUMN = 1;
}
