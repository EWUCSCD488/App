package com.spokanevalley.bankStore;

/*
 * factory returns coupons and price of coupons 
 */

public class CouponCostFactory extends NameHolder{

	private static CouponCostFactory couponCostFactory= null;
	public static CouponCostFactory create(){
		if(couponCostFactory == null)
			couponCostFactory = new CouponCostFactory();
		return couponCostFactory;
	}
	
	private CouponCostFactory() {
		
	}
	
	public String getCouponImagePath(String id){
		
		if(id.equals(coupon1ID)){
			return coupon1ImagePath;
		}else if(id.equals(coupon2ID)){
			return coupon2ImagePath;
		}else if(id.equals(coupon3ID)){
			return coupon3ImagePath;
		}
		return null;
	}
	
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
