package hkbu.cs.ivanyu.fyp2014_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork() // or
																			// .detectAll()
																			// for
																			// all
																			// detectable
																			// problems
					.penaltyLog().build());
		}

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		Typeface typeface = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto/RobotoCondensed-LightItalic.ttf");
		TextView textView = (TextView) findViewById(R.id.main);
		textView.setTypeface(typeface);

		if (getIntent().getExtras() != null
				&& getIntent().getExtras().getString("loginFail") != null) {
			AlertDialog dialog;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			dialog = builder.setTitle("iEditio")
					.setMessage("Login Fail! Please check your input data!")
					.create();
			dialog.show();
		}
		// Set up the login form.
		// mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		// mEmailView.setText(mEmail);
		Button mRegisterView = (Button) findViewById(R.id.reg_in_button);
		mRegisterView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				reg();
			}

		});
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void reg() {
		// Create Object of Dialog class
		final Dialog login = new Dialog(this,
				android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth);
		// Set GUI of login screen
		login.setContentView(R.layout.register_dialog);

		// Init button of login GUI
		Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
		Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
		final EditText mUsernameView = (EditText) login
				.findViewById(R.id.txtUsername);
		final EditText mFirstNameView = (EditText) login
				.findViewById(R.id.firstName);
		final EditText mLastNameView = (EditText) login
				.findViewById(R.id.lastName);
		final EditText mEmailView = (EditText) login.findViewById(R.id.email);
		final EditText mTxtPasswordView = (EditText) login
				.findViewById(R.id.txtPassword);
		final EditText mRetypePasswordView = (EditText) login
				.findViewById(R.id.retypePassword);
		mUsernameView.setError(null);
		mFirstNameView.setError(null);
		mLastNameView.setError(null);
		mEmailView.setError(null);
		mTxtPasswordView.setError(null);
		mRetypePasswordView.setError(null);
		// txtPassword
		// Attached listener for login GUI button
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				boolean cancel = false;
				View focusView = null;

				// Check for a valid password.
				if (TextUtils.isEmpty(mUsernameView.getText())) {
					mUsernameView
							.setError(getString(R.string.error_field_required));
					focusView = mUsernameView;
					cancel = true;
				} else if (mUsernameView.getText().length() < 4) {
					mUsernameView.setError("This field can't be too short");
					focusView = mUsernameView;
					cancel = true;
				}

				if (TextUtils.isEmpty(mFirstNameView.getText())) {
					mFirstNameView
							.setError(getString(R.string.error_field_required));
					focusView = mFirstNameView;
					cancel = true;
				} else if (mFirstNameView.getText().length() < 4) {
					mFirstNameView.setError("This field can't be too short");
					focusView = mFirstNameView;
					cancel = true;
				}

				if (TextUtils.isEmpty(mLastNameView.getText())) {
					mLastNameView
							.setError(getString(R.string.error_field_required));
					focusView = mLastNameView;
					cancel = true;
				} else if (mLastNameView.getText().length() < 1) {
					mLastNameView.setError("This field can't be too short");
					focusView = mLastNameView;
					cancel = true;
				}

				if (TextUtils.isEmpty(mEmailView.getText())) {
					mEmailView
							.setError(getString(R.string.error_field_required));
					focusView = mLastNameView;
					cancel = true;
				} else if (mEmailView.getText().length() < 4) {
					mEmailView.setError("This field can't be too short");
					focusView = mEmailView;
					cancel = true;
				}

				if (TextUtils.isEmpty(mTxtPasswordView.getText())) {
					mTxtPasswordView
							.setError(getString(R.string.error_field_required));
					focusView = mTxtPasswordView;
					cancel = true;
				} else if (mTxtPasswordView.getText().length() < 4) {
					mTxtPasswordView.setError("This field can't be too short");
					focusView = mTxtPasswordView;
					cancel = true;
				}

				if (TextUtils.isEmpty(mRetypePasswordView.getText())) {
					mRetypePasswordView
							.setError(getString(R.string.error_field_required));
					focusView = mTxtPasswordView;
					cancel = true;
				} else if (mRetypePasswordView.getText().length() < 4) {
					mRetypePasswordView
							.setError("This field can't be too short");
					focusView = mRetypePasswordView;
					cancel = true;
				}

				if (!mTxtPasswordView.getText().toString().equals(
						mRetypePasswordView.getText().toString())) {
					//Log.("i" , )
					mRetypePasswordView
							.setError("password not match");
					focusView = mRetypePasswordView;
					cancel = true;
				}
				if (cancel) {
					// There was an error; don't attempt login and focus the
					// first
					// form field with an error.
					focusView.requestFocus();
				} else {
					ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
					if (networkInfo != null && networkInfo.isConnected()) {
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/register.php");
						try {
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
							nameValuePairs.add(new BasicNameValuePair(
									"userName", mUsernameView.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"firstName", mFirstNameView.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"lastName", mLastNameView.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair("email",
									mEmailView.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"password", mTxtPasswordView.getText()
											.toString()));

							httppost.setEntity(new UrlEncodedFormEntity(
									nameValuePairs));
							HttpResponse response = httpclient
									.execute(httppost);

							HttpEntity entity = response.getEntity();
							InputStream is = entity.getContent();
							Log.i("postData", convertStreamToString(is) + " "
									+ response.getStatusLine().toString());
							login.cancel();

							Toast.makeText(LoginActivity.this,
									"Create Account Sucessfull",
									Toast.LENGTH_LONG).show();
							startActivity(new Intent(getBaseContext(),
									MainActivity.class).putExtra("userName",
									mUsernameView.getText().toString()));
						} catch (Exception e) {
							Log.e("log_tag",
									"Error in http connection" + e.toString());
						}
					} else {
						Toast.makeText(LoginActivity.this,
								"Internet Access, Denied!!", Toast.LENGTH_LONG)
								.show();
					}
				}
				attemptSubmit();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login.dismiss();
			}
		});

		// Make dialog box visible.
		login.show();
	}

	public void attemptSubmit() {

		boolean cancel = false;
		View focusView = null;
		LayoutInflater inflater = getLayoutInflater();

		View modifyView = inflater.inflate(R.layout.register_dialog, null);

		// Check for a valid email address.
		/*
		 * if (TextUtils.isEmpty(mEmail)) {
		 * mEmailView.setError(getString(R.string.error_field_required));
		 * focusView = mEmailView; cancel = true; } else if
		 * (!mEmail.contains("@")) {
		 * mEmailView.setError(getString(R.string.error_invalid_email));
		 * focusView = mEmailView; cancel = true; }
		 */
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			Log.i("hihihi",
					"hihihi"
							+ ((EditText) modifyView
									.findViewById((R.id.txtUsername)))
									.getText());
		} else {

			Log.i("hihihi",
					"hihihi2"
							+ ((EditText) modifyView
									.findViewById((R.id.txtUsername)))
									.toString());
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			// mPasswordView.setError(getString(R.string.error_invalid_password));
			// focusView = mPasswordView;
			// cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			// mEmailView.setError(getString(R.string.error_invalid_email));
			// focusView = mEmailView;
			// cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			boolean t = false;
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			try {
				JSONParser jsp = new JSONParser();
				JSONObject obj = jsp
						.getJSONFromUrl("http://cslinux0.comp.hkbu.edu.hk/~e1017295/iBook/JSON/User.php");
				// Log.i("hihihi", "hihihi" + obj);
				if (obj.getJSONArray("Users").length() > 0) {
					int length = obj.getJSONArray("Users").length();
					for (int i = 0; i < length; i++) {
						if (obj.getJSONArray("Users").getJSONObject(i)
								.getString("userName").equals(mEmail)) {
							if (obj.getJSONArray("Users").getJSONObject(i)
									.getString("password").equals(mPassword)) {
								t = true;
							}
						}
						Log.i("hihihi", "hihihi"
								+ obj.getJSONArray("Users").getJSONObject(i)
										.getString("email")
								+ " "
								+ obj.getJSONArray("Users").getJSONObject(i)
										.getString("password"));
					}
					/*
					 * if (obj.getJSONArray("Users").getJSONObject(0)
					 * .getString("isbn") != null) { Log.i("hihihi", "hihihi" +
					 * obj.getJSONArray("Books").getJSONObject(1)
					 * .getString("author")); Button tw = (Button)
					 * findViewById(R.id.sign_in_button); //
					 * tw.setText("312312"); tw.setText("jj" +
					 * obj.getJSONArray("Books").getJSONObject(1)
					 * .getString("author")); }
					 */
				}
				// Log.i("hi",
				// obj.getJSONArray("results").getJSONObject(0).getString("formatted_address"));
			} catch (Exception e) {
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			if (t == true) {
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				startActivity(new Intent(i).putExtra("username", mEmailView
						.getText().toString()));
			} else {

				Intent i = new Intent(getBaseContext(), LoginActivity.class);
				i.putExtra("loginFail", "loginFail");
				startActivity(new Intent(i));
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	public void onBackPressed() {

	}
}
