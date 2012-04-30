package android.mobile.HatfieldHall;


/* This class will parse the
 * Hatfield Hall website
 * http://hatfieldhall.com and save
 * a xml file of the current show
 * information.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;
import android.util.Log;
import android.mobile.HatfieldHall.Event;


public class WebsiteParser {
	private ArrayList<Event> shows;
	
	
	/*
	 * This method takes no parameters
	 * and queries http://hatfieldhall.com
	 * to get upcoming show data off the main
	 * page. This function returns the HTML
	 * list information that stores the show
	 * data.
	 * 
	 * If no data connection exists, this
	 * method will instead load the values
	 * from an existing XML file if possible.
	 * The values will be loaded in the "shows"
	 * data structure and returns null.
	 * 
	 */
	public String getWebsiteData()
	{
		//TODO: Implement Method
		return null;		
	}
	
	/*
	 * This method takes the raw HTML list string
	 * from http://hatfieldhall.com and organizes
	 * the data into the event data structure for
	 * future use.
	 * 
	 * If a null string is passed in (for example
	 * we are unable to get current data from the
	 * internet) then the method will exit,
	 * and return the "shows" attribute.
	 * 
	 */
	public ArrayList<Event> parseWebsiteData(String rawHTML)
	{
		//TODO: Implement Method
		return null;		
	}
	
	/*
	 * This method saves the show data stored in
	 * the private attribute "shows" as an XML
	 * file. HatfieldHallApp uses the same XML
	 * file for all instances of the application.
	 * 
	 */
	public void saveShowData()
	{
		//TODO: Implement Method
	}
	
	/*
	 * This method loads the XML data saved from
	 * a previous instance of the app. Should no
	 * XML file be found, it will return a value
	 * of -1. If successful, the method will 
	 * return 0.
	 * 
	 */
	public int loadXmlData()
	{
		//TODO: Implement Method
		return -1;
	}
}
