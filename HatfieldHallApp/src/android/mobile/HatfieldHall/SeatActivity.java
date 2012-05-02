package android.mobile.HatfieldHall;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
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
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    public EditText numBox;
    public Spinner rows;
    private Button searchButton;
    private Bitmap mBitmap;
	private ImageView floorMap;
	private int seatNumber;
	public String seatRow;
	public String log = "log";
	private Point seatCoord;
	
//	TODO implement HashMap which which relates seat rows with their available seats.
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
	                 Log.d(log, seat.get(seatRow+seatNumber).toString() + "");
	                 System.out.println(seat.get(seatRow+seatNumber));
	                seatCoord =(seat.get(seatRow+seatNumber));
	             }
	         });
	       
	       BitmapFactory.Options options = new BitmapFactory.Options();
	       options.inDither= false;       
	       options.inInputShareable=true;
	       options.inTempStorage=new byte[8*1024];

	       floorMap = (ImageView) findViewById(R.id.floorMap);
	       mBitmap =BitmapFactory.decodeResource(this.getResources(),R.drawable.floor_map, options);
	        floorMap.setImageBitmap(mBitmap);
	        
	        floorMap.setOnTouchListener(SeatActivity.this);
	        
//	        Canvas c = new Canvas(mBitmap);
//	        c.drawRect(new RectF(seatCoord.x, seatCoord.y, seatCoord.x+9, seatCoord.y+9),new Paint());
	        
	       

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
         floorMap.setScaleType(ImageView.ScaleType.MATRIX);
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
             if (mode == DRAG) {
                 matrix.set(savedMatrix);
                 matrix.postTranslate(event.getX() - start.x,
                         event.getY() - start.y);
             }  else if (mode == ZOOM) {
                 float newDist = spacing(event);
                 Log.d(TAG, "newDist=" + newDist);
                 if (newDist > 10f) {
                     matrix.set(savedMatrix);
                     float scale = newDist / oldDist;
                     matrix.postScale(scale, scale, mid.x, mid.y);
                 }
             }
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
 	public void seatMappingInit(){
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
 	Log.d(log, "done init");	
 	
 	}
     
     

}
