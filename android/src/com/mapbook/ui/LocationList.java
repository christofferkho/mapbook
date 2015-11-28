package com.mapbook.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapbook.GPSTracker;
import com.mapbook.locationsaver.R;
import com.mapbook.parse.Location;
import com.mapbook.parse.Marker;
import com.mapbook.parse.User;
import com.parse.ParseUser;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationList extends MapbookActivity {
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_view);
		prepareComponents();
		GPSTracker.trackActivity(this);

		((Button) findViewById(R.id.buttonz)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Button) v).setText(String.format("(%.4f, %.4f)", GPSTracker.getLatitude(), GPSTracker.getLongitude()));
			}
		});
	}
	
	public String hex() {
		return Integer.toHexString((int) (Math.random() * Integer.MAX_VALUE));
	}
	
	// components
	private TextView header;
	private LinearLayout locations;
	
	// prepare component fonts and variables
	private void prepareComponents() {
		header = (TextView) findViewById(R.id.location_view_header);
		locations = (LinearLayout) findViewById(R.id.location_view);
	}
	
	// a map of entries (as Views) to Marker object
	private Map<View, Location> entryToLocation = new HashMap<View, Location>();
	
	// default listener for click events
	private OnClickListener locationClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Location location = entryToLocation.get(view);
			alert(String.format("Name: %s\nDescription: %s\nAddress: %s\nContact: %s\nNotes: %s\n",
				location.getName(),
				location.getDescription(),
				location.getAddress(),
				location.getContact(),
				location.getNotes()
			));
			// launchActivity(LocationView.class, entryToLocation.get(view).getParseObject().getObjectId());
		}
	};
	
	// adds a location to the set and represent it as an "entry" object
	public LinearLayout addLocation(Location location) {
		
		// inflate the empty layout from layout/location_view_entry.xml
		LinearLayout entryLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.location_view_entry, null);
		TextView nameView = (TextView) entryLayout.findViewWithTag("name");
		TextView notesView = (TextView) entryLayout.findViewWithTag("notes");
		
		// set initial text of entry
		nameView.setText(location.getName());
		notesView.setText(location.getNotes());
		
		// set listener for location
		entryLayout.setOnClickListener(locationClick);
		
		// manage associative definitions
		entryToLocation.put(entryLayout, location);
		locations.addView(entryLayout);
		return entryLayout;
	}

}