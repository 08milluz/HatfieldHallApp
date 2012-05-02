package android.mobile.HatfieldHall;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class HomeActivity extends Activity {
	private ArrayList<Event> shows;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}
	

	
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
	 * If no show data is available to be saved
	 * (and the file exists) it is not touched.
	 * Otherwise an XML file with no shows is created.
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
	
	/*
	 * This method returns the "shows" structure.
	 * 
	 */
	public ArrayList<Event> getShows()
	{
		return shows;
	}
	
	/*
	 * This method sets the shows field externally.
	 * This method should only be used for testing.
	 */
	public void setShows(ArrayList<Event> manualSet)
	{
		shows = manualSet;		
	}
}
