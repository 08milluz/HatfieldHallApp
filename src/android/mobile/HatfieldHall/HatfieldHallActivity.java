package android.mobile.HatfieldHall;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HatfieldHallActivity extends TabActivity{

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);

			TabHost tabHost = getTabHost();

			// Tab for Home
			TabSpec homeSpec = tabHost.newTabSpec("Home");
			// setting Title and Icon for the Tab
			homeSpec.setIndicator("Home",
					getResources().getDrawable(R.drawable.icon_home_tab));
			Intent homeIntent = new Intent(this, HomeActivity.class);
			homeSpec.setContent(homeIntent);

			// Tab for Find Seat
			TabSpec seatSpec = tabHost.newTabSpec("Find Seat");
			seatSpec.setIndicator("Find Seat",
					getResources().getDrawable(R.drawable.icon_search_tab));
			Intent seatIntent = new Intent(this, SeatActivity.class);
			seatSpec.setContent(seatIntent);

			// Tab for Venue
			TabSpec venueSpec = tabHost.newTabSpec("Venue");
			venueSpec.setIndicator("Venue",
					getResources().getDrawable(R.drawable.icon_venue_tab));
			Intent venueIntent = new Intent(this, VenueActivity.class);
			venueSpec.setContent(venueIntent);

			// Adding all TabSpec to TabHost
			tabHost.addTab(homeSpec); // Adding Home tab
			tabHost.addTab(seatSpec);//Adding Find-Seat tab
			tabHost.addTab(venueSpec); // Adding Venue tab


		}
	}