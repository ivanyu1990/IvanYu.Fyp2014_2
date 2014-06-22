package hkbu.cs.ivanyu.fyp2014_2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.askerov.dynamicgid.DynamicGridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.client.android.CaptureActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	SectionsPagerAdapter mSectionsPagerAdapter;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private ActionBarDrawerToggle mDrawerToggle;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ArrayList<String> listOfSellingBook = new ArrayList();
	public static ArrayList<String> mainMenu = new ArrayList<String>() {
		{
			this.add("Second Hand Book");
			this.add("Search your Book");
			this.add("Find Your Book By District");
			this.add("Scan Barcode for Selling your Book");
			this.add("Selling Your Book Manually");
			this.add("Voice Control");
		}
	};

	public static ArrayList<String> districts = new ArrayList<String>() {
		{
			this.add("<Districts> Islands <id>9");
			this.add("<Districts> Kwai Tsing <id>10");
			this.add("<Districts> North <id>11");
			this.add("<Districts> Sai Kung <id>12");
			this.add("<Districts> Sha Tin <id>13");
			this.add("<Districts> Tai Po <id>14");
			this.add("<Districts> Tsuen Wan <id>15");
			this.add("<Districts> Tuen Mun <id>16");
			this.add("<Districts> Yuen Long <id>17");
			this.add("<Districts> Sham Shui Po <id>18");
			this.add("<Districts> Kowloon City <id>19");
			this.add("<Districts> Kwun Tong <id>20");
			this.add("<Districts> Wong Tai Sin <id>21");
			this.add("<Districts> Yau Tsim Mong <id>22");
			this.add("<Districts> Central and Western <id>23");
			this.add("<Districts> Eastern <id>24");
			this.add("<Districts> Southern <id>25");
			this.add("<Districts> Wan Chai <id>26");
		}
	};
	public static ArrayList<String> hotBooks = new ArrayList<String>();
	ViewPager mViewPager;
	static String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// mainMenu.get
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.drawer_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		userName = this.getIntent().getExtras().getString("username");
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		// lvlList = (ListView) findViewById(R.id.lvlList);

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				if (position == 0) {
					startActivity(new Intent(getBaseContext(),
							MainActivity.class).addFlags(
							Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
							"username", userName));
				} else if (position == 1) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "User Profile")
							.putExtra("username", userName));
				} else if (position == 2) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Purchase Record")
							.putExtra("username", userName));
				} else if (position == 3) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Selling Record")
							.putExtra("username", userName));
				} else if (position == 4) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Favourite Books")
							.putExtra("username", userName));
				} else if (position == 5) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Comment Records")
							.putExtra(
									"username",
									getIntent().getExtras().getString(
											"username")));
				} else if (position == 6) {
					startActivity(new Intent(getBaseContext(),
							LoginActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "User Settings")
							.putExtra("username", userName));
				} else if (position == 7) {
					finish();
					System.exit(0);
				}
			}
		});

		if (getIntent().getExtras().getString("tag") != null
				&& getIntent().getExtras().getString("tag").equals("Search")) {
			// Log.i("asdasd", "asdasd");
			reg();
		}
		if (getIntent().getExtras().getString("tag") != null
				&& getIntent().getExtras().getString("tag").equals("Manually")) {
			// Log.i("asdasd", "asdasd");
			inputMan();
		}
		// mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerList.setBackgroundColor((Color.parseColor("#212121")));

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		ActionBar bar = getActionBar();
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.abs_layout);
		Button a = (Button) findViewById(R.id.navBut);
		// a.setBackground(this.getResources().getDrawable(R.drawable.menu));
		a.setBackgroundResource(R.drawable.menubutton);
		a.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if(mDrawerLayout.is)
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
				// mDrawerLayout.openDrawer(mDrawerList);
			}

		});
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#303030")));
		// set your
		// desired
		// color
		// readBookFromCP();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	public static class PlanetFragment extends android.app.Fragment {
		public static final String ARG_PLANET_NUMBER = "planet_number";

		public PlanetFragment() {
			// Empty constructor required for fragment subclasses
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_planet,
					container, false);

			int i = getArguments().getInt(ARG_PLANET_NUMBER);

			String planet = getResources().getStringArray(R.array.drawer_array)[i];

			getActivity().setTitle(planet);
			return rootView;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	public static ArrayList<String> readBookFromCP() {


		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		ArrayList<String> myBooks = new ArrayList<String>();
		String url = "http://www.cp1897.com.hk/api/bookinfo/keystoneBooks/";
		JSONParser jsp = new JSONParser();
		JSONArray result;
		try {

			JSONObject obj = jsp.getJSONFromUrl(url);
			result = obj.getJSONArray("results");
			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);
				myBooks.add("<book>" + jo.getString("products_name") + "<isbn>"
						+ jo.getString("isbn"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myBooks;
	}

	public static ArrayList<String> readBookFromCF() {


		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		ArrayList<String> myBooks = new ArrayList<String>();
		String url = "http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/predict.php?username=" + userName;
		JSONParser jsp = new JSONParser();
		JSONArray result;
		try {

			JSONObject obj = jsp.getJSONFromUrl(url);
			result = obj.getJSONArray("Book");
			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);
				myBooks.add("<book>" + jo.getString("bookName") + "<isbn>"
						+ jo.getString("item"));
				Log.i("asdasd1", "fdbdf1");
			}
		} catch (JSONException e) {
			Log.i("asdasd", "fdbdf");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myBooks;
	}
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			((DummySectionFragment) fragment).setPosition(position);
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return "collaborative filtering".toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		private int position;
		DynamicGridView gridView;

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_grid, container,
					false);

			gridView = (DynamicGridView) rootView
					.findViewById(R.id.dynamic_grid);
			if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
				Toast.makeText(rootView.getContext(), "Large screen",
						Toast.LENGTH_LONG).show();
				gridView.setNumColumns(3);
			} else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {

			} else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
				// Toast.makeText(rootView.getContext(), "Small sized screen",
				// Toast.LENGTH_LONG).show();
			} else {
				/*
				 * Toast.makeText(rootView.getContext(),
				 * "Screen size is neither large, normal or small",
				 * Toast.LENGTH_LONG).show();
				 */
				gridView.setNumColumns(3);
			}

			if (position == 0) {

				gridView.setAdapter(new CheeseDynamicAdapter(rootView
						.getContext(), mainMenu, 3));
				// applyFontRecursively(rootView.getRootView());
				gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Log.i("asdasd", "asdasd");
					}

				});
			} else if (position == 1) {

				gridView.setAdapter(new CheeseDynamicAdapter(rootView
						.getContext(), readBookFromCP(), 3));
			} else if (position == 2) {
				gridView.setAdapter(new CheeseDynamicAdapter(rootView
						.getContext(), districts, 3));
			} else if (position == 3) {
				gridView.setAdapter(new CheeseDynamicAdapter(rootView
						.getContext(), readBookFromCF(), 3));
			}
			return rootView;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}
	}

	public void onBackPressed() {

	}

	public void inputMan() {
		// Create Object of Dialog class
		final Dialog search = new Dialog(this,
				android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
		// Set GUI of login screen
		
		String spinnerArray[] = { "Red", "Blue", "White", "Yellow", "Black",
				"Green", "Purple", "Orange", "Grey" };

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		Spinner spinner = new Spinner(this);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, spinnerArray);
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(spinnerArrayAdapter);
		search.addContentView(spinner, lp);
		search.setContentView(R.layout.mandialog);
		//*/
		//search.addContentView(spinner, lp);
		
		final EditText bookName = (EditText) search.findViewById(R.id.bookName);
		final EditText author = (EditText) search.findViewById(R.id.author);
		final EditText isbn = (EditText) search.findViewById(R.id.isbn);
		final EditText price = (EditText) search.findViewById(R.id.price);
		final Spinner region = (Spinner) search.findViewById(R.id.spinnner);
		final EditText owner_desc = (EditText) search
				.findViewById(R.id.owner_desc);
		final EditText book_desc = (EditText) search
				.findViewById(R.id.book_desc);

		final Button btnSearch = (Button) search.findViewById(R.id.btnLogin);
		bookName.setError(null);
		author.setError(null);
		isbn.setError(null);
		price.setError(null);
		owner_desc.setError(null);
		book_desc.setError(null);

		search.show();
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (bookName.getText().length() == 0
						|| author.getText().length() == 0
						|| isbn.getText().length() == 0
						|| price.getText().length() == 0
						|| owner_desc.getText().length() == 0
						|| book_desc.getText().length() == 0) {
					bookName.setError("can't be emptied");
				} else {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_manbook.php");
					HttpResponse response;
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("isbn", isbn
							.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("author", author
							.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("bookName", bookName
							.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("price", price
							.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("region", region.getSelectedItemId()+""));
					nameValuePairs.add(new BasicNameValuePair("username", userName));
					nameValuePairs.add(new BasicNameValuePair("owner_desc", owner_desc
							.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("book_desc", book_desc
							.getText().toString()));
					try {
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));
						HttpResponse response1 = httpclient
								.execute(httppost);

						HttpEntity entity = response1
								.getEntity();
						InputStream is = entity
								.getContent();
						
						search.cancel();
					} catch (Exception e) {
						// TODO Auto-generated catch
						// block
						e.printStackTrace();
					}
				}
			}

		});
	}

	public void reg() {
		// Create Object of Dialog class
		final Dialog search = new Dialog(this,
				android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
		// Set GUI of login screen
		search.setContentView(R.layout.search_dialog);
		final EditText bookName = (EditText) search.findViewById(R.id.bookName);
		final EditText author = (EditText) search.findViewById(R.id.author);
		final EditText isbn = (EditText) search.findViewById(R.id.isbn);
		final Button btnSearch = (Button) search.findViewById(R.id.btnLogin);
		bookName.setError(null);
		author.setError(null);
		isbn.setError(null);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (bookName.getText().length() == 0
						&& author.getText().length() == 0
						&& isbn.getText().length() == 0) {
					bookName.setError("At least one field needs to be entered");
				} else {
					startActivity(new Intent(getBaseContext(),
							ListOfSecondHandBook.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Search Results")
							.putExtra("isbn", (isbn.getText() + ""))
							.putExtra("author", (author.getText() + ""))
							.putExtra("bookName", (bookName.getText() + ""))
							.putExtra("username", userName));
				}
			}
		});
		search.show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		String DEBUG_TAG = "DEBUG_TAG";

		View v = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);
		int action = MotionEventCompat.getActionMasked(event);

		switch (action) {
		case (MotionEvent.ACTION_DOWN):
			Log.d(DEBUG_TAG, "Action was DOWN");
			return true;
		case (MotionEvent.ACTION_MOVE):
			Log.d(DEBUG_TAG, "Action was MOVE");
			return true;
		case (MotionEvent.ACTION_UP):
			Log.d(DEBUG_TAG, "Action was UP");
			return true;
		case (MotionEvent.ACTION_CANCEL):
			Log.d(DEBUG_TAG, "Action was CANCEL");
			return true;
		case (MotionEvent.ACTION_OUTSIDE):
			Log.d(DEBUG_TAG, "Movement occurred outside bounds "
					+ "of current screen element");
			return true;
		default:
			return super.onTouchEvent(event);
		}
	}

	public void onResume() {
		super.onResume();
		// super.onCreate(this.getBaseContext());
	}

	public static void voiceControl(Activity activity, int requestCode) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

		try {
			activity.startActivityForResult(intent, 1);
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(activity.getApplicationContext(),
					"Ops! Your device doesn't support Speech to Text",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 1: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				Log.i("overover", text.get(0));
				if (text.get(0).toLowerCase().contains("second hand book")) {

					Intent a = new Intent(this, ListOfSecondHandBook.class)
							.putExtra("username", userName).putExtra("tag",
									"List Of Second Hand Book");
					startActivity(a);
				}
				if (text.get(0).toLowerCase().contains("scan barcode")) {
					Intent a = new Intent(this, CaptureActivity.class)
							.putExtra("username", userName).putExtra(
									"home",
									new Intent(this, MainActivity.class)
											.putExtra("username", userName));
					startActivity(a);
				}
				if (text.get(0).toLowerCase().contains("map")) {
					Intent a = new Intent(this, map.class)
							.putExtra("tag", "User Profile")
							.putExtra("username", userName)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(a);
				}
				// txtText.setText(text.get(0));
			}
			break;
		}

		}
	}
}
