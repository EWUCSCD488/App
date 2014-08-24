package com.spokanevalley.app;

import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import android.content.Context;
import android.content.res.XmlResourceParser;

/**
 * Reads the xml file and parses out the location information and bundles it together in a location object.
 */
public class LocationInflator {
    public static ArrayList<Location> inflate(Context context, int xmlFileResId)
        throws Exception
    {
        ArrayList<Location> locations = new ArrayList<Location>();
        XmlResourceParser parser = context.getResources().getXml(R.xml.locations);
        Location c_Location = null;
        int token;

        //Pull values from xml and populate
        while ((token = parser.next()) != XmlPullParser.END_DOCUMENT)
        {
            if(token == XmlPullParser.START_TAG)
            {   //start of a new location
                if("Location".equals(parser.getName()))
                {
                    c_Location = new Location();
                    locations.add(c_Location);
                }
                //title
                else if ("Title".equals(parser.getName()))
                {
                    //get the text
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        c_Location.setTitle(parser.getText());
                        token = parser.next();
                    }
                }
                //ID
                else if ("ID".equals(parser.getName()))
                {
                    //get the text
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        c_Location.setID(parser.getText());
                        token = parser.next();
                    }
                }
                //lat
                else if ("Latitude".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        double lat = Double.parseDouble(parser.getText());
                        c_Location.setLatitude(lat);
                        token = parser.next();
                    }
                }
                //lon
                else if ("Longitude".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        double lon = Double.parseDouble(parser.getText());
                        c_Location.setLongitude(lon);
                        token = parser.next();
                    }
                }
                //info
                else if ("Info".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        c_Location.setInfo(parser.getText());
                        token = parser.next();
                    }
                }
                //markerImage
                else if ("MarkerImage".equals(parser.getName()))
                {
                    //get the text
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        c_Location.setMarkerImage(parser.getText());
                        token = parser.next();
                    }
                }
                //latBottomLeft
                else if ("latitudeBottomLeft".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        double lat = Double.parseDouble(parser.getText());
                        c_Location.setLatitudeBottomLeft(lat);
                        token = parser.next();
                    }
                }
                //lonBottomLeft
                else if ("longitudeBottomLeft".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        double lon = Double.parseDouble(parser.getText());
                        c_Location.setLongitudeBottomLeft(lon);
                        token = parser.next();
                    }
                }                
                //latTopRight
                else if ("latitudeTopRight".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        double lat = Double.parseDouble(parser.getText());
                        c_Location.setLatitudeTopRight(lat);
                        token = parser.next();
                    }
                }
                //lonTopRight
                else if ("longitudeTopRight".equals(parser.getName()))
                {
                    token = parser.next();
                    if(token == XmlPullParser.TEXT)
                    {
                        double lon = Double.parseDouble(parser.getText());
                        c_Location.setLongitudeTopRight(lon);
                        token = parser.next();
                    }
                }
            }
        }
        return locations;
    }
}
