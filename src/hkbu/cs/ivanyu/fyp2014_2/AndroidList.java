package hkbu.cs.ivanyu.fyp2014_2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidList extends SimpleAdapter {
		Context mContext;
		public AndroidList(Context context, int textViewResourceId,
				String[] objects) {
			super(context, null, textViewResourceId, objects, null);
			// TODO Auto-generated constructor stub
			 mContext = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// return super.getView(position, convertView, parent);
			View row = convertView;

			if (row == null) {
				LayoutInflater inflater =  ((Activity)mContext).getLayoutInflater();
				row = inflater.inflate(R.layout.user_layout_no_scroll_view, parent, false);
			}

			TextView bookName = (TextView) row.findViewById(R.id.textView1);
			TextView author = (TextView) row.findViewById(R.id.textView2);
			TextView isbn = (TextView) row.findViewById(R.id.textView3);
			//bookName.setText(month[position]);
			ImageView icon = (ImageView) row.findViewById(R.id.imageView1);

			/*if (month[position] == "December") {
				icon.setImageResource(R.drawable.icon);
			} else {
				icon.setImageResource(R.drawable.icongray);
			}*/

			return row;
		}
	}