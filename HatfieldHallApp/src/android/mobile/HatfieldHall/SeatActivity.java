/* Implements a Seat Finder based upon the Hatfield Hall Floor Plan
 * Developer:  Caleb Drake
 */

package android.mobile.HatfieldHall;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class SeatActivity extends Activity{
	
    // These matrices are used to determine where to move the seat marker
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();


    // If a users seat is incorrect, the seat marker will move to the top left of the floor-map
    private static final Point NullPoint = new Point(0,0);

    private EditText numBox; 		//Box user inputs seat number
    private Spinner rows;			//List of Seat rows [A-BE]
    private Button searchButton;	//Button used to search for the user's seat
    private Bitmap mBitmap;			//Seat Marker
	private ImageView floorMap;		//Hatfield Hall Floor Map
	private int seatNumber;			//Fetched integer value of the user's seat number
	private String seatRow;			//Fetched string value of the user's seat row 
	private Point seatCoord;		//Native Point coordinate of user's seat  
	
	private HashMap<String, Point> seat;	//Hash-Map of all the seat locations in Hatfield Hall  

	//Initializes the SeatActivity & contains the Seat-Finding algorithm
	//COMPLEXITY RATING:	5
	//CODE QUALITY:			100
	public void onCreate(Bundle savedInstanceState) {
		
		//Initializing Seat Finding App and it's components
	       super.onCreate(savedInstanceState);
	       
	       //Setting variables to their counterparts within the SeatActivity Layout  
	       setContentView(R.layout.seat);
	       numBox= (EditText) findViewById(R.id.seat_num);
	       rows = (Spinner) findViewById( R.id.rowSpinner );
	       searchButton = (Button) findViewById(R.id.seatSearch);
	       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rows, android.R.layout.simple_spinner_item );
	       adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
	       rows.setAdapter( adapter );
	       BitmapFactory.Options options = new BitmapFactory.Options();
	       options.inDither= false;       
	       options.inInputShareable=true;
	       options.inTempStorage=new byte[8*1024];
	       floorMap = (ImageView) findViewById(R.id.floorMap);
	       mBitmap =BitmapFactory.decodeResource(this.getResources(),R.drawable.seat_marker, options);
	       
	       floorMap.setImageBitmap(mBitmap);	//Layers the seat marker on-top of the floor-map
	       	       
	       floorMap.setScaleType(ImageView.ScaleType.MATRIX);	//Sets the scaling method for drawing to the floor map [i.e. sizing the seat marker correctly]
	       seat = new HashMap<String, Point>();
	       seatCoord = new Point();
	       seatMappingInit();
	       
	       //Sets event which occurs when the Search Button is clicked
	         searchButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 seatRow=rows.getSelectedItem().toString();		//Fetches seat row
	                 
	                 //Attempts to fetch the integer value of the seat number from the numBox
	                 try {
	                	 seatNumber=Integer.parseInt(numBox.getText().toString());

	                 } 
	                 //If numBox is null or unable to be parsed to an integer, then the seat number is set to zero.
	                 catch (Exception e) {
	                	 seatNumber = 0;
	                 }
	                 
	                 //If a seat row & number match an existing seat, that seat's coordinate is fetched;
	                 //otherwise, the coordinate is set to (0,0) [The top-left corner of the screen.
	                 seatCoord = getSeat();
	                 
	            	 matrix.set(savedMatrix);
	            	 
	            	 //Scales seat coordinate with respect to the floor map 
	                 matrix.setTranslate((float)floorMap.getWidth()*seatCoord.x/606, (float)floorMap.getHeight()*seatCoord.y/720);
	                 
	                 //if the queried seat does not exist, an alert is displayed making a suggestion to change the seat values  
	                 if (seatCoord.equals(NullPoint))	showAlert(getString(R.string.seat_error));
	                 
	                 //else upon clicking the search button, the digital keyboard will be minimized
	                 else{InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                 imm.hideSoftInputFromWindow(numBox.getWindowToken(), 0);
	                 }

	             // Performs the transformation
		             floorMap.setImageMatrix(matrix);
	             }
	           
	         });
	       

	        



	 }
	
	//Creates an pop-up alert which displays a message 
	//COMPLEXITY RATING:	3
	//CODE QUALITY:		100
	private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                     SeatActivity.this);
        builder.setMessage(message).setCancelable(true)
                     .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                   dialog.dismiss();
                            }
                     });
        AlertDialog alert = builder.create();
        alert.show();
	}


	 
	 //@Override
	 //COMPLEXITY RATING:	1
	 //CODE QUALITY:		100
     public void onLowMemory(){
     Log.e("low memory", "low");  
      System.gc();
    }
     
 
     //@Override
     //COMPLEXITY RATING:	1
     //CODE QUALITY:		100
	public void onPause()
	{
		super.onPause();
		System.gc();
	}
       
     //@Override
     //COMPLEXITY RATING:	1
     //CODE QUALITY:		100
     protected void onResume()
     {
         super.onResume();
         System.gc();            
     }
     
     //@Override
	 //COMPLEXITY RATING:	1
	 //CODE QUALITYgg:		100
	protected void onDestroy() {
		super.onDestroy();
		System.gc();

		floorMap.setImageBitmap(null); 
		mBitmap.recycle();
	}
     
     //COMPLEXITY RATING:	1
     //CODE QUALITY:		100
	public EditText getNumBox(){
		return numBox;
	}
     
     //COMPLEXITY RATING:	1
     //CODE QUALITY:		100
	public Spinner getRows(){
		return rows;
	}
     
     //COMPLEXITY RATING:	1
     //CODE QUALITY:		100
	public Button getSearchButton(){
		return searchButton;
	}
     
     //COMPLEXITY RATING:	1
     //CODE QUALITY:		100
	public Bitmap getMBitmap(){
		return mBitmap;
	}
     
    //COMPLEXITY RATING:	1
    //CODE QUALITY:		100
 	public ImageView getFloorMap(){
 		return floorMap;
 	}
 	
 	//COMPLEXITY RATING:	1
 	//CODE QUALITY:		100
 	public int getSeatNumber(){
 		return seatNumber;
 	}
 	
 	
 	//COMPLEXITY RATING:	1
 	//CODE QUALITY:		100
 	public String getSeatRow(){
 		return seatRow;
 	}
 	
 	
 	//If a seat row & number match an existing seat, that seat's coordinate is fetched;
    //otherwise, the coordinate is set to (0,0) [The top-left corner of the screen.
 	//COMPLEXITY RATING:	1
 	//CODE QUALITY:		100
 	public Point getSeat(){
 		return (seat.containsKey(seatRow+seatNumber)) ?	(seat.get(seatRow+seatNumber)):NullPoint;
 	}
 	
 	
 	//Takes-in a string value as opposed to utilizing the global values
 	//If a seat row & number match an existing seat, that seat's coordinate is fetched;
    //otherwise, the coordinate is set to (0,0) [The top-left corner of the screen.
 	//**This version of getSeat is primarily used for testing purposes; however, may be used in place of the latter function.
 	//COMPLEXITY RATING:	1
 	//CODE QUALITY:		100
 	public Point getSeat(String key){
 		return (seat.containsKey(key)) ?	(seat.get(key)):NullPoint;
 	}
 	
 	//initializes native seat locations
 	//COMPLEXITY RATING:	1
 	//CODE QUALITY:		95
 	public void seatMappingInit(){
 		////MAIN FLOOR////
 		//Row A
 		seat.put("A103", new Point(460,651));
 		seat.put("A102", new Point(448,651));
 		seat.put("A101", new Point(435,651));
 		
 		seat.put("A1", new Point(389,651));
 		seat.put("A2", new Point(377,651));
 		seat.put("A3", new Point(365,651));
 		seat.put("A4", new Point(353,651));
 		seat.put("A5", new Point(341,651));
 		seat.put("A6", new Point(329,651));
 		seat.put("A7", new Point(317,651));
 		seat.put("A8", new Point(306,651));
 		seat.put("A9", new Point(294,651));
 		seat.put("A10", new Point(282,651));
 		seat.put("A11", new Point(271,651));
 		seat.put("A12", new Point(259,651));
 		seat.put("A13", new Point(246,651));
 		seat.put("A14", new Point(235,651));
 		seat.put("A15", new Point(223,651));
 		seat.put("A16", new Point(211,651));
 		
 		seat.put("A201", new Point(162,651));
 		seat.put("A202", new Point(151,651));
 		seat.put("A203", new Point(139,651));
 		
 		
 		//Row B
 		seat.put("B103", new Point(467,625));
 		seat.put("B102", new Point(455,625));
 		seat.put("B101", new Point(443,625));
 		
 		seat.put("B1", new Point(393,625));
 		seat.put("B2", new Point(381,625));
 		seat.put("B3", new Point(370,625));
 		seat.put("B4", new Point(358,625));
 		seat.put("B5", new Point(346,625));
 		seat.put("B6", new Point(334,625));
 		seat.put("B7", new Point(322,625));
 		seat.put("B8", new Point(310,625));
 		seat.put("B9", new Point(298,625));
 		seat.put("B10", new Point(286,625));
 		seat.put("B11", new Point(275,625));
 		seat.put("B12", new Point(262,625));
 		seat.put("B13", new Point(251,625));
 		seat.put("B14", new Point(239,625));
 		seat.put("B15", new Point(228,625));
 		seat.put("B16", new Point(216,625));
 		seat.put("B17", new Point(204,625));
 		
 		seat.put("B201", new Point(155,625));
 		seat.put("B202", new Point(143,625));
 		seat.put("B203", new Point(131,625));
 		
 		
 		//Row C
 		seat.put("C103", new Point(473,598));
 		seat.put("C102", new Point(462,598));
 		seat.put("C101", new Point(449,598));
 		
 		seat.put("C1", new Point(400,598));
 		seat.put("C2", new Point(389,598));
 		seat.put("C3", new Point(377,598));
 		seat.put("C4", new Point(365,598));
 		seat.put("C5", new Point(353,598));
 		seat.put("C6", new Point(341,598));
 		seat.put("C7", new Point(330,598));
 		seat.put("C8", new Point(318,598));
 		seat.put("C9", new Point(306,598));
 		seat.put("C10", new Point(294,598));
 		seat.put("C11", new Point(282,598));
 		seat.put("C12", new Point(271,598));
 		seat.put("C13", new Point(259,598));
 		seat.put("C14", new Point(246,598));
 		seat.put("C15", new Point(235,598));
 		seat.put("C16", new Point(223,598));
 		seat.put("C17", new Point(211,598));
 		seat.put("C18", new Point(199,598));
 		
 		seat.put("C201", new Point(149,598));
 		seat.put("C202", new Point(137,598));
 		seat.put("C203", new Point(125,598));
 		
 		
 		//Row D
 		seat.put("D103", new Point(480,572));
 		seat.put("D102", new Point(468,572));
 		seat.put("D101", new Point(456,572));
 		
 		seat.put("D1", new Point(405,572));
 		seat.put("D2", new Point(393,572));
 		seat.put("D3", new Point(381,572));
 		seat.put("D4", new Point(370,572));
 		seat.put("D5", new Point(358,572));
 		seat.put("D6", new Point(346,572));
 		seat.put("D7", new Point(334,572));
 		seat.put("D8", new Point(322,572));
 		seat.put("D9", new Point(310,572));
 		seat.put("D10", new Point(298,572));
 		seat.put("D11", new Point(286,572));
 		seat.put("D12", new Point(275,572));
 		seat.put("D13", new Point(262,572));
 		seat.put("D14", new Point(251,572));
 		seat.put("D15", new Point(239,572));
 		seat.put("D16", new Point(228,572));
 		seat.put("D17", new Point(216,572));
 		seat.put("D18", new Point(204,572));
 		seat.put("D19", new Point(192,572));
 		
 		seat.put("D201", new Point(142,572));
 		seat.put("D202", new Point(130,572));
 		seat.put("D203", new Point(118,572));
 		
 		
 		//Row E
 		seat.put("E104", new Point(497,546));
 		seat.put("E103", new Point(485,546));
 		seat.put("E102", new Point(473,546));
 		seat.put("E101", new Point(462,546));
 		
 		seat.put("E1", new Point(411,546));
 		seat.put("E2", new Point(400,546));
 		seat.put("E3", new Point(389,546));
 		seat.put("E4", new Point(377,546));
 		seat.put("E5", new Point(365,546));
 		seat.put("E6", new Point(353,546));
 		seat.put("E7", new Point(341,546));
 		seat.put("E8", new Point(330,546));
 		seat.put("E9", new Point(318,546));
 		seat.put("E10", new Point(306,546));
 		seat.put("E11", new Point(294,546));
 		seat.put("E12", new Point(282,546));
 		seat.put("E13", new Point(271,546));
 		seat.put("E14", new Point(259,546));
 		seat.put("E15", new Point(246,546));
 		seat.put("E16", new Point(235,546));
 		seat.put("E17", new Point(223,546));
 		seat.put("E18", new Point(211,546));
 		seat.put("E19", new Point(199,546));
 		seat.put("E20", new Point(187,546));
 		
 		seat.put("E201", new Point(137,546));
 		seat.put("E202", new Point(125,546));
 		seat.put("E203", new Point(114,546));
 		seat.put("E204", new Point(102,546));
 		
 		
 		//Row F
 		seat.put("F104", new Point(506,521));
 		seat.put("F103", new Point(493,521));
 		seat.put("F102", new Point(480,521));
 		seat.put("F101", new Point(468,521));
 		
 		seat.put("F1", new Point(418,521));
 		seat.put("F2", new Point(405,521));
 		seat.put("F3", new Point(393,521));
 		seat.put("F4", new Point(381,521));
 		seat.put("F5", new Point(370,521));
 		seat.put("F6", new Point(358,521));
 		seat.put("F7", new Point(346,521));
 		seat.put("F8", new Point(334,521));
 		seat.put("F9", new Point(322,521));
 		seat.put("F10", new Point(310,521));
 		seat.put("F11", new Point(298,521));
 		seat.put("F12", new Point(286,521));
 		seat.put("F13", new Point(275,521));
 		seat.put("F14", new Point(262,521));
 		seat.put("F15", new Point(251,521));
 		seat.put("F16", new Point(239,521));
 		seat.put("F17", new Point(228,521));
 		seat.put("F18", new Point(216,521));
 		seat.put("F19", new Point(204,521));
 		seat.put("F20", new Point(192,521));
 		seat.put("F21", new Point(181,521));
 		
 		seat.put("F201", new Point(130,521));
 		seat.put("F202", new Point(118,521));
 		seat.put("F203", new Point(106,521));
 		seat.put("F204", new Point(94,521));
 		
 		
 		//Row G
 		seat.put("G104", new Point(509,496));
 		seat.put("G103", new Point(497,496));
 		seat.put("G102", new Point(485,496));
 		seat.put("G101", new Point(473,496));
 		 		
 		seat.put("G1", new Point(423,496));
 		seat.put("G1", new Point(411,496));
 		seat.put("G3", new Point(400,496));
 		seat.put("G4", new Point(389,496));
 		seat.put("G5", new Point(377,496));
 		seat.put("G6", new Point(365,496));
 		seat.put("G7", new Point(353,496));
 		seat.put("G8", new Point(341,496));
 		seat.put("G9", new Point(330,496));
 		seat.put("G10", new Point(318,496));
 		seat.put("G11", new Point(306,496));
 		seat.put("G12", new Point(294,496));
 		seat.put("G13", new Point(282,496));
 		seat.put("G14", new Point(271,496));
 		seat.put("G15", new Point(259,496));
 		seat.put("G16", new Point(246,496));
 		seat.put("G17", new Point(235,496));
 		seat.put("G18", new Point(223,496));
 		seat.put("G19", new Point(211,496));
 		seat.put("G20", new Point(199,496));
 		seat.put("G21", new Point(187,496));
 		seat.put("G22", new Point(175,496));
 		
 		seat.put("G201", new Point(122,496));
 		seat.put("G202", new Point(110,496));
 		seat.put("G203", new Point(99,496));
 		seat.put("G204", new Point(86,496));
 		
 		
 		//Row H
 		seat.put("H1", new Point(430,469));
 		seat.put("H2", new Point(418,469));
 		seat.put("H3", new Point(405,469));
 		seat.put("H4", new Point(393,469));
 		seat.put("H5", new Point(381,469));
 		seat.put("H6", new Point(370,469));
 		seat.put("H7", new Point(358,469));
 		seat.put("H8", new Point(346,469));
 		seat.put("H9", new Point(334,469));
 		seat.put("H10", new Point(322,469));
 		seat.put("H11", new Point(310,469));
 		seat.put("H12", new Point(298,469));
 		seat.put("H13", new Point(286,469));
 		seat.put("H14", new Point(275,469));
 		seat.put("H15", new Point(262,469));
 		seat.put("H16", new Point(251,469));
 		seat.put("H17", new Point(239,469));
 		seat.put("H18", new Point(228,469));
 		seat.put("H19", new Point(216,469));
 		seat.put("H20", new Point(204,469));
 		seat.put("H21", new Point(192,469));
 		seat.put("H22", new Point(181,469));
 		seat.put("H23", new Point(169,469));
 		
 		
 		//Row J
 		seat.put("J1", new Point(423,431));
 		seat.put("J1", new Point(411,431));
 		seat.put("J3", new Point(400,431));
 		seat.put("J4", new Point(389,431));
 		seat.put("J5", new Point(377,431));
 		seat.put("J6", new Point(365,431));
 		seat.put("J7", new Point(353,431));
 		seat.put("J8", new Point(341,431));
 		seat.put("J9", new Point(330,431));
 		seat.put("J10", new Point(318,431));
 		seat.put("J11", new Point(306,431));
 		seat.put("J12", new Point(294,431));
 		seat.put("J13", new Point(282,431));
 		seat.put("J14", new Point(271,431));
 		seat.put("J15", new Point(259,431));
 		seat.put("J16", new Point(246,431));
 		seat.put("J17", new Point(235,431));
 		seat.put("J18", new Point(223,431));
 		seat.put("J19", new Point(211,431));
 		seat.put("J20", new Point(199,431));
 		seat.put("J21", new Point(187,431));
 		seat.put("J22", new Point(175,431));
 		
 		
 		//Row K
 		seat.put("K107", new Point(509,408));
 		seat.put("K106", new Point(497,408));
 		seat.put("K105", new Point(485,408));
 		seat.put("K104", new Point(473,408));
 		seat.put("K103", new Point(509,408));
 		seat.put("K102", new Point(497,408));
 		seat.put("K101", new Point(485,408));
 		
 		seat.put("K1", new Point(423,404));
 		seat.put("K1", new Point(411,404));
 		seat.put("K3", new Point(400,404));
 		seat.put("K4", new Point(389,404));
 		seat.put("K5", new Point(377,404));
 		seat.put("K6", new Point(365,404));
 		seat.put("K7", new Point(353,404));
 		seat.put("K8", new Point(341,404));
 		seat.put("K9", new Point(330,404));
 		seat.put("K10", new Point(318,404));
 		seat.put("K11", new Point(306,404));
 		seat.put("K12", new Point(294,404));
 		seat.put("K13", new Point(282,404));
 		seat.put("K14", new Point(271,404));
 		seat.put("K15", new Point(259,404));
 		seat.put("K16", new Point(246,404));
 		seat.put("K17", new Point(235,404));
 		seat.put("K18", new Point(223,404));
 		seat.put("K19", new Point(211,404));
 		seat.put("K20", new Point(199,404));
 		seat.put("K21", new Point(187,404));
 		seat.put("K22", new Point(175,404));
 		
 		seat.put("K201", new Point(106,404));
 		seat.put("K202", new Point(94,404));
 		seat.put("K203", new Point(82,404));
 		seat.put("K204", new Point(70,404));
 		seat.put("K205", new Point(58,404));
 		seat.put("K206", new Point(46,404));
 		seat.put("K207", new Point(34,404));
 		
 		
 		//Row L
 		seat.put("L107", new Point(509,381));
 		seat.put("L106", new Point(497,381));
 		seat.put("L105", new Point(485,381));
 		seat.put("L104", new Point(473,381));
 		seat.put("L103", new Point(509,381));
 		seat.put("L102", new Point(497,381));
 		seat.put("L101", new Point(485,381));
 		
 		seat.put("L1", new Point(423,372));
 		seat.put("L1", new Point(411,372));
 		seat.put("L3", new Point(400,372));
 		seat.put("L4", new Point(389,372));
 		seat.put("L5", new Point(377,372));
 		seat.put("L6", new Point(365,372));
 		seat.put("L7", new Point(353,372));
 		seat.put("L8", new Point(341,372));
 		seat.put("L9", new Point(330,372));
 		seat.put("L10", new Point(318,372));
 		seat.put("L11", new Point(306,372));
 		seat.put("L12", new Point(294,372));
 		seat.put("L13", new Point(282,372));
 		seat.put("L14", new Point(271,372));
 		seat.put("L15", new Point(259,372));
 		seat.put("L16", new Point(246,372));
 		seat.put("L17", new Point(235,372));
 		seat.put("L18", new Point(223,372));
 		seat.put("L19", new Point(211,372));
 		seat.put("L20", new Point(199,372));
 		seat.put("L21", new Point(187,372));
 		seat.put("L22", new Point(175,372));
 		
 		seat.put("L201", new Point(106,378));
 		seat.put("L202", new Point(94,378));
 		seat.put("L203", new Point(82,378));
 		seat.put("L204", new Point(70,378));
 		seat.put("L205", new Point(58,378));
 		seat.put("L206", new Point(46,378));
 		seat.put("L207", new Point(34,378));
 		
 		
 		//Row M
 		seat.put("M107", new Point(509,348));
 		seat.put("M106", new Point(497,348));
 		seat.put("M105", new Point(485,348));
 		seat.put("M104", new Point(473,348));
 		seat.put("M103", new Point(509,348));
 		seat.put("M102", new Point(497,348));
 		seat.put("M101", new Point(485,348));
 		
 		seat.put("M1", new Point(423,146));
 		seat.put("M1", new Point(411,146));
 		seat.put("M3", new Point(400,146));
 		seat.put("M4", new Point(389,146));
 		seat.put("M5", new Point(377,146));
 		seat.put("M6", new Point(365,146));
 		seat.put("M7", new Point(353,146));
 		seat.put("M8", new Point(341,146));
 		seat.put("M9", new Point(330,146));
 		seat.put("M10", new Point(318,146));
 		seat.put("M11", new Point(306,146));
 		seat.put("M12", new Point(294,146));
 		seat.put("M13", new Point(282,146));
 		seat.put("M14", new Point(271,146));
 		seat.put("M15", new Point(259,146));
 		seat.put("M16", new Point(246,146));
 		seat.put("M17", new Point(235,146));
 		seat.put("M18", new Point(223,146));
 		seat.put("M19", new Point(211,146));
 		seat.put("M20", new Point(199,146));
 		seat.put("M21", new Point(187,146));
 		seat.put("M22", new Point(175,146));
 		
 		seat.put("M201", new Point(106,146));
 		seat.put("M202", new Point(94,146));
 		seat.put("M203", new Point(82,146));
 		seat.put("M204", new Point(70,146));
 		seat.put("M205", new Point(58,146));
 		seat.put("M206", new Point(46,146));
 		seat.put("M207", new Point(34,146));
 		
 		
 		//Row N
 		seat.put("N107", new Point(509,321));
 		seat.put("N106", new Point(497,321));
 		seat.put("N105", new Point(485,321));
 		seat.put("N104", new Point(473,321));
 		seat.put("N103", new Point(509,321));
 		seat.put("N102", new Point(497,321));
 		seat.put("N101", new Point(485,321));
 		
 		seat.put("N1", new Point(377,311));
 		seat.put("N2", new Point(365,311));
 		seat.put("N3", new Point(353,311));
 		seat.put("N4", new Point(341,311));
 		seat.put("N5", new Point(330,311));
 		seat.put("N6", new Point(318,311));
 		seat.put("N7", new Point(306,311));
 		seat.put("N8", new Point(294,311));
 		seat.put("N9", new Point(282,311));
 		seat.put("N10", new Point(271,311));
 		seat.put("N11", new Point(259,311));
 		seat.put("N12", new Point(246,311));
 		seat.put("N13", new Point(235,311));
 		seat.put("N14", new Point(223,311));
 		
 		seat.put("N201", new Point(106,319));
 		seat.put("N202", new Point(94,319));
 		seat.put("N203", new Point(82,319));
 		seat.put("N204", new Point(70,319));
 		seat.put("N205", new Point(58,319));
 		seat.put("N206", new Point(46,319));
 		seat.put("N207", new Point(34,319));
 		
 		
 		//Row O
 		seat.put("O106", new Point(497,294));
 		seat.put("O105", new Point(485,294));
 		seat.put("O104", new Point(473,294));
 		seat.put("O103", new Point(509,294));
 		seat.put("O102", new Point(497,294));
 		seat.put("O101", new Point(485,294));
 		
 		seat.put("O1", new Point(381,285));
 		seat.put("O2", new Point(370,285));
 		seat.put("O3", new Point(358,285));
 		seat.put("O4", new Point(146,285));
 		seat.put("O5", new Point(334,285));
 		seat.put("O6", new Point(323,285));
 		seat.put("O7", new Point(311,285));
 		seat.put("O8", new Point(298,285));
 		seat.put("O9", new Point(287,285));
 		seat.put("O10", new Point(275,285));
 		seat.put("O11", new Point(264,285));
 		seat.put("O12", new Point(251,285));
 		seat.put("O13", new Point(239,285));
 		seat.put("O14", new Point(227,285));
 		seat.put("O15", new Point(215,285));
 		
 		seat.put("O201", new Point(106,290));
 		seat.put("O202", new Point(94,290));
 		seat.put("O203", new Point(82,290));
 		seat.put("O204", new Point(70,290));
 		seat.put("O205", new Point(58,290));
 		seat.put("O206", new Point(46,290));
 		 		
 		
 		//Row P
 		seat.put("P106", new Point(497,266));
 		seat.put("P105", new Point(485,266));
 		seat.put("P104", new Point(473,266));
 		seat.put("P103", new Point(509,266));
 		seat.put("P102", new Point(497,266));
 		seat.put("P101", new Point(485,266));
 		
 		seat.put("P1", new Point(389,259));
 		seat.put("P2", new Point(377,259));
 		seat.put("P3", new Point(365,259));
 		seat.put("P4", new Point(353,259));
 		seat.put("P5", new Point(341,259));
 		seat.put("P6", new Point(330,259));
 		seat.put("P7", new Point(318,259));
 		seat.put("P8", new Point(306,259));
 		seat.put("P9", new Point(294,259));
 		seat.put("P10", new Point(282,259));
 		seat.put("P11", new Point(271,259));
 		seat.put("P12", new Point(259,259));
 		seat.put("P13", new Point(246,259));
 		seat.put("P14", new Point(235,259));
 		seat.put("P15", new Point(223,259));
 		seat.put("P16", new Point(211,259));
 		
 		seat.put("P201", new Point(106,264));
 		seat.put("P202", new Point(94,264));
 		seat.put("P203", new Point(82,264));
 		seat.put("P204", new Point(70,264));
 		seat.put("P205", new Point(58,264));
 		seat.put("P206", new Point(46,264));
 		 		
 		
 		
 		
 		////BALCONY////
 		//Row BA
 		seat.put("BA1", new Point(471,146));
 		seat.put("BA2", new Point(459,146));
 		seat.put("BA3", new Point(447,146));
 		seat.put("BA4", new Point(435,146));
 		seat.put("BA5", new Point(423,146));
 		seat.put("BA6", new Point(411,146));
 		seat.put("BA7", new Point(400,146));
 		seat.put("BA8", new Point(389,146));
 		seat.put("BA9", new Point(377,146));
 		seat.put("BA10", new Point(365,146));
 		seat.put("BA11", new Point(353,146));
 		seat.put("BA12", new Point(341,146));
 		seat.put("BA13", new Point(330,146));
 		seat.put("BA14", new Point(318,146));
 		seat.put("BA15", new Point(306,146));
 		seat.put("BA16", new Point(294,146));
 		seat.put("BA17", new Point(282,146));
 		seat.put("BA18", new Point(271,146));
 		seat.put("BA19", new Point(259,146));
 		seat.put("BA20", new Point(246,146));
 		seat.put("BA21", new Point(235,146));
 		seat.put("BA22", new Point(223,146));
 		seat.put("BA23", new Point(211,146));
 		seat.put("BA24", new Point(199,146));
 		seat.put("BA25", new Point(187,146));
 		seat.put("BA26", new Point(175,146));
 		seat.put("BA27", new Point(163,146));
 		seat.put("BA28", new Point(151,146));
 		seat.put("BA29", new Point(139,146));
 		seat.put("BA30", new Point(127,146));
 		
 		
 		//Row BB
 		seat.put("BB1", new Point(478,119));
 		seat.put("BB2", new Point(465,119));
 		seat.put("BB3", new Point(454,119));
 		seat.put("BB4", new Point(443,119));
 		seat.put("BB5", new Point(430,119));
 		seat.put("BB6", new Point(418,119));
 		seat.put("BB7", new Point(405,119));
 		seat.put("BB8", new Point(393,119));
 		seat.put("BB9", new Point(381,119));
 		seat.put("BB10", new Point(370,119));
 		seat.put("BB11", new Point(358,119));
 		seat.put("BB12", new Point(346,119));
 		seat.put("BB13", new Point(334,119));
 		seat.put("BB14", new Point(322,119));
 		seat.put("BB15", new Point(310,119));
 		seat.put("BB16", new Point(298,119));
 		seat.put("BB17", new Point(286,119));
 		seat.put("BB18", new Point(275,119));
 		seat.put("BB19", new Point(262,119));
 		seat.put("BB20", new Point(251,119));
 		seat.put("BB21", new Point(239,119));
 		seat.put("BB22", new Point(228,119));
 		seat.put("BB23", new Point(216,119));
 		seat.put("BB24", new Point(204,119));
 		seat.put("BB25", new Point(192,119));
 		seat.put("BB26", new Point(181,119));
 		seat.put("BB27", new Point(169,119));
 		seat.put("BB28", new Point(158,119));
 		seat.put("BB29", new Point(147,119));
 		seat.put("BB30", new Point(135,119));
 		seat.put("BB31", new Point(123,119));
 		
 		
 		//Row BC
 		seat.put("BC1", new Point(471,90));
 		seat.put("BC2", new Point(459,90));
 		seat.put("BC3", new Point(447,90));
 		seat.put("BC4", new Point(435,90));
 		seat.put("BC5", new Point(423,90));
 		seat.put("BC6", new Point(411,90));
 		seat.put("BC7", new Point(400,90));
 		seat.put("BC8", new Point(389,90));
 		seat.put("BC9", new Point(377,90));
 		seat.put("BC10", new Point(365,90));
 		seat.put("BC11", new Point(353,90));
 		seat.put("BC12", new Point(341,90));
 		seat.put("BC13", new Point(330,90));
 		seat.put("BC14", new Point(318,90));
 		seat.put("BC15", new Point(306,90));
 		seat.put("BC16", new Point(294,90));
 		seat.put("BC17", new Point(282,90));
 		seat.put("BC18", new Point(271,90));
 		seat.put("BC19", new Point(259,90));
 		seat.put("BC20", new Point(246,90));
 		seat.put("BC21", new Point(235,90));
 		seat.put("BC22", new Point(223,90));
 		seat.put("BC23", new Point(211,90));
 		seat.put("BC24", new Point(199,90));
 		seat.put("BC25", new Point(187,90));
 		seat.put("BC26", new Point(175,90));
 		seat.put("BC27", new Point(163,90));
 		seat.put("BC28", new Point(151,90));
 		seat.put("BC29", new Point(139,90));
 		seat.put("BC30", new Point(127,90));
 		
 		
 		//Row BD
 		seat.put("BD1", new Point(478,64));
 		seat.put("BD2", new Point(465,64));
 		seat.put("BD3", new Point(454,64));
 		seat.put("BD4", new Point(443,64));
 		seat.put("BD5", new Point(430,64));
 		seat.put("BD6", new Point(418,64));
 		seat.put("BD7", new Point(405,64));
 		seat.put("BD8", new Point(393,64));
 		seat.put("BD9", new Point(381,64));
 		seat.put("BD10", new Point(370,64));
 		seat.put("BD11", new Point(358,64));
 		seat.put("BD12", new Point(346,64));
 		seat.put("BD13", new Point(334,64));
 		seat.put("BD14", new Point(322,64));
 		seat.put("BD15", new Point(310,64));
 		seat.put("BD16", new Point(298,64));
 		seat.put("BD17", new Point(286,64));
 		seat.put("BD18", new Point(275,64));
 		seat.put("BD19", new Point(262,64));
 		seat.put("BD20", new Point(251,64));
 		seat.put("BD21", new Point(239,64));
 		seat.put("BD22", new Point(228,64));
 		seat.put("BD23", new Point(216,64));
 		seat.put("BD24", new Point(204,64));
 		seat.put("BD25", new Point(192,64));
 		seat.put("BD26", new Point(181,64));
 		seat.put("BD27", new Point(169,64));
 		seat.put("BD28", new Point(158,64));
 		seat.put("BD29", new Point(147,64));
 		seat.put("BD30", new Point(135,64));
 		seat.put("BD31", new Point(123,64));
 		
 		
 		//Row BE
 		seat.put("BE1", new Point(471,35));
 		seat.put("BE2", new Point(459,35));
 		seat.put("BE3", new Point(447,35));
 		seat.put("BE4", new Point(435,35));
 		seat.put("BE5", new Point(423,35));
 		seat.put("BE6", new Point(411,35));
 		seat.put("BE7", new Point(400,35));
 		seat.put("BE8", new Point(389,35));
 		seat.put("BE9", new Point(377,35));
 		seat.put("BE10", new Point(365,35));
 		seat.put("BE11", new Point(353,35));
 		seat.put("BE12", new Point(341,35));
 		seat.put("BE13", new Point(330,35));
 		seat.put("BE14", new Point(318,35));
 		seat.put("BE15", new Point(306,35));
 		seat.put("BE16", new Point(294,35));
 		seat.put("BE17", new Point(282,35));
 		seat.put("BE18", new Point(271,35));
 		seat.put("BE19", new Point(259,35));
 		seat.put("BE20", new Point(246,35));
 		seat.put("BE21", new Point(235,35));
 		seat.put("BE22", new Point(223,35));
 		seat.put("BE23", new Point(211,35));
 		seat.put("BE24", new Point(199,35));
 		seat.put("BE25", new Point(187,35));
 		seat.put("BE26", new Point(175,35));
 		seat.put("BE27", new Point(163,35));
 		seat.put("BE28", new Point(151,35));
 		seat.put("BE29", new Point(139,35));
 		seat.put("BE30", new Point(127,35));	
 	
 	}
     
     

}
