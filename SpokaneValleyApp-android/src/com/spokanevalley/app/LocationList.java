package com.spokanevalley.app;

import java.util.ArrayList;

public class LocationList {
    public static ArrayList<Location> LIST = null;

    public static void Create(ArrayList<Location> set)
    {
        if(LIST == null)
        {
            new LocationList(set);
        }
    }

    private LocationList(ArrayList<Location> set) {
        LIST = set;
    }
}
