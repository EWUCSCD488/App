package com.spokanevalley.bankStore;

public class ThumbNailFactory extends NameHolder{

	private static ThumbNailFactory thumbNailFactory= null;
	
	public static ThumbNailFactory create(){
		if(thumbNailFactory == null){
			thumbNailFactory = new ThumbNailFactory();
		}
		return thumbNailFactory;
	}
	
	private ThumbNailFactory() {
		
	}
	
	public String getThumbNail(String ID){
		if(ID.equals(AppleID)){
			return "applethumbnail";
		}else if(ID.equals(DiscoveryID)){
			return "discoverythumbnail";
		}else if(ID.equals(PlantesFerryID)){
			return "ferrythumbnail";
		}else if(ID.equals(GreenacresID)){
			return "greenacresthumbnail";
		}else if(ID.equals(SkiID)){
			return "skithumbnail";
		}
		
		return null;
	}

}
