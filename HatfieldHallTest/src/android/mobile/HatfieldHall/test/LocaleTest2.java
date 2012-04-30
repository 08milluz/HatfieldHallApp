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
	private Locale locale2;

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
		assertEquals("Téléphone", mActivity.getString(R.string.phone));
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
		assertEquals("ãƒ�ãƒƒãƒˆãƒ•ã‚£ãƒ¼ãƒ«ãƒ‰ãƒ›ãƒ¼ãƒ«", mActivity.getString(R.string.app_name));
	}

	
	public void testJapaneseHome() {
		japaneseLocaleSetUp();
		assertEquals("ãƒ›ãƒ¼ãƒ ", mActivity.getString(R.string.home));
	}

	
	public void testJapaneseFindSeat() {
		japaneseLocaleSetUp();
		assertEquals("ã�¯ã‚·ãƒ¼ãƒˆ", mActivity.getString(R.string.find_seat));
	}

	
	public void testJapaneseVenue() {
		japaneseLocaleSetUp();
		assertEquals("ä¼šå ´", mActivity.getString(R.string.venue));
	}

	
	public void testJapaneseInfo() {
		japaneseLocaleSetUp();
		assertEquals("æƒ…å ±", mActivity.getString(R.string.info));
	}

	
	public void testJapaneseAddress() {
		japaneseLocaleSetUp();
		assertEquals("ã‚¢ãƒ‰ãƒ¬ã‚¹:", mActivity.getString(R.string.address));
	}

	
	public void testJapaneseHallAddress() {
		japaneseLocaleSetUp();
		assertEquals("5500ã‚¦ã‚©ãƒ�ã‚·ãƒ¥ã‚¢ãƒ™ãƒ‹ãƒ¥ãƒ¼ - CM17ã€�ãƒ†ãƒ¬ãƒ›ãƒ¼ãƒˆã€�ã‚¤ãƒ³ãƒ‡ã‚£ã‚¢ãƒŠå·ž47803",
				mActivity.getString(R.string.hall_address));
	}

	
	public void testJapanesePhone() {
		japaneseLocaleSetUp();
		assertEquals("é›»è©±ç•ªå�·:", mActivity.getString(R.string.phone));
	}

	
	public void testJapaneseHallPhone() {
		japaneseLocaleSetUp();
		assertEquals("8128778544", mActivity.getString(R.string.hall_phone));
	}

	
	public void testJapaneseHours() {
		japaneseLocaleSetUp();
		assertEquals("æ™‚é–“:", mActivity.getString(R.string.hours));
	}

	
	public void testJapaneseHallHours() {
		japaneseLocaleSetUp();
		assertEquals(
				"æœˆæ›œæ—¥ã€œé‡‘æ›œæ—¥å�ˆå‰�10æ™‚ - å�ˆå¾Œ5æ™‚ã€�åœŸæ›œæ—¥å�ˆå¾Œ12æ™‚ - å�ˆå¾Œ5æ™‚ã€‚ã‚¤ãƒ™ãƒ³ãƒˆå‰�ã�«2æ™‚é–“ã‚’é–‹ã��ã�¾ã�™ã€‚ãƒ­ãƒ¼ã‚ºãƒ�ãƒ«ãƒžãƒ³ä¼‘æ—¥ã�®ä¼‘æ†©ä¸­ã�«é–‰ã�˜ã�Ÿã€‚",
				mActivity.getString(R.string.hall_hours));
	}
	
	
	public void testJapaneseSeatNumber() {
		japaneseLocaleSetUp();
		assertEquals("åº§å¸­ç•ªå�·",
				mActivity.getString(R.string.seat_number));
	}

	
	public void testJapaneseSearch() {
		japaneseLocaleSetUp();
		assertEquals("æ¤œç´¢", mActivity.getString(R.string.search));
	}


}
