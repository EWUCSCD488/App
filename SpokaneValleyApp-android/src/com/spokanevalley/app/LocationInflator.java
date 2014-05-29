package com.spokanevalley.app;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.util.ArrayList;


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
            {   //start of location
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
            }
        }
        return locations;
    }
}