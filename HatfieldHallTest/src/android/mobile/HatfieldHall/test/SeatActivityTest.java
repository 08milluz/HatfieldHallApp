package android.mobile.HatfieldHall.test;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.mobile.HatfieldHall.SeatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.widget.TextView;


@SuppressWarnings("rawtypes")
public class SeatActivityTest extends ActivityInstrumentationTestCase2 {
	
	private Activity mActivity;		// SeatActivity is under test
	private TextView mView;				// SeatActivity's TextView
	private String resourceString;
	private static final ArrayList<Locale> locales= new ArrayList<Locale>(); // Locales to test
	private Configuration config = new Configuration(); // Configuration used to set locality
	
	
	@SuppressWarnings("unchecked")
	public SeatActivityTest() {
		super("android.mobile.HatfieldHall",SeatActivity.class);
		
	}
	
	
	protected void setUp() throws Exception{
		super.setUp();
		
		locales.add(Locale.getDefault());
    	locales.add(new Locale("en", "US"));
    	locales.add(new Locale("de", "DE"));
    	locales.add(new Locale("fr", "FR"));
    	Configuration config = new Configuration();
	    	
    	 
    	 
		mActivity = this.getActivity();
		mView = (TextView) mActivity.findViewById(android.mobile.HatfieldHall.R.id.textView1);
		resourceString = mActivity.getString(android.mobile.HatfieldHall.R.string.find_seat);
	}
	
	//TODO: Test mView is not null
	public void testPreconditions() {
	      assertNotNull(mView);
	    }
	
	
	//TODO: Test the locale is correctly-set
	public void testLocaleIsRecognized(){
		Resources res = mActivity.getResources();
	    // Change locale settings in the app.
	    DisplayMetrics dm = res.getDisplayMetrics();
	    config = res.getConfiguration();
 
		for (Locale loc: locales){
			Locale.setDefault(loc);
			config.locale = loc;
			res.updateConfiguration(config, dm);
			assertEquals(Locale.getDefault(), Locale.getDefault());
		}
		
	}
	
	
	//TODO: Test text is properly translated
	public void testLocaleText(){
		fail("This test is not yet implemented");
	}
	
	
	//TODO: Test that the activity referenced is SeatActivity
	public void testCorrectActivityIsReferenced(){
		fail("This test is not yet implemented");
	}

	
	//TODO:	Test that the row spinner widget is created
	public void testRowSpinnerIsCreated(){
		fail("This test is not yet implemented");
	}
	
	
	//TODO:	Test that the seat-number spinner widget is created
	public void testSeatNumberSpinnerIsCreated(){
		fail("This test is not yet implemented");
	}

	
	//TODO: Test that the row spinner keys are correct
	public void testRowSpinnerKeysGetCorrectValues(){
		fail("This test is not yet implemented");
	}
	
	
	//TODO: Test that the seat-number spinner keys are correct
	public void testSeatNumberSpinnerKeysGetCorrectValues(){
		fail("This test is not yet implemented");
	}

	
	//TODO: Test that the floor-map loads
	public void testFloorMapLoads(){
		fail("This test is not yet implemented");
	}
	
	
	//TODO: Test that the hot-spots layer-over the correct area of the image 
	public void testHotSpotsLayerOverImage(){
		fail("This test is not yet implemented");
	}
	
	
	//TODO: Test that each hot-spot highlights its respective seat on the floor-map 
	public void testHotSpotsHighLightWithRespectToAppropriateRowAndSeatNumber(){
		fail("This test is not yet implemented");
	}
}