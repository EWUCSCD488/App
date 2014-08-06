package com.spokanevalley.bankStore;

public class CouponCostFactory extends NameHolder{

	private static CouponCostFactory couponCostFactory= null;
	public static CouponCostFactory create(){
		if(couponCostFactory == null)
			couponCostFactory = new CouponCostFactory();
		return couponCostFactory;
	}
	
	private CouponCostFactory() {
		
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

}
