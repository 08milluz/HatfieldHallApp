package android.mobile.HatfieldHall.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.res.XmlResourceParser;
import android.mobile.HatfieldHall.Event;
import android.mobile.HatfieldHall.HomeActivity;
import android.mobile.HatfieldHall.R;
import android.os.Environment;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Xml;

public class HomeActivityTest extends
		ActivityInstrumentationTestCase2<HomeActivity> {

	private HomeActivity homeActivity;
	
	
	public HomeActivityTest() {
		super("android.mobile.HatfieldHall", HomeActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		homeActivity = getActivity();
	}
	
	public void testLoadXmlFileReturnsErrorForNoFile()
	{
		//no file is present thus loadXmlData will return -1;

		assertEquals(-1, homeActivity.loadXmlData());
		
		
	}
	
	public void testLoadXmlFileReturnsZeroForFile()
	{
		// Create sample XML File
		try {
			
			// Create and save file
			File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
			newxmlfile.createNewFile();
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream fileos = new FileOutputStream(newxmlfile);
			serializer.setOutput(fileos, "UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.startTag(null, "Events");
			serializer.startTag(null, "show");
			serializer.startTag(null, "name");
			serializer.text("RDC: Phantom of the Opera");
			serializer.endTag(null, "name");
			serializer.startTag(null, "date");
			serializer.text("Opens Friday, 4.27.12");
			serializer.endTag(null, "date");
			serializer.startTag(null, "link");
			serializer.text("http://hatfieldhall.com/events/phantomoftheopera/");
			serializer.endTag(null, "link");
			serializer.startTag(null, "image");
			serializer.text("http://hatfieldhall.com/images/featured/Phantom_480x320.png");
			serializer.endTag(null, "image");
			serializer.endTag(null, "show");
			serializer.endTag(null, "Events");
			serializer.endDocument();
			serializer.flush();
			fileos.close();
			
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
		
		// Load file
		assertEquals(0, homeActivity.loadXmlData());
		
		// Check that values are added to Event structure
		ArrayList<Event> shows = homeActivity.getShows();
		assertEquals(1, shows.size());
		assertEquals("RDC: Phantom of the Opera", shows.get(0).name);
		assertEquals("Opens Friday, 4.27.12", shows.get(0).dates);
		assertEquals("http://hatfieldhall.com/events/phantomoftheopera/", shows.get(0).link);
		assertEquals("http://hatfieldhall.com/images/featured/Phantom_480x320.png", shows.get(0).imageURL);
		
		// Delete File
		File xmlFile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
		xmlFile.delete();
		
	}

	public void testSaveShowDataSavesWithNoEvents()
	{
		HomeActivity emptyShows = new HomeActivity();
		emptyShows.saveShowData();
		File xmlFile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
		assertFalse(xmlFile.exists());	
		
		// Delete File
		xmlFile.delete();
	}
	
	public void testSaveShowDataSavesWithExistingFileAndNoEvents()
	{
		// Create sample XML File
		try {
			
			// Create and save file
			File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
			newxmlfile.createNewFile();
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream fileos = new FileOutputStream(newxmlfile);
			serializer.setOutput(fileos, "UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.startTag(null, "Events");
			serializer.startTag(null, "show");
			serializer.startTag(null, "name");
			serializer.text("RDC: Phantom of the Opera");
			serializer.endTag(null, "name");
			serializer.startTag(null, "date");
			serializer.text("Opens Friday, 4.27.12");
			serializer.endTag(null, "date");
			serializer.startTag(null, "link");
			serializer.text("http://hatfieldhall.com/events/phantomoftheopera/");
			serializer.endTag(null, "link");
			serializer.startTag(null, "image");
			serializer.text("http://hatfieldhall.com/images/featured/Phantom_480x320.png");
			serializer.endTag(null, "image");
			serializer.endTag(null, "show");
			serializer.endTag(null, "Events");
			serializer.endDocument();
			serializer.flush();
			fileos.close();
			
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
		
		// Create new activity and test it.
		HomeActivity emptyShows = new HomeActivity();
		emptyShows.saveShowData();
		File xmlFile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
		assertTrue(xmlFile.exists());	
		
		// Parse File
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
			
			// Remove file
			xmlFile.delete();
		} catch (FileNotFoundException e) {
			// just use stack trace
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    // check values
		assertEquals(0,data.get(0).compareTo("RDC: Phantom of the Opera"));
		assertEquals(0,data.get(1).compareTo("Opens Friday, 4.27.12"));
		assertEquals(0,data.get(2).compareTo("http://hatfieldhall.com/events/phantomoftheopera/"));
		assertEquals(0,data.get(3).compareTo("http://hatfieldhall.com/images/featured/Phantom_480x320.png"));
	}
	
	public void testSaveShowDataWithShows()
	{
		// make dummy event to populate in activity
		Event phantomEvent = new Event();
		phantomEvent.name = "RDC: Phantom of the Opera";
		phantomEvent.dates = "Opens Friday, 4.27.12";
		phantomEvent.link = "http://hatfieldhall.com/events/phantomoftheopera/";
		phantomEvent.imageURL = "http://hatfieldhall.com/images/featured/Phantom_480x320.png";
		ArrayList<Event> testEvents = new ArrayList<Event>(1);
		testEvents.add(phantomEvent);
		
		// create activity and open file.
		HomeActivity manuallySetShows = new HomeActivity();
		manuallySetShows.setShows(testEvents);
		manuallySetShows.saveShowData();
		File xmlFile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
		assertTrue(xmlFile.exists());	
		
		// Parse File
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
			
			// Remove file
			xmlFile.delete();
		} catch (FileNotFoundException e) {
			// just use stack trace
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    // check values
		assertEquals(0,data.get(0).compareTo("RDC: Phantom of the Opera"));
		assertEquals(0,data.get(1).compareTo("Opens Friday, 4.27.12"));
		assertEquals(0,data.get(2).compareTo("http://hatfieldhall.com/events/phantomoftheopera/"));
		assertEquals(0,data.get(3).compareTo("http://hatfieldhall.com/images/featured/Phantom_480x320.png"));	
	}
	
	public void testParseWebsiteData()
	{
		// make dummy event to populate in activity
		Event phantomEvent = new Event();
		phantomEvent.name = "RDC: Phantom of the Opera";
		phantomEvent.dates = "Opens Friday, 4.27.12";
		phantomEvent.link = "http://hatfieldhall.com/events/phantomoftheopera/";
		phantomEvent.imageURL = "http://hatfieldhall.com/images/featured/Phantom_480x320.png";
		ArrayList<Event> testEvents = new ArrayList<Event>();
		testEvents.add(phantomEvent);
		
		// simulated website string
		String websiteString = "<li><a href=\"/images/featured/Phantom_480x320.png\" alt=\"3\" link=\"/events/phantomoftheopera/\"></a><div class=\"block\"><h2 style=\"font-size:16px\">RDC: Phantom of the Opera</h2><small>Opens Friday, 4.27.12</small></div></li>";
		ArrayList<Event> realEvents = new ArrayList<Event>();
		realEvents =	homeActivity.parseWebsiteData(websiteString);
		for(int i =0; i < realEvents.size(); i++)
		{
		assertEquals(testEvents.get(i).name, realEvents.get(i).name);
		assertEquals(testEvents.get(i).imageURL, realEvents.get(i).imageURL);
		assertEquals(testEvents.get(i).link, realEvents.get(i).link);
		assertEquals(testEvents.get(i).dates, realEvents.get(i).dates);
		}
		
	}
	
	public void testParseWebsiteDataWithoutString()
	{
		// make dummy event to populate in activity
		Event phantomEvent = new Event();
		phantomEvent.name = "RDC: Phantom of the Opera";
		phantomEvent.dates = "Opens Friday, 4.27.12";
		phantomEvent.link = "http://hatfieldhall.com/events/phantomoftheopera/";
		phantomEvent.imageURL = "http://hatfieldhall.com/images/featured/Phantom_480x320.png";
		ArrayList<Event> testEvents = new ArrayList<Event>(1);
		testEvents.add(phantomEvent);
		
		// create activity and open file.
		HomeActivity manuallySetShows = new HomeActivity();
		manuallySetShows.setShows(testEvents);
		
		assertEquals(testEvents, manuallySetShows.parseWebsiteData(null));
				
		
	}
	
	public void testGetWebsiteDataWithConnection()
	{
		// make dummy event to populate in activity
				Event phantomEvent = new Event();
				phantomEvent.name = "RDC: Phantom of the Opera";
				phantomEvent.dates = "Opens Friday, 4.27.12";
				phantomEvent.link = "http://hatfieldhall.com/events/phantomoftheopera/";
				phantomEvent.imageURL = "http://hatfieldhall.com/images/featured/Phantom_480x320.png";
				ArrayList<Event> testEvents = new ArrayList<Event>(1);
				testEvents.add(phantomEvent);
				
				// simulated website string
				String websiteString = "<li><a href=\"/images/featured/Phantom_480x320.png\" alt=\"3\" link=\"/events/phantomoftheopera/\"></a><div class=\"block\"><h2 style=\"font-size:16px\">RDC: Phantom of the Opera</h2><small>Opens Friday, 4.27.12</small></div></li>";
				
				String resultString = homeActivity.getWebsiteData().toString();
				assertEquals(websiteString, resultString);
		
	}
	
	public void testGetWebsiteDataWithoutConnection()
	{
		// I have no idea how to turn off the connection
		
		
		File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/HatfieldHall.xml");
		// Create sample XML File
		try {
			
			// Create and save file
			newxmlfile.createNewFile();
			XmlSerializer serializer = Xml.newSerializer();
			FileOutputStream fileos = new FileOutputStream(newxmlfile);
			serializer.setOutput(fileos, "UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.startTag(null, "Events");
			serializer.startTag(null, "show");
			serializer.startTag(null, "name");
			serializer.text("RDC: Phantom of the Opera");
			serializer.endTag(null, "name");
			serializer.startTag(null, "date");
			serializer.text("Opens Friday, 4.27.12");
			serializer.endTag(null, "date");
			serializer.startTag(null, "link");
			serializer.text("http://hatfieldhall.com/events/phantomoftheopera/");
			serializer.endTag(null, "link");
			serializer.startTag(null, "image");
			serializer.text("http://hatfieldhall.com/images/featured/Phantom_480x320.png");
			serializer.endTag(null, "image");
			serializer.endTag(null, "show");
			serializer.endTag(null, "Events");
			serializer.endDocument();
			serializer.flush();
			fileos.close();
			
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
		String websiteData = homeActivity.getWebsiteData();
		newxmlfile.delete();
		assertEquals(null, websiteData);
		
		
		
	}
	}
	
