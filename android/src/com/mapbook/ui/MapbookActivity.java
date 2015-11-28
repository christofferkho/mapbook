package com.mapbook.ui;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Extend this class to automatically connect to Mapbook's Parse database.
 * @author Rico Tiongson
 * 11/01/15
 * {@code public class MyActivity extends MapbookActivity {...}}
 */
public class MapbookActivity extends Activity {
	
	private static boolean initializedParse = false;

	@Override
	/**
	 * Initializes Parse along with the creation of this activity.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!initializedParse) {
			Parse.enableLocalDatastore(this);
			Parse.initialize(this); // Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
			initializedParse = true;
			// do not log out on exit
			ParseUser.enableRevocableSessionInBackground();
		}
		if (getIntent().hasExtra("message"))
			alert(getIntent().getStringExtra("message"));
	}
	
	@Override
	public View findViewById(int id) {
		View view = super.findViewById(id);
		changeFont(this, view);
		return view;
	}
	
	public static void changeFont(Context context, View view) {
		if (usePacifico && (view instanceof TextView)) {
			// load font if does not exist yet
			if (pacificoFont == null)
				pacificoFont = Typeface.createFromAsset(context.getAssets(), "fonts/Pacifico.ttf");
			
			// set the font to view
			((TextView) view).setTypeface(pacificoFont);
		}
	}
	
	// specific application keys to connect to Mapbook's Parse database online
	// (for reference only)
	public static final String APPLICATION_ID = "YyN0pW0gRrPy4vQYebW86IT03bYysrQZi8n5XkfC";
	public static final String CLIENT_KEY = "AjGE0VZly2K7Qja72nmAgQa0JAt02zpZPWlCN48g";	
	
	// the default Pacifico font
	public static Typeface pacificoFont = null;
	static boolean usePacifico = true;
	
	// default message box
	public AlertDialog alert(Object message) {
		return alert(message, "");
	}
	
	// message box with title
	public AlertDialog alert(Object message, Object title) {
		return new AlertDialog.Builder(this)
	    .setTitle(title.toString())
	    .setMessage(message.toString())
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	// launch new activity handler
	public void launchActivity(Class<?> activityClass) {
		Intent intent = new Intent(this.getApplicationContext(), activityClass);
		startActivity(intent);
		finish();
	}
	
	// launch new activity with message
	public void launchActivity(Class<?> activityClass, Object message) {
		Intent intent = new Intent(this.getApplicationContext(), activityClass);
		intent.putExtra("message", message.toString());
		startActivity(intent);
		finish();
	}
	
	// launch activity on click handler
	public void launchActivityOnClick(View clicker, final Class<?> activityClass) {
		clicker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchActivity(activityClass);
			}
		});
	}
	
	// receive messages from another activity and show on dialog
	public void showMessage() {
		Intent intent = getIntent();
		if (intent.hasExtra("message"))
			alert(intent.getStringExtra("message"));
	}
}