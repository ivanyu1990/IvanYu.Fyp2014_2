package hkbu.cs.ivanyu.fyp2014_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ListOfSecondHandBook extends FragmentActivity implements
		LocationListener, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	SectionsPagerAdapter mSectionsPagerAdapter;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	static String username;
	private ActionBarDrawerToggle mDrawerToggle;

	private static Bitmap mImageBitmap;
	private ImageView mImageView;

	private LocationRequest mLocationRequest;
	private LocationClient mLocationClient;
	public static double lat = 0;
	public static String districts = "";
	public static double log = 0;
	public static boolean uploaded = false;
	static String address = "";
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String tag;

	boolean mUpdatesRequested = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		tag = getIntent().getExtras().getString("tag");
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

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
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
				} else if (position == 3) {
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
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		ActionBar bar = getActionBar();
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#303030")));

		// Set up the ViewPager with the sections adapter.

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Create a new global location parameters object
		mLocationRequest = LocationRequest.create();

		/*
		 * Set the update interval
		 */
		mLocationRequest
				.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		// Set the interval ceiling to one minute
		mLocationRequest
				.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

		// Note that location updates are off until the user turns them on
		mUpdatesRequested = false;
		mLocationClient = new LocationClient(this, this, this);

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG, "asd");
			// this.getLocation();
		}
		AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
		// camera.takePicture(null, rawCallback, jpegCallback);

	}

	private void startPeriodicUpdates() {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	private void stopPeriodicUpdates() {
		mLocationClient.removeLocationUpdates(this);
	}

	public void onStop() {

		// If the client is connected
		if (mLocationClient.isConnected()) {
			stopPeriodicUpdates();
		}

		// After disconnect() is called, the client is considered "dead".
		mLocationClient.disconnect();

		super.onStop();
	}

	public void onStart() {

		super.onStart();

		/*
		 * Connect the client. Don't re-start any requests here; instead, wait
		 * for onResume()
		 */
		mLocationClient.connect();
	}

	public void getLocation() {

		// If Google Play Services is available
		if (servicesConnected() && mLocationClient.isConnected()) {

			// Get the current location
			// mLocationClient.connect();
			Location currentLocation = mLocationClient.getLastLocation();
			Log.i("asd", LocationUtils.getLatLng(this, currentLocation));
			Toast.makeText(this,
					LocationUtils.getLatLng(this, currentLocation), 100000);
			// Display the current location in the UI
			// mLatLng.setText(LocationUtils.getLatLng(this, currentLocation));
		}
	}

	private boolean servicesConnected() {

		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			// Log.d(LocationUtils.APPTAG,
			// getString(R.string.play_services_available));

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			return false;
		}
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
			if (getActivity().getIntent().getExtras().getString("tag")
					.equals("User Profile")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				new UserLoginTask().execute((Void) null);
			} else if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Purchase Record")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				PurchaseRecord aaa = new PurchaseRecord();
				aaa.execute((Void) null);
			} else if (getActivity().getIntent().getExtras().getString("tag")
					.equals("List Of Second Hand Book")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				ListOfBook aaa = new ListOfBook();
				aaa.execute((Void) null);
			} else if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Show Book Information")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				String isbn = getActivity().getIntent().getExtras()
						.getString("isbn");
				new BookInfo().setISBN(isbn).execute((Void) null);
			} else if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Search Results")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				String isbn = getActivity().getIntent().getExtras()
						.getString("isbn");
				String bookName = getActivity().getIntent().getExtras()
						.getString("bookName");
				String author = getActivity().getIntent().getExtras()
						.getString("author");
				new SearchBookInfo().setISBN(isbn).setAuthor(author)
						.setBookName(bookName).execute((Void) null);
			} else if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Search By District")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				String district = getActivity().getIntent().getExtras()
						.getString("district");
				new ListOfBookByDistricts().setDistrict(district).execute(
						(Void) null);
			}
			// new UserLoginTask().execute((Void) null);
		}

		private static String convertStreamToString(InputStream is) {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
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

		public class PurchaseRecord extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.
				boolean t = false;
				myList = new ArrayList();
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_PurchaseRecord.php?username="
									+ username);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						// nameValuePairs.add(new BasicNameValuePair(
						// "userName",username));

						// httppost.setEntity(new UrlEncodedFormEntity(
						// nameValuePairs));
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						// Log.i("postData", convertStreamToString(is) + " "
						// + response.getStatusLine().toString());

						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("PurchaseRecord").length() > 0) {
								int length = obj.getJSONArray("PurchaseRecord")
										.length();
								for (int i = 0; i < length; i++) {
									myList.add("bookName :"
											+ obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("bookName"));
									myList.add("price :"
											+ obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("price"));
									myList.add("owner_description :"
											+ obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString(
															"owner_description"));
									myList.add("isbn :"
											+ obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("isbn"));
								}
							}
							// Log.i("hi",
							// obj.getJSONArray("results").getJSONObject(0).getString("formatted_address"));
						} catch (Exception e) {
						}
					} catch (Exception e) {

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

				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				for (String m : myList) {
					Button myButton = new Button(getActivity());
					myButton.setText(m);
					// myButton.setBackgroundResource(R.drawable.isbn);
					LayoutParams lp = new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					Log.i(m, ll + " " + getActivity() + myList.size());
					ll.addView(myButton, lp);
				}
			}

			@Override
			protected void onCancelled() {
			}
		}

		public class SearchBookInfo extends AsyncTask<Void, Void, Boolean> {
			private Book myBook = new Book();
			String isbn;
			String author;
			String bookName;
			String total;
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			private ArrayList<Drawable> myDrawable = new ArrayList<Drawable>();

			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.

				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				boolean t = false;
				// myList = new ArrayList();
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/search.php?q="
									+ (isbn.trim()).replace(" ", "+")
											.toLowerCase()
									+ "+"
									+ (author.trim()).replace(" ", "+")
											.toLowerCase()
									+ "+"
									+ (bookName.trim()).replace(" ", "+")
											.toLowerCase());
					try {

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						String myStream = convertStreamToString(is);

						JSONObject job = new JSONObject(myStream);

						String total = (isbn.trim()).replace(" ", "+")
								.toLowerCase()
								+ "+"
								+ (author.trim()).replace(" ", "+")
										.toLowerCase()
								+ "+"
								+ (bookName.trim()).replace(" ", "+")
										.toLowerCase();
						for (int i = 0; i < job.getJSONArray("books").length(); i++) {
							HashMap<String, Object> item = new HashMap<String, Object>();
							Log.i("asdasd", job.getJSONArray("books")
									.getJSONObject(i).getString("title")
									+ "");
							item.put("bookName", job.getJSONArray("books")
									.getJSONObject(i).getString("title"));
							String author = "";
							for (int x = 0; x < job.getJSONArray("books")
									.getJSONObject(i).getJSONArray("author")
									.length(); x++) {
								Log.i("author",
										job.getJSONArray("books")
												.getJSONObject(i)
												.getJSONArray("author")
												.length()
												+ " "
												+ job.getJSONArray("books")
														.getJSONObject(i)
														.getJSONArray("author")
														.getString(x));
								author += job.getJSONArray("books")
										.getJSONObject(i)
										.getJSONArray("author").getString(x)
										+ ",";
							}

							item.put("author", job.getJSONArray("books")
									.getJSONObject(i).getString("author"));
							item.put("summary", job.getJSONArray("books")
									.getJSONObject(i).getString("summary"));
							item.put("isbn", job.getJSONArray("books")
									.getJSONObject(i).getString("isbn10"));
							item.put("search", total);
							Log.i("asd", job.getJSONArray("books")
									.getJSONObject(i).getJSONObject("images")
									.getString("small"));
							list.add(item);
						}
					} catch (Exception e) {
						Log.i("error", e.getLocalizedMessage());
					}

				}
				return true;
			}

			public SearchBookInfo setISBN(String isbn) {
				this.isbn = isbn;
				return this;
			}

			public SearchBookInfo setAuthor(String author) {
				this.author = author;
				return this;
			}

			public SearchBookInfo setBookName(String bookName) {
				this.bookName = bookName;
				return this;
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

			// public ArrayList<String> getArrayList() {
			// return myList;
			// }

			@Override
			protected void onPostExecute(final Boolean success) {
				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				ListView lv = new ListView(getActivity());
				SimpleAdapter adapter = new SimpleAdapter(
						getActivity(),
						list,
						R.layout.mylistview2,
						new String[] { "image", "bookName", "author", "isbn",
								"search" },
						new int[] { R.id.imageView1, R.id.textView1,
								R.id.textView2, R.id.textView3, R.id.textView4 });

				adapter.setViewBinder(new ViewBinder() {
					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						if (view instanceof ImageView
								&& data instanceof Drawable) {
							ImageView iv = (ImageView) view;
							iv.setImageDrawable((Drawable) data);
							return true;
						}
						return false;
					}
				});
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView4);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView3);
						Log.i("asdasd", tv.getText() + "");

						startActivity(new Intent(arg1.getContext(),
								ListOfSecondHandBook.class)
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
								.putExtra("tag", "Show Book Information")
								.putExtra("pev", "search")
								.putExtra("isbn", isbn.getText())
								.putExtra("search", tv.getText())
								.putExtra(
										"username",
										((FragmentActivity) arg1.getContext())
												.getIntent().getExtras()
												.getString("username")));
					}
				});
				lv.setAdapter(adapter);
				lv.setLayoutParams(lp);
				ll.removeAllViews();
				// ll.addView(new Button(getActivity()));
				ll.addView(lv, lp);
				Log.i("i", "end");
			}

			@Override
			protected void onCancelled() {
			}
		}

		public class BookInfo extends AsyncTask<Void, Void, Boolean> {
			private Book myBook = new Book();
			String isbn;

			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.
				boolean t = false;
				// myList = new ArrayList();
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/book.php?isbn="
									+ isbn);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							String myStream = convertStreamToString(is);
							Log.i("asd", myStream);
							JSONObject obj = new JSONObject(myStream);
							// obj
							Log.i("hihihi", "hihihi" + obj.toString());
							String title = (String) obj.get("title");
							String summary = (String) obj.get("summary");
							String page = (String) obj.get("pages");
							String price = (String) obj.get("price");
							String image = (String) obj.getJSONObject("images")
									.get("large");
							JSONArray jsarr = (JSONArray) obj
									.getJSONArray("author");
							JSONArray jstag = (JSONArray) obj
									.getJSONArray("tags");
							String[] author = new String[10];
							String[] tag = new String[20];

							for (int i = 0; i < jstag.length(); i++) {
								Log.i("tag", jstag.getString(i));
								tag[i] = jstag.getString(i);
							}

							for (int i = 0; i < jsarr.length(); i++) {
								Log.i("author", jsarr.getString(i));
								author[i] = jsarr.getString(i);
							}
							Log.i("asdasd", title + " " + summary + " " + page
									+ " " + price + " " + image + " ");
							myBook = new Book(
									title,
									summary,
									new ArrayList<String>(Arrays.asList(author)),
									new ArrayList<String>(Arrays.asList(tag)),
									(price), image);
							return true;
						} catch (Exception e) {
							Log.i("error", e.getLocalizedMessage());
							return false;
						}
					} catch (Exception e) {
						Log.i("error", e.getLocalizedMessage());
						return false;
					}

				}
				return false;
			}

			public BookInfo setISBN(String isbn) {
				this.isbn = isbn;
				return this;
			}

			// public ArrayList<String> getArrayList() {
			// return myList;
			// }

			@Override
			protected void onPostExecute(final Boolean success) {
				// ArrayList<String> myString;
				try {
					if (success) {
						LinearLayout ll = (LinearLayout) getActivity()
								.findViewById(R.id.rooot);
						LinearLayout myll = new LinearLayout(getActivity());
						LinearLayout myll2 = new LinearLayout(getActivity());
						myll.setOrientation(LinearLayout.HORIZONTAL);
						ll.removeAllViewsInLayout();
						ll.setGravity(Gravity.NO_GRAVITY);
						TextView tv = new TextView(getActivity());
						String text = "Title : " + myBook.getTitle()
								+ "\nAuthor : ";
						if (myBook.getAuthor() != null) {
							for (int i = 0; i < myBook.getAuthor().size(); i++) {
								if (myBook.getAuthor().get(i) != null) {
									text += (myBook.getAuthor().get(i) + " ");
								}
							}
						}
						text += "\n";
						if (myBook.getSummary().length() > 0) {
							text += ("Description : " + myBook.getSummary());
						}
						tv.setText(text);
						tv.setMovementMethod(new ScrollingMovementMethod());

						tv.setTextSize(20);
						LayoutParams lp = new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
						try {
							if (myBook.getImageLink() != null
									&& !myBook
											.getImageLink()
											.equals("http://img3.douban.com/pics/book-default-large.gif")) {
								Drawable a = drawableFromUrl(myBook
										.getImageLink());
								ImageView iv = new ImageView(getActivity());
								iv.setBackgroundDrawable(a);

								ll.addView(iv, lp);
							} else {
								Drawable a = getResources().getDrawable(
										R.drawable.ic_launcher);
								Bitmap bitmap = ((BitmapDrawable) a)
										.getBitmap();
								// Scale it to 50 x 50
								Drawable d = new BitmapDrawable(getResources(),
										Bitmap.createScaledBitmap(bitmap, 300,
												300, true));

								ImageView iv = new ImageView(getActivity());
								iv.setBackgroundDrawable(d);

								ll.addView(iv, lp);
							}

							RatingBar rb = new RatingBar(getActivity());
							rb.setNumStars(5);
							rb.setStepSize((float) 0.5);
							rb.setRating(0);
							rb.isIndicator();// .isIndicator(true);

							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(
									"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/getRating.php?username="
											+ username + "&isbn=" + isbn);
							HttpResponse response;
							JSONObject obj = null;
							int length = -1;
							String temp = "";
							try {
								response = httpclient.execute(httppost);
								HttpEntity entity = response.getEntity();
								InputStream is = entity.getContent();

								obj = new JSONObject(convertStreamToString(is));

								// if(obj != null && obj.getJSONArray("Rating")
								// !=
								// null)
								// Log.i("asd",obj.getJSONArray("Rating").length()
								// +
								// "" );
								length = obj.getJSONArray("Rating").length();
								rb.setRating(obj.getJSONArray("Rating")
										.getJSONObject(0).getInt("rating"));
								rb.setEnabled(false);
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								if (length == -1)
									rb.setOnRatingBarChangeListener((new OnRatingBarChangeListener() {

										@Override
										public void onRatingChanged(
												RatingBar arg0, float arg1,
												boolean arg2) {
											// TODO Auto-generated method stub
											boolean confirm = false;
											final float rating = arg1;
											final RatingBar ratBar = arg0;
											new AlertDialog.Builder(arg0
													.getContext())
													.setMessage(
															"Do you really want to rate this book with "
																	+ rating
																	* 2
																	+ " score?")
													.setIcon(
															android.R.drawable.ic_dialog_alert)
													.setPositiveButton(
															android.R.string.yes,
															new DialogInterface.OnClickListener() {

																public void onClick(
																		DialogInterface dialog,
																		int whichButton) {
																	HttpClient httpclient = new DefaultHttpClient();
																	HttpPost httppost = new HttpPost(
																			"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/giveRating.php?isbn="
																					+ isbn
																					+ "&username="
																					+ username
																					+ "&rating="
																					+ rating
																					* 2);
																	HttpResponse response;

																	String temp = "";
																	try {
																		response = httpclient
																				.execute(httppost);
																		HttpEntity entity = response
																				.getEntity();
																		InputStream is = entity
																				.getContent();

																		Toast.makeText(
																				ratBar.getContext(),
																				"successfully rated this book",
																				10000);
																		((RatingBar) ratBar)
																				.setEnabled(false);
																	} catch (Exception e) {

																	}
																}
															})
													.setNegativeButton(
															android.R.string.no,
															null).show();
										}

									}));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							// this);
							myll.addView(rb, lp);

							Button b = new Button(getActivity());
							b.setText("Comment");
							b.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									// Create Object of Dialog class
									final Dialog commentDialog = new Dialog(
											arg0.getContext(),
											android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
									// Set GUI of login screen
									commentDialog
											.setContentView(R.layout.comment_dialog);
									final TextView commentTextvView = (TextView) commentDialog
											.findViewById(R.id.myTextView);
									commentTextvView
											.setMovementMethod(new ScrollingMovementMethod());
									commentTextvView.setHeight(400);
									HttpClient httpclient = new DefaultHttpClient();
									HttpPost httppost = new HttpPost(
											"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/comment.php?isbn="
													+ isbn);
									HttpResponse response;

									String temp = "";
									try {
										response = httpclient.execute(httppost);
										HttpEntity entity = response
												.getEntity();
										InputStream is = entity.getContent();

										JSONObject obj = new JSONObject(
												convertStreamToString(is));

										for (int i = 0; i < obj.getJSONArray(
												"Comments").length(); i++) {
											temp += "Username : "
													+ obj.getJSONArray(
															"Comments")
															.getJSONObject(i)
															.get("userName")
													+ "\n";
											temp += "Comment : "
													+ obj.getJSONArray(
															"Comments")
															.getJSONObject(i)
															.get("comment")
													+ "\n";
											temp += "===================================\n";
										}

									} catch (Exception e) {
										temp += "NO comment right now";
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									commentTextvView.setText(temp);

									Button send = (Button) commentDialog
											.findViewById(R.id.btnLogin);
									final TextView comment = (TextView) commentDialog
											.findViewById(R.id.et3);

									Button cancel = (Button) commentDialog
											.findViewById(R.id.btnCancel);

									cancel.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											commentDialog.cancel();
										}
									});

									send.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View arg0) {

											boolean cancel = false;
											View focusView = null;
											if (TextUtils.isEmpty(comment
													.getText())) {
												comment.setError(getString(R.string.error_field_required));
												focusView = comment;
												cancel = true;
											} else if (comment.getText()
													.length() < 4) {
												comment.setError("This field can't be too short");
												focusView = comment;
												cancel = true;
											}
											// TODO Auto-generated method stub
											if (cancel) {
												// There was an error; don't
												// attempt login and focus the
												// first
												// form field with an error.
												focusView.requestFocus();
											} else {
												HttpClient httpclient = new DefaultHttpClient();
												HttpPost httppost = new HttpPost(
														"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_PostComment.php");
												HttpResponse response;
												List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
												nameValuePairs
														.add(new BasicNameValuePair(
																"isbn", isbn));
												nameValuePairs
														.add(new BasicNameValuePair(
																"username",
																username));
												nameValuePairs
														.add(new BasicNameValuePair(
																"comment",
																""
																		+ comment
																				.getText()));

												try {
													httppost.setEntity(new UrlEncodedFormEntity(
															nameValuePairs));
													HttpResponse response1 = httpclient
															.execute(httppost);

													HttpEntity entity = response1
															.getEntity();
													InputStream is = entity
															.getContent();

													commentDialog.cancel();
												} catch (Exception e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}
										}
									});
									commentDialog.show();
								}
							});
							Button c = new Button(getActivity());
							c.setText("Purchase");
							c.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									// Facebook mFacebook;
									final Dialog purchaseDialog = new Dialog(
											v.getContext(),
											android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
									// Set GUI of login screen
									purchaseDialog
											.setContentView(R.layout.purchase_dialog);
									final ListView lv = (ListView) purchaseDialog
											.findViewById(R.id.myListView);
									final Button asc = (Button) purchaseDialog
											.findViewById(R.id.asc);
									final Button desc = (Button) purchaseDialog
											.findViewById(R.id.desc);
									final Button region = (Button) purchaseDialog
											.findViewById(R.id.region);

									region.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											final CharSequence[] items = {
													"Hong Kong", "Kowloon",
													"New Territories" };
											AlertDialog.Builder builder = new AlertDialog.Builder(
													v.getContext());
											builder.setTitle("Make your selection");
											final Context c = v.getContext();
											builder.setItems(
													items,
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int item) {
															// Do something with
															// the
															// selection
															switch (item) {
															case 0:
																AlertDialog.Builder builder = new AlertDialog.Builder(
																		c);
																builder.setTitle("Make your selection");

																final CharSequence[] hki = {
																		"Central and Western",
																		"Eastern",
																		"Southern",
																		"Wan Chai" };
																builder.setItems(
																		hki,
																		new DialogInterface.OnClickListener() {

																			@Override
																			public void onClick(
																					DialogInterface dialog,
																					int which) {
																				// TODO
																				// Auto-generated
																				// method
																				// stub
																				HttpClient httpclient = new DefaultHttpClient();
																				HttpPost httppost = new HttpPost(
																						"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
																								+ isbn
																								+ "&REGION="
																								+ hki[which]
																										.toString()
																										.replace(
																												" ",
																												"+"));

																				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
																				HttpResponse response;
																				try {
																					response = httpclient
																							.execute(httppost);
																					HttpEntity entity = response
																							.getEntity();
																					InputStream is = entity
																							.getContent();

																					JSONObject obj = new JSONObject(
																							convertStreamToString(is));

																					for (int i = 0; i < obj
																							.getJSONArray(
																									"Products")
																							.length(); i++) {
																						HashMap<String, Object> item = new HashMap<String, Object>();
																						item.put(
																								"price",
																								"HKD"
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"price"));
																						item.put(
																								"owner_description",
																								"Owner Description : "
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"owner_description"));
																						item.put(
																								"userName",
																								"Username : "
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"userName"));
																						item.put(
																								"district",
																								"District : "
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"district"));
																						item.put(
																								"sid",
																								""
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"s_id"));
																						try {
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"images") != null) {
																								item.put(
																										"images",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"images"));
																							}
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"lat") != null) {
																								item.put(
																										"lat",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"lat"));
																								Log.i("halo",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"lat"));
																							}
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"log") != null) {
																								item.put(
																										"log",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"log"));
																							}
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"address") != null) {
																								item.put(
																										"address",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"address"));
																							}
																						} catch (Exception e) {

																						}
																						list.add(item);
																					}

																				} catch (ClientProtocolException e) {
																					// TODO
																					// Auto-generated
																					// catch
																					// block
																					e.printStackTrace();
																				} catch (Exception e) {
																					// TODO
																					// Auto-generated
																					// catch
																					// block
																					e.printStackTrace();
																				}
																				// commentTextvView.setText(temp);

																				if (list.size() == 0) {
																					HashMap<String, Object> item = new HashMap<String, Object>();
																					item.put(
																							"price",
																							"No Result Found");
																					list.add(item);
																				}

																				SimpleAdapter adapter = new SimpleAdapter(
																						getActivity(),
																						list,
																						R.layout.purchase_listview,
																						new String[] {
																								"price",
																								"owner_description",
																								"userName",
																								"district",
																								"sid",
																								"images",
																								"lat",
																								"log",
																								"address" },
																						new int[] {
																								R.id.textView1,
																								R.id.textView2,
																								R.id.textView3,
																								R.id.textView4,
																								R.id.textView5,
																								R.id.textView6,
																								R.id.textView7,
																								R.id.textView8,
																								R.id.textView9 });
																				if (!list
																						.get(0)
																						.get("price")
																						.equals("No Result Found"))
																					lv.setOnItemClickListener(new OnItemClickListener() {

																						@Override
																						public void onItemClick(
																								AdapterView<?> arg0,
																								View arg1,
																								int arg2,
																								long arg3) {
																							// TODO

																							final String price = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("price")
																									.toString();
																							// seven
																							if (!price
																									.equals("No Result Found")) {
																								final String b = ((HashMap<String, Object>) arg0
																										.getItemAtPosition(arg2))
																										.get("owner_description")
																										.toString();
																								final String hisusername = ((HashMap<String, Object>) arg0
																										.getItemAtPosition(arg2))
																										.get("userName")
																										.toString();
																								final String district = ((HashMap<String, Object>) arg0
																										.getItemAtPosition(arg2))
																										.get("district")
																										.toString();
																								final String sid = ((HashMap<String, Object>) arg0
																										.getItemAtPosition(arg2))
																										.get("sid")
																										.toString();

																								String message = "Do you want to purchase this item in "
																										+ price
																										+ " at "
																										+ district
																										+ "?";
																								String address = "";
																								if (((HashMap<String, Object>) arg0
																										.getItemAtPosition(arg2))
																										.get("address") != null
																										&& ((HashMap<String, Object>) arg0
																												.getItemAtPosition(arg2))
																												.get("address")
																												.toString()
																												.length() > 0) {
																									message += "\n(around "
																											+ ((HashMap<String, Object>) arg0
																													.getItemAtPosition(arg2))
																													.get("address")
																													.toString()
																											+ ")";
																								}

																								// firstPurchase
																								ImageView image = new ImageView(
																										arg0.getContext());
																								image.setImageResource(R.drawable.ic_launcher);
																								if (((HashMap<String, Object>) arg0
																										.getItemAtPosition(arg2))
																										.get("images") != null
																										&& ((HashMap<String, Object>) arg0
																												.getItemAtPosition(arg2))
																												.get("images")
																												.toString()
																												.length() > 0) {
																									try {
																										Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																												.drawableFromUrl2(((HashMap<String, Object>) arg0
																														.getItemAtPosition(arg2))
																														.get("images")
																														.toString())))
																												.getBitmap();
																										image.setImageDrawable(new BitmapDrawable(
																												getResources(),
																												Bitmap.createScaledBitmap(
																														bitmap,
																														600,
																														600,
																														true)));
																										// Set
																										// your
																										// new,
																										// scaled
																										// drawable
																										// "d");
																									} catch (IOException e) {
																										// TODO
																										// Auto-generated
																										// catch
																										// block
																										e.printStackTrace();
																									}
																								}
																								new AlertDialog.Builder(
																										arg0.getContext())
																										.setMessage(
																												message)
																										.setCancelable(
																												false)
																										.setPositiveButton(
																												"Yes",
																												new DialogInterface.OnClickListener() {
																													public void onClick(
																															DialogInterface dialog,
																															int id) {
																														HttpClient httpclient = new DefaultHttpClient();
																														HttpPost httppost = new HttpPost(
																																"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																														List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																														nameValuePairs
																																.add(new BasicNameValuePair(
																																		"sid",
																																		sid));
																														nameValuePairs
																																.add(new BasicNameValuePair(
																																		"username",
																																		username));
																														try {
																															httppost.setEntity(new UrlEncodedFormEntity(
																																	nameValuePairs));
																															HttpResponse response1 = httpclient
																																	.execute(httppost);

																															HttpEntity entity = response1
																																	.getEntity();
																															InputStream is = entity
																																	.getContent();
																														} catch (Exception e) {
																															e.printStackTrace();
																														}
																													}
																												})
																										.setNegativeButton(
																												"No",
																												null)
																										.setView(
																												image)
																										.show();
																							}
																						}
																					});
																				lv.setAdapter(adapter);
																				purchaseDialog
																						.show();
																			}
																		});
																builder.show();
																break;
															case 1:
																AlertDialog.Builder builder2 = new AlertDialog.Builder(
																		c);
																builder2.setTitle("Make your selection");
																final CharSequence[] kl = {
																		"Sham Shui Po",
																		"Kowloon City",
																		"Kwun Tong",
																		"Wong Tai Sin",
																		"Yau Tsim Mong" };
																builder2.setItems(
																		kl,
																		new DialogInterface.OnClickListener() {

																			@Override
																			public void onClick(
																					DialogInterface dialog,
																					int which) {
																				// TODO
																				// Auto-generated
																				// method
																				// stub
																				HttpClient httpclient = new DefaultHttpClient();
																				HttpPost httppost = new HttpPost(
																						"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
																								+ isbn
																								+ "&REGION="
																								+ kl[which]
																										.toString()
																										.replace(
																												" ",
																												"+"));

																				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
																				HttpResponse response;
																				try {
																					response = httpclient
																							.execute(httppost);
																					HttpEntity entity = response
																							.getEntity();
																					InputStream is = entity
																							.getContent();

																					JSONObject obj = new JSONObject(
																							convertStreamToString(is));
																					if (obj.getJSONArray("Products") != null)
																						for (int i = 0; i < obj
																								.getJSONArray(
																										"Products")
																								.length(); i++) {
																							HashMap<String, Object> item = new HashMap<String, Object>();
																							item.put(
																									"price",
																									"HKD"
																											+ obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"price"));
																							item.put(
																									"owner_description",
																									"Owner Description : "
																											+ obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"owner_description"));
																							item.put(
																									"userName",
																									"Username : "
																											+ obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"userName"));
																							item.put(
																									"district",
																									"District : "
																											+ obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"district"));
																							item.put(
																									"sid",
																									""
																											+ obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"s_id"));
																							try {
																								if (obj.getJSONArray(
																										"Products")
																										.getJSONObject(
																												i)
																										.getString(
																												"images") != null) {
																									item.put(
																											"images",
																											obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"images"));
																								}
																								if (obj.getJSONArray(
																										"Products")
																										.getJSONObject(
																												i)
																										.getString(
																												"lat") != null) {
																									item.put(
																											"lat",
																											obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"lat"));
																									Log.i("halo",
																											obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"lat"));
																								}
																								if (obj.getJSONArray(
																										"Products")
																										.getJSONObject(
																												i)
																										.getString(
																												"log") != null) {
																									item.put(
																											"log",
																											obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"log"));
																								}
																								if (obj.getJSONArray(
																										"Products")
																										.getJSONObject(
																												i)
																										.getString(
																												"address") != null) {
																									item.put(
																											"address",
																											obj.getJSONArray(
																													"Products")
																													.getJSONObject(
																															i)
																													.getString(
																															"address"));
																								}
																							} catch (Exception e) {

																							}
																							list.add(item);
																						}

																				} catch (ClientProtocolException e) {
																					// TODO
																					// Auto-generated
																					// catch
																					// block
																					e.printStackTrace();
																				} catch (Exception e) {
																					// TODO
																					// Auto-generated
																					// catch
																					// block
																					e.printStackTrace();
																				}

																				SimpleAdapter adapter = new SimpleAdapter(
																						getActivity(),
																						list,
																						R.layout.purchase_listview,
																						new String[] {
																								"price",
																								"owner_description",
																								"userName",
																								"district",
																								"sid",
																								"images",
																								"lat",
																								"log",
																								"address" },
																						new int[] {
																								R.id.textView1,
																								R.id.textView2,
																								R.id.textView3,
																								R.id.textView4,
																								R.id.textView5,
																								R.id.textView6,
																								R.id.textView7,
																								R.id.textView8,
																								R.id.textView9 });
																				if (list.size() == 0) {
																					HashMap<String, Object> item = new HashMap<String, Object>();
																					item.put(
																							"price",
																							"No Result Found");
																					list.add(item);
																				}// six
																				lv.setOnItemClickListener(new OnItemClickListener() {

																					@Override
																					public void onItemClick(
																							AdapterView<?> arg0,
																							View arg1,
																							int arg2,
																							long arg3) {
																						// TODO
																						// Auto-generated
																						// method
																						// stub

																						final String price = ((HashMap<String, Object>) arg0
																								.getItemAtPosition(arg2))
																								.get("price")
																								.toString();

																						if (!price
																								.equals("No Result Found")) {
																							final String b = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("owner_description")
																									.toString();
																							final String hisusername = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("userName")
																									.toString();
																							final String district = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("district")
																									.toString();
																							final String sid = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("sid")
																									.toString();

																							String message = "Do you want to purchase this item in "
																									+ price
																									+ " at "
																									+ district
																									+ "?";
																							String address = "";
																							if (((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("address") != null
																									&& ((HashMap<String, Object>) arg0
																											.getItemAtPosition(arg2))
																											.get("address")
																											.toString()
																											.length() > 0) {
																								message += "\n(around "
																										+ ((HashMap<String, Object>) arg0
																												.getItemAtPosition(arg2))
																												.get("address")
																												.toString()
																										+ ")";
																							}

																							// firstPurchase
																							ImageView image = new ImageView(
																									arg0.getContext());
																							image.setImageResource(R.drawable.ic_launcher);
																							if (((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("images") != null
																									&& ((HashMap<String, Object>) arg0
																											.getItemAtPosition(arg2))
																											.get("images")
																											.toString()
																											.length() > 0) {
																								try {
																									Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																											.drawableFromUrl2(((HashMap<String, Object>) arg0
																													.getItemAtPosition(arg2))
																													.get("images")
																													.toString())))
																											.getBitmap();
																									image.setImageDrawable(new BitmapDrawable(
																											getResources(),
																											Bitmap.createScaledBitmap(
																													bitmap,
																													600,
																													600,
																													true)));
																									// Set
																									// your
																									// new,
																									// scaled
																									// drawable
																									// "d");
																								} catch (IOException e) {
																									// TODO
																									// Auto-generated
																									// catch
																									// block
																									e.printStackTrace();
																								}
																							}
																							new AlertDialog.Builder(
																									arg0.getContext())
																									.setMessage(
																											message)
																									.setCancelable(
																											false)
																									.setPositiveButton(
																											"Yes",
																											new DialogInterface.OnClickListener() {
																												public void onClick(
																														DialogInterface dialog,
																														int id) {
																													HttpClient httpclient = new DefaultHttpClient();
																													HttpPost httppost = new HttpPost(
																															"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																													List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																													nameValuePairs
																															.add(new BasicNameValuePair(
																																	"sid",
																																	sid));
																													nameValuePairs
																															.add(new BasicNameValuePair(
																																	"username",
																																	username));
																													try {
																														httppost.setEntity(new UrlEncodedFormEntity(
																																nameValuePairs));
																														HttpResponse response1 = httpclient
																																.execute(httppost);

																														HttpEntity entity = response1
																																.getEntity();
																														InputStream is = entity
																																.getContent();
																													} catch (Exception e) {
																														e.printStackTrace();
																													}
																												}
																											})
																									.setNegativeButton(
																											"No",
																											null)
																									.setView(
																											image)
																									.show();
																						}
																					}
																				});
																				lv.setAdapter(adapter);
																				purchaseDialog
																						.show();
																			}
																		});
																builder2.show();
																break;
															case 2:
																AlertDialog.Builder builder3 = new AlertDialog.Builder(
																		c);
																builder3.setTitle("Make your selection");

																final CharSequence[] nt = {
																		"Islands",
																		"Kwai Tsing",
																		"North",
																		"Sai Kung",
																		"Sha Tin",
																		"Tai Po",
																		"Tsuen Wan",
																		"Tuen Mun",
																		"Yuen Long" };
																builder3.setItems(
																		nt,
																		new DialogInterface.OnClickListener() {

																			@Override
																			public void onClick(
																					DialogInterface dialog,
																					int which) {
																				// TODO
																				// Auto-generated
																				// method
																				// stub
																				HttpClient httpclient = new DefaultHttpClient();
																				HttpPost httppost = new HttpPost(
																						"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
																								+ isbn
																								+ "&REGION="
																								+ nt[which]
																										.toString()
																										.replace(
																												" ",
																												"+"));

																				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
																				HttpResponse response;
																				try {
																					response = httpclient
																							.execute(httppost);
																					HttpEntity entity = response
																							.getEntity();
																					InputStream is = entity
																							.getContent();

																					JSONObject obj = new JSONObject(
																							convertStreamToString(is));

																					for (int i = 0; i < obj
																							.getJSONArray(
																									"Products")
																							.length(); i++) {
																						HashMap<String, Object> item = new HashMap<String, Object>();
																						item.put(
																								"price",
																								"HKD"
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"price"));
																						item.put(
																								"owner_description",
																								"Owner Description : "
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"owner_description"));
																						item.put(
																								"userName",
																								"Username : "
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"userName"));
																						item.put(
																								"district",
																								"District : "
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"district"));
																						item.put(
																								"sid",
																								""
																										+ obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"s_id"));
																						try {
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"images") != null) {
																								item.put(
																										"images",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"images"));
																							}
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"lat") != null) {
																								item.put(
																										"lat",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"lat"));
																								Log.i("halo",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"lat"));
																							}
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"log") != null) {
																								item.put(
																										"log",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"log"));
																							}
																							if (obj.getJSONArray(
																									"Products")
																									.getJSONObject(
																											i)
																									.getString(
																											"address") != null) {
																								item.put(
																										"address",
																										obj.getJSONArray(
																												"Products")
																												.getJSONObject(
																														i)
																												.getString(
																														"address"));
																							}
																						} catch (Exception e) {

																						}
																						list.add(item);
																					}

																				} catch (ClientProtocolException e) {
																					// TODO
																					// Auto-generated
																					// catch
																					// block
																					e.printStackTrace();
																				} catch (Exception e) {
																					// TODO
																					// Auto-generated
																					// catch
																					// block
																					e.printStackTrace();
																				}
																				// commentTextvView.setText(temp);
																				// five
																				if (list.size() == 0) {// ((String)list.get(0).get("price")).equals("No Result Found")
																										// )
																										// {
																					HashMap<String, Object> item = new HashMap<String, Object>();
																					item.put(
																							"price",
																							"No Result Found");
																					list.add(item);
																				}

																				SimpleAdapter adapter = new SimpleAdapter(
																						getActivity(),
																						list,
																						R.layout.purchase_listview,
																						new String[] {
																								"price",
																								"owner_description",
																								"userName",
																								"district",
																								"sid",
																								"images",
																								"lat",
																								"log",
																								"address" },
																						new int[] {
																								R.id.textView1,
																								R.id.textView2,
																								R.id.textView3,
																								R.id.textView4,
																								R.id.textView5,
																								R.id.textView6,
																								R.id.textView7,
																								R.id.textView8,
																								R.id.textView9 });

																				lv.setOnItemClickListener(new OnItemClickListener() {

																					@Override
																					public void onItemClick(
																							AdapterView<?> arg0,
																							View arg1,
																							int arg2,
																							long arg3) {
																						// TODO
																						// Auto-generated
																						// method
																						// stub

																						final String price = ((HashMap<String, Object>) arg0
																								.getItemAtPosition(arg2))
																								.get("price")
																								.toString();

																						if (!price
																								.equals("No Result Found")) {
																							final String b = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("owner_description")
																									.toString();
																							final String hisusername = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("userName")
																									.toString();
																							final String district = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("district")
																									.toString();
																							final String sid = ((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("sid")
																									.toString();
																							String message = "Do you want to purchase this item in "
																									+ price
																									+ " at "
																									+ district
																									+ "?";
																							String address = "";
																							if (((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("address") != null
																									&& ((HashMap<String, Object>) arg0
																											.getItemAtPosition(arg2))
																											.get("address")
																											.toString()
																											.length() > 0) {
																								message += "\n(around "
																										+ ((HashMap<String, Object>) arg0
																												.getItemAtPosition(arg2))
																												.get("address")
																												.toString()
																										+ ")";
																							}

																							// firstPurchase
																							ImageView image = new ImageView(
																									arg0.getContext());
																							image.setImageResource(R.drawable.ic_launcher);
																							if (((HashMap<String, Object>) arg0
																									.getItemAtPosition(arg2))
																									.get("images") != null
																									&& ((HashMap<String, Object>) arg0
																											.getItemAtPosition(arg2))
																											.get("images")
																											.toString()
																											.length() > 0) {
																								try {
																									Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																											.drawableFromUrl2(((HashMap<String, Object>) arg0
																													.getItemAtPosition(arg2))
																													.get("images")
																													.toString())))
																											.getBitmap();
																									image.setImageDrawable(new BitmapDrawable(
																											getResources(),
																											Bitmap.createScaledBitmap(
																													bitmap,
																													600,
																													600,
																													true)));
																									// Set
																									// your
																									// new,
																									// scaled
																									// drawable
																									// "d");
																								} catch (IOException e) {
																									// TODO
																									// Auto-generated
																									// catch
																									// block
																									e.printStackTrace();
																								}
																							}
																							new AlertDialog.Builder(
																									arg0.getContext())
																									.setMessage(
																											message)
																									.setCancelable(
																											false)
																									.setPositiveButton(
																											"Yes",
																											new DialogInterface.OnClickListener() {
																												public void onClick(
																														DialogInterface dialog,
																														int id) {
																													HttpClient httpclient = new DefaultHttpClient();
																													HttpPost httppost = new HttpPost(
																															"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																													List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																													nameValuePairs
																															.add(new BasicNameValuePair(
																																	"sid",
																																	sid));
																													nameValuePairs
																															.add(new BasicNameValuePair(
																																	"username",
																																	username));
																													try {
																														httppost.setEntity(new UrlEncodedFormEntity(
																																nameValuePairs));
																														HttpResponse response1 = httpclient
																																.execute(httppost);

																														HttpEntity entity = response1
																																.getEntity();
																														InputStream is = entity
																																.getContent();
																													} catch (Exception e) {
																														e.printStackTrace();
																													}
																												}
																											})
																									.setNegativeButton(
																											"No",
																											null)
																									.setView(
																											image)
																									.show();
																						}
																					}
																				});
																				lv.setAdapter(adapter);
																				purchaseDialog
																						.show();
																			}
																		});
																builder3.show();
																break;
															default:
																break;
															}
														}
													});
											builder.show();

											AlertDialog alert = builder
													.create();
											HttpClient httpclient = new DefaultHttpClient();
											HttpPost httppost = new HttpPost(
													"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
															+ isbn
															+ "&REGION=1");

											ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
											HttpResponse response;

											try {
												response = httpclient
														.execute(httppost);
												HttpEntity entity = response
														.getEntity();
												InputStream is = entity
														.getContent();

												JSONObject obj = new JSONObject(
														convertStreamToString(is));
												if (obj.getJSONArray("Products") != null)
													for (int i = 0; i < obj
															.getJSONArray(
																	"Products")
															.length(); i++) {
														HashMap<String, Object> item = new HashMap<String, Object>();
														item.put(
																"price",
																"HKD"
																		+ obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"price"));
														item.put(
																"owner_description",
																"Owner Description : "
																		+ obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"owner_description"));
														item.put(
																"userName",
																"Username : "
																		+ obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"userName"));
														item.put(
																"district",
																"District : "
																		+ obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"district"));
														item.put(
																"sid",
																""
																		+ obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"s_id"));
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"images") != null) {
															item.put(
																	"images",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"images"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"lat") != null) {
															item.put(
																	"lat",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"lat"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"log") != null) {
															item.put(
																	"log",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"log"));
														}

														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"address") != null) {
															item.put(
																	"address",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"address"));
														}
														try {
															if (obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"images") != null) {
																item.put(
																		"images",
																		obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"images"));
															}
															if (obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"lat") != null) {
																item.put(
																		"lat",
																		obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"lat"));
																Log.i("halo",
																		obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"lat"));
															}
															if (obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"log") != null) {
																item.put(
																		"log",
																		obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"log"));
															}
															if (obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"address") != null) {
																item.put(
																		"address",
																		obj.getJSONArray(
																				"Products")
																				.getJSONObject(
																						i)
																				.getString(
																						"address"));
															}
														} catch (Exception e) {

														}
														list.add(item);
													}

											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											// commentTextvView.setText(temp);
											if (list.size() == 0) {

												HashMap<String, Object> item = new HashMap<String, Object>();
												item.put("price",
														"No Result Found");
												list.add(item);
											}
											SimpleAdapter adapter = new SimpleAdapter(
													getActivity(),
													list,
													R.layout.purchase_listview,
													new String[] {
															"price",
															"owner_description",
															"userName",
															"district", "sid",
															"images", "lat",
															"log", "address" },
													new int[] { R.id.textView1,
															R.id.textView2,
															R.id.textView3,
															R.id.textView4,
															R.id.textView5,
															R.id.textView6,
															R.id.textView7,
															R.id.textView8,
															R.id.textView9 });
											if (!list.get(0).get("price")
													.equals("No Result Found"))
												lv.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO Auto-generated
														// method

														final String price = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("price")
																.toString();

														if (!price
																.equals("No Result Found")) {
															final String b = ((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("owner_description")
																	.toString();
															final String hisusername = ((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("userName")
																	.toString();
															final String district = ((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("district")
																	.toString();
															final String sid = ((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("sid")
																	.toString();

															String message = "Do you want to purchase this item in "
																	+ price
																	+ " at "
																	+ district
																	+ "?";
															String images = "";
															String lat = "";
															String log = "";
															String address = "";
															if (((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("address") != null
																	&& ((HashMap<String, Object>) arg0
																			.getItemAtPosition(arg2))
																			.get("address")
																			.toString()
																			.length() > 0) {
																message += "\n(around "
																		+ ((HashMap<String, Object>) arg0
																				.getItemAtPosition(arg2))
																				.get("address")
																				.toString()
																		+ ")";
															}

															// firstPurchase
															ImageView image = new ImageView(
																	arg0.getContext());
															image.setImageResource(R.drawable.ic_launcher);
															if (((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("images") != null
																	&& ((HashMap<String, Object>) arg0
																			.getItemAtPosition(arg2))
																			.get("images")
																			.toString()
																			.length() > 0) {
																try {
																	Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																			.drawableFromUrl2(((HashMap<String, Object>) arg0
																					.getItemAtPosition(arg2))
																					.get("images")
																					.toString())))
																			.getBitmap();
																	image.setImageDrawable(new BitmapDrawable(
																			getResources(),
																			Bitmap.createScaledBitmap(
																					bitmap,
																					600,
																					600,
																					true)));
																	// Set your
																	// new,
																	// scaled
																	// drawable
																	// "d");
																} catch (IOException e) {
																	// TODO
																	// Auto-generated
																	// catch
																	// block
																	e.printStackTrace();
																}
															}
															new AlertDialog.Builder(
																	arg0.getContext())
																	.setMessage(
																			message)
																	.setCancelable(
																			false)
																	.setPositiveButton(
																			"Yes",
																			new DialogInterface.OnClickListener() {
																				public void onClick(
																						DialogInterface dialog,
																						int id) {
																					HttpClient httpclient = new DefaultHttpClient();
																					HttpPost httppost = new HttpPost(
																							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																					nameValuePairs
																							.add(new BasicNameValuePair(
																									"sid",
																									sid));
																					nameValuePairs
																							.add(new BasicNameValuePair(
																									"username",
																									username));
																					try {
																						httppost.setEntity(new UrlEncodedFormEntity(
																								nameValuePairs));
																						HttpResponse response1 = httpclient
																								.execute(httppost);

																						HttpEntity entity = response1
																								.getEntity();
																						InputStream is = entity
																								.getContent();
																					} catch (Exception e) {
																						e.printStackTrace();
																					}
																				}
																			})
																	.setNegativeButton(
																			"No",
																			null)
																	.setView(
																			image)
																	.show();
														}// fourpur
													}

												});
											lv.setAdapter(adapter);
											purchaseDialog.show();
										}
									});

									desc.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											HttpClient httpclient = new DefaultHttpClient();
											HttpPost httppost = new HttpPost(
													"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
															+ isbn + "&DESC=1");

											ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
											HttpResponse response;

											try {
												response = httpclient
														.execute(httppost);
												HttpEntity entity = response
														.getEntity();
												InputStream is = entity
														.getContent();

												JSONObject obj = new JSONObject(
														convertStreamToString(is));

												for (int i = 0; i < obj
														.getJSONArray(
																"Products")
														.length(); i++) {
													HashMap<String, Object> item = new HashMap<String, Object>();
													item.put(
															"price",
															"HKD"
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"price"));
													item.put(
															"owner_description",
															"Owner Description : "
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"owner_description"));
													item.put(
															"userName",
															"Username : "
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"userName"));
													item.put(
															"district",
															"District : "
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"district"));
													item.put(
															"sid",
															""
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"s_id"));
													try {
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"images") != null) {
															item.put(
																	"images",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"images"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"lat") != null) {
															item.put(
																	"lat",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"lat"));
															Log.i("halo",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"lat"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"log") != null) {
															item.put(
																	"log",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"log"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"address") != null) {
															item.put(
																	"address",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"address"));
														}
													} catch (Exception e) {

													}
													list.add(item);
												}

											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											// commentTextvView.setText(temp);

											if (list.size() == 0) {

												HashMap<String, Object> item = new HashMap<String, Object>();
												item.put("price",
														"No Result Found");
												list.add(item);
											}
											SimpleAdapter adapter = new SimpleAdapter(
													getActivity(),
													list,
													R.layout.purchase_listview,
													new String[] {
															"price",
															"owner_description",
															"userName",
															"district", "sid",
															"images", "lat",
															"log", "address" },
													new int[] { R.id.textView1,
															R.id.textView2,
															R.id.textView3,
															R.id.textView4,
															R.id.textView5,
															R.id.textView6,
															R.id.textView7,
															R.id.textView8,
															R.id.textView9 });
											if (!list.get(0).get("price")
													.equals("No Result Found"))
												lv.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO Auto-generated
														// method
														final String price = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("price")
																.toString();
														final String b = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("owner_description")
																.toString();
														final String hisusername = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("userName")
																.toString();
														final String district = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("district")
																.toString();
														final String sid = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("sid")
																.toString();
														String message = "Do you want to purchase this item in "
																+ price
																+ " at "
																+ district
																+ "?";
														if (((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("address") != null
																&& ((HashMap<String, Object>) arg0
																		.getItemAtPosition(arg2))
																		.get("address")
																		.toString()
																		.length() > 0) {
															message += "\n(around "
																	+ ((HashMap<String, Object>) arg0
																			.getItemAtPosition(arg2))
																			.get("address")
																			.toString()
																	+ ")";
														}

														// firstPurchase
														ImageView image = new ImageView(
																arg0.getContext());
														image.setImageResource(R.drawable.ic_launcher);
														if (((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("images") != null
																&& ((HashMap<String, Object>) arg0
																		.getItemAtPosition(arg2))
																		.get("images")
																		.toString()
																		.length() > 0) {
															try {
																Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																		.drawableFromUrl2(((HashMap<String, Object>) arg0
																				.getItemAtPosition(arg2))
																				.get("images")
																				.toString())))
																		.getBitmap();
																image.setImageDrawable(new BitmapDrawable(
																		getResources(),
																		Bitmap.createScaledBitmap(
																				bitmap,
																				600,
																				600,
																				true)));
																// Set your new,
																// scaled
																// drawable
																// "d");
															} catch (IOException e) {
																// TODO
																// Auto-generated
																// catch block
																e.printStackTrace();
															}
														}

														new AlertDialog.Builder(
																arg0.getContext())
																.setMessage(
																		message)
																.setCancelable(
																		false)
																.setPositiveButton(
																		"Yes",
																		new DialogInterface.OnClickListener() {
																			public void onClick(
																					DialogInterface dialog,
																					int id) {
																				HttpClient httpclient = new DefaultHttpClient();
																				HttpPost httppost = new HttpPost(
																						"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																				nameValuePairs
																						.add(new BasicNameValuePair(
																								"sid",
																								sid));
																				nameValuePairs
																						.add(new BasicNameValuePair(
																								"username",
																								username));
																				try {
																					httppost.setEntity(new UrlEncodedFormEntity(
																							nameValuePairs));
																					HttpResponse response1 = httpclient
																							.execute(httppost);

																					HttpEntity entity = response1
																							.getEntity();
																					InputStream is = entity
																							.getContent();
																				} catch (Exception e) {
																					e.printStackTrace();
																				}
																			}
																		})
																.setNegativeButton(
																		"No",
																		null)
																.setView(image)
																.show();
													}// thirdPur

												});
											lv.setAdapter(adapter);
											purchaseDialog.show();
										}
									});

									asc.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub

											HttpClient httpclient = new DefaultHttpClient();
											HttpPost httppost = new HttpPost(
													"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
															+ isbn + "&ASC=1");

											ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
											HttpResponse response;

											try {
												response = httpclient
														.execute(httppost);
												HttpEntity entity = response
														.getEntity();
												InputStream is = entity
														.getContent();

												JSONObject obj = new JSONObject(
														convertStreamToString(is));

												for (int i = 0; i < obj
														.getJSONArray(
																"Products")
														.length(); i++) {
													HashMap<String, Object> item = new HashMap<String, Object>();
													item.put(
															"price",
															"HKD"
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"price"));
													item.put(
															"owner_description",
															"Owner Description : "
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"owner_description"));
													item.put(
															"userName",
															"Username : "
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"userName"));
													item.put(
															"district",
															"District : "
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"district"));
													item.put(
															"sid",
															""
																	+ obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"s_id"));
													Log.i("asd",
															obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"district"));
													try {
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"images") != null) {
															item.put(
																	"images",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"images"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"lat") != null) {
															item.put(
																	"lat",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"lat"));
															Log.i("halo",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"lat"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"log") != null) {
															item.put(
																	"log",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"log"));
														}
														if (obj.getJSONArray(
																"Products")
																.getJSONObject(
																		i)
																.getString(
																		"address") != null) {
															item.put(
																	"address",
																	obj.getJSONArray(
																			"Products")
																			.getJSONObject(
																					i)
																			.getString(
																					"address"));
														}
													} catch (Exception e) {

													}// secondpur
													list.add(item);
												}

											} catch (ClientProtocolException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
											// commentTextvView.setText(temp);

											if (list.size() == 0) {

												HashMap<String, Object> item = new HashMap<String, Object>();
												item.put("price",
														"No Result Found");
												list.add(item);
											}
											SimpleAdapter adapter = new SimpleAdapter(
													getActivity(),
													list,
													R.layout.purchase_listview,
													new String[] {
															"price",
															"owner_description",
															"userName",
															"district", "sid",
															"images", "lat",
															"log", "address" },
													new int[] { R.id.textView1,
															R.id.textView2,
															R.id.textView3,
															R.id.textView4,
															R.id.textView5,
															R.id.textView6,
															R.id.textView7,
															R.id.textView8,
															R.id.textView9 });
											if (!list.get(0).get("price")
													.equals("No Result Found"))
												lv.setOnItemClickListener(new OnItemClickListener() {

													@Override
													public void onItemClick(
															AdapterView<?> arg0,
															View arg1,
															int arg2, long arg3) {
														// TODO Auto-generated
														// method
														// stub
														// Toast.makeText(arg0.getContext(),
														// ((TextView)arg0.findViewById(R.id.textView5)).getText()
														// + "", 1000);

														final String price = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("price")
																.toString();
														final String b = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("owner_description")
																.toString();
														final String hisusername = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("userName")
																.toString();
														final String district = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("district")
																.toString();
														final String sid = ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("sid")
																.toString();

														String message = "Do you want to purchase this item in "
																+ price
																+ " at "
																+ district
																+ "?";
														if (((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("address") != null
																&& ((HashMap<String, Object>) arg0
																		.getItemAtPosition(arg2))
																		.get("address")
																		.toString()
																		.length() > 0) {
															message += "\n(around "
																	+ ((HashMap<String, Object>) arg0
																			.getItemAtPosition(arg2))
																			.get("address")
																			.toString()
																	+ ")";
														}

														// firstPurchase
														ImageView image = new ImageView(
																arg0.getContext());
														image.setImageResource(R.drawable.ic_launcher);
														if (((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("images") != null
																&& ((HashMap<String, Object>) arg0
																		.getItemAtPosition(arg2))
																		.get("images")
																		.toString()
																		.length() > 0) {
															try {
																Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																		.drawableFromUrl2(((HashMap<String, Object>) arg0
																				.getItemAtPosition(arg2))
																				.get("images")
																				.toString())))
																		.getBitmap();
																image.setImageDrawable(new BitmapDrawable(
																		getResources(),
																		Bitmap.createScaledBitmap(
																				bitmap,
																				600,
																				600,
																				true)));
																// Set your new,
																// scaled
																// drawable
																// "d");
															} catch (IOException e) {
																// TODO
																// Auto-generated
																// catch block
																e.printStackTrace();
															}
														}

														new AlertDialog.Builder(
																arg0.getContext())
																.setMessage(
																		message)
																.setCancelable(
																		false)
																.setPositiveButton(
																		"Yes",
																		new DialogInterface.OnClickListener() {
																			public void onClick(
																					DialogInterface dialog,
																					int id) {
																				HttpClient httpclient = new DefaultHttpClient();
																				HttpPost httppost = new HttpPost(
																						"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																				nameValuePairs
																						.add(new BasicNameValuePair(
																								"sid",
																								sid));
																				nameValuePairs
																						.add(new BasicNameValuePair(
																								"username",
																								username));
																				try {
																					httppost.setEntity(new UrlEncodedFormEntity(
																							nameValuePairs));
																					HttpResponse response1 = httpclient
																							.execute(httppost);

																					HttpEntity entity = response1
																							.getEntity();
																					InputStream is = entity
																							.getContent();
																				} catch (Exception e) {
																					e.printStackTrace();
																				}
																			}
																		})
																.setNegativeButton(
																		"No",
																		null)
																.setView(image)
																.show();
													}

												});
											lv.setAdapter(adapter);
											purchaseDialog.show();
										}
									});

									HttpClient httpclient = new DefaultHttpClient();
									HttpPost httppost = new HttpPost(
											"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchase.php?isbn="
													+ isbn);

									ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
									HttpResponse response;

									try {
										response = httpclient.execute(httppost);
										HttpEntity entity = response
												.getEntity();
										InputStream is = entity.getContent();

										JSONObject obj = new JSONObject(
												convertStreamToString(is));

										for (int i = 0; i < obj.getJSONArray(
												"Products").length(); i++) {
											HashMap<String, Object> item = new HashMap<String, Object>();
											item.put(
													"price",
													"HKD"
															+ obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"price"));
											item.put(
													"owner_description",
													"Owner Description : "
															+ obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"owner_description"));
											item.put(
													"userName",
													"Username : "
															+ obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"userName"));
											item.put(
													"district",
													"District : "
															+ obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"district"));
											item.put(
													"sid",
													""
															+ obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"s_id"));
											try {
												if (obj.getJSONArray("Products")
														.getJSONObject(i)
														.getString("images") != null) {
													item.put(
															"images",
															obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"images"));
												}
												if (obj.getJSONArray("Products")
														.getJSONObject(i)
														.getString("lat") != null) {
													item.put(
															"lat",
															obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"lat"));
													Log.i("halo",
															obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"lat"));
												}
												if (obj.getJSONArray("Products")
														.getJSONObject(i)
														.getString("log") != null) {
													item.put(
															"log",
															obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"log"));
												}
												if (obj.getJSONArray("Products")
														.getJSONObject(i)
														.getString("address") != null) {
													item.put(
															"address",
															obj.getJSONArray(
																	"Products")
																	.getJSONObject(
																			i)
																	.getString(
																			"address"));
												}
											} catch (Exception e) {

											}
											list.add(item);
										}

									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									// commentTextvView.setText(temp);

									if (list.size() == 0) {

										HashMap<String, Object> item = new HashMap<String, Object>();
										item.put("price", "No Result Found");
										list.add(item);
									}
									SimpleAdapter adapter = new SimpleAdapter(
											getActivity(), list,
											R.layout.purchase_listview,
											new String[] { "price",
													"owner_description",
													"userName", "district",
													"sid", "images", "lat",
													"log", "address" },
											new int[] { R.id.textView1,
													R.id.textView2,
													R.id.textView3,
													R.id.textView4,
													R.id.textView5,
													R.id.textView6,
													R.id.textView7,
													R.id.textView8,
													R.id.textView9 });
									if (!list.get(0).get("price")
											.equals("No Result Found"))
										lv.setOnItemClickListener(new OnItemClickListener() {

											@Override
											public void onItemClick(
													AdapterView<?> arg0,
													View arg1, int arg2,
													long arg3) {
												// TODO Auto-generated method
												// stub
												// Toast.makeText(arg0.getContext(),
												// ((TextView)arg0.findViewById(R.id.textView5)).getText()
												// + "", 1000);
												final String price = ((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("price")
														.toString();
												final String b = ((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("owner_description")
														.toString();
												final String hisusername = ((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("userName")
														.toString();
												final String district = ((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("district")
														.toString();
												final String sid = ((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("sid").toString();
												String message = "Do you want to purchase this item in "
														+ price
														+ " at "
														+ district + "?";
												if (((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("address") != null
														&& ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("address")
																.toString()
																.length() > 0) {
													message += "\n(around "
															+ ((HashMap<String, Object>) arg0
																	.getItemAtPosition(arg2))
																	.get("address")
																	.toString()
															+ ")";
												}

												// firstPurchase
												ImageView image = new ImageView(
														arg0.getContext());
												image.setImageResource(R.drawable.ic_launcher);
												if (((HashMap<String, Object>) arg0
														.getItemAtPosition(arg2))
														.get("images") != null
														&& ((HashMap<String, Object>) arg0
																.getItemAtPosition(arg2))
																.get("images")
																.toString()
																.length() > 0) {
													try {
														Bitmap bitmap = ((BitmapDrawable) (ListOfSecondHandBook
																.drawableFromUrl2(((HashMap<String, Object>) arg0
																		.getItemAtPosition(arg2))
																		.get("images")
																		.toString())))
																.getBitmap();
														image.setImageDrawable(new BitmapDrawable(
																getResources(),
																Bitmap.createScaledBitmap(
																		bitmap,
																		600,
																		600,
																		true)));
														// Set your new, scaled
														// drawable "d");
													} catch (IOException e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
													}
												}

												new AlertDialog.Builder(arg0
														.getContext())
														.setMessage(message)
														.setCancelable(false)
														.setPositiveButton(
																"Yes",
																new DialogInterface.OnClickListener() {
																	public void onClick(
																			DialogInterface dialog,
																			int id) {
																		HttpClient httpclient = new DefaultHttpClient();
																		HttpPost httppost = new HttpPost(
																				"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/purchaseAction.php");
																		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
																		nameValuePairs
																				.add(new BasicNameValuePair(
																						"sid",
																						sid));
																		nameValuePairs
																				.add(new BasicNameValuePair(
																						"username",
																						username));
																		try {
																			httppost.setEntity(new UrlEncodedFormEntity(
																					nameValuePairs));
																			HttpResponse response1 = httpclient
																					.execute(httppost);

																			HttpEntity entity = response1
																					.getEntity();
																			InputStream is = entity
																					.getContent();
																			purchaseDialog
																					.cancel();
																		} catch (Exception e) {
																			e.printStackTrace();
																		}
																	}
																})
														.setNegativeButton(
																"No", null)
														.setView(image).show();
											}

										});
									lv.setAdapter(adapter);
									purchaseDialog.show();
								}

							});
							Button d = new Button(getActivity());
							d.setText("Sell");
							final String username = getActivity().getIntent()
									.getExtras().getString("username");
							d.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									final Dialog sellDialog = new Dialog(
											v.getContext(),
											android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
									sellDialog
											.setContentView(R.layout.sell_dialog);
									sellDialog.show();

									Button upload = ((Button) sellDialog
											.findViewById(R.id.upload));
									final ImageView iv = ((ImageView) sellDialog
											.findViewById(R.id.imageView));
									upload.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											String dir = Environment
													.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
													+ "/mobileComputing/";
											File text = new File(dir
													+ "dataone.txt");
											FileOutputStream out;
											try {
												out = new FileOutputStream(text);
												out.write((dir + isbn
														+ username + ".jpg" + "\r\n")
														.getBytes());
												out.close();

											} catch (Exception e) {
												e.printStackTrace();
											}
											Intent takePictureIntent = new Intent(
													MediaStore.ACTION_IMAGE_CAPTURE);

											String file = dir + isbn + username
													+ ".jpg";
											File newfile = new File(file);

											Uri outputFileUri = Uri
													.fromFile(newfile);

											Intent cameraIntent = new Intent(
													MediaStore.ACTION_IMAGE_CAPTURE);
											cameraIntent.putExtra(
													MediaStore.EXTRA_OUTPUT,
													outputFileUri);

											startActivityForResult(
													cameraIntent, 0);
											if (mImageBitmap != null) {
												ImageView iv = ((ImageView) v
														.findViewById(R.id.imageView));
												iv.setImageBitmap(mImageBitmap);
											}

										}
									});

									Spinner sp = ((Spinner) sellDialog
											.findViewById(R.id.spinner1));
									final Spinner sp2 = ((Spinner) sellDialog
											.findViewById(R.id.spinner2));
									TextView tv = (TextView) sellDialog
											.findViewById(R.id.location);
									final EditText price = (EditText) sellDialog
											.findViewById(R.id.price);
									final EditText owner_desc = (EditText) sellDialog
											.findViewById(R.id.owner_description);
									price.setError(null);
									owner_desc.setError(null);

									Button can = (Button) sellDialog
											.findViewById(R.id.btnCancel);
									can.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											sellDialog.cancel();
										}
									});

									final CheckBox checkBox1 = (CheckBox) sellDialog
											.findViewById(R.id.checkBox1);
									final String district = "";
									Button bb = (Button) sellDialog
											.findViewById(R.id.create);
									bb.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											boolean cancel = false;
											View focusView = null;

											if (TextUtils.isEmpty(price
													.getText())) {
												price.setError(getString(R.string.error_field_required));
												focusView = price;
												cancel = true;
											} else if (!ListOfSecondHandBook
													.isNum(price.getText()
															.toString())) {
												price.setError("Number only");
												focusView = price;
												cancel = true;
											}

											if (TextUtils.isEmpty(owner_desc
													.getText())) {
												owner_desc
														.setError(getString(R.string.error_field_required));
												focusView = owner_desc;
												cancel = true;
											} else if (owner_desc.getText()
													.length() < 4) {
												owner_desc
														.setError("Can't be too short");
												focusView = owner_desc;
												cancel = true;
											}

											if (cancel) {
												// There was an error; don't
												// attempt login and focus the
												// first
												// form field with an error.
												focusView.requestFocus();
											} else {
												HttpClient httpclient = new DefaultHttpClient();
												HttpPost httppost = new HttpPost(
														"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_sellBook.php");
												List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
												nameValuePairs
														.add(new BasicNameValuePair(
																"isbn", isbn));
												nameValuePairs
														.add(new BasicNameValuePair(
																"username",
																username));
												nameValuePairs
														.add(new BasicNameValuePair(
																"price",
																""
																		+ price.getText()));
												nameValuePairs
														.add(new BasicNameValuePair(
																"description",
																""
																		+ owner_desc
																				.getText()));
												Log.i("upload", uploaded + "");
												if (uploaded) {
													nameValuePairs
															.add(new BasicNameValuePair(
																	"images",
																	"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/image/"
																			+ isbn
																			+ username
																			+ ".jpg"));
												}
												// tv.getText()
												if (checkBox1.isChecked()) {
													Log.i("checkedtest", "true"
															+ districts);
													nameValuePairs
															.add(new BasicNameValuePair(
																	"address",
																	address));

													nameValuePairs
															.add(new BasicNameValuePair(
																	"lat",
																	lat + ""));

													nameValuePairs
															.add(new BasicNameValuePair(
																	"log",
																	log + ""));

													nameValuePairs
															.add(new BasicNameValuePair(
																	"district",
																	districts
																			.substring(
																					0,
																					districts
																							.indexOf("District"))));
												} else {
													Log.i("districtdebugtest",
															sp2.getSelectedItem()
																	.toString());
													nameValuePairs
															.add(new BasicNameValuePair(
																	"district",
																	(String) sp2
																			.getSelectedItem()
																			.toString()));

													nameValuePairs
															.add(new BasicNameValuePair(
																	"address",
																	""));

													nameValuePairs
															.add(new BasicNameValuePair(
																	"lat", ""));

													nameValuePairs
															.add(new BasicNameValuePair(
																	"log", ""));
												}
												try {
													httppost.setEntity(new UrlEncodedFormEntity(
															nameValuePairs));
													HttpResponse response1 = httpclient
															.execute(httppost);

													HttpEntity entity = response1
															.getEntity();
													InputStream is = entity
															.getContent();
													Log.i("is",
															convertStreamToString(is));
												} catch (Exception e) {
													e.printStackTrace();
													Log.i("huh", e.getMessage());
												}
												sellDialog.cancel();
											}
										}
									});
									List<String> list = new ArrayList<String>();
									list.add("Hong Kong Island");
									list.add("Kowloon");
									list.add("New Territories");
									ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
											v.getContext(),
											android.R.layout.simple_spinner_item,
											list);
									dataAdapter
											.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									sp.setAdapter(dataAdapter);
									sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
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

											if (((Spinner) arg1.getParent())
													.getItemAtPosition(arg2)
													.equals("Hong Kong Island")) {
												Spinner sp2 = ((Spinner) sellDialog
														.findViewById(R.id.spinner2));
												ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
														arg0.getContext(),
														android.R.layout.simple_spinner_item,
														list);
												dataAdapter
														.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
												sp2.setAdapter(dataAdapter);
											}
											if (((Spinner) arg1.getParent())
													.getItemAtPosition(arg2)
													.equals("Kowloon")) {
												Spinner sp2 = ((Spinner) sellDialog
														.findViewById(R.id.spinner2));
												ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
														arg0.getContext(),
														android.R.layout.simple_spinner_item,
														list2);
												dataAdapter
														.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
												sp2.setAdapter(dataAdapter);
											}
											if (((Spinner) arg1.getParent())
													.getItemAtPosition(arg2)
													.equals("New Territories")) {
												Spinner sp2 = ((Spinner) sellDialog
														.findViewById(R.id.spinner2));
												ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
														arg0.getContext(),
														android.R.layout.simple_spinner_item,
														list3);
												dataAdapter
														.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
												sp2.setAdapter(dataAdapter);
											}
										}

										@Override
										public void onNothingSelected(
												AdapterView<?> arg0) {
											// TODO Auto-generated method stub

										}
										// new
										// AdapterView.OnItemSelectedListener();
									});
									double abc = ListOfSecondHandBook.getLat();
									double efg = ListOfSecondHandBook.getLog();
									String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
											+ abc + "," + efg + "&sensor=true";// &language=zh-TW";
									JSONParser jsp = new JSONParser();
									Log.i("hihihi",
											"Helloken http://maps.googleapis.com/maps/api/geocode/json?latlng="
													+ abc + "," + efg
													+ "&sensor=true");
									JSONObject obj = jsp.getJSONFromUrl(url);
									try {
										Log.i("results",
												obj.getJSONArray("results")
														.getJSONObject(0)
														.getString(
																"formatted_address"));
										address = obj.getJSONArray("results")
												.getJSONObject(0)
												.getString("formatted_address");
										JSONArray result = obj
												.getJSONArray("results");
										for (int i = 0; i < result.length(); i++) {
											JSONObject jo = result
													.getJSONObject(i);
											JSONArray a = jo
													.getJSONArray("address_components");
											for (int x = 0; x < a.length(); x++) {
												JSONObject b = a
														.getJSONObject(x);
												if (b.getString("types")
														.contains(
																"administrative_area_level_1")) {
													String area = b.getString(
															"long_name").trim();
													Log.i("asd", area);
													if (area.equals("Hong Kong Island")) {
														sp.setSelection(0);
													}
													if (area.equals("Kowloon")) {
														sp.setSelection(1);
														Log.i("asd", area);
													}
													if (area.equals("New Territories")) {
														sp.setSelection(2);
													}
												}
												if (b.getString("types")
														.contains(
																"administrative_area_level_2")) {
													districts = b.getString(
															"long_name").trim();
												}
												// area.substring(0,
												// * area.indexOf("District") -
												// 1);

											}
											if (district.length() > 0)
												break;
										}
										// progressBar.setProgress(50);

										tv.setText((tv.getText() + " " + obj
												.getJSONArray("results")
												.getJSONObject(0)
												.getString("formatted_address")));
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									// final ListView lv = (ListView)
									// purchaseDialog
								}

							});

							Button email = new Button(getActivity());
							email.setText("Share");
							email.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {

									String urlToShare = "http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/search/book?isbn="
											+ isbn;
									Intent intent = new Intent(
											Intent.ACTION_SEND);
									intent.setType("text/plain");
									// intent.putExtra(Intent.EXTRA_SUBJECT,
									// "Foo bar"); // NB: has no effect!
									intent.putExtra(Intent.EXTRA_TEXT,
											urlToShare);

									// See if official Facebook app is found
									boolean facebookAppFound = false;
									List<ResolveInfo> matches = getActivity()
											.getPackageManager()
											.queryIntentActivities(intent, 0);
									for (ResolveInfo info : matches) {
										if (info.activityInfo.packageName
												.toLowerCase().startsWith(
														"com.facebook")) {
											intent.setPackage(info.activityInfo.packageName);
											facebookAppFound = true;
											break;
										}
									}

									// As fallback, launch sharer.php in a
									// browser
									if (!facebookAppFound) {
										String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u="
												+ urlToShare;
										intent = new Intent(Intent.ACTION_VIEW,
												Uri.parse(sharerUrl));
									}

									startActivity(intent);
								}
							});

							myll2.addView(b, lp);
							myll2.addView(c, lp);
							myll2.addView(d, lp);
							myll2.addView(email, lp);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ll.addView(myll, lp);
						ll.addView(myll2, lp);

						ll.addView(tv, lp);
					} else {
						LinearLayout ll = (LinearLayout) getActivity()
								.findViewById(R.id.rooot);
						ll.removeAllViews();
						ll.setGravity(Gravity.NO_GRAVITY);
						Button a = new Button(ll.getContext());
						a.setText("Fail to find this book's information!");
						ll.addView(a);
					}

				} catch (Exception e) {

				}
			}

			public Drawable drawableFromUrl(String url) {

				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				try {
					Bitmap x;

					HttpURLConnection connection = (HttpURLConnection) new URL(
							url).openConnection();
					connection.connect();
					InputStream input = connection.getInputStream();

					x = BitmapFactory.decodeStream(input);
					return new BitmapDrawable(x);
				} catch (Exception e) {

				}
				return null;
			}

			@Override
			protected void onCancelled() {
			}
		}

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		public class ListOfBookByDistricts extends
				AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList = new ArrayList<String>();
			String[] mString = new String[100];
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			SimpleAdapter adapter;
			private ArrayList<Drawable> myDrawable = new ArrayList<Drawable>();
			private String districts;

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.

				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/listOfSecondHandBook.php?did="
									+ districts);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							if (obj.getJSONArray("Book").length() > 0) {
								int length = obj.getJSONArray("Book").length();
								for (int i = 0; i < length; i++) {
									HashMap<String, Object> item = new HashMap<String, Object>();
									item.put("bookName",
											obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("bookName"));
									item.put("author",
											obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("author"));
									item.put("isbn", obj.getJSONArray("Book")
											.getJSONObject(i).getString("isbn"));
									try {
										item.put(
												"image",
												this.drawableFromUrl(obj
														.getJSONArray("Book")
														.getJSONObject(i)
														.getString("image")));
									} catch (Exception e) {

									}
									list.add(item);
									Log.i("aaaa", i + "");
									mString[i] = obj.getJSONArray("Book")
											.getJSONObject(i)
											.getString("bookName");
									myList.add("<Book Name>"
											+ obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("bookName"));
									myList.add("<Author>"
											+ obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("author"));
									myList.add("<ISBN>"
											+ obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("isbn"));
								}
							}
						} catch (Exception e) {
							Log.i("listofbookbydistrictdebug",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("listofbookbydistrictdebug",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			public ListOfBookByDistricts setDistrict(String district) {
				this.districts = district;
				return this;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				// ArrayList<String> myString;

				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				// ScrollView aa = ((ScrollView)ll.getParent());

				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				ListView lv = new ListView(getActivity());
				SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
						R.layout.mylistview2, new String[] { "image",
								"bookName", "author", "isbn" }, new int[] {
								R.id.imageView1, R.id.textView1,
								R.id.textView2, R.id.textView3 });
				lv.setAdapter(adapter);
				lv.setLayoutParams(lp);
				adapter.setViewBinder(new ViewBinder() {
					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						if (view instanceof ImageView
								&& data instanceof Drawable) {
							ImageView iv = (ImageView) view;
							iv.setImageDrawable((Drawable) data);
							return true;
						}
						return false;
					}
				});
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView3);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView3);
						Log.i("asdasd", tv.getText() + "");

						startActivity(new Intent(arg1.getContext(),
								ListOfSecondHandBook.class)
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
								.putExtra("tag", "Show Book Information")
								.putExtra("pev", "list")
								.putExtra("isbn", isbn.getText())
								.putExtra(
										"username",
										((FragmentActivity) arg1.getContext())
												.getIntent().getExtras()
												.getString("username")));
					}
				});
				ll.removeAllViews();
				ll.addView(lv, lp);
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

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		public class ListOfBook extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList = new ArrayList<String>();
			String[] mString = new String[100];
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			SimpleAdapter adapter;
			private ArrayList<Drawable> myDrawable = new ArrayList<Drawable>();

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.

				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/listOfSecondHandBook.php");
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						Log.i("asd", "ASdasd");
						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							if (obj.getJSONArray("Book").length() > 0) {
								int length = obj.getJSONArray("Book").length();
								for (int i = 0; i < length; i++) {
									HashMap<String, Object> item = new HashMap<String, Object>();
									item.put("bookName",
											obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("bookName"));
									item.put("author",
											obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("author"));
									item.put("isbn", obj.getJSONArray("Book")
											.getJSONObject(i).getString("isbn"));
									try {
										item.put(
												"image",
												this.drawableFromUrl(obj
														.getJSONArray("Book")
														.getJSONObject(i)
														.getString("image")));
									} catch (Exception e) {

									}
									list.add(item);
									Log.i("aaaa", i + "");
									mString[i] = obj.getJSONArray("Book")
											.getJSONObject(i)
											.getString("bookName");
									myList.add("<Book Name>"
											+ obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("bookName"));
									myList.add("<Author>"
											+ obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("author"));
									myList.add("<ISBN>"
											+ obj.getJSONArray("Book")
													.getJSONObject(i)
													.getString("isbn"));
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

				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				// ScrollView aa = ((ScrollView)ll.getParent());

				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				ListView lv = new ListView(getActivity());
				SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
						R.layout.mylistview2, new String[] { "image",
								"bookName", "author", "isbn" }, new int[] {
								R.id.imageView1, R.id.textView1,
								R.id.textView2, R.id.textView3 });
				lv.setAdapter(adapter);
				lv.setLayoutParams(lp);
				adapter.setViewBinder(new ViewBinder() {
					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						if (view instanceof ImageView
								&& data instanceof Drawable) {
							ImageView iv = (ImageView) view;
							iv.setImageDrawable((Drawable) data);
							return true;
						}
						return false;
					}
				});
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView3);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView3);
						Log.i("asdasd", tv.getText() + "");

						startActivity(new Intent(arg1.getContext(),
								ListOfSecondHandBook.class)
								.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
								.putExtra("tag", "Show Book Information")
								.putExtra("pev", "list")
								.putExtra("isbn", isbn.getText())
								.putExtra(
										"username",
										((FragmentActivity) arg1.getContext())
												.getIntent().getExtras()
												.getString("username")));
					}
				});
				ll.removeAllViews();
				ll.addView(lv, lp);
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

		public static void setListViewHeightBasedOnChildren(ListView listView) {
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				// pre-condition
				return;
			}

			int totalHeight = 0;
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
			listView.requestLayout();
		}

		public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.
				boolean t = false;
				myList = new ArrayList();
				try {
					JSONParser jsp = new JSONParser();
					JSONObject obj = jsp
							.getJSONFromUrl("http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/JSON_User.php");
					// Log.i("hihihi", "hihihi" + obj);
					if (obj.getJSONArray("Users").length() > 0) {
						int length = obj.getJSONArray("Users").length();
						for (int i = 0; i < length; i++) {
							if (obj.getJSONArray("Users").getJSONObject(i)
									.getString("userName").equals(username)) {
								myList.add("UserName :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("userName"));
								myList.add("FirstName :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("firstName"));
								myList.add("LastName :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("lastName"));
								myList.add("Email :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("email"));
								myList.add("Password :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("password"));
								myList.add("Status :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("status"));
							}

						}
					}
					// Log.i("hi",
					// obj.getJSONArray("results").getJSONObject(0).getString("formatted_address"));
				} catch (Exception e) {
				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				// ArrayList<String> myString;
				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				for (String m : myList) {
					Button myButton = new Button(getActivity());
					myButton.setText(m);
					LayoutParams lp = new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					Log.i(m, ll + " " + getActivity() + myList.size());
					ll.addView(myButton, lp);
				}
			}

			@Override
			protected void onCancelled() {
			}
		}

		public class ShowBookInfoByISBN extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.
				boolean t = false;
				myList = new ArrayList();
				try {
					JSONParser jsp = new JSONParser();
					JSONObject obj = jsp
							.getJSONFromUrl("http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/JSON_User.php");
					// Log.i("hihihi", "hihihi" + obj);
					if (obj.getJSONArray("Users").length() > 0) {
						int length = obj.getJSONArray("Users").length();
						for (int i = 0; i < length; i++) {
							if (obj.getJSONArray("Users").getJSONObject(i)
									.getString("userName").equals(username)) {
								myList.add("UserName :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("userName"));
								myList.add("FirstName :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("firstName"));
								myList.add("LastName :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("lastName"));
								myList.add("Email :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("email"));
								myList.add("Password :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("password"));
								myList.add("Status :"
										+ obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("status"));
							}

						}
					}
					// Log.i("hi",
					// obj.getJSONArray("results").getJSONObject(0).getString("formatted_address"));
				} catch (Exception e) {
				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				// ArrayList<String> myString;
				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				for (String m : myList) {
					Button myButton = new Button(getActivity());
					myButton.setText(m);
					LayoutParams lp = new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					Log.i(m, ll + " " + getActivity() + myList.size());
					ll.addView(myButton, lp);
				}
			}

			@Override
			protected void onCancelled() {
			}
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.user_layout_no_scroll_view, container, false);
			return rootView;
		}
	}

	public static double getLat() {
		return lat;
	}

	public static double getLog() {
		return log;
	}

	static void applyFontRecursively(ViewGroup parent) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			View child = parent.getChildAt(i);
			if (child instanceof ViewGroup) {
				applyFontRecursively((ViewGroup) child);
			} else if (child != null) {
				if (child.getClass() == Button.class) {
					Button current = (Button) child;
					current.setFocusable(false);
					Log.d("menfis", child.toString());
					if ((i % 3) == 0)
						current.setBackgroundColor(Color.RED);
					if ((i % 3) == 1)
						current.setBackgroundColor(Color.BLACK);
					if ((i % 3) == 2)
						current.setBackgroundColor(Color.GRAY);
					// current.setWidth(300);
					current.setBackgroundResource(R.drawable.isbn);
					current.setGravity(Gravity.BOTTOM);
					current.setHeight(300);
				}
			}
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Log.i("id", item.getItemId() + "");
		switch (item.getItemId()) {

		case android.R.id.home:

			this.onBackPressed();

			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		if (connectionResult.hasResolution()) {
			try {

				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */

			} catch (IntentSender.SendIntentException e) {

				// Log the error
				e.printStackTrace();
			}
		} else {

			// If no resolution is available, display a dialog to the user with
			// the error.
			// showErrorDialog(connectionResult.getErrorCode());
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			uploaded = true;
		}
		super.onActivityResult(requestCode, resultCode, data);

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		String dir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
				+ "/mobileComputing/";

		File dataone = new File(dir + "dataone.txt");
		String line = "";
		Log.i("dataone", dataone + "");
		if (dataone != null) {
			try {
				Log.i("dataone", dataone + "");
				Scanner scanner = new Scanner(dataone);
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					// System.out.println(line);
					// Log.i("asd32", myline);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Log.i("asd", "asd");
				e.printStackTrace();
			}
		}
		String file = line;// (data.getExtras()).getString("path");

		params.add(new BasicNameValuePair("file", file));
		params.add(new BasicNameValuePair("des", "image"));
		String url = "http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/image/upload.php";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			// setup multipart entity
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			for (int i = 0; i < params.size(); i++) {
				// identify param type by Key
				if (params.get(i).getName().equals("file")) {
					File f = new File(params.get(i).getValue());
					FileBody fileBody = new FileBody(f);
					entity.addPart("image" + i, fileBody);
				} else {
					entity.addPart(params.get(i).getName(), new StringBody(
							params.get(i).getValue(), Charset.forName("UTF-8")));
				}
			}

			post.setEntity(entity);

			// create response handler
			ResponseHandler<String> handler = new BasicResponseHandler();
			// execute and get response
			String response = new String(client.execute(post, handler)
					.getBytes(), HTTP.UTF_8);
			Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
			Log.i("hihihi", "asd" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// LinearLayout ll = (LinearLayout) findViewById(R.layout.sell_dialog);
		// ImageView iv = (ImageView)ll.findViewById(R.id.imageView);
		// iv.setImageBitmap((Bitmap) data.getExtras().get("data"));
		// handleSmallCameraPhoto(data);
		// mImageBitmap = (Bitmap) data.getExtras().get("data");
		/*
		 * // 取得通知管理器 NotificationManager noMgr = (NotificationManager) this
		 * .getSystemService(Context.NOTIFICATION_SERVICE);
		 * 
		 * // 當使用者按下通知欄中的通知時要開啟的 Activity Intent call = new Intent(this,
		 * LoginActivity.class); // 非必要,可以利用intent傳值 call.putExtra("notiId", 1);
		 * // 建立待處理意圖 PendingIntent pIntent = PendingIntent.getActivity(this, 0,
		 * call, 0);
		 * 
		 * // 指定通知欄位要顯示的圖示 int icon = R.drawable.ic_launcher; //
		 * 指定通知出現時要顯示的文字,幾秒後會消失只剩圖示 String ticket = "電影時刻通知"; //
		 * 何時送出通知,傳入當前時間則立即發出通知 long when = System.currentTimeMillis(); //
		 * 建立通知物件 Notification notification = new
		 * Notification(R.drawable.ic_launcher, "Uploading file",
		 * System.currentTimeMillis()); notification.flags = notification.flags
		 * | Notification.FLAG_ONGOING_EVENT; notification.contentView = new
		 * RemoteViews(getApplicationContext() .getPackageName(),
		 * R.layout.upload_progress_bar); // notification.contentIntent =
		 * contentIntent;
		 * notification.contentView.setProgressBar(R.id.progressBar1, 100, 0,
		 * false); notification.contentView.setProgressBar(R.id.progressBar1,
		 * 100, 50, false);
		 * 
		 * noMgr.notify(1, notification);
		 */
		/*
		 * notification = new Notification(icon, ticket, when);
		 * 
		 * notification.flags = notification.flags |
		 * Notification.FLAG_ONGOING_EVENT; notification.contentView = new
		 * RemoteViews(getApplicationContext() .getPackageName(),
		 * R.layout.upload_progress_bar); //notification.contentIntent =
		 * contentIntent;
		 * notification.contentView.setProgressBar(R.id.progressBar1, 100,0,
		 * false);
		 * 
		 * //指定通知標題 String title = "鐵達尼號3D"; //通知內容 String desc =
		 * "播放時間17:00 - 新光影城"; //設定事件資訊 notification.setLatestEventInfo(this,
		 * title, desc, pIntent);
		 * 
		 * //非必要,會在通知圖示旁顯示數字 notification.number = 5;
		 * 
		 * //執行通知 noMgr.notify(1, notification);
		 */
		Log.i("asd", "asdasd");
	}

	private void handleSmallCameraPhoto(Intent intent) {
		// Bundle extras = intent.getExtras();
		// Log.i("asd", "asd");
		// mImageBitmap = (Bitmap) extras.get("data");
		// Dialog sellDialog = new Dialog(this,
		// android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
		// ImageView abc = ((ImageView)sellDialog.findViewById(R.id.imageView));
		// Log.i("asd", "asdsad" + abc);
		// Set GUI of login screen

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		Log.i("asd", mLocationClient.getLastLocation().getLongitude() + "");
		log = mLocationClient.getLastLocation().getLongitude();
		Log.i("asd", mLocationClient.getLastLocation().getLatitude() + "");
		lat = mLocationClient.getLastLocation().getLatitude();
		if (mLocationClient.getLastLocation() != null)
			mLocationClient.removeLocationUpdates(this);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	public static Drawable drawableFromUrl2(String url) throws IOException {

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

	public static boolean isNum(String strNum) {
		boolean ret = true;
		try {

			Double.parseDouble(strNum);

		} catch (NumberFormatException e) {
			ret = false;
		}
		return ret;
	}
}
