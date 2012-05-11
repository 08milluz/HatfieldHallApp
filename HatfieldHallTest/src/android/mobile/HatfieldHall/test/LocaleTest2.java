package android.mobile.HatfieldHall.test;

import java.util.Locale;

import android.app.Activity;
import android.content.res.Configuration;
import android.mobile.HatfieldHall.HatfieldHallActivity;
import android.mobile.HatfieldHall.R;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class LocaleTest2 extends
		ActivityInstrumentationTestCase2<HatfieldHallActivity> {
	private Activity mActivity; // VenueActivity is under test
	private Locale locale2;
	public static int points = 0;
	public final String testPoints = "testPoints";
	public LocaleTest2() {
		super("android.mobile.HatfieldHall", HatfieldHallActivity.class);


	}
	
	public void frenchLocaleSetUp() {
		locale2 = new Locale("fr");
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
		frenchLocaleSetUp();
		assertEquals("Hatfield salle", mActivity.getString(R.string.app_name));
		points++;
	}


	public void testFrenchHome() {
		frenchLocaleSetUp();
		assertEquals("Accueil", mActivity.getString(R.string.home));
	}


	public void testFrenchFindSeat() {
		frenchLocaleSetUp();
		assertEquals("Trouver Seat", mActivity.getString(R.string.find_seat));
	}

	
	public void testFrenchVenue() {
		frenchLocaleSetUp();
		assertEquals("Lieu", mActivity.getString(R.string.venue));
	}

	
	public void testFrenchInfo() {
		frenchLocaleSetUp();
		assertEquals("infos:", mActivity.getString(R.string.info));
	}

	
	public void testFrenchAddress() {
		frenchLocaleSetUp();
		assertEquals("Adresse:", mActivity.getString(R.string.address));
	}

	
	public void testFrenchHallAddress() {
		frenchLocaleSetUp();
		assertEquals("5500 Wabash Avenue - CM17, Terre Haute, Indiana 47803",
				mActivity.getString(R.string.hall_address));
	}

	
	public void testFrenchPhone() {
		frenchLocaleSetUp();
		assertEquals("Téléphone:", mActivity.getString(R.string.phone));
	}

	
	public void testFrenchHallPhone() {
		frenchLocaleSetUp();
		assertEquals("8128778544", mActivity.getString(R.string.hall_phone));
	}

	
	public void testFrenchHours() {
		frenchLocaleSetUp();
		assertEquals("Heures:", mActivity.getString(R.string.hours));
	}

	
	public void testFrenchHallHours() {
		frenchLocaleSetUp();
		assertEquals(
				"du lundi au vendredi 10 heures-17 heures, le samedi 12 heures-17 heures. Ouvrez 2 heures avant un événement. Fermé pendant les périodes de vacances Rose-Hulman.",
				mActivity.getString(R.string.hall_hours));
	}

	
	public void testFrenchSeatNumber() {
		frenchLocaleSetUp();
		assertEquals("Siège #",
				mActivity.getString(R.string.seat_number));
	}

	
	public void testFrenchSearch() {
		frenchLocaleSetUp();
		assertEquals("Rechercher", mActivity.getString(R.string.search));
	}

	public void japaneseLocaleSetUp() {
		locale2 = new Locale("jp");
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
		japaneseLocaleSetUp();
		assertEquals("ハットフィールドホール", mActivity.getString(R.string.app_name));
	}

	
	public void testJapaneseHome() {
		japaneseLocaleSetUp();
		assertEquals("ホーム", mActivity.getString(R.string.home));
	}

	
	public void testJapaneseFindSeat() {
		japaneseLocaleSetUp();
		assertEquals("はシート", mActivity.getString(R.string.find_seat));
	}

	
	public void testJapaneseVenue() {
		japaneseLocaleSetUp();
		assertEquals("会場", mActivity.getString(R.string.venue));
	}

	
	public void testJapaneseInfo() {
		japaneseLocaleSetUp();
		assertEquals("情報", mActivity.getString(R.string.info));
	}

	
	public void testJapaneseAddress() {
		japaneseLocaleSetUp();
		assertEquals("アドレス:", mActivity.getString(R.string.address));
	}

	
	public void testJapaneseHallAddress() {
		japaneseLocaleSetUp();
		assertEquals("5500ウォバシュアベニュー - CM17、テレホート、インディアナ州47803",
				mActivity.getString(R.string.hall_address));
	}

	
	public void testJapanesePhone() {
		japaneseLocaleSetUp();
		assertEquals("電話番号:", mActivity.getString(R.string.phone));
	}

	
	public void testJapaneseHallPhone() {
		japaneseLocaleSetUp();
		assertEquals("8128778544", mActivity.getString(R.string.hall_phone));
	}

	
	public void testJapaneseHours() {
		japaneseLocaleSetUp();
		assertEquals("時間:", mActivity.getString(R.string.hours));
	}

	
	public void testJapaneseHallHours() {
		japaneseLocaleSetUp();
		assertEquals(
				"月曜日〜金曜日午前10時 - 午後5時、土曜日午後12時 - 午後5時。イベント前に2時間を開きます。ローズハルマン休日の休憩中に閉じた。",
				mActivity.getString(R.string.hall_hours));
	}
	
	
	public void testJapaneseSeatNumber() {
		japaneseLocaleSetUp();
		assertEquals("座席番号",
				mActivity.getString(R.string.seat_number));
	}

	
	public void testJapaneseSearch() {
		japaneseLocaleSetUp();
		assertEquals("検索", mActivity.getString(R.string.search));
	}
	
	public void totalPoints() {
		Log.d(testPoints, points + "");
		System.out.println(points);
	}


}
