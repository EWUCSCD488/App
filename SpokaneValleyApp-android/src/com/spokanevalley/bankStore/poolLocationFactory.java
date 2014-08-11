package com.spokanevalley.bankStore;

import java.util.List;

import android.content.Context;

/**
 * 
 * @author Quyen Ha
 * Eastern Washington University
 * Responsible for initializing new Array List of pool locations
 * and maintaining the values for each pool location
 */
public class poolLocationFactory extends NameHolder{
	
	private Context context = null;
	private static poolLocationFactory pools = null;
	private static List<poolLocation> poolLocations = null;
	
	private poolLocationFactory(){
		//saveInitialPoolLocation();
	} // End DVC
	/**
	 * Creates a new Pool Location Factory
	 * @return
	 */
	public static poolLocationFactory create(){
		if(pools == null)
			pools = new poolLocationFactory();
		return pools;
	} // End poolLocationFactory
	
} // End poolLocationFactory class
