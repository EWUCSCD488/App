package com.spokanevalley.bankStore;


/**
 * important data and information for database, pool locations, coupons, games and max scores for games
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */
public class NameHolder {	
	protected static final String TotalScoretableName = "totalScoreTable";				// table names in database
	protected static final String ScoretableName = "GameScoreTable";
	protected static final String PooltableName = "PoolLocationTable";
	
	protected static final String AppleID = "Apple Game";								// game IDs
	protected static final String DiscoveryID = "Discovery Game";
	protected static final String PlantesFerryID = "Plantes Ferry Game";
	protected static final String GreenacresID = "Greenacres Game";
	protected static final String SkiID = "Ski Game";
	protected static final String totalScoreID = "totalAwesomeScore";
	
	protected static final String pool1ID = "Park Road Pool";								// pool IDs
	protected static final String pool2ID = "Terrace View";
	protected static final String pool3ID = "Valley Mission Pool";
	
	protected static final String pool1ThumbnailImagePath = "parkroadpoolthumb";								// pool thumbnails image path
	protected static final String pool2ThumbnailImagePath = "terraceviewthumb";
	protected static final String pool3ThumbnailImagePath = "valleymissionthumb";
	
	protected static final String pool1ActualImagePath = "parkroadpool_actual";								// pool actual image path
	protected static final String pool2ActualImagePath = "terracepool_actual";
	protected static final String pool3ActualImagePath = "valleymissionpool_actual";
	
	protected static final String coupon1ID = "Park Road Coupon";						// coupon IDs
	protected static final String coupon2ID = "Terrace View Coupon";
	protected static final String coupon3ID = "Valley Mission Coupon ";
	
	protected static final String coupon1TrophyImagePath = "parkroadtrophy";								// coupon image paths
	protected static final String coupon2TrophyImagePath = "terraceviewtrophy";
	protected static final String coupon3TrophyImagePath = "valleymissiontrophy";
	
	protected static final String coupon1ImagePath = "parkroadtrophy_thumb";								// coupon thumbnail image paths
	protected static final String coupon2ImagePath = "terraceviewtrophy_thumb";
	protected static final String coupon3ImagePath = "valleymissiontrophy_thumb";
	
	protected static final String Coupon1Description = "Redeem this at\n Park Road Pool\nin Spokane Valley";					// coupon descriptions
	protected static final String Coupon2Description = "Redeem this at\n Terrace View Pool\nin Spokane Valley";
	protected static final String Coupon3Description = "Redeem this at\n Valley Mission Pool\nin Spokane Valley";
		
	protected static final int Coupon1Cost = 2;										// coupon prices
	protected static final int Coupon2Cost = 1;
	protected static final int Coupon3Cost = 1;
	
	protected static final String pool1Description = "Coupon Cost: " + Coupon1Cost;			// pool descriptions
	protected static final String pool2Description = "Coupon Cost: " + Coupon2Cost;
	protected static final String pool3Descrption = "Coupon Cost: " + Coupon3Cost;
	
	protected static final int NumPool = 3;												// number of pool locations
	
	protected static final int NumCoupon = 3;											//number of coupons
	
	protected static final int ID_MAXSCORE_COLUMN = 0;							// column numbers in max score table in database
	protected static final int SCORE_MAXSCORE_COLUMN = 1;
	
	protected static final int ID_POOL_LCOATION_COLUMN = 0;						// column number in pool location table in database
	protected static final int DESCRIPTION_POOL_LCOATION_COLUMN = 1;
	protected static final int COUPON_IS_USED_COLUMN = 2;
		
	protected static final int ID_TOTAL_SCORE_COLUMN = 0;						// column number in total score table in database
	protected static final int POINT_TOTAL_SCORE_COLUMN = 1;
}
