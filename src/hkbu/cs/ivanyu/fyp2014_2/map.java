package hkbu.cs.ivanyu.fyp2014_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class map extends FragmentActivity {

	private String[] mPlanetTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	SectionsPagerAdapter mSectionsPagerAdapter;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	static String username;
	private ActionBarDrawerToggle mDrawerToggle;
	GoogleMap mMap;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
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

		/*
		 * mSectionsPagerAdapter = new SectionsPagerAdapter(
		 * getSupportFragmentManager()); mViewPager = (ViewPager)
		 * findViewById(R.id.pager);
		 * mViewPager.setAdapter(mSectionsPagerAdapter);
		 */
		setUpMapIfNeeded();
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
			//mMap.
			Log.i("asd", "asd" + mMap.toString());
			// Check if we were successful in obtaining the map.
			final PolygonOptions shatin = new PolygonOptions().add(new LatLng(
					22.407810, 114.217348), new LatLng(22.411460, 114.213571),
					new LatLng(22.418442, 114.214429), new LatLng(22.425583,
							114.208936), new LatLng(22.429074, 114.202241),
					new LatLng(22.427646, 114.194002), new LatLng(22.419712,
							114.195203), new LatLng(22.417173, 114.198980),
					new LatLng(22.396859, 114.176664), new LatLng(22.392256,
							114.181814), new LatLng(22.384638, 114.173917),
					new LatLng(22.384638, 114.154691), new LatLng(22.359081,
							114.140787), new LatLng(22.349237, 114.141645),
					new LatLng(22.344951, 114.153833), new LatLng(22.363367,
							114.164991), new LatLng(22.356858, 114.166708),
					new LatLng(22.355747, 114.175977), new LatLng(22.367177,
							114.190569), new LatLng(22.372574, 114.211340),
					new LatLng(22.353842, 114.228334), new LatLng(22.372574,
							114.247732), new LatLng(22.403525, 114.260778),
					new LatLng(22.406699, 114.276571), new LatLng(22.421299,
							114.267473), new LatLng(22.426218, 114.270563),
					new LatLng(22.434152, 114.268331), new LatLng(22.436532,
							114.236574), new LatLng(22.423044, 114.223871),
					new LatLng(22.407334, 114.219923), new LatLng(22.403525,
							114.213228));
			final PolygonOptions cAndW = new PolygonOptions().add(new LatLng(
					22.2856950, 114.113747),
					new LatLng(22.2843050, 114.115135), new LatLng(22.2851390,
							114.111808), new LatLng(22.2856950, 114.113747),
					new LatLng(22.290971, 114.143472), new LatLng(22.2848610,
							114.160973), new LatLng(22.2749720, 114.168289),
					new LatLng(22.2615020, 114.164506), new LatLng(22.254760,
							114.145981), new LatLng(22.2556040, 114.144714),
					new LatLng(22.2729160, 114.134743), new LatLng(22.2751410,
							114.11625), new LatLng(22.2859730, 114.125695),
					new LatLng(22.2909710, 114.143472));
			shatin.getPoints();

			final PolygonOptions eastern = new PolygonOptions().add(new LatLng(
					22.29514, 114.204026), new LatLng(22.294306, 114.205138),
					new LatLng(22.284027, 114.22625), new LatLng(22.274860,
							114.243195), new LatLng(22.2648620, 114.254028),
					new LatLng(22.2545830, 114.255692), new LatLng(22.2560230,
							114.241548), new LatLng(22.254760, 114.233124),
					new LatLng(22.2577070, 114.224281), new LatLng(22.2655540,
							114.200386), new LatLng(22.2780350, 114.194679),
					new LatLng(22.2854170, 114.182641), new LatLng(22.295140,
							114.202636), new LatLng(22.295140, 114.204026));
			final PolygonOptions kowloonCity = new PolygonOptions().add(new LatLng(
					22.3469610, 114.181342),
					new LatLng(22.3389610, 114.185973), new LatLng(22.3334890,
							114.196922), new LatLng(22.3204360, 114.206604),
					new LatLng(22.3081950, 114.215142), new LatLng(22.3034720,
							114.214859), new LatLng(22.3123610, 114.204865),
					new LatLng(22.3193060, 114.194862), new LatLng(22.2979140,
							114.184838), new LatLng(22.317070, 114.174607),
					new LatLng(22.3296980, 114.175446), new LatLng(22.3400980,
							114.174789), new LatLng(22.3490670, 114.179658),
					new LatLng(22.3469610, 114.181342));
			final PolygonOptions north = new PolygonOptions().add(new LatLng(
					22.561671, 114.163239), new LatLng(22.5549340, 114.191865),
					new LatLng(22.5561030, 114.195404), new LatLng(22.5535920,
							114.220101), new LatLng(22.5434720, 114.226806),
					new LatLng(22.5345840, 114.213471), new LatLng(22.5248610,
							114.217918), new LatLng(22.5293070, 114.225692),
					new LatLng(22.535140, 114.236809), new LatLng(22.5495830,
							114.24514), new LatLng(22.5445840, 114.25708),
					new LatLng(22.5348620, 114.267363), new LatLng(22.5245830,
							114.27903), new LatLng(22.5259730, 114.285141),
					new LatLng(22.5223610, 114.284858), new LatLng(22.5145840,
							114.273193), new LatLng(22.5048610, 114.295135),
					new LatLng(22.5115280, 114.294861), new LatLng(22.5151380,
							114.307358), new LatLng(22.5145840, 114.319305),
					new LatLng(22.5048610, 114.299584), new LatLng(22.5051390,
							114.321525), new LatLng(22.5044150, 114.32869),
					new LatLng(22.4943110, 114.310592), new LatLng(22.4951520,
							114.289544), new LatLng(22.4947330, 114.267227),
					new LatLng(22.4955740, 114.249543), new LatLng(22.4943110,
							114.230599), new LatLng(22.4951520, 114.194397),
					new LatLng(22.4947330, 114.178817), new LatLng(22.4846270,
							114.172082), new LatLng(22.4863120, 114.164506),
					new LatLng(22.4842060, 114.153138), new LatLng(22.4745240,
							114.143456), new LatLng(22.4757860, 114.089142),
					new LatLng(22.519990, 114.084511), new LatLng(22.5257820,
							114.086639), new LatLng(22.5355680, 114.096298),
					new LatLng(22.5330430, 114.105561), new LatLng(22.5351470,
							114.117348), new LatLng(22.5465150, 114.15229),
					new LatLng(22.5553560, 114.155244), new LatLng(22.5616710,
							114.163239));
			final PolygonOptions saikung = new PolygonOptions().add(new LatLng(
					22.4419050, 114.368468), new LatLng(22.434860, 114.400971),
					new LatLng(22.4145830, 114.405137), new LatLng(22.4151390,
							114.395141), new LatLng(22.4148610, 114.379303),
					new LatLng(22.403750, 114.374863), new LatLng(22.3948610,
							114.380974), new LatLng(22.3831940, 114.390977),
					new LatLng(22.3695830, 114.384864), new LatLng(22.3554160,
							114.374581), new LatLng(22.35375, 114.36486),
					new LatLng(22.336527, 114.354859), new LatLng(22.348194,
							114.344863), new LatLng(22.359029, 114.334304),
					new LatLng(22.379584, 114.324586), new LatLng(22.385138,
							114.314583), new LatLng(22.38375, 114.304582),
					new LatLng(22.388471, 114.294585), new LatLng(22.395139,
							114.284026), new LatLng(22.381806, 114.274864),
					new LatLng(22.362363, 114.275413), new LatLng(22.350416,
							114.274864), new LatLng(22.356806, 114.264863),
					new LatLng(22.365139, 114.269859), new LatLng(22.364861,
							114.261253), new LatLng(22.35486, 114.254304),
					new LatLng(22.356528, 114.259309), new LatLng(22.352916,
							114.265419), new LatLng(22.322362, 114.276528),
					new LatLng(22.314027, 114.290412), new LatLng(22.30486,
							114.295417), new LatLng(22.307083, 114.305138),
					new LatLng(22.290696, 114.304862), new LatLng(22.284309,
							114.290412), new LatLng(22.27486, 114.30153),
					new LatLng(22.264862, 114.30597), new LatLng(22.26514,
							114.302918), new LatLng(22.264584, 114.279862),
					new LatLng(22.26514, 114.272088), new LatLng(22.275416,
							114.264581), new LatLng(22.285139, 114.267639),
					new LatLng(22.296806, 114.264863), new LatLng(22.294584,
							114.251252), new LatLng(22.29602, 114.241967),
					new LatLng(22.322542, 114.234391), new LatLng(22.33391,
							114.223023), new LatLng(22.36843, 114.225129),
					new LatLng(22.37643, 114.239442), new LatLng(22.385271,
							114.247018), new LatLng(22.397058, 114.255439),
					new LatLng(22.40548, 114.282807), new LatLng(22.415583,
							114.288277), new LatLng(22.426108, 114.306382),
					new LatLng(22.424855, 114.320267), new LatLng(22.414742,
							114.330375), new LatLng(22.416846, 114.336273),
					new LatLng(22.410533, 114.345955), new LatLng(22.415164,
							114.365738), new LatLng(22.433265, 114.364899),
					new LatLng(22.437474, 114.365318), new LatLng(22.441905,
							114.368468));
			final PolygonOptions taipo = new PolygonOptions().add(new LatLng(
					22.5109740, 114.334587), new LatLng(22.499305, 114.324859),
					new LatLng(22.48875, 114.314583), new LatLng(22.482362,
							114.304862), new LatLng(22.474583, 114.290412),
					new LatLng(22.46486, 114.268471), new LatLng(22.454862,
							114.257363), new LatLng(22.45514, 114.247359),
					new LatLng(22.465138, 114.237083), new LatLng(22.46486,
							114.209305), new LatLng(22.454862, 114.221252),
					new LatLng(22.45514, 114.205697), new LatLng(22.454028,
							114.199028), new LatLng(22.444916, 114.179307),
					new LatLng(22.433472, 114.203194), new LatLng(22.418086,
							114.194901), new LatLng(22.41494, 114.173493),
					new LatLng(22.415606, 114.145562), new LatLng(22.425203,
							114.120163), new LatLng(22.436212, 114.114823),
					new LatLng(22.450946, 114.116507), new LatLng(22.463156,
							114.126189), new LatLng(22.469893, 114.135452),
					new LatLng(22.475786, 114.145562), new LatLng(22.489258,
							114.156082), new LatLng(22.484627, 114.170815),
					new LatLng(22.492205, 114.175446), new LatLng(22.496415,
							114.185554), new LatLng(22.493468, 114.196074),
					new LatLng(22.496415, 114.231866), new LatLng(22.494733,
							114.250808), new LatLng(22.495996, 114.266807),
					new LatLng(22.494311, 114.291221), new LatLng(22.495152,
							114.311852), new LatLng(22.505677, 114.331216),
					new LatLng(22.510974, 114.334587));
			final PolygonOptions tsuenWan = new PolygonOptions().add(
					new LatLng(22.416037,114.140404 ),
					new LatLng(22.414742,114.145562 ),
					new LatLng(22.404217,114.16787),
					new LatLng(22.392427,114.164506),
					new LatLng(22.382745,114.153976),
					new LatLng(22.374746,114.135871),
					new LatLng(22.364642,114.118194),
					new LatLng(22.365419,114.111528),
					new LatLng(22.364861,114.072083),
					new LatLng(22.365419,114.063187),
					new LatLng(22.36404,114.05487 ),
					new LatLng(22.354851,114.0307),
					new LatLng(22.366746,114.035256),
					new LatLng(22.378114,114.030206 ),
					new LatLng(22.385271,114.045357 ),
					new LatLng(22.396639,114.073571),
					new LatLng(22.40548,114.097985),
					new LatLng(22.415583,114.124086),
					new LatLng(22.416037,114.140404));
			final PolygonOptions island = new PolygonOptions().add(new LatLng(
					22.314134, 113.998405), new LatLng(22.304582, 114.016526),
					new LatLng(22.294862, 114.025971), new LatLng(22.284861,
							114.020141), new LatLng(22.265418, 114.013748),
					new LatLng(22.2648620, 114.00264), new LatLng(22.2548610,
							114.009864), new LatLng(22.2448620, 113.999032),
					new LatLng(22.2331950, 114.005691), new LatLng(22.224860,
							114.015419), new LatLng(22.2148620, 114.004028),
					new LatLng(22.2168060, 113.994583), new LatLng(22.2148620,
							113.984024), new LatLng(22.2404180, 113.973198),
					new LatLng(22.2348610, 113.962638), new LatLng(22.224860,
							113.93264), new LatLng(22.2259730, 113.924308),
					new LatLng(22.2181940, 113.92514), new LatLng(22.2081950,
							113.924028), new LatLng(22.215140, 113.898476),
					new LatLng(22.2148620, 113.880143), new LatLng(22.2040270,
							113.868752), new LatLng(22.1945840, 113.847084),
					new LatLng(22.1959720, 113.844581), new LatLng(22.2356960,
							113.845696), new LatLng(22.2454160, 113.855416),
					new LatLng(22.2565270, 113.851806), new LatLng(22.2656930,
							113.869025), new LatLng(22.2765290, 113.885697),
					new LatLng(22.2893050, 113.895974), new LatLng(22.2845830,
							113.925972), new LatLng(22.2873610, 113.935143),
					new LatLng(22.295140, 113.950142), new LatLng(22.3051380,
							113.977913), new LatLng(22.3141340, 113.998405));
			
			final PolygonOptions tuenMun = new PolygonOptions().add(
					new LatLng(22.426822,113.938256),
					new LatLng(22.424064,113.995339),
					new LatLng(22.414742,114.010833),
					new LatLng(22.404641,114.056031),
					new LatLng(22.385271,114.054198),
					new LatLng(22.384008,114.044938),
					new LatLng(22.374746,114.030206),
					new LatLng(22.355416,114.023751),
					new LatLng(22.365139,114.01014),
					new LatLng(22.363473,114.004586),
					new LatLng(22.365139,113.992639),
					new LatLng(22.364305,113.984582),
					new LatLng(22.365417,113.974861),
					new LatLng(22.362917,113.964026),
					new LatLng(22.365695,113.940971),
					new LatLng(22.361251,113.934028),
					new LatLng(22.366805,113.924857),
					new LatLng(22.375972,113.914863),
					new LatLng(22.385138,113.920693),
					new LatLng(22.395139,113.914581),
					new LatLng(22.405416,113.90236),
					new LatLng(22.415139,113.909309),
					new LatLng(22.42514,113.932358),
					new LatLng(22.426822,113.938256));
			final PolygonOptions waiChai = new PolygonOptions().add(
					new LatLng(22.284861,114.174584),
					new LatLng(22.274238,114.197303),
					new LatLng(22.257322,114.192742),
					new LatLng(22.266163,114.166222),
					new LatLng(22.284861,114.174584));
			final PolygonOptions yauTsimMon = new PolygonOptions().add(
					new LatLng(22.323383,114.172501), 
					new LatLng(22.314123,114.175872 ), 
					new LatLng(22.292918,114.174858), 
					new LatLng(22.304029,114.164308), 
					new LatLng(22.312361,114.154861), 
					new LatLng(22.315139,114.155419),
					new LatLng(22.326332,114.153976),
					new LatLng(22.324648,114.156082),
					new LatLng(22.328014,114.165345),
					new LatLng(22.323383,114.172501));
			final PolygonOptions yeungLong = new PolygonOptions().add(
					new LatLng(22.505708, 114.059071),
					new LatLng(22.474938, 114.013409),
					new LatLng(22.486676, 114.012036),
					new LatLng(22.490165, 113.996930),
					new LatLng(22.455902, 113.972897),
					new LatLng(22.427343, 113.936505),
					new LatLng(22.415283, 113.938908),
					new LatLng(22.434642, 113.981480),
					new LatLng(22.421948, 114.004826),
					new LatLng(22.428295, 114.009976),
					new LatLng(22.428613, 114.018902),
					new LatLng(22.421313, 114.013409),
					new LatLng(22.410840, 114.015813),
					new LatLng(22.405127, 114.027486),
					new LatLng(22.405444, 114.046025),
					new LatLng(22.426391, 114.054265),
					new LatLng(22.426709, 114.064564),
					new LatLng(22.418457, 114.063878),
					new LatLng(22.414648, 114.076924),
					new LatLng(22.432421, 114.107136),
					new LatLng(22.460662, 114.107480),
					new LatLng(22.473035, 114.121899),
					new LatLng(22.528226, 114.078641)
					);
			final PolygonOptions kwunTong = new PolygonOptions().add(
					new LatLng(22.334834, 114.204630),
					new LatLng(22.327847, 114.204973),
					new LatLng(22.323719, 114.201540),
					new LatLng(22.315937, 114.210295),
					new LatLng(22.314985, 114.209179),
					new LatLng(22.313238, 114.211067),
					new LatLng(22.315540, 114.213385),
					new LatLng(22.298547, 114.229092),
					new LatLng(22.292393, 114.234585),
					new LatLng(22.286000, 114.241623),
					new LatLng(22.307560, 114.242846),
					new LatLng(22.312880, 114.245593),
					new LatLng(22.321694, 114.242589),
					new LatLng(22.322488, 114.234091),
					new LatLng(22.334477, 114.228341),
					new LatLng(22.334477, 114.207741)
					);
			final PolygonOptions wongTaiSin = new PolygonOptions().add(
					new LatLng(22.363250, 114.165019),
					new LatLng(22.344358, 114.153518),
					new LatLng(22.341341, 114.161243),
					new LatLng(22.346104, 114.162444),
					new LatLng(22.344040, 114.172057),
					new LatLng(22.346898, 114.180812),
					new LatLng(22.334990, 114.184760),
					new LatLng(22.336578, 114.193858),
					new LatLng(22.332291, 114.196433),
					new LatLng(22.335784, 114.203300),
					new LatLng(22.333402, 114.220981),
					new LatLng(22.338483, 114.218921),
					new LatLng(22.340706, 114.214114),
					new LatLng(22.347374, 114.211196),
					new LatLng(22.362615, 114.215316),
					new LatLng(22.372775, 114.211325),
					new LatLng(22.367060, 114.202141),
					new LatLng(22.367299, 114.190468),
					new LatLng(22.359123, 114.178537),
					new LatLng(22.355630, 114.176392),
					new LatLng(22.356900, 114.166607)
					);
			final PolygonOptions shumShuiPo = new PolygonOptions().add(
					new LatLng(22.348675, 114.141947),
					new LatLng(22.344706, 114.135338),
					new LatLng(22.339546, 114.134823),
					new LatLng(22.338672, 114.128987),
					new LatLng(22.337005, 114.129502),
					new LatLng(22.335417, 114.124094),
					new LatLng(22.328669, 114.126583),
					new LatLng(22.330574, 114.134222),
					new LatLng(22.327557, 114.135252),
					new LatLng(22.323111, 114.121605),
					new LatLng(22.317712, 114.128643),
					new LatLng(22.315092, 114.141947),
					new LatLng(22.322079, 114.147698),
					new LatLng(22.320650, 114.154736),
					new LatLng(22.326446, 114.160658),
					new LatLng(22.326684, 114.175078),
					new LatLng(22.335259, 114.176365),
					new LatLng(22.340895, 114.170872),
					new LatLng(22.345341, 114.147161)
					);
			
			final PolygonOptions kwaiT = new PolygonOptions().add(
					new LatLng(22.375645, 114.121212),
					new LatLng(22.351912, 114.115977),
					new LatLng(22.363263, 114.107308),
					new LatLng(22.363501, 114.098639),
					new LatLng(22.359691, 114.080357),
					new LatLng(22.352309, 114.079842),
					new LatLng(22.341910, 114.091343),
					new LatLng(22.331668, 114.089197),
					new LatLng(22.325793, 114.091257),
					new LatLng(22.327699, 114.114432),
					new LatLng(22.330874, 114.117007),
					new LatLng(22.338178, 114.123358),
					new LatLng(22.340878, 114.136233),
					new LatLng(22.348499, 114.141383),
					new LatLng(22.359612, 114.140868),
					new LatLng(22.376598, 114.147906),
					new LatLng(22.381042, 114.136748),
					new LatLng(22.375963, 114.119925),
					new LatLng(22.345341, 114.147161)
					);
			
			final PolygonOptions south = new PolygonOptions().add(
					new LatLng(22.279362, 114.196052),
					new LatLng(22.259346, 114.191760),
					new LatLng(22.261570, 114.188327),
					new LatLng(22.254739, 114.186610),
					new LatLng(22.256010, 114.170302),
					new LatLng(22.259823, 114.161548),
					new LatLng(22.257916, 114.148501),
					new LatLng(22.263635, 114.138373),
					new LatLng(22.284286, 114.140777),
					new LatLng(22.279044, 114.116744),
					new LatLng(22.249019, 114.134768),
					new LatLng(22.234719, 114.159144),
					new LatLng(22.229952, 114.172019),
					new LatLng(22.244094, 114.179057),
					new LatLng(22.243935, 114.187297),
					new LatLng(22.236467, 114.190215),
					new LatLng(22.235196, 114.197082),
					new LatLng(22.222801, 114.195022),
					new LatLng(22.218829, 114.210299),
					new LatLng(22.195307, 114.213389),
					new LatLng(22.197373, 114.223346),
					new LatLng(22.224073, 114.215793),
					new LatLng(22.231223, 114.235705),
					new LatLng(22.208817, 114.243258),
					new LatLng(22.208499, 114.260596),
					new LatLng(22.249655, 114.252356),
					new LatLng(22.256804, 114.255961),
					new LatLng(22.258075, 114.225749)
					);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					22.407969, 114.218549), 10));
			final Context con = this.getBaseContext();
			mMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public void onMapClick(LatLng point) {
					// TODO Auto-generated method stub
					boolean checkPoly = true;
					if (isPointInPolygon(point, shatin.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "13"));
					}
					else if (isPointInPolygon(point, cAndW.getPoints())) {

						Log.i("region", "cAndW");
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "23"));
					}
					else if (isPointInPolygon(point, island.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "9"));
					}
					else if (isPointInPolygon(point, eastern.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "24"));
					}
					else if (isPointInPolygon(point, kowloonCity.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "19"));
					}
					else if (isPointInPolygon(point, saikung.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "12"));
					}
					else if (isPointInPolygon(point, north.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "11"));
					}
					else if (isPointInPolygon(point, taipo.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "14"));
					}
					else if (isPointInPolygon(point, tsuenWan.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "15"));
					}
					else if (isPointInPolygon(point, tuenMun.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "16"));
					}
					else if (isPointInPolygon(point, waiChai.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "26"));
					}
					else if (isPointInPolygon(point, yauTsimMon.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "22"));
					}
					else if (isPointInPolygon(point, yeungLong.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "17"));
					}
					else if (isPointInPolygon(point, kwunTong.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "20"));
					}
					else if (isPointInPolygon(point, wongTaiSin.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "21"));
					}
					else if (isPointInPolygon(point, shumShuiPo.getPoints())) {
						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "18"));
					}
					else if (isPointInPolygon(point, kwaiT.getPoints())) {

						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "10"));
					}
					else if (isPointInPolygon(point, south.getPoints())) {

						startActivity(new Intent(getBaseContext(),
								LocalMap.class).addFlags(
								Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(
								"username", username).putExtra("region", "25"));
					}
				}
			});

			// Get back the mutable Polygon
			// mMap.setMyLocationEnabled(true);
			mMap.addPolygon(shatin.fillColor(Color.WHITE));
			mMap.addPolygon(cAndW.fillColor(Color.GRAY));
			mMap.addPolygon(island.fillColor(Color.RED));
			mMap.addPolygon(eastern.fillColor(Color.MAGENTA));
			mMap.addPolygon(kowloonCity.fillColor(Color.CYAN));
			
			mMap.addPolygon(saikung.fillColor(Color.GREEN));
			mMap.addPolygon(north.fillColor(Color.BLUE));
			mMap.addPolygon(taipo.fillColor(Color.LTGRAY));
			mMap.addPolygon(tsuenWan.fillColor(Color.rgb(238,130,238)));
			mMap.addPolygon(tuenMun.fillColor(Color.rgb(238,210,238)));
			

			mMap.addPolygon(waiChai.fillColor(Color.rgb(0,205,102)));
			mMap.addPolygon(yauTsimMon.fillColor(Color.rgb(142,56,142)));
			mMap.addPolygon(yeungLong.fillColor(Color.rgb(139,26,26)));
			mMap.addPolygon(kwunTong.fillColor(Color.rgb(198,113,113)));
			mMap.addPolygon(wongTaiSin.fillColor(Color.BLACK));

			mMap.addPolygon(shumShuiPo.fillColor(Color.YELLOW));
			mMap.addPolygon(kwaiT.fillColor(Color.BLUE));
			mMap.addPolygon(south.fillColor(Color.BLUE));
			if (mMap != null) {
				// setUpMap();
			}
		}
	}

	private boolean isPointInPolygon(LatLng tap, List<LatLng> list) {
		int intersectCount = 0;
		for (int j = 0; j < list.size() - 1; j++) {
			if (rayCastIntersect(tap, list.get(j), list.get(j + 1))) {
				intersectCount++;
			}
		}
		if ((intersectCount % 2) == 1) {
			return true;
		}
		return false;
		// return (intersectCount%2) == 1); // odd = inside, even = outside;
	}

	private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

		double aY = vertA.latitude;
		double bY = vertB.latitude;
		double aX = vertA.longitude;
		double bX = vertB.longitude;
		double pY = tap.latitude;
		double pX = tap.longitude;

		if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
				|| (aX < pX && bX < pX)) {
			return false; // a and b can't both be above or below pt.y, and a or
							// b must be east of pt.x
		}
/*
		double m = (aY - bY) / (aX - bX); // Rise over run
		double bee = (-aX) * m + aY; // y = mx + b
		double x = (pY - bee) / m; // algebra is neat!
*/
		return true;//x > pX;
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
					// current.setPadding(100, 0, 100, 0);
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
		super.onBackPressed();
		return super.onOptionsItemSelected(item);
	}
}
