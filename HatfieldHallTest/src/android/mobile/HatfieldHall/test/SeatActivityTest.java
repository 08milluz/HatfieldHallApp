/* This class tests the functionality of Seat Activity.java
 * Developer:  Caleb Drake
 */

package android.mobile.HatfieldHall.test;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.mobile.HatfieldHall.SeatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


@SuppressWarnings("rawtypes")
public class SeatActivityTest extends ActivityInstrumentationTestCase2<SeatActivity> {
	private SeatActivity seatActivity;
	
	
	@SuppressWarnings("unchecked")
	public SeatActivityTest() {
		super("android.mobile.HatfieldHall",SeatActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		
		seatActivity = getActivity();
	}
	
	//TEST:				the activity loads and is references SeatActivity 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testPreconditions() {
	      assertNotNull(seatActivity);
	      assertTrue(seatActivity instanceof SeatActivity);
	    }
	

	//TEST:				the row spinner widget is created 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testRowSpinnerIsCreated(){
		assertTrue((seatActivity.getRows() != null) && (seatActivity.getRows() instanceof Spinner));
	}
	
	
	//TEST:				the seat-number spinner widget is created 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testSeatNumberBoxIsCreated(){
		assertTrue((seatActivity.getNumBox() != null) && (seatActivity.getNumBox() instanceof EditText));
		
	}

	//TEST: 			the search button is created
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testSearchButtonIsCreated(){
		assertTrue((seatActivity.getSearchButton() != null) && (seatActivity.getSearchButton() instanceof Button));
	}
	
	
	//TEST:				the row spinner keys are correct 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testRowSpinnerKeysGetCorrectValues(){
	
		assertEquals(22, seatActivity.getRows().getAdapter().getCount());	 
		}

	
	//TEST:				the floor-map loads 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testFloorMapLoads(){
		assertTrue((seatActivity.getFloorMap() != null) && (seatActivity.getFloorMap() instanceof ImageView));
	}
	
	
	//TEST:				the seat marker loads 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testSeatMarkerLoads(){
		assertTrue((seatActivity.getMBitmap() != null) && (seatActivity.getMBitmap() instanceof Bitmap));
	}
	

	//TEST:				Seat Identifiers (i.e. A12, C13, etc...) are assigned to their native Point coordinates 
	//CODE COMPLEXITY:	1
	//CODE QUALITY:		100
	public void testMapping(){
		//Checking Invalid Seats Get Re-routed to Point(0,0)
		assertEquals(new Point(0,0), seatActivity.getSeat(""));
		assertEquals(new Point(0,0), seatActivity.getSeat("This should be (0,0)"));
		assertEquals(new Point(0,0), seatActivity.getSeat("B19923"));
		assertEquals(new Point(0,0), seatActivity.getSeat("9876543210ZYXzyxABCabc"));
		assertEquals(new Point(0,0), seatActivity.getSeat("A0"));
		
		//Checking Valid Seats
		assertEquals(new Point(162,651), seatActivity.getSeat("A201"));
		assertEquals(new Point(259,651), seatActivity.getSeat("A12"));
		assertEquals(new Point(509,408), seatActivity.getSeat("K107"));
		assertEquals(new Point(139,35), seatActivity.getSeat("BE29"));
		assertEquals(new Point(211,259), seatActivity.getSeat("P16"));
	}
	

}
