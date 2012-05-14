package android.mobile.HatfieldHall;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
		
		int [] picDepth = {0 , 0};
		int [] titleDepth = {0 , 1 , 0};
		int [] infoDepth = {0 , 1 , 1};
		
		//ImageView image = (ImageView) expand(line , picDepth);
		TextView title = (TextView) expand(line, titleDepth);
		TextView info = (TextView) expand(line, infoDepth);
		
		
		title.setText(this.list.get(position).name);
		info.setText(this.list.get(position).dates);
		
		return line;
	}

	private View expand(LinearLayout root, int[] depth) {
		for (int i = 0; i < depth.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depth[i]);
		}
		return  root.getChildAt(depth[depth.length - 1]);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}
}
