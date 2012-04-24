package android.mobile.HatfieldHall.test;

import java.util.Locale;



import android.app.Activity;
import android.content.res.Configuration;
import android.mobile.HatfieldHall.HatfieldHallActivity;
import android.mobile.HatfieldHall.R;
import android.test.ActivityInstrumentationTestCase2;

public class LocaleTest2 extends
		ActivityInstrumentationTestCase2<HatfieldHallActivity> {
	private Activity mActivity; // VenueActivity is under test

	public LocaleTest2() {
		super("android.mobile.HatfieldHall", HatfieldHallActivity.class);


	}
	
	public void frenchLocaleSetUp() {
		Locale locale2 = new Locale("fr");
		Locale.setDefault(locale2);
		Configuration config2 = new Configuration();
		config2.locale = locale2;
		mActivity = this.getActivity();
		mActivity
				.getBaseContext()
				.getResources()
				.updateConfiguration(
						config2,
						mActivity.getBaseContext().getResources()
								.getDisplayMetrics());
	}


	public void testFrenchAppName() {

		assertEquals("Hatfield salle", mActivity.getString(R.string.app_name));
	}


	public void testFrenchHome() {

		assertEquals("Accueil", mActivity.getString(R.string.home));
	}


	public void testFrenchFindSeat() {

		assertEquals("Trouver Seat", mActivity.getString(R.string.find_seat));
	}

	
	public void testFrenchVenue() {

		assertEquals("Lieu", mActivity.getString(R.string.venue));
	}

	
	public void testFrenchInfo() {

		assertEquals("infos:", mActivity.getString(R.string.info));
	}

	
	public void testFrenchAddress() {

		assertEquals("Adresse:", mActivity.getString(R.string.address));
	}

	
	public void testFrenchHallAddress() {

		assertEquals("5500 Wabash Avenue - CM17, Terre Haute, Indiana 47803",
				mActivity.getString(R.string.hall_address));
	}

	
	public void testFrenchPhone() {

		assertEquals("Téléphone:", mActivity.getString(R.string.phone));
	}

	
	public void testFrenchHallPhone() {

		assertEquals("8128778544", mActivity.getString(R.string.hall_phone));
	}

	
	public void testFrenchHours() {

		assertEquals("Heures:", mActivity.getString(R.string.hours));
	}

	
	public void testFrenchHallHours() {

		assertEquals(
				"du lundi au vendredi 10 heures-17 heures, le samedi 12 heures-17 heures. Ouvrez 2 heures avant un événement. Fermé pendant les périodes de vacances Rose-Hulman.",
				mActivity.getString(R.string.hall_hours));
	}

	
	public void testFrenchSeatNumber() {

		assertEquals("numéro du siège",
				mActivity.getString(R.string.seat_number));
	}

	
	public void testFrenchSearch() {

		assertEquals("Rechercher", mActivity.getString(R.string.search));
	}

	public void japaneseLocaleSetUp() {
		Locale locale2 = new Locale("jp");
		Locale.setDefault(locale2);
		Configuration config2 = new Configuration();
		config2.locale = locale2;
		mActivity = this.getActivity();
		mActivity
				.getBaseContext()
				.getResources()
				.updateConfiguration(
						config2,
						mActivity.getBaseContext().getResources()
								.getDisplayMetrics());
	}

	
	public void testJapaneseAppName() {

		assertEquals("ハットフィールドホール", mActivity.getString(R.string.app_name));
	}

	
	public void testJapaneseHome() {

		assertEquals("ホーム", mActivity.getString(R.string.home));
	}

	
	public void testJapaneseFindSeat() {

		assertEquals("はシート", mActivity.getString(R.string.find_seat));
	}

	
	public void testJapaneseVenue() {

		assertEquals("会場", mActivity.getString(R.string.venue));
	}

	
	public void testJapaneseInfo() {

		assertEquals("情報", mActivity.getString(R.string.info));
	}

	
	public void testJapaneseAddress() {

		assertEquals("情報", mActivity.getString(R.string.address));
	}

	
	public void testJapaneseHallAddress() {

		assertEquals("5500ウォバシュアベニュー - CM17、テレホート、インディアナ州47803",
				mActivity.getString(R.string.hall_address));
	}

	
	public void testJapanesePhone() {

		assertEquals("電話番号:", mActivity.getString(R.string.phone));
	}

	
	public void testJapaneseHallPhone() {

		assertEquals("8128778544", mActivity.getString(R.string.hall_phone));
	}

	
	public void testJapaneseHours() {

		assertEquals("時間:", mActivity.getString(R.string.hours));
	}

	
	public void testJapaneseHallHours() {

		assertEquals(
				"月曜日〜金曜日午前10時 - 午後5時、土曜日午後12時 - 午後5時。イベント前に2時間を開きます。ローズハルマン休日の休憩中に閉じた。",
				mActivity.getString(R.string.hall_hours));
	}
	
	
	public void testJapaneseSeatNumber() {

		assertEquals("座席番号",
				mActivity.getString(R.string.seat_number));
	}

	
	public void testJapaneseSearch() {

		assertEquals("検索", mActivity.getString(R.string.search));
	}


}
