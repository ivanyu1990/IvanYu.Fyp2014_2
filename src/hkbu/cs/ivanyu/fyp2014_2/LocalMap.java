package hkbu.cs.ivanyu.fyp2014_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.maps.android.clustering.ClusterManager;

public class LocalMap extends FragmentActivity {

    private ClusterManager<MyItem> mClusterManager;
	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	SectionsPagerAdapter mSectionsPagerAdapter;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	static String username;
	private boolean firstcome = true;
	static float region = 0;
	double camLat = 0;
	double camLog = 0;
	private ActionBarDrawerToggle mDrawerToggle;
	private static Context context;
	GoogleMap mMap;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinnermap);
		tag = getIntent().getExtras().getString("tag");
		region = Float.parseFloat(getIntent().getExtras().getString("region"));
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.drawer_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		// GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		// lvlList = (ListView) findViewById(R.id.lvlList);
		context = this;
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				// Toast.makeText(getBaseContext(), "Click", Toast.LENGTH_LONG)
				// .show();
				// selectItem(position);

				if (position == 0) {
					startActivity(new Intent(getBaseContext(),
							MainActivity.class).addFlags(
							Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
							"username",
							getIntent().getExtras().getString("username")));
				} else if (position == 1) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "User Profile")
							.putExtra(
									"username",
									getIntent().getExtras().getString(
											"username")));

				} else if (position == 2) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Purchase Record")
							.putExtra(
									"username",
									getIntent().getExtras().getString(
											"username")));
				}else if (position == 3) {
					startActivity(new Intent(getBaseContext(), userPage.class)
					.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
					.putExtra("tag", "Selling Record")
					.putExtra(
							"username",
							getIntent().getExtras().getString(
									"username")));
				} else if (position == 4) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Favourite Books")
							.putExtra(
									"username",
									getIntent().getExtras().getString(
											"username")));
				} else if (position == 5) {
					startActivity(new Intent(getBaseContext(), userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "User Settings")
							.putExtra(
									"username",
									getIntent().getExtras().getString(
											"username")));
				} else if (position == 6) {
					startActivity(new Intent(getBaseContext(),
							LoginActivity.class));
				} else if (position == 7) {
					finish();
					System.exit(0);
				}
			}
		});

		// mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerList.setBackgroundColor((Color.parseColor("#212121")));
		mDrawerList.bringToFront();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		ActionBar bar = getActionBar();
		// getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		// getActionBar().setCustomView(R.layout.actionbar_layout);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#303030")));
		// Set up the ViewPager with the sections adapter.

		setUpMapIfNeeded();
		Spinner sp = (Spinner) findViewById(R.id.spinner1);

		Spinner sp2 = ((Spinner) findViewById(R.id.spinner2));
		List<String> list = new ArrayList<String>();
		list.add("Hong Kong Island");
		list.add("Kowloon");
		list.add("New Territories");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this.getBaseContext(), android.R.layout.simple_spinner_item,
				list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(dataAdapter);
		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				List<String> list = new ArrayList<String>();
				list.add("Central and Western");
				list.add("Eastern");
				list.add("Southern");
				list.add("Wan Chai");

				List<String> list2 = new ArrayList<String>();// {
				list2.add("Sham Shui Po");
				list2.add("Kowloon City");
				list2.add("Kwun Tong");
				list2.add("Wong Tai Sin");
				list2.add("Yau Tsim Mong");

				List<String> list3 = new ArrayList<String>();
				list3.add("Islands");
				list3.add("Kwai Tsing");
				list3.add("North");
				list3.add("Sai Kung");
				list3.add("Sha Tin");
				list3.add("Tai Po");
				list3.add("Tsuen Wan");
				list3.add("Tuen Mun");
				list3.add("Yuen Long");

				if (((Spinner) arg1.getParent()).getItemAtPosition(arg2)
						.equals("Hong Kong Island")) {
					Spinner sp2 = ((Spinner) findViewById(R.id.spinner2));
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
							arg0.getContext(),
							android.R.layout.simple_spinner_item, list);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp2.setAdapter(dataAdapter);

				}
				if (((Spinner) arg1.getParent()).getItemAtPosition(arg2)
						.equals("Kowloon")) {
					Spinner sp2 = ((Spinner) findViewById(R.id.spinner2));
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
							arg0.getContext(),
							android.R.layout.simple_spinner_item, list2);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp2.setAdapter(dataAdapter);
				}
				if (((Spinner) arg1.getParent()).getItemAtPosition(arg2)
						.equals("New Territories")) {
					Spinner sp2 = ((Spinner) findViewById(R.id.spinner2));
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
							arg0.getContext(),
							android.R.layout.simple_spinner_item, list3);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp2.setAdapter(dataAdapter);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
			// new
			// AdapterView.OnItemSelectedListener();
		});

		if (region < 18) {
			sp.setSelection(2);
			//sp2.setSelection((int) (region - 9));
		} else if (region < 23) {
			sp.setSelection(1);
			//sp2.setSelection((int) (region - 18));
		} else {
			sp.setSelection(0);
			//sp2.setSelection((int) (region - 23));
		}
		sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				mMap.clear();
				if (firstcome) {
					if (region < 18) {
						arg0.setSelection((int) (region - 9));
						Log.i("t1", (region - 9) + "");
					} else if (region < 23) {
						arg0.setSelection((int) (region - 18));
						Log.i("t2", (region - 18) + "");
					} else {
						arg0.setSelection((int) (region - 23));
						Log.i("t3", (region - 23) + "");
					}
					firstcome = false;
					return;
				}
				Log.i("asdasdsa", arg2 + ""
						+ ((TextView) arg1).getText().toString());
				if (((TextView) arg1).getText().toString()
						.equals("Central and Western")) {
					region = 23;
					// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
					// LatLng(
					// 22.278971, 114.153756), 13));
				}
				if (((TextView) arg1).getText().toString().equals("Eastern")) {
					region = 24;
					// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
					// LatLng(
					// 22.278326, 114.229790), 13));
				}
				if (((TextView) arg1).getText().toString().equals("Southern")) {
					region = 25;
					// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
					// LatLng(
					// 22.248542, 114.176997), 13));
				}
				if (((TextView) arg1).getText().toString()
						.equals("Yau Tsim Mong")) {
					region = 22;
				}
				if (((TextView) arg1).getText().toString().equals("Kwun Tong")) {
					region = 20;
				}
				if (((TextView) arg1).getText().toString()
						.equals("Wong Tai Sin")) {
					region = 21;
				}
				if (((TextView) arg1).getText().toString().equals("Tuen Mun")) {
					region = 16;
				}
				if (((TextView) arg1).getText().toString().equals("Yuen Long")) {
					region = 17;
				}
				if (((TextView) arg1).getText().toString()
						.equals("Kowloon City")) {
					region = 19;
				}
				if (((TextView) arg1).getText().toString().equals("Tai Po")) {
					region = 14;
				}
				if (((TextView) arg1).getText().toString().equals("Tsuen Wan")) {
					region = 15;
				}
				if (((TextView) arg1).getText().toString().equals("North")) {
					region = 11;
				}
				if (((TextView) arg1).getText().toString().equals("Sai Kung")) {
					region = 12;
				}
				if (((TextView) arg1).getText().toString().equals("Sha Tin")) {
					region = 13;
				}
				if (((TextView) arg1).getText().toString().equals("Islands")) {
					region = 9;
				}
				if (((TextView) arg1).getText().toString().equals("Kwai Tsing")) {
					region = 10;
				}
				if (((TextView) arg1).getText().toString().equals("Wan Chai")) {
					region = 26;
				}
				if (((TextView) arg1).getText().toString()
						.equals("Sham Shui Po")) {
					region = 18;
				}
				setUpMapIfNeeded();
				// 22.278971, 114.153756if()22.278326, 114.229790
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            /*@Override
            public void onInfoWindowClick(Marker marker) {
               Intent intent = new Intent(MapActivity.this,OtherActivity.class);
               startActivity(intent);
            }*/

			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO Auto-generated method stub
				
				String isbn = marker.getTitle().substring(marker.getTitle().indexOf("||")+2);
				startActivity(new Intent(context,
						ListOfSecondHandBook.class)
						.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
						.putExtra("tag", "Show Book Information")
						.putExtra("isbn", isbn)
						//.putExtra("search", tv.getText())
						.putExtra(
								"username",username));
			}
        });
	}

	/** Swaps fragments in the main content view */
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
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return tag;
			}
			return null;
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// mMap.
			Log.i("asd", "asd" + mMap.toString());
			// Check if we were successful in obtaining the map.
			final Context con = this.getBaseContext();
			mMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng point) {
					// TODO Auto-generated method stub

				}

			});
			if (mMap != null) {
				// setUpMap();
			}
		}
		Log.i("hi ", region + " ");
		if (region == 9) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.287190, 113.970724), 11));
		}
		if (region == 10) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.369534, 114.128208), 13));
		}
		if (region == 11) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.512581, 114.148762), 12));
		}
		if (region == 12) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.363644, 114.312918), 10));
		}
		if (region == 13) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.393050, 114.204645), 11));
		}
		if (region == 14) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.455660, 114.182004), 10));
		}
		if (region == 15) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.379961, 114.101043), 10));
		}
		if (region == 16) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.403825, 113.976433), 11));
		}

		if (region == 17) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.451103, 114.001304), 11));
		}
		
		if (region == 18) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.332321, 114.150702), 11));
		}
		if (region == 19) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.321228, 114.188428), 11));
		}
		if (region == 20) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.316096, 114.224371), 11));
		}
		if (region == 21) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.337213, 114.197592), 11));
		}
		if (region == 22) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.311758, 114.167129), 11));
		}
		if (region == 23) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.280798, 114.154013), 12));
		}
		if (region == 24) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.280798, 114.154013), 12));
		}
		if (region == 25) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.239963, 114.173221), 12));
		}
		if (region == 26) {
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.279103, 114.179014), 12));
		}
		marker();
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

		public DummySectionFragment() {
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			// new ListOfBook().execute((Void) null);
		}

	}
	public void marker(){
		mClusterManager = new ClusterManager<MyItem>(this, mMap);
		List<MyItem> items = new ArrayList<MyItem>();
		mMap.setOnCameraChangeListener(mClusterManager);
		
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_map.php?id=" + (region - 9));
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				HttpResponse response = httpclient.execute(httppost);

				Log.i("asd", "ASdasd");
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				try {
					JSONObject obj = new JSONObject(
							convertStreamToString(is));
					if (obj.getJSONArray("MapRecord").length() > 0) {
						int length = obj.getJSONArray("MapRecord").length();
						for (int i = 0; i < length; i++) {
							HashMap<String, Object> item = new HashMap<String, Object>();
							item.put("bookName",
									obj.getJSONArray("MapRecord")
											.getJSONObject(i)
											.getString("bookName"));
							item.put("price",
									obj.getJSONArray("MapRecord")
											.getJSONObject(i)
											.getString("price"));
							item.put("lat",
									obj.getJSONArray("MapRecord")
											.getJSONObject(i)
											.getString("lat"));
							item.put("log",
									obj.getJSONArray("MapRecord")
											.getJSONObject(i)
											.getString("log"));
							item.put("isbn", obj.getJSONArray("MapRecord")
									.getJSONObject(i).getString("isbn"));
							MarkerOptions markerOpt = new MarkerOptions();
							markerOpt.position(new LatLng(Float.parseFloat(obj.getJSONArray("MapRecord")
									.getJSONObject(i)
									.getString("lat")), Float.parseFloat(obj.getJSONArray("MapRecord")
											.getJSONObject(i)
											.getString("log"))));
							markerOpt.title(obj.getJSONArray("MapRecord")
									.getJSONObject(i)
									.getString("bookName"));
							markerOpt.draggable(true);
							
							//mMap.addMarker(markerOpt);
							//List<MyItem> items = new MyItemReader().read(inputStream);
							double lat = Double.parseDouble(
									obj.getJSONArray("MapRecord")
									.getJSONObject(i)
									.getString("lat"));
							double log = Double.parseDouble(
									obj.getJSONArray("MapRecord")
									.getJSONObject(i)
									.getString("log"));
							double price = Double.parseDouble(
										obj.getJSONArray("MapRecord")
												.getJSONObject(i)
												.getString("price"));
							String isbn = obj.getJSONArray("MapRecord")
									.getJSONObject(i).getString("isbn");
							items.add(new MyItem(lat, log,  
									obj.getJSONArray("MapRecord")
									.getJSONObject(i)
									.getString("bookName"), price, isbn));
					        //mClusterManager.addItems(items);
							try {
								/*item.put(
										"image",
										this.drawableFromUrl(obj
												.getJSONArray("MapRecord")
												.getJSONObject(i)
												.getString("image")));*/
							} catch (Exception e) {

							}
							//list.add(item);
							Log.i("aaaa", i + "");
						}
					}
				} catch (Exception e) {
					Log.i("asd",
							"ASdasd" + e.getLocalizedMessage()
									+ e.getMessage());
				}
			} catch (Exception e) {

				Log.i("asd", "ASdasd" + e.getLocalizedMessage() + e.getMessage());
			}

		}
		mClusterManager.addItems(items);
		mClusterManager.setRenderer(new MyClusterRenderer(this, mMap,mClusterManager));
	}
	public class ListOfBook extends AsyncTask<Void, Void, Boolean> {
		private ArrayList<String> myList = new ArrayList<String>();
		String[] mString = new String[100];
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		SimpleAdapter adapter;
		private ArrayList<Drawable> myDrawable = new ArrayList<Drawable>();

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_map.php?id=" + (region - 9));
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					HttpResponse response = httpclient.execute(httppost);

					Log.i("asd", "ASdasd");
					HttpEntity entity = response.getEntity();
					InputStream is = entity.getContent();
					try {
						JSONObject obj = new JSONObject(
								convertStreamToString(is));
						if (obj.getJSONArray("MapRecord").length() > 0) {
							int length = obj.getJSONArray("MapRecord").length();
							for (int i = 0; i < length; i++) {
								HashMap<String, Object> item = new HashMap<String, Object>();
								item.put("bookName",
										obj.getJSONArray("MapRecord")
												.getJSONObject(i)
												.getString("bookName"));
								item.put("price",
										obj.getJSONArray("MapRecord")
												.getJSONObject(i)
												.getString("price"));
								item.put("lat",
										obj.getJSONArray("MapRecord")
												.getJSONObject(i)
												.getString("lat"));
								item.put("log",
										obj.getJSONArray("MapRecord")
												.getJSONObject(i)
												.getString("log"));
								item.put("isbn", obj.getJSONArray("MapRecord")
										.getJSONObject(i).getString("isbn"));
								MarkerOptions markerOpt = new MarkerOptions();
								markerOpt.position(new LatLng(25.033611, 121.565000));
								//markerOpt.title("¥x¥_101");
								markerOpt.draggable(true);

								mMap.addMarker(markerOpt);
								try {
									item.put(
											"image",
											this.drawableFromUrl(obj
													.getJSONArray("MapRecord")
													.getJSONObject(i)
													.getString("image")));
								} catch (Exception e) {

								}
								list.add(item);
								Log.i("aaaa", i + "");
							}
						}
					} catch (Exception e) {
						Log.i("asd",
								"ASdasd" + e.getLocalizedMessage()
										+ e.getMessage());
					}
				} catch (Exception e) {

					Log.i("asd", "ASdasd");
				}

			}
			return true;
		}

		public ArrayList<String> getArrayList() {
			return myList;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			// ArrayList<String> myString;
		}

		@Override
		protected void onCancelled() {
		}

		public Drawable drawableFromUrl(String url) throws IOException {

			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			Bitmap x;

			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			connection.connect();
			InputStream input = connection.getInputStream();

			x = BitmapFactory.decodeStream(input);
			return new BitmapDrawable(x);
		}
	}
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onBackPressed();
		return super.onOptionsItemSelected(item);
	}
	
}
