package com.spokanevalley.bankStore;

/**
 *  model for pool locations, max scores and coupons
 * 
 * @author Quyen Ha
 * Eastern Washington University
 *
 */

public class Model {
	private String title;
	private String description;
	private boolean isCouponUsed;
	private String imagePath;
	private int maxScore;
	
	public Model(String tile,String description,boolean isCouponUsed,String imagePath){
		this.title = tile;
		this.description = description;
		this.isCouponUsed = isCouponUsed;
		this.imagePath = imagePath;
	}
	
	public Model(String ID,int maxScore,String imagePath){
		this.title = ID;
		this.maxScore = maxScore;
		this.imagePath = imagePath;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getIsCouponUsed() {
		return isCouponUsed;
	}
	public void setCouponUsed(boolean isCouponUsed) {
		this.isCouponUsed = isCouponUsed;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
}
