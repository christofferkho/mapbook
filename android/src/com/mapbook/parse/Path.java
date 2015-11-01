package com.mapbook.parse;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * 
 * @author Rico
 *
 */

public class Path {
	
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
	
	// members
	private double epsilon; // allowable meter range until next point
	private ArrayList<Marker> pathList = new ArrayList<Marker>();
	private ParseObject parseObject = new ParseObject("PATH");
	private boolean destroyed = false;
	
	// static members
	public static double DEFAULT_EPSILON = 15.0; // 15 meters, change-able
	
	// getters
	public String getName() {return parseObject.getString("name");}
	public String getDescription() {return parseObject.getString("description");}
	public double getEpsilon() {return epsilon;}
	public List<Marker> getPathList() {return pathList;}
	public ParseObject getParseObject() {return parseObject;}
	public boolean isDestroyed() {return destroyed;}
	
	// setters
	public void setName(String name) {parseObject.put("name", name);}
	public void setDescription(String description) {parseObject.put("description", description);}
	public void setEpsilon(double epsilon) {this.epsilon = epsilon;}
	
	// list properties/methods
	public int size() {return pathList.size();}
	public boolean isEmpty() {return pathList.isEmpty();}
	public Marker back() {return pathList.get(size() - 1);}
	
	/**
	 * Adds a marker to the path list.
	 * It won't be added if the distance from the most recent marker does not exceed <var>epsilon</var> meters.<br>
	 * If the checkpoint is <var>null</var>, an empty marker will be added instead.
	 * @param latitude
	 * @param longitude
	 * @param checkpoint the information about the location
	 * @return true if the point was successfully inserted
	 */
	public boolean add(double latitude, double longitude, Location checkpoint) {
		// check first if already destroyed
		if (destroyed) return false;
		
		// create the point to be added
		ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
		
		// replace the last GPS coordinate with checkpoint
		// if the previous GPS coordinate is an empty marker
		if (checkpoint != null && !isEmpty() && back().getLocation() == null && point.distanceInKilometersTo(back()) <= epsilon * 1e3) {
			pathList.set(size() - 1, new Marker(latitude, longitude, this, checkpoint));
			return true;
		}
		
		// otherwise, add new point to the list if it's far
		// from the last GPS coordinate in the path
		else if (isEmpty() || point.distanceInKilometersTo(back()) > epsilon * 1e3) {
			pathList.add(new Marker(latitude, longitude, this, checkpoint));
			return true;
		}
		
		return false;
	}

	/**
	 * Adds an empty marker to the path list.
	 * It won't be added if the distance from the most recent marker does not exceed <var>epsilon</var> meters.<br>
	 * @param latitude
	 * @param longitude
	 * @return true if the point was successfully inserted
	 */
	public boolean add(double latitude, double longitude) {
		return this.add(latitude, longitude, null);
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
	 * Note that this destroys the current Path object after save to ensure data stability.
	 * @see com.parse.ParseObject#saveEventually()
	 */
	public void save() {
		// save path node
		parseObject.saveEventually();
		
		// save checkpoint locations
		for (Marker marker : pathList) {
			if (marker.getLocation() != null)
				marker.getLocation().save();
			marker.save();
		}
		
		// decapacitate current path object to ensure stability
		destroy();
	}
}