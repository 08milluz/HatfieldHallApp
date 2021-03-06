package android.mobile.HatfieldHall;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HatfieldHallActivity extends TabActivity{
		
		//COMPLEXITY RATING: 1
		//CODE QUALITY: 100
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);			
			TabHost tabHost = getTabHost();
			
			
			// Tab for Home
			TabSpec homeSpec = tabHost.newTabSpec(getString(R.string.home));
			// setting Title and Icon for the Tab
			homeSpec.setIndicator(getString(R.string.home),
					getResources().getDrawable(R.drawable.icon_home_tab));
			Intent homeIntent = new Intent(this, HomeActivity.class);
			homeSpec.setContent(homeIntent);

			// Tab for Find Seat
			TabSpec seatSpec = tabHost.newTabSpec(getString(R.string.find_seat));
			seatSpec.setIndicator(getString(R.string.find_seat),
					getResources().getDrawable(R.drawable.icon_search_tab));
			Intent seatIntent = new Intent(this, SeatActivity.class);
			seatSpec.setContent( 	seatIntent);

			// Tab for Venue
			TabSpec venueSpec = tabHost.newTabSpec(getString(R.string.venue));
			venueSpec.setIndicator(getString(R.string.venue),
					getResources().getDrawable(R.drawable.icon_venue_tab));
			Intent venueIntent = new Intent(this, VenueActivity.class);
			venueSpec.setContent(venueIntent);

			// Adding all TabSpec to TabHost
			tabHost.addTab(homeSpec); // Adding Home tab
			tabHost.addTab(seatSpec);//Adding Find-Seat tab
			tabHost.addTab(venueSpec); // Adding Venue tab


		}
	}