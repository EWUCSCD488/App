package com.spokanevalley.bankStore;

/**
 * generate coupon costs based on couponID or poolID, convert poolID to couponID and backward
 * generate trophy image paths for pool locations's trophies
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */

public class CouponCostFactory extends NameHolder{

	private static CouponCostFactory couponCostFactory= null;
	public static CouponCostFactory create(){						// implement singleton pattern
		if(couponCostFactory == null)
			couponCostFactory = new CouponCostFactory();
		return couponCostFactory;
	}
	
	/**
	 * default constructor
	 */
	
	private CouponCostFactory() {
		
	}
	
	/**
	 * get trophy image path based on id
	 * 
	 * @param id
	 * @return String image path or null
	 */
	
	public String getCouponImagePath(String id){
		
		if(id.equals(coupon1ID)){
			return coupon1TrophyImagePath;
		}else if(id.equals(coupon2ID)){
			return coupon2TrophyImagePath;
		}else if(id.equals(coupon3ID)){
			return coupon3TrophyImagePath;
		}
		return null;
	}
	
	/**
	 * get price of coupon based on pool ID
	 * 
	 * @param id
	 * @return price of coupon
	 */
	
	public int getPrice(String id){
		if(id.equals(pool1ID)){
			return Coupon1Cost;
		}else if(id.equals(pool2ID)){
			return Coupon2Cost;
		}else if(id.equals(pool3ID)){
			return Coupon3Cost;
		}
		return 1000000;				// ensure they will not get free coupon
	}
	
	/**
	 * get coupon ID based on pool ID
	 * 
	 * @param id
	 * @return coupon ID or "unknown"
	 */
	
	public String getTheRightCouponFromPool(String id){
		if(id.equals(pool1ID)){
			return coupon1ID;
		}else if(id.equals(pool2ID)){
			return coupon2ID;
		}else if(id.equals(pool3ID)){
			return coupon3ID;
		}
		return "unknown";
	}
	
	/**
	 * get pool ID based on coupon ID
	 * 
	 * @param id for coupon
	 * @return pool ID or "Unknown"
	 */
	
	public String getTheRightPoolFromCoupon(String id){
		if(id.equals(coupon1ID)){
			return pool1ID;
		}else if(id.equals(coupon2ID)){
			return pool2ID;
		}else if(id.equals(coupon3ID)){
			return pool3ID;
		}
		return "unknown";
	}

}
