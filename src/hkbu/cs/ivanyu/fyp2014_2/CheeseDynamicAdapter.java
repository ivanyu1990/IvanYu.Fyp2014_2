package hkbu.cs.ivanyu.fyp2014_2;

/**
 * Author: alex askerov
 * Date: 9/9/13
 * Time: 10:52 PM
 */

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;  
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.askerov.dynamicgid.BaseDynamicGridAdapter;
import org.askerov.dynamicgid.DynamicGridView;

import com.google.zxing.client.android.CaptureActivity;

import java.util.List;

/**
 * Author: alex askerov Date: 9/7/13 Time: 10:56 PM
 */
public class CheeseDynamicAdapter extends BaseDynamicGridAdapter {
	private String username;
	static Button myButton;
	Activity a;
	public CheeseDynamicAdapter(Context context, List<?> items, int columnCount) {
		super(context, items, columnCount);
		Log.i("asdasd", ((Activity) context).toString());
		username = ((Activity) context).getIntent().getExtras()
				.getString("username");
		a =  ((Activity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheeseViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_grid, null);
			holder = new CheeseViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (CheeseViewHolder) convertView.getTag();
		}
		holder.build(getItem(position).toString());
		return convertView;
	}

	public String niceString(String badString) {
		String temp = "";
		int x = 0;
		for (int i = 0; i < badString.length(); i += 17) {
			if (i + 17 < badString.length()) {
				temp += badString.substring(i, i + 17) + "\n";
			} else {
				temp += badString.substring(i);
			}
			x = i;
		}
		return temp;
	}

	private class CheeseViewHolder {
		private TextView titleText;
		private ImageView image;
		private Button button;

		private CheeseViewHolder(View view) {
			// titleText = (TextView) view.findViewById(R.id.item_title);
			// image = (ImageView) view.findViewById(R.id.item_img);
			button = (Button) view.findViewById(R.id.item_button);
		}

		void build(String title) {
			
			
			if (title.equals("Scan Barcode for Selling your Book")) {
				button.setBackgroundResource(R.drawable.isbn);
			} else if (title.equals("Search your Book")) {
				button.setBackgroundResource(R.drawable.search);
			} else if (title.equals("Second Hand Book")) {
				button.setBackgroundResource(R.drawable.book);
			} else if (title.equals("Find Your Book By District")) {
				button.setBackgroundResource(R.drawable.map);
			} else if (title.equals("Selling Your Book Manually")) {
				button.setBackgroundResource(R.drawable.pen);
			} else {
				int rand = (int)((int)title.toCharArray()[8]) %4;
				Log.i("rand", rand + "");
				if(rand == 0){
					button.setBackgroundResource(R.drawable.abc);
				}else if(rand == 1){
					button.setBackgroundResource(R.drawable.blue);
				}else if(rand == 2){
					button.setBackgroundResource(R.drawable.orange);
				} else {
					button.setBackgroundResource(R.drawable.green);
				}
			}
			if(title.equals("Voice Control")){
				button.setBackgroundResource(R.drawable.voicecontrol);
			}
			
			final String myTitle = title;
			button.setGravity((Gravity.BOTTOM)); // mainMenu
			//button.setLongClickable(true);
			button.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					DynamicGridView g = (DynamicGridView)v.getParent().getParent();
					g.startEditMode();
					((Button)v).setFocusable(true);
					((LinearLayout)v.getParent()).setFocusable(true);
					g.setFocusable(true);
					return true;
				}
				
			});
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String text =myTitle;
					if (text.equals("Scan Barcode for Selling your Book")) {
						LinearLayout ab = ((LinearLayout) arg0.getParent());
						// ((Button)(arg0)).setBackgroundResource(R.drawable.isbn);
						Intent a = new Intent(ab.getContext(),
								CaptureActivity.class).putExtra("username",
								username).putExtra(
								"home",
								new Intent(ab.getContext(), MainActivity.class)
										.putExtra("username", username));
						ab.getContext().startActivity(a);
					} else if (text.equals("Second Hand Book")) {
						// button.setBackgroundResource(R.drawable.book);
						LinearLayout ab = ((LinearLayout) arg0.getParent());
						Intent a = new Intent(ab.getContext(),
								ListOfSecondHandBook.class).putExtra(
								"username", username).putExtra("tag",
								"List Of Second Hand Book");
						ab.getContext().startActivity(a);
					} else if (text.equals("Search your Book")) {
						LinearLayout ab = ((LinearLayout) arg0.getParent());
						Intent a = new Intent(ab.getContext(),
								MainActivity.class)
								.putExtra("username", username)
								.putExtra("tag", "Search")
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						ab.getContext().startActivity(a);
					} else if (text.equals("Find Your Book By District")) {
						LinearLayout ab = ((LinearLayout) arg0.getParent());
						Intent a = new Intent(ab.getContext(), map.class)
								.putExtra("tag", "User Profile")
								.putExtra("username", username)
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						ab.getContext().startActivity(a);
					}  else if (text.equals("Voice Control")) {
						MainActivity.voiceControl(a, 1);
					}else if (text.equals("Selling Your Book Manually")) {
						LinearLayout ab = ((LinearLayout) arg0.getParent());
						Intent a = new Intent(ab.getContext(),
								MainActivity.class)
								.putExtra("username", username)
								.putExtra("tag", "Manually")
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						ab.getContext().startActivity(a);
					} else if (text.contains("<book>")){

						LinearLayout ab = ((LinearLayout) arg0.getParent());
						Intent a = (new Intent(arg0.getContext(),
								ListOfSecondHandBook.class)
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
								.putExtra("tag", "Show Book Information")
								.putExtra("pev", "main")
								.putExtra("isbn", text.substring(text.indexOf("<isbn>") + 6))
								.putExtra(
										"username",
										((FragmentActivity) arg0.getContext())
												.getIntent().getExtras()
												.getString("username")));
						ab.getContext().startActivity(a);
					} else if (text.contains("<Districts>")){

						LinearLayout ab = ((LinearLayout) arg0.getParent());
						Intent a = (new Intent(arg0.getContext(),
								ListOfSecondHandBook.class)
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
								.putExtra("tag", "Search By District")
								.putExtra("pev", "main")
								.putExtra("district", text.substring(text.indexOf("<id>") + 4))
								.putExtra(
										"username",
										((FragmentActivity) arg0.getContext())
												.getIntent().getExtras()
												.getString("username")));
						ab.getContext().startActivity(a);
					}
					// Log.i("assd", "sadsad" + text);
				}

			});
			if (title.contains("<Districts>")){
				//String [] spiler = title.split(" ");
				Log.i("asd", (title.indexOf("<Districts>") + ""));
				String myText = title.substring(12, title.indexOf("<id>"));
				button.setText (myText);
				button.setBackgroundResource(R.drawable.map);
			} else if (title.contains("<book>")){
				String myText = title.substring(6, title.indexOf("<isbn>"));
				button.setText (niceString(myText));
			} else {
				button.setText(niceString(title));
			}
		}
	}
}