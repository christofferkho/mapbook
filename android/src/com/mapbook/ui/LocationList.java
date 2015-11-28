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

import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		openOptionsMenu();
		showUserLocations();
	}
	
	// components
	private TextView header;
	private LinearLayout locations;
	
	// prepare component fonts and variables
	private void prepareComponents() {
		header = (TextView) findViewById(R.id.location_view_header);
		locations = (LinearLayout) findViewById(R.id.location_view);
	}
	
	// show the current user's locations in the view
	private void showUserLocations() {
		List<Marker> markers = User.getLocationMarkers();
		if (markers == null || markers.isEmpty()) {
			Button button = new Button(this);
			button.setText("You have no markers. Click here to add a new marker.");
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO: Open add marker activity
				}
			});
			locations.addView(button);
		}
		for (Marker marker : markers) {
			addLocation(marker.getLocation());
		}
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_add_marker:
				// TODO: open add marker activity
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}