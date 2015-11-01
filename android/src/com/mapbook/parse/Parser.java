package com.mapbook.parse;

import com.parse.Parse;

import android.content.Context;

public class Parser {

	// specific application keys to connect to mapbook Parse database online
	public static final String APPLICATION_ID = "YyN0pW0gRrPy4vQYebW86IT03bYysrQZi8n5XkfC";
	public static final String CLIENT_KEY = "AjGE0VZly2K7Qja72nmAgQa0JAt02zpZPWlCN48g";
	
	// initialization flags
	private static boolean initialized = false;
	public static boolean isInitialized() {return initialized;}
	
	/**
	 * Initializes Parse online and enables local database.
	 * @param context the Android activity/context Parse will be linked to
	 */
	
	public static void init(Context context) {
		if (isInitialized()) return;
		Parse.enableLocalDatastore(context);
		Parse.initialize(context);
		initialized = true;
	}
	
}