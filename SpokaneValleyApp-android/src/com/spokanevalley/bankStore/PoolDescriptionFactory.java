package com.spokanevalley.bankStore;

public class PoolDescriptionFactory extends NameHolder{

	private static PoolDescriptionFactory poolDescriptionFactory = null;
	
	// WRITE DESCRIPTION ABOUT POOL HERE
	private String descriptionPool1 = "Park Road Pool\nWith a Slide Feature\n\nAddress: 906 N. Park Road\nPhone: 509-926-1840"; // Park Road Pool
	private String descriptionPool2 = "Terrace View Pool\nWith a Lazy River Feature\n\nAddress: 13525 E. 24th Ave.\nPhone: 509-924-4707"; // Terrace View Pool
	private String descriptionPool3 = "Valley Mission Pool\nWith a Zero Depth Entry Pool Feature\n\nAddress: 11123 E. Mission Ave.\nPhone: 509-922-7091"; // Valley Mission Pool
	
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
