package com.mapbook.parse;

import java.util.List;

import com.mapbook.GPSTracker;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Marker extends ParseGeoPoint {
	
	// constructors
	/**
	 * Construct an empty marker using the current GPS coordinates.
	 */
	public Marker() {
		this(GPSTracker.getLatitude(), GPSTracker.getLongitude());
	}
	
	/**
	 * Construct a marker object using a specified (latitude, longitude) pair.
	 * @param latitude
	 * @param longitude
	 */
	public Marker(double latitude, double longitude) {
		this.user = ParseUser.getCurrentUser();
		this.parseObject = new ParseObject("MARKER");
		this.setCoordinates(latitude, longitude);
	}
	
	/**
	 * Wraps a ParseObject through this Marker class.
	 * @param parseObject
	 * @throws ParseException
	 */
	public Marker(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		parseObject.fetchIfNeeded();
		ParseGeoPoint point = parseObject.getParseGeoPoint("coordinates");
		this.setCoordinates(point.getLatitude(), point.getLongitude());
		if (parseObject.has("location")) {
			this.location = new Location(parseObject.getParseObject("location"));
		}
		if (parseObject.has("path")) {
			this.path = new Path(parseObject.getParseObject("path"));
		}
		this.user = parseObject.getParseUser("user");
	}
	
	// members
	private Location location = null;
	private Path path = null;
	private ParseUser user;
	private ParseObject parseObject;
	
	// getters
	public Location getLocation() {return location;}
	public Path getPath() {return path;}
	public ParseUser getUser() {return user;}
	public ParseObject getParseObject() {return parseObject;}
	public ParseGeoPoint getCoordinates() {return this;}
	
	// setters
	public void setLocation(Location location) {
		this.location = location;
		if (location == null) parseObject.remove("location");
		else parseObject.put("location",  location.getParseObject());
	}
	public void setPath(Path path) {
		this.path = path;
		if (path == null) parseObject.remove("path");
		else parseObject.put("path",  path.getParseObject());
	}
	public void setUser(ParseUser user) {
		this.user = user;;
		if (user == null) parseObject.remove("user");
		else parseObject.put("user",  user);
	}
	@Override
	public void setLatitude(double latitude) {
		super.setLatitude(latitude);
		parseObject.put("coordinates", this);
	}
	@Override
	public void setLongitude(double longitude) {
		super.setLongitude(longitude);
		parseObject.put("coordinates", this);
	}
	public void setCoordinates(double latitude, double longitude) {
		super.setLatitude(latitude);
		super.setLongitude(longitude);
		parseObject.put("coordinates", this);
	}
	
	/**
	 * Saves the current marker. This does not save the
	 * @see com.parse.ParseObject#saveEventually()
	 */
	public void save() {
		getParseObject().saveEventually();
	}
	
}