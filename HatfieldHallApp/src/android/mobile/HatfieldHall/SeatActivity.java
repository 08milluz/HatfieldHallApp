package android.mobile.HatfieldHall;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class SeatActivity extends Activity implements OnTouchListener{
	private static final String TAG = "Touch" ;
    // These matrices will be used to move the seat marker
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    public String log = "log";

    // We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    
    // If a users seat is incorrect, the seat marker will move to the top left of the floor-map
    private static final Point NullPoint = new Point(0,0);
    
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private EditText numBox;
    private Spinner rows;
    private Button searchButton;
    private Bitmap mBitmap;
	private ImageView floorMap;
	private int seatNumber;
	private String seatRow;
	private Point seatCoord;
	

	private HashMap<String, Point> seat;  

	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
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
	       floorMap.setImageBitmap(mBitmap);
	       floorMap.setOnTouchListener(SeatActivity.this);
	       
	       floorMap.setScaleType(ImageView.ScaleType.MATRIX);
	       seat = new HashMap<String, Point>();
	       seatCoord = new Point();
	       seatMappingInit();
	       
	         searchButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 seatRow=rows.getSelectedItem().toString();
	                 try {
	                	 seatNumber=Integer.parseInt(numBox.getText().toString());

	                 } 
	                 catch (Exception e) {
	                	 seatNumber = 0;
	                 }
	                 
	                 seatCoord = getSeat();
	             }
	           
	         });
	       

	        

	        
	       

	 }

	 
	 @Override
     public void onLowMemory(){
     Log.e("low memory", "low");  
      System.gc();
    }
  private float spacing(MotionEvent event) {
         float x = event.getX(0) - event.getX(1);
         float y = event.getY(0) - event.getY(1);
         return FloatMath.sqrt(x * x + y * y);
     }
     private void midPoint(PointF point, MotionEvent event) {
         float x = event.getX(0) + event.getX(1);
         float y = event.getY(0) + event.getY(1);
         point.set(x / 2, y / 2);
     }
     @Override
     public boolean onTouch(View v, MotionEvent event) {
         
         // TODO Auto-generated method stub
         ImageView view = (ImageView) v;
         // Dump touch event to log
         dumpEvent(event);
         // Handle touch events here...
         /*spacing(event);
             midPoint(point,event);*/
         switch (event.getAction() & MotionEvent.ACTION_MASK) {
         case MotionEvent.ACTION_DOWN:
             savedMatrix.set(matrix);
             start.set(event.getX(), event.getY());
             Log.d(TAG, "mode=DRAG" );
             mode = DRAG;
             break;
         case MotionEvent.ACTION_UP:
         case MotionEvent.ACTION_POINTER_UP:
             mode = NONE;
             Log.d(TAG, "mode=NONE" );
             break;
             
         case MotionEvent.ACTION_MOVE:
        	 matrix.set(savedMatrix);
        	 Log.d("Size", "Width: "+(float)view.getWidth()*seatCoord.x/606+"\tHeight: "+(float)view.getHeight()*seatCoord.y/720);
             matrix.setTranslate((float)view.getWidth()*seatCoord.x/606, (float)view.getHeight()*seatCoord.y/720);
             
             break;
         case MotionEvent.ACTION_POINTER_DOWN:
             oldDist = spacing(event);
             Log.d(TAG, "oldDist=" + oldDist);
             if (oldDist > 10f) {
                 savedMatrix.set(matrix);
                 midPoint(mid, event);
                 mode = ZOOM;
                 Log.d(TAG, "mode=ZOOM" );
             }
             break;

         }

         // Perform the transformation
         view.setImageMatrix(matrix);
         return true; // indicate event was handled
     }
     private void dumpEvent(MotionEvent event) {
         String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
                 "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
         StringBuilder sb = new StringBuilder();
         int action = event.getAction();
         int actionCode = action & MotionEvent.ACTION_MASK;
         sb.append("event ACTION_" ).append(names[actionCode]);
         if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                 || actionCode == MotionEvent.ACTION_POINTER_UP) {
             sb.append("(pid " ).append(
                     action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
             sb.append(")" );
         }
         sb.append("[" );
         for (int i = 0; i < event.getPointerCount(); i++) {
             sb.append("#" ).append(i);
             sb.append("(pid " ).append(event.getPointerId(i));
             sb.append(")=" ).append((int) event.getX(i));
             sb.append("," ).append((int) event.getY(i));
             if (i + 1 < event.getPointerCount())
                 sb.append(";" );
         }
         sb.append("]" );
         Log.d(TAG, sb.toString());
     }
     @Override
       public void onPause()
       {
         super.onPause();
         System.gc();
       }
     @Override
       protected void onResume()
       {
         super.onResume();
         System.gc();            
       }
     @Override
     protected void onDestroy() {
     super.onDestroy();
     System.gc();

      floorMap.setImageBitmap(null); 
      mBitmap.recycle();
     }
     
     public EditText getNumBox(){
    	 return numBox;
     }
     public Spinner getRows(){
    	 return rows;
     }
     public Button getSearchButton(){
    	 return searchButton;
     }
     public Bitmap getMBitmap(){
    	 return mBitmap;
     }
 	public ImageView getFloorMap(){
 		return floorMap;
 	}
 	public int getSeatNumber(){
 		return seatNumber;
 	}
 	public String getSeatRow(){
 		return seatRow;
 	}
 	public Point getSeat(){
 		return (seat.containsKey(seatRow+seatNumber)) ?	(seat.get(seatRow+seatNumber)):NullPoint;
 	}
 	public Point getSeat(String key){
 		return (seat.containsKey(key)) ?	(seat.get(key)):NullPoint;
 	}
 	public void seatMappingInit(){
 		//Row A
 		seat.put("A0", new Point());
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
 		//NOT COMPLETED!
 		seat.put("B0", new Point());
 		seat.put("B103", new Point(460,625));
 		seat.put("B102", new Point(448,625));
 		seat.put("B101", new Point(435,625));
 		seat.put("B1", new Point(389,625));
 		seat.put("B2", new Point(377,625));
 		seat.put("B3", new Point(365,625));
 		seat.put("B4", new Point(353,625));
 		seat.put("B5", new Point(341,625));
 		seat.put("B6", new Point(329,625));
 		seat.put("B7", new Point(317,625));
 		seat.put("B8", new Point(306,625));
 		seat.put("B9", new Point(294,625));
 		seat.put("B10", new Point(282,625));
 		seat.put("B11", new Point(271,625));
 		seat.put("B12", new Point(259,625));
 		seat.put("B13", new Point(246,625));
 		seat.put("B14", new Point(235,625));
 		seat.put("B15", new Point(223,625));
 		seat.put("B16", new Point(211,625));
 		seat.put("B201", new Point(162,625));
 		seat.put("B202", new Point(151,625));
 		seat.put("B203", new Point(139,625));
 		
 		//Row C
 		//NOT COMPLETED!
 		seat.put("C0", new Point());
 		seat.put("C103", new Point(460,598));
 		seat.put("C102", new Point(448,598));
 		seat.put("C101", new Point(435,598));
 		seat.put("C1", new Point(389,598));
 		seat.put("C2", new Point(377,598));
 		seat.put("C3", new Point(365,598));
 		seat.put("C4", new Point(353,598));
 		seat.put("C5", new Point(341,598));
 		seat.put("C6", new Point(329,598));
 		seat.put("C7", new Point(317,598));
 		seat.put("C8", new Point(306,598));
 		seat.put("C9", new Point(294,598));
 		seat.put("C10", new Point(282,598));
 		seat.put("C11", new Point(271,598));
 		seat.put("C12", new Point(259,598));
 		seat.put("C13", new Point(246,598));
 		seat.put("C14", new Point(235,598));
 		seat.put("C15", new Point(223,598));
 		seat.put("C16", new Point(211,598));
 		seat.put("C201", new Point(162,598));
 		seat.put("C202", new Point(151,598));
 		seat.put("C203", new Point(139,598));
 		
 		//Row D
 		//NOT COMPLETED!
 		seat.put("D0", new Point());
 		seat.put("D103", new Point(460,572));
 		seat.put("D102", new Point(448,572));
 		seat.put("D101", new Point(435,572));
 		seat.put("D1", new Point(389,572));
 		seat.put("D2", new Point(377,572));
 		seat.put("D3", new Point(365,572));
 		seat.put("D4", new Point(353,572));
 		seat.put("D5", new Point(341,572));
 		seat.put("D6", new Point(329,572));
 		seat.put("D7", new Point(317,572));
 		seat.put("D8", new Point(306,572));
 		seat.put("D9", new Point(294,572));
 		seat.put("D10", new Point(282,572));
 		seat.put("D11", new Point(271,572));
 		seat.put("D12", new Point(259,572));
 		seat.put("D13", new Point(246,572));
 		seat.put("D14", new Point(235,572));
 		seat.put("D15", new Point(223,572));
 		seat.put("D16", new Point(211,572));
 		seat.put("D201", new Point(162,572));
 		seat.put("D202", new Point(151,572));
 		seat.put("D203", new Point(139,572));
 	Log.d(log, "done init");	
 	
 	}
     
     

}
