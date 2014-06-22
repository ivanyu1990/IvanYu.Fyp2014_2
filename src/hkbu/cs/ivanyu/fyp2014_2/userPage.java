package hkbu.cs.ivanyu.fyp2014_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.ListView;

public class userPage extends FragmentActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	SectionsPagerAdapter mSectionsPagerAdapter;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	static String s_id;
	static String username;
	static String oldpassword;
	static String newpassword;
	static Context myContext;
	private ActionBarDrawerToggle mDrawerToggle;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		tag = getIntent().getExtras().getString("tag");
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.drawer_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		myContext = this;
		username =getIntent().getExtras().getString(
				"username");
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		// lvlList = (ListView) findViewById(R.id.lvlList);

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
					.putExtra("tag", "Comment Records")
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

		mDrawerList.setBackgroundColor((Color.parseColor("#212121")));

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
			// Button myButton = new Button(getActivity());
			if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Purchase Record")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				Log.i("HelloWorld", "HelloWorld");
				PurchaseRecord aaa = new PurchaseRecord();
				aaa.execute((Void) null);
			}
			if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Comment Records")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				CommentRecords aaa = new CommentRecords();
				aaa.execute((Void) null);
			}
			if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Favourite Books")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				Log.i("HelloWorld", "HelloWorld");
				FavBook aaa = new FavBook();
				aaa.execute((Void) null);
			}

			if (getActivity().getIntent().getExtras().getString("tag")
					.equals("Selling Record")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				SellingRecord aaa = new SellingRecord();
				aaa.execute((Void) null);
			}

			if (getActivity().getIntent().getExtras().getString("tag")
					.equals("User Profile")) {
				username = getActivity().getIntent().getExtras()
						.getString("username");
				Log.i("HelloWorld", "HelloWorld");
				UserInfo aaa = new UserInfo();
				aaa.execute((Void) null);
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

		public class UserInfo extends AsyncTask<Void, Void, Boolean> {

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			ArrayList<String> myList = new ArrayList<String>();

			protected Boolean doInBackground(Void... params) {
				Log.i("helloFavBook", "i am here");
				// TODO: attempt authentication against a network service.
				boolean t = false;
				myList = new ArrayList();
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/JSON_User.php");
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("Users").length() > 0) {
								int length = obj.getJSONArray("Users").length();
								for (int i = 0; i < length; i++) {
									if (obj.getJSONArray("Users")
											.getJSONObject(i)
											.getString("userName")
											.equals(username)) {
										myList.add(obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("userName"));
										myList.add(obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("firstName"));
										myList.add(obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("lastName"));
										myList.add(obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("password"));
										myList.add(obj.getJSONArray("Users")
												.getJSONObject(i)
												.getString("status"));
									}
								}
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {

				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.removeAllViews();
				ll.setGravity(Gravity.NO_GRAVITY);
				TextView usernametextview = new TextView(ll.getContext());
				usernametextview.setTextSize(20);
				usernametextview.setText("Username  = " + myList.get(0));
				ll.addView(usernametextview);

				TextView userfirstname = new TextView(ll.getContext());
				userfirstname.setTextSize(20);
				userfirstname.setText("First name  = " + myList.get(1));
				ll.addView(userfirstname);

				TextView userlastname = new TextView(ll.getContext());
				userlastname.setTextSize(20);
				userlastname.setText("Last name  = " + myList.get(2));
				ll.addView(userlastname);

				TextView password = new TextView(ll.getContext());
				password.setTextSize(20);
				String star = "";
				for (int i = 0; i < myList.get(3).length(); i++) {
					star += "*";
				}
				password.setText("Password  = " + star);
				ll.addView(password);

				Button changePassWord = new Button(ll.getContext());
				changePassWord.setText("Change Password ");
				changePassWord.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						LinearLayout ll = new LinearLayout(v.getContext());
						ll.setOrientation(LinearLayout.VERTICAL);
						LayoutParams lp = new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);

						TextView old = new TextView(v.getContext());
						old.setText("Old Password");
						final EditText oldet = new EditText(v.getContext());
						ll.addView(old, lp);
						ll.addView(oldet, lp);

						TextView newP = new TextView(v.getContext());
						newP.setText("New Password");
						final EditText newPet = new EditText(v.getContext());
						ll.addView(newP, lp);
						ll.addView(newPet, lp);

						final TextView retypeNewP = new TextView(v.getContext());
						retypeNewP.setText("Retype New Password");
						final EditText retypeNewPet = new EditText(v.getContext());
						ll.addView(retypeNewP, lp);
						ll.addView(retypeNewPet, lp);
						final View a = v;
							new AlertDialog.Builder(v.getContext())
									.setTitle("Update Status")
									.setMessage("Update your password here")
									.setView(ll)
									.setPositiveButton(
											"Ok",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// Editable value =
													// input.getText();
													// if()

													if (!retypeNewPet.getText().toString().equals(newPet
															.getText().toString())) {
														new AlertDialog.Builder(a.getContext())
																.setTitle("Update Status")
																.setMessage("Not match on your retyping password!").setPositiveButton(
																		"Ok", null).show();
													} else {
														oldpassword = oldet.getText().toString();
														newpassword = retypeNewPet.getText().toString();
														new UpdatePassword().execute((Void)null);
													}
												}
											})
									.setNegativeButton(
											"Cancel",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int whichButton) {
													// Do nothing.
												}
											}).show();
						
					}

				});
				ll.addView(changePassWord);

				TextView status = new TextView(ll.getContext());
				status.setTextSize(20);
				status.setText("Status  = " + myList.get(4));
				ll.addView(status);
				// ll.addView(new Button(getActivity()));
				// ll.addView(lv, lp);
				Log.i("i", "end");
			}

			@Override
			protected void onCancelled() {
			}

			class MyBinder implements ViewBinder {
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if (view.getId() == R.id.listViewRatingBar) {
						String stringval = (String) data;
						float ratingValue = Float.parseFloat(stringval);
						RatingBar ratingBar = (RatingBar) view;
						ratingBar.setRating(ratingValue);
						ratingBar.setEnabled(false);
						return true;
					}
					return false;
				}
			}
		}

		public class UpdatePassword extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

			protected Boolean doInBackground(Void... params) {
				Log.i("helloFavBook", "i am here");
				// TODO: attempt authentication against a network service.
				boolean t = false;
				myList = new ArrayList();
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_UpdatePassword.php?username="
									+ username + "&oldpassword=" +oldpassword + "&newpassword=" + newpassword);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							if(obj.get("message").equals("NO OK")){
								return false;
							} else {
								return true;
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				if(success){

					new AlertDialog.Builder(userPage.getMyContext())
							.setTitle("Update Status")
							.setMessage("Password update success!").show();
				} else {

					new AlertDialog.Builder(userPage.getMyContext())
							.setTitle("Update Status")
							.setMessage("Update fail!").show();
				}
			}

			@Override
			protected void onCancelled() {
			}

			class MyBinder implements ViewBinder {
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if (view.getId() == R.id.listViewRatingBar) {
						String stringval = (String) data;
						float ratingValue = Float.parseFloat(stringval);
						RatingBar ratingBar = (RatingBar) view;
						ratingBar.setRating(ratingValue);
						ratingBar.setEnabled(false);
						return true;
					}
					return false;
				}
			}
		}

		public class FavBook extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

			protected Boolean doInBackground(Void... params) {
				Log.i("helloFavBook", "i am here");
				// TODO: attempt authentication against a network service.
				boolean t = false;
				myList = new ArrayList();
				ConnectivityManager connMgr = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_FavBook.php?username="
									+ username);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("RatingRecord").length() > 0) {
								int length = obj.getJSONArray("RatingRecord")
										.length();
								for (int i = 0; i < length; i++) {
									Log.i("aaaa",
											i
													+ ""
													+ obj.getJSONArray(
															"RatingRecord")
															.getJSONObject(i)
															.getString(
																	"bookName"));
									HashMap<String, Object> item = new HashMap<String, Object>();

									item.put("bookName",
											obj.getJSONArray("RatingRecord")
													.getJSONObject(i)
													.getString("bookName"));

									item.put("rating",
											obj.getJSONArray("RatingRecord")
													.getJSONObject(i)
													.getString("rating"));
									/*
									 * item.put("owner_description",
									 * "Owner's description : "
									 * +obj.getJSONArray("RatingRecord")
									 * .getJSONObject(i) .getString(
									 * "owner_description"));
									 */
									item.put("isbn",
											obj.getJSONArray("RatingRecord")
													.getJSONObject(i)
													.getString("isbn"));

									list.add(item);
								}
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {

				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				ListView lv = new ListView(getActivity());
				SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
						R.layout.myratinglistview, new String[] { /* "image", */
								"bookName", "rating", "isbn" }, new int[] { /*
																			 * R.
																			 * id
																			 * .
																			 * imageView1
																			 * ,
																			 */
								R.id.textView1, R.id.listViewRatingBar,
								R.id.textView3 });
				adapter.setViewBinder(new MyBinder());

				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView4);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView4);
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

			class MyBinder implements ViewBinder {
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if (view.getId() == R.id.listViewRatingBar) {
						String stringval = (String) data;
						float ratingValue = Float.parseFloat(stringval);
						RatingBar ratingBar = (RatingBar) view;
						ratingBar.setRating(ratingValue);
						ratingBar.setEnabled(false);
						return true;
					}
					return false;
				}
			}
		}

		public class CommentRecords extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

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
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_CommentsRecords.php?username="
									+ username);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							if (obj.getJSONArray("CommentsRecord").length() > 0) {
								int length = obj.getJSONArray("CommentsRecord")
										.length();
								for (int i = 0; i < length; i++) {
									HashMap<String, Object> item = new HashMap<String, Object>();

									item.put("isbn",
											obj.getJSONArray("CommentsRecord")
													.getJSONObject(i)
													.getString("isbn"));

									item.put(
											"comment",
											"Comments : "
													+ obj.getJSONArray(
															"CommentsRecord")
															.getJSONObject(i)
															.getString(
																	"comment"));
									item.put("bookName",
											obj.getJSONArray("CommentsRecord")
													.getJSONObject(i)
													.getString("bookName"));
									item.put(
											"userName",
											"Username : "
													+ obj.getJSONArray(
															"CommentsRecord")
															.getJSONObject(i)
															.getString(
																	"userName"));

									list.add(item);
								}
							}
						} catch (Exception e) {
							Log.i("CommentsRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("CommentsRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

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
						new String[] { /* "image", */"bookName", "userName",
								"comment", "isbn" },
						new int[] { /* R.id.imageView1, */R.id.textView1,
								R.id.textView2, R.id.textView3, R.id.textView4 });

				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView4);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView4);
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

		public class SellingRecord extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

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
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_SellingRecord.php?username="
									+ username);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("PurchaseRecord").length() > 0) {
								int length = obj.getJSONArray("PurchaseRecord")
										.length();
								for (int i = 0; i < length; i++) {
									Log.i("aaaa",
											i
													+ ""
													+ obj.getJSONArray(
															"PurchaseRecord")
															.getJSONObject(i)
															.getString(
																	"bookName"));
									HashMap<String, Object> item = new HashMap<String, Object>();

									item.put("bookName",
											obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("bookName"));

									item.put(
											"price",
											"Price : "
													+ obj.getJSONArray(
															"PurchaseRecord")
															.getJSONObject(i)
															.getString("price"));
									item.put(
											"owner_description",
											"Owner's description : "
													+ obj.getJSONArray(
															"PurchaseRecord")
															.getJSONObject(i)
															.getString(
																	"owner_description"));
									item.put("isbn",
											obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("isbn"));

									item.put("s_id",
											obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("s_id"));
									String sold =
											obj.getJSONArray("PurchaseRecord")
											.getJSONObject(i)
											.getString("sold");
									if(sold.equals("1")){
										item.put("sold","Status : Sold Already");
									} else if (sold.equals("2")){
										item.put("sold","Status : Cancel");
									} else{
										item.put("sold","Status : Selling");
									}
									list.add(item);
								}
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {

				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				ListView lv = new ListView(getActivity());
				SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
						R.layout.mylistview3, new String[] { /* "image", */
								"bookName", "price", "owner_description",
								"isbn", "sold", "s_id" }, new int[] { /* R.id.imageView1, */
								R.id.textView1, R.id.textView2, R.id.textView3,
								R.id.textView4, R.id.textView5, R.id.textView6 });

				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView4);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView4);
						String sold = ((TextView) arg1
								.findViewById(R.id.textView5)).getText().toString();
						Log.i("asdasd", tv.getText() + "");
						final View arg12 = arg1;
						if(sold.contains("Selling")){
						new AlertDialog.Builder(arg1.getContext())
					    .setTitle("Cancel Selling?")
					    .setMessage("Are you sure you want to cancel this entry?")
					    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // continue with delete
					        	s_id = ((TextView)arg12.findViewById(R.id.textView6)).getText().toString();
					        	new CancelRecord().execute((Void) null);
					        }
					     })
					    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // do nothing
					        }
					     })
					     .show();
						} else if(sold.contains("Sold")){ 
							s_id = ((TextView)arg12.findViewById(R.id.textView6)).getText().toString();
						        	new PostPurchase().execute((Void) null);
						}
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

		public class CancelRecord extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

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
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_CancelRecord.php?s_id="
									+ s_id);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							
							String a = obj.getString("success");
							if(a.equals("1")){
								return true;
							}else{
								return false;
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				if(success){
					Toast.makeText(myContext, "Record deleted successfully", 10000);
					startActivity(new Intent(myContext, userPage.class)
							.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
							.putExtra("tag", "Selling Record")
							.putExtra(
									"username", userPage.username));
				} else {
					Toast.makeText(myContext, "Record deleted fail", 10000);
					
				}
			}

			@Override
			protected void onCancelled() {
			}
		}
		

		public class PostPurchase2 extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<String> list = new ArrayList<String>();

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
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_postpurchase2.php?seller_id="
									+ s_id);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("PurchaseRecord").length() > 0) {
								int length = obj.getJSONArray("PurchaseRecord")
										.length();
									list.add(
											obj.getJSONArray("PurchaseRecord").getJSONObject(0)
											.getString("email"));
									Log.i("email",
											obj.getJSONArray("PurchaseRecord").getJSONObject(0)
											.getString("email") );
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				Button aButton = new Button(userPage.myContext);
				aButton.setText("Send email to contact seller");
				aButton.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_SEND);
						
						intent.setType("text/html");
						intent.putExtra(Intent.EXTRA_EMAIL,new String[]{ list.get(0)});
						intent.putExtra(Intent.EXTRA_SUBJECT, "Hey Second Hand Book Trading");
						intent.putExtra(Intent.EXTRA_TEXT, "I have made the purchase decision, please contact me as soon as possible!");

						startActivity(Intent.createChooser(intent, "Send Email"));
					}
					
				});
				new AlertDialog.Builder(userPage.getMyContext())
						.setTitle("Get Seller information")
						.setMessage("Buyer email : "+ list.get(0)).setPositiveButton(
								"Ok", null).setView(aButton).show();
			}
		}
		
		public class PostPurchase extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<String> list = new ArrayList<String>();

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
							"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/class/JSON_postpurchase.php?seller_id="
									+ s_id);
					try {
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("PurchaseRecord").length() > 0) {
								int length = obj.getJSONArray("PurchaseRecord")
										.length();
									list.add(
											obj.getJSONArray("PurchaseRecord").getJSONObject(0)
											.getString("email"));
									Log.i("email",
											obj.getJSONArray("PurchaseRecord").getJSONObject(0)
											.getString("email") );
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				Button aButton = new Button(userPage.myContext);
				aButton.setText("Send email to contact buyer");
				aButton.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_SEND);
						
						intent.setType("text/html");
						intent.putExtra(Intent.EXTRA_EMAIL, new String[]{list.get(0)});
						intent.putExtra(Intent.EXTRA_SUBJECT, "Hey Second Hand Book Trading");
						intent.putExtra(Intent.EXTRA_TEXT, "I have made the purchase decision, please contact us!");

						startActivity(Intent.createChooser(intent, "Send Email"));
					}
					
				});
				new AlertDialog.Builder(userPage.getMyContext())
						.setTitle("Get Buyer information")
						.setMessage("Buyer email : "+ list.get(0)).setPositiveButton(
								"Ok", null).setView(aButton).show();
			}
		}
		
		public class PurchaseRecord extends AsyncTask<Void, Void, Boolean> {
			private ArrayList<String> myList;

			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

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
						HttpResponse response = httpclient.execute(httppost);

						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						try {
							// JSONParser jsp = new JSONParser();
							JSONObject obj = new JSONObject(
									convertStreamToString(is));
							// Log.i("hihihi", "hihihi" + obj);
							if (obj.getJSONArray("PurchaseRecord").length() > 0) {
								int length = obj.getJSONArray("PurchaseRecord")
										.length();
								for (int i = 0; i < length; i++) {
									Log.i("aaaa",
											i
													+ ""
													+ obj.getJSONArray(
															"PurchaseRecord")
															.getJSONObject(i)
															.getString(
																	"bookName"));
									HashMap<String, Object> item = new HashMap<String, Object>();

									item.put("bookName",
											obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("bookName"));

									item.put(
											"price",
											"Price : "
													+ obj.getJSONArray(
															"PurchaseRecord")
															.getJSONObject(i)
															.getString("price"));
									item.put(
											"owner_description",
											"Owner's description : "
													+ obj.getJSONArray(
															"PurchaseRecord")
															.getJSONObject(i)
															.getString(
																	"owner_description"));
									item.put("isbn",
											obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("isbn"));
									item.put("s_id",
											obj.getJSONArray("PurchaseRecord")
													.getJSONObject(i)
													.getString("s_id"));
									String sold =
											obj.getJSONArray("PurchaseRecord")
											.getJSONObject(i)
											.getString("sold");
									if(sold.equals("1")){
										item.put("sold","Status : Sold Already");
									} else if(sold.equals("2")){
										item.put("sold","Status : Cancel");
									} else {
										item.put("sold","Status : Selling");
									}
									list.add(item);
								}
							}
						} catch (Exception e) {
							Log.i("purchaseRecord",
									e.getLocalizedMessage() + e.getMessage());
						}
					} catch (Exception e) {
						Log.i("purchaseRecord",
								e.getLocalizedMessage() + e.getMessage());
					}

				}
				return true;
			}

			public ArrayList<String> getArrayList() {
				return myList;
			}

			@Override
			protected void onPostExecute(final Boolean success) {

				LinearLayout ll = (LinearLayout) getActivity().findViewById(
						R.id.rooot);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);

				ll.removeAllViewsInLayout();
				ll.setGravity(Gravity.NO_GRAVITY);
				ListView lv = new ListView(getActivity());

				SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
						R.layout.mylistview3, new String[] { /* "image", */
								"bookName", "price", "owner_description",
								"isbn", "sold", "s_id" }, new int[] { /* R.id.imageView1, */
								R.id.textView1, R.id.textView2, R.id.textView3,
								R.id.textView4, R.id.textView5, R.id.textView6 });

				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						TextView tv = (TextView) arg1
								.findViewById(R.id.textView4);
						TextView isbn = (TextView) arg1
								.findViewById(R.id.textView4);
						String sold = ((TextView) arg1
								.findViewById(R.id.textView5)).getText().toString();
						Log.i("asdasd", tv.getText() + "");
						final View arg12 = arg1;
						if(sold.contains("Selling")){
						new AlertDialog.Builder(arg1.getContext())
					    .setTitle("Cancel Selling?")
					    .setMessage("Are you sure you want to cancel this entry?")
					    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // continue with delete
					        	s_id = ((TextView)arg12.findViewById(R.id.textView6)).getText().toString();
					        	new CancelRecord().execute((Void) null);
					        }
					     })
					    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // do nothing
					        }
					     })
					     .show();
						} else if(sold.contains("Sold")) { 
							s_id = ((TextView)arg12.findViewById(R.id.textView6)).getText().toString();
						        	new PostPurchase2().execute((Void) null);
						}
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

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.user_layout_no_scroll_view, container, false);
			return rootView;
		}
	}

	public static Context getMyContext(){
		return myContext;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// NavUtils.navigateUpFromSameTask(this);
			Log.i("asdas", "SAdasdas");
			startActivity(new Intent(this, MainActivity.class).addFlags(
					Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("username",
					getIntent().getExtras().getString("username")));
			return false;
		}
		return super.onOptionsItemSelected(item);
	}
}
