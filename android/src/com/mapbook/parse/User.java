package com.mapbook.parse;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class User {

	/**
	 * Fetches all locations associated with user.
	 */
	public static List<Marker> getLocationMarkers() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MARKER");
		query.whereNotEqualTo("location", null);
		query.whereEqualTo("path", null);
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.orderByDescending("createdAt");
		try {
			List<ParseObject> parseObjects = query.find();
			List<Marker> list = new ArrayList<Marker>();
			for (ParseObject marker : parseObjects)
				list.add(new Marker(marker));
			return list;
		} catch (ParseException ex) {
			ex.printStackTrace(System.err);
			return null;
		}
	}
	
	/**
	 * Fetches all paths associated with current user.
	 */
	public static List<Path> getPathLists() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MARKER");
		query.whereNotEqualTo("path", null);
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.orderByDescending("createdAt");
		try {
			List<ParseObject> parseObjects = query.find();
			List<Path> list = new ArrayList<Path>();
			for (ParseObject path : parseObjects)
				list.add(new Path(path));
			return list;
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	// getters
	
	public static ParseUser getCurrentUser() {
		return ParseUser.getCurrentUser();
	}
	
	public static String getUsername() {
		return getCurrentUser().getUsername();
	}
	
}