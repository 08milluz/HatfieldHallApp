package android.mobile.HatfieldHall;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowsListAdapter extends BaseAdapter {
	private ArrayList<Event> list;
	private Context context;
	private LayoutInflater inflater;

	public ShowsListAdapter(ArrayList<Event> showsList, Context c) {
		this.list = showsList;
		this.context = c;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout line = (LinearLayout) inflater.inflate(
				R.layout.show_entry, null);

		int[] titleDepth = { 0, 1, 0 };
		int[] infoDepth = { 0, 1, 1 };
		int[] picDepth = { 0, 0 };
		int[] buttonDepth = { 0, 1, 2 };

		TextView title = (TextView) expand(line, titleDepth);
		TextView info = (TextView) expand(line, infoDepth);
		ImageView image = (ImageView) expand(line, picDepth);
		Button buyTickets = (Button) expand(line, buttonDepth);

		final String name = this.list.get(position).name;
		title.setText(this.list.get(position).name);
		info.setText(this.list.get(position).dates);
		
		Drawable drawable = LoadImageFromWeb("http://hatfieldhall.com/images/featured/Phantom_480x320.png");
		Bitmap d = ((BitmapDrawable)drawable).getBitmap();
	    Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, 128, 128, false);
		Log.d("ali", this.list.get(position).link);
		image.setImageBitmap(bitmapOrig);

		buyTickets.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				Log.d("testMail", name);
				HomeActivity home = (HomeActivity) context;
				home.sendEmail(name);

			}
		});

		return line;
	}

	private View expand(LinearLayout root, int[] depth) {
		for (int i = 0; i < depth.length - 1; i++) {
			root = (LinearLayout) root.getChildAt(depth[i]);
		}
		return root.getChildAt(depth[depth.length - 1]);
	}

	public long getItemId(int i) {
		return i;
	}

	private Drawable LoadImageFromWeb(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

}
