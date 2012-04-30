package android.mobile.HatfieldHall.test;

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
	
	
	public void testPreconditions() {
	      assertNotNull(seatActivity);
	    }
	
	//TODO: Test that the activity referenced is SeatActivity
	public void testCorrectActivityIsReferenced(){
		assertTrue(seatActivity instanceof SeatActivity);
	}

	
	//TODO:	Test that the row spinner widget is created
	public void testRowSpinnerIsCreated(){
		assertTrue((seatActivity.getRows() != null) && (seatActivity.getRows() instanceof Spinner));
	}
	
	
	//TODO:	Test that the seat-number spinner widget is created
	public void testSeatNumberBoxIsCreated(){
		assertTrue((seatActivity.getNumBox() != null) && (seatActivity.getNumBox() instanceof EditText));
		
	}

	public void testSearchButtonIsCreated(){
		assertTrue((seatActivity.getSearchButton() != null) && (seatActivity.getSearchButton() instanceof Button));
	}
	//TODO: Test that the row spinner keys are correct
	
	public void testRowSpinnerKeysGetCorrectValues(){
	
		assertEquals(22, seatActivity.rows.getAdapter().getCount());	 
		}
	
	

	
	//TODO: Test that the floor-map loads
	public void testFloorMapLoads(){
		assertTrue((seatActivity.getFloorMap() != null) && (seatActivity.getFloorMap() instanceof ImageView));
	}
	
	
	

}
