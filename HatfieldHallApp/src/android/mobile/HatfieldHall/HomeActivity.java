package android.mobile.HatfieldHall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.widget.ListView;

public class HomeActivity extends Activity {
	private ArrayList<Event> shows = new ArrayList<Event>();;
	private ListView showsList;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		String data = getWebsiteData();
		if(data != null)
			parseWebsiteData(data);
		shows = getShows();
		ShowsListAdapter adapter = new ShowsListAdapter(shows, this);
		showsList = (ListView) findViewById(R.id.home_shows_list);
		showsList.setAdapter(adapter);
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
		ConnectivityManager cm =
		        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		boolean connection = (netInfo != null && netInfo.isConnectedOrConnecting());
		if(connection)
			{
				HttpGet pageGet = new HttpGet("http://hatfieldhall.com");

			    ResponseHandler<String> handler = new ResponseHandler<String>() {
			        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			            HttpEntity entity = response.getEntity();
			            String html; 

			            if (entity != null) {
			                html = EntityUtils.toString(entity);
			                return html;
			            }
			            else
			            	return null;
			        }
			    };
			    HttpClient client = new DefaultHttpClient();
			    String html = null;
			    try {
					html = client.execute(pageGet, handler);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			    html=html.substring(html.indexOf("<div id=\"mainContainer\">"));
			    int comment = html.indexOf("<!--");
			    int startList = html.indexOf("<li>");
			    html = html.substring(startList, comment);
			    int endList = html.lastIndexOf("</li>");
			    html = html.substring(0, endList+5);
			    html = html.replace("\n", "");
			    html = html.replace("\t", "");
			    return html;
			    
			}
			else
			{
				if(loadXmlData()!=0)
					{
					return getString(R.string.no_connection);
					
					}
				return null;
			}
			
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
		if(rawHTML == null)
			if(shows.size()>0)
				return shows;
		ArrayList<Event> currentShows = new ArrayList<Event>();
		while(rawHTML.length()>0)
		{
			Event tempEvent = new Event();
			int imageStart = rawHTML.indexOf("href");
			int imageEnd = rawHTML.indexOf("alt");
			String image = rawHTML.substring(imageStart, imageEnd);
			rawHTML = rawHTML.substring(imageEnd);
			imageStart= image.indexOf("\"");
			imageEnd = image.lastIndexOf("\"");
			image = "http://hatfieldhall.com"+image.substring(imageStart+1, imageEnd);
			int linkStart = rawHTML.indexOf("link");
			int linkEnd = rawHTML.indexOf("</a>");
			String link = rawHTML.substring(linkStart, linkEnd);
			rawHTML = rawHTML.substring(linkEnd);
			linkStart = link.indexOf("\"");
			linkEnd = link.lastIndexOf("\"");
			link = "http://hatfieldhall.com"+ link.substring(linkStart+1, linkEnd);
			int nameStart = rawHTML.indexOf("h2");
			int nameEnd = rawHTML.lastIndexOf("</h2>");
			String name = rawHTML.substring(nameStart+3, nameEnd);
			rawHTML = rawHTML.substring(nameEnd);
			nameStart = name.indexOf(">");
			name = name.substring(nameStart+1);
			int datesStart = rawHTML.indexOf("small");
			int datesEnd = rawHTML.indexOf("</small>");
			String dates = rawHTML.substring(datesStart+6, datesEnd);
			tempEvent.dates = dates;
			tempEvent.imageURL = image;
			tempEvent.link = link;
			tempEvent.name = name;
			currentShows.add(tempEvent);
			if(rawHTML.indexOf("href") < 0)
				rawHTML = "";

		}
		shows = currentShows;
		return currentShows;		
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
		
		try {
			
			// Create and save file
			File newXmlFile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
			
			//if file exists, remove it so we can create a new one
			if(shows.size()!=0)
			{
				if(newXmlFile.exists())
					newXmlFile.delete();
				newXmlFile.createNewFile();
				XmlSerializer serializer = Xml.newSerializer();
				FileOutputStream fileos = new FileOutputStream(newXmlFile);
				serializer.setOutput(fileos, "UTF-8");
				serializer.startDocument(null, Boolean.valueOf(true));
				serializer.startTag(null, "Events");
				
				// loop through all the shows to construct the xml file
				for(int i = 0; i< shows.size(); i++)
				{
					serializer.startTag(null, "show");
					serializer.startTag(null, "name");
					serializer.text(shows.get(i).name);
					serializer.endTag(null, "name");
					serializer.startTag(null, "date");
					serializer.text(shows.get(i).dates);
					serializer.endTag(null, "date");
					serializer.startTag(null, "link");
					serializer.text(shows.get(i).link);
					serializer.endTag(null, "link");
					serializer.startTag(null, "image");
					serializer.text(shows.get(i).imageURL);
					serializer.endTag(null, "image");
					serializer.endTag(null, "show");
				}
				serializer.endTag(null, "Events");
				serializer.endDocument();
				
				// write file
				serializer.flush();
				fileos.close();
			}
				
		} catch (IllegalArgumentException e) {
			// just save the stack and debug
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// just save the stack and debug
			e.printStackTrace();
		} catch (IOException e) {
			// just save the stack and debug
			e.printStackTrace();
		}
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
		File xmlFile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
		if(xmlFile.exists())
		{
			XmlPullParser parser = Xml.newPullParser();
			ArrayList<String> data= new ArrayList<String>(4);
			try {
				FileInputStream fIn = new FileInputStream(xmlFile);
				InputStreamReader isr = new InputStreamReader(fIn);
				parser.setInput(isr);
			    int eventType = parser.getEventType();
			    boolean done = false;
			    while (eventType != XmlPullParser.END_DOCUMENT && !done){
			        //String tag = null;
			        switch (eventType){
			            case XmlPullParser.START_DOCUMENT:
			                break;
			            case XmlPullParser.START_TAG:
			                //tag = parser.getName();
			                break;
			            case XmlPullParser.TEXT:
			            	data.add(parser.getText());
			            	break;
			            }
			        eventType = parser.next();
			        }
				

			} catch (FileNotFoundException e) {
				// just use stack trace
				e.printStackTrace();
				return -1;
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				return -1;
			} catch (IOException e) {
				e.printStackTrace();
				return -1;
			}
			if(data.size()%4 != 0)
			{
				//checks to see if the file had the correct amount of show data
				return -1;
			}
			for(int i=0; i<data.size(); i+=4)
			{
				Event addShow = new Event();
				addShow.name=data.get(i);
				addShow.dates=data.get(i+1);
				addShow.link=data.get(i+2);
				addShow.imageURL=data.get(i+3);
				shows.add(addShow);
				
			}
			return 0;
		}
		else
		{
			return -1;
		}
		
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
