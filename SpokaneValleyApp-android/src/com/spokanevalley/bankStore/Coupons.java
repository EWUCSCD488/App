package com.spokanevalley.bankStore;

/**
 * 
 * @author quyen_000
 * Eastern Washington University
 */

public class Coupons extends Model{

	public Coupons(String tile, String description, boolean isCouponUsed,
			String imagePath) {
		super(tile, description, isCouponUsed, imagePath);
	}
}
