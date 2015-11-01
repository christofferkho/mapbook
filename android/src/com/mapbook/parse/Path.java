package com.mapbook.parse;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Path {
	
	// members
	private double epsilon; // allowable meter range until next point
	private ArrayList<Point> pathList = new ArrayList<Point>();
	private ParseObject parseObject = new ParseObject("PATH");
	private boolean destroyed = false;
	
	// static members
	public static double DEFAULT_EPSILON = 15.0; // 15 meters, change-able
	
	// getters
	public String getName() {return parseObject.getString("name");}
	public String getDescription() {return parseObject.getString("description");}
	public double getEpsilon() {return epsilon;}
	public List<Point> getPathList() {return pathList;}
	public ParseObject getParseObject() {return parseObject;}
	public boolean isDestroyed() {return destroyed;}
	
	// setters
	public void setName(String name) {parseObject.put("name", name);}
	public void setDescription(String description) {parseObject.put("description", description);}
	public void setEpsilon(double epsilon) {this.epsilon = epsilon;}
	
	// list properties/methods
	public int size() {return pathList.size();}
	public boolean isEmpty() {return pathList.isEmpty();}
	public Point back() {return pathList.get(size() - 1);}
	
	/**
	 * Adds a marker to the path list.
	 * It won't be added if the distance from the most recent marker does not exceed epsilon meters.
	 * @param point
	 * @return true if the point was successfully inserted
	 */
	public boolean add(Point point) {
		// check first if already destroyed
		if (destroyed) return false;
		// get the checkpoint location
		Location checkpoint = point.getCheckPoint();
		// replace the last GPS coordinate with checkpoint
		// if the previous GPS coordinate is an empty marker
		if (checkpoint != null && !isEmpty() && back().getCheckPoint() == null && point.distanceInKilometersTo(back()) < epsilon * 1e3) {
			pathList.set(size() - 1,  point);
			return true;
		}
		// otherwise, add new point to the list if it's far
		// from the last GPS coordinate in the path
		else if (isEmpty() || point.distanceInKilometersTo(back()) >= epsilon * 1e3) {
			pathList.add(point);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a checkpoint marker to the path list.
	 * It won't be added if the distance from the most recent marker does not exceed epsilon meters.
	 * @param latitude
	 * @param longitude
	 * @param checkpoint
	 * @return true if the point was successfully inserted
	 */
	public boolean add(double latitude, double longitude, Location checkpoint) {
		return this.add(new Point(latitude, longitude, checkpoint));
	}

	/**
	 * Adds an empty marker to the path list.
	 * @param latitude
	 * @param longitude
	 * @return true if the point was successfully inserted
	 */
	public boolean add(double latitude, double longitude) {
		return this.add(new Point(latitude, longitude, null));
	}
	
	/**
	 * Decapacitates current Path object.
	 */
	public void destroy() {
		if (!destroyed) {
			pathList.clear();
			pathList = null;
			parseObject = null;
			destroyed = true;
		}
	}
	
	/**
	 * Saves all markers in the path list into the Parse database.
	 * Destroys current Path object after save to ensure data stability.
	 */
	public void saveAndDestroy() {
		// save path node
		parseObject.saveEventually();
		
		// save checkpoint locations
		for (Point point : pathList) {
			// create a marker for the point
			ParseObject marker = new ParseObject("MARKER");
			marker.put("coordinates", point);
			marker.put("user", ParseUser.getCurrentUser());
			marker.put("location", point.getParseObject());
			marker.put("path", this.parseObject);
			
			// save eventually
			point.getParseObject().saveEventually();
			marker.saveEventually();
		}
		
		// decapacitate current path object to ensure stability
		destroy();
	}

	/**
	 * Default constructor of the Path class.
	 * Sets name and description to empty strings.
	 * Epsilon value of DEFAULT_EPSILON will be used.
	 */
	public Path() {
		this.setName("");
		this.setDescription("");
		this.setEpsilon(DEFAULT_EPSILON);
	}
	
	/**
	 * Constructor of the Path class.
	 * Epsilon value of DEFAULT_EPSILON will be used.
	 * @param name the name of the path
	 * @param description text describing the path
	 */
	public Path(String name, String description) {
		this.setName(name);
		this.setDescription(description);
		this.setEpsilon(DEFAULT_EPSILON);
	}
	
	/**
	 * Constructor of the Path class.
	 * @param name the name of the path
	 * @param description text describing the path
	 * @param epsilon the distance to exceed (meters) to be able to add a new marker
	 */
	public Path(String name, String description, double epsilon) {
		this.setName(name);
		this.setDescription(description);
		this.setEpsilon(epsilon);
	}
	
	/**
	 * A GeoPoint object that can be uploaded to Parse with an optional checkpoint location
	 * @author Rico Tiongson
	 * @version 1.0
	 * 11/01/15
	 */
	public static class Point extends ParseGeoPoint {
		private Location checkPoint;
		public Location getCheckPoint() {return checkPoint;}
		public ParseObject getParseObject() {return checkPoint.getParseObject();}
		public Point(double latitude, double longitude) {
			super(latitude, longitude);
			this.checkPoint = null;
		}
		public Point(double latitude, double longitude, Location location) {
			super(latitude, longitude);
			this.checkPoint = location;
		}
	}
}