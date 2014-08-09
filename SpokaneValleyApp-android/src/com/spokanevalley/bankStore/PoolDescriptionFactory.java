package com.spokanevalley.bankStore;

public class PoolDescriptionFactory extends NameHolder{

	private static PoolDescriptionFactory poolDescriptionFactory = null;
	
	// WRITE DESCRIPTION ABOUT POOL HERE
	private String descriptionPool1 = "hello 1";
	private String descriptionPool2 = "hello 2";
	private String descriptionPool3 = "hello 3";
	
	public static PoolDescriptionFactory create(){
		if(poolDescriptionFactory == null)
			poolDescriptionFactory = new PoolDescriptionFactory();
		return poolDescriptionFactory;
	}
	
	private PoolDescriptionFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription(String id){
		if(id.equals(pool1ID)){
			return descriptionPool1; 
		}else if(id.equals(pool2ID)){
			return descriptionPool2;
		}else if(id.equals(pool3ID)){
			return descriptionPool3;
		}
		
		return null;
	}

}
