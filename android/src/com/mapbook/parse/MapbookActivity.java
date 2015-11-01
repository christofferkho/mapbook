package com.mapbook.parse;

import com.parse.Parse;

import android.app.Activity;
import android.os.Bundle;

/**
 * Extend this class to automatically connect to Mapbook's Parse database.
 * @author Rico Tiongson
 * 11/01/15
 * {@code public class MyActivity extends MapbookActivity {...}}
 */
public class MapbookActivity extends Activity {

	/**
	 * Initializes Parse along with the creation of this activity.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.enableLocalDatastore(this);
		Parse.initialize(this); // Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
	}
	
	// specific application keys to connect to Mapbook's Parse database online
	// (for reference only)
	public static final String APPLICATION_ID = "YyN0pW0gRrPy4vQYebW86IT03bYysrQZi8n5XkfC";
	public static final String CLIENT_KEY = "AjGE0VZly2K7Qja72nmAgQa0JAt02zpZPWlCN48g";	
	
}