package com.spokanevalley.bankStore;

public class NameHolder {	
	protected static final String LocationtableName = "LocationTable";
	protected static final String ScoretableName = "GameScoreTable";
	protected static final String PooltableName = "PoolLocationTable";
	
	protected static final String AppleID = "Apple Game";
	protected static final String DiscoveryID = "Discovery Game";
	protected static final String PlantesFerryID = "Plantes Ferry Game";
	protected static final String GreenacresID = "Greenacres Game";
	protected static final String SkiID = "Ski Game";
	
	protected static final String pool1ID = "pool 1";
	protected static final String pool2ID = "pool 2";
	protected static final String pool3ID = "pool 3";
	
	protected static final String pool1Address = "123 somewhere Spokane valley 12345";
	protected static final String pool2Address = "1234 somewhere Spokane valley 12345";
	protected static final String pool3Address = "1235 somewhere Spokane valley 12345";
		
	protected static final int NumPool = 3;
	
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
}
