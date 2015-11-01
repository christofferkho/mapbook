package com.mapbook.parse;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Marker extends ParseGeoPoint {
	
	// constructors
	public Marker(double latitude, double longitude) {
		setCoordinates(latitude, longitude);
	}
	public Marker(double latitude, double longitude, Path path) {
		this(latitude, longitude);
		setPath(path);
	}
	public Marker(double latitude, double longitude, Location location) {
		this(latitude, longitude);
		setLocation(location);
	}
	public Marker(double latitude, double longitude, Path path, Location location) {
		this(latitude, longitude);
		setPath(path);
		setLocation(location);
	}
	
	// members
	private Location location = null;
	private Path path = null;
	private ParseUser user = ParseUser.getCurrentUser();
	private ParseObject parseObject = new ParseObject("MARKER");
	
	// getters
	public Location getLocation() {return location;}
	public Path getPath() {return path;}
	public ParseUser getUser() {return user;}
	public ParseObject getParseObject() {return parseObject;}
	public ParseGeoPoint getCoordinates() {return this;}
	
	// setters
	public void setLocation(Location location) {
		this.location = location;
		parseObject.put("location", location == null ? null : location.getParseObject());
	}
	public void setPath(Path path) {
		this.path = path;
		parseObject.put("path", path == null ? null : path.getParseObject());
	}
	public void setUser(ParseUser user) {
		this.user = user;
		parseObject.put("user", user == null ? null : user);
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