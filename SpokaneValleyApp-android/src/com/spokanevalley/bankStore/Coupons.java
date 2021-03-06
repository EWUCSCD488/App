package com.spokanevalley.bankStore;

/**
 * 
 * @author Quyen Ha
 * Eastern Washington University
 */

public class Coupons extends Model{

	/**
	 * coupon constructor
	 * 
	 * @param tile
	 * @param description
	 * @param isCouponUsed
	 * @param imagePath
	 */
	
	public Coupons(String tile, String description, boolean isCouponUsed,
			String imagePath) {
		super(tile, description, isCouponUsed, imagePath);
	}
}
