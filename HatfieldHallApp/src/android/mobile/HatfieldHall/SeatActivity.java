package android.mobile.HatfieldHall;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SeatActivity extends Activity {
	
	
	 public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.seat);
	   
	       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rows, android.R.layout.simple_spinner_item );
	       adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
	       Spinner s = (Spinner) findViewById( R.id.rowSpinner );
	       s.setAdapter( adapter );
	 }
}
