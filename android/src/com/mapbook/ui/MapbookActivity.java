package com.mapbook.ui;

import java.util.HashSet;

import com.parse.Parse;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Extend this class to automatically connect to Mapbook's Parse database.
 * @author Rico Tiongson
 * 11/01/15
 * {@code public class MyActivity extends MapbookActivity {...}}
 */
public class MapbookActivity extends Activity {

	@Override
	/**
	 * Initializes Parse along with the creation of this activity.
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.enableLocalDatastore(this);
		Parse.initialize(this); // Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);		
	}
	
	@Override
	/**
	 * Finds view in the current layout by id. Applies Pacifico font to TextViews by default.
	 * @see android.app.Activity#findViewById(int)
	 */
	public View findViewById(int id) {
		View view = super.findViewById(id);
		
		if (usePacifico && (view instanceof TextView) && !pacificoViews.contains(id)) {
			// load font if does not exist yet
			if (pacificoFont == null)
				pacificoFont = Typeface.createFromAsset(this.getAssets(), "fonts/Pacifico.ttf");
			
			// set the font to view
			((TextView) view).setTypeface(pacificoFont);
			
			// add view to assigned
			pacificoViews.add(id);
			
		}
		
		return view;
	}
	
	// specific application keys to connect to Mapbook's Parse database online
	// (for reference only)
	public static final String APPLICATION_ID = "YyN0pW0gRrPy4vQYebW86IT03bYysrQZi8n5XkfC";
	public static final String CLIENT_KEY = "AjGE0VZly2K7Qja72nmAgQa0JAt02zpZPWlCN48g";	
	
	// the default Pacifico font
	public static Typeface pacificoFont = null;
	static boolean usePacifico = true;
	static HashSet<Integer> pacificoViews = new HashSet<Integer>();
}