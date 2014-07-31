package com.spokanevalley.bankStore;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.spokanevalley.database.DatabaseInterface;

public class poolLocationFactory extends NameHolder{
	
	private Context context = null;
	private static poolLocationFactory pools = null;
	private static List<poolLocation> poolLocations = null;
	private poolLocationFactory(){
		//saveInitialPoolLocation();
	}
	
	public static poolLocationFactory create(){
		if(pools == null)
			pools = new poolLocationFactory();
		return pools;
	}
	
}
