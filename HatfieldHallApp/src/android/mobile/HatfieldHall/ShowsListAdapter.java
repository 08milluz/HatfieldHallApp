package android.mobile.HatfieldHall;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ShowsListAdapter extends BaseAdapter{
	private ArrayList<Event> list;
	private Context context;
	private LayoutInflater inflater;


	public ShowsListAdapter (ArrayList<Event> showsList, Context c){
		this.list = showsList;
		this.context = c;
	}
	
	public int getCount(){
		return this.list.size();
	}
	
	public Object getItem (int position){
		return list.get(position);
	}
	
	public View getView (int position, View convertView, ViewGroup parent){
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout line = (LinearLayout) inflater.inflate(
				R.layout.show_entry, null);
		
		int [] titleDepth = {0 , 1 , 0};
		int [] infoDepth = {0 , 1 , 1};
		int [] picDepth = {0 , 0};
		int [] buttonDepth = {0 , 1 , 2};
		
		//ImageView image = (ImageView) expand(line , picDepth);
		TextView title = (TextView) expand(line, titleDepth);
		TextView info = (TextView) expand(line, infoDepth);
		
		Button buyTickets = (Button) expand (line, buttonDepth);
		
		title.setText(this.list.get(position).name);
		info.setText(this.list.get(position).dates);
		
		buyTickets.setOnClickListener(new View.OnClickListener() {

			
			public void onClick(View arg0) {
				HomeActivity home = (HomeActivity) context;
				home.sendEmail("test");
				
			}});
		
		return line;
	}

	private View expand(LinearLayout root, int[] depth) {
		for (int i = 0; i < depth.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depth[i]);
		}
		return  root.getChildAt(depth[depth.length - 1]);
	}

	
	public long getItemId(int i) {
		return i;
	}
}
