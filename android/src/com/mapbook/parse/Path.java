package com.mapbook.parse;

import java.util.ArrayList;
import java.util.List;

import com.mapbook.GPSTracker;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
		this.parseObject = new ParseObject("PATH");
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
		this.parseObject = new ParseObject("PATH");
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
		this.parseObject = new ParseObject("PATH");
		this.setName(name);
		this.setDescription(description);
		this.setEpsilon(epsilon);
	}
	
	/**
	 * Wraps a ParseObject using this Path class.
	 * @param parseObject
	 * @throws ParseException 
	 */
	public Path(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		this.parseObject.fetchIfNeeded();
		this.setEpsilon(DEFAULT_EPSILON);
		this.syncMarkers();
	}
	
	// members
	private double epsilon; // allowable meter range until next point
	private ArrayList<Marker> pathList = new ArrayList<Marker>();
	private ParseObject parseObject;
	
	// static members
	public static double DEFAULT_EPSILON = 15.0; // 15 meters, change-able
	
	// getters
	public String getName() {return parseObject.getString("name");}
	public String getDescription() {return parseObject.getString("description");}
	public double getEpsilon() {return epsilon;}
	public List<Marker> getPathList() {return pathList;}
	public ParseObject getParseObject() {return parseObject;}
	
	// setters
	public void setName(String name) {parseObject.put("name", name);}
	public void setDescription(String description) {parseObject.put("description", description);}
	public void setEpsilon(double epsilon) {this.epsilon = epsilon;}
	
	// list properties/methods
	public int size() {return pathList.size();}
	public boolean isEmpty() {return pathList.isEmpty();}
	public Marker back() {return pathList.get(size() - 1);}
	
	/**
	 * Adds the current GPS coordinates as an empty marker to the path list.
	 * It won't be added if the distance from the most recent marker does not exceed <var>epsilon</var> meters.<br>
	 * @return true if the point was successfully inserted
	 */
	public boolean add() {
		return this.add(GPSTracker.getLatitude(), GPSTracker.getLongitude(), null);
	}
	
	/**
	 * Adds a (named) marker to the path list with a Location checkpoint.
	 * It won't be added if the distance from the most recent marker does not exceed <var>epsilon</var> meters.<br>
	 * If the checkpoint is <var>null</var>, an empty marker will be added instead.
	 * @param checkpoint the information about the location
	 * @return true if the point was successfully inserted
	 */
	public boolean add(Location checkpoint) {
		return this.add(GPSTracker.getLatitude(), GPSTracker.getLongitude(), checkpoint);
	}
	
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
		
		// create the point to be added
		ParseGeoPoint point = new ParseGeoPoint(latitude, longitude);
		
		// replace the last GPS coordinate with checkpoint
		// if the previous GPS coordinate is an empty marker
		if (checkpoint != null && !isEmpty() && back().getLocation() == null && point.distanceInKilometersTo(back()) <= epsilon * 1e3) {
			Marker marker = new Marker(latitude, longitude);
			marker.setPath(this);
			marker.setLocation(checkpoint);
			pathList.set(size() - 1, marker);
			return true;
		}
		
		// otherwise, add new point to the list if it's far
		// from the last GPS coordinate in the path
		else if (isEmpty() || point.distanceInKilometersTo(back()) > epsilon * 1e3) {
			Marker marker = new Marker(latitude, longitude);
			marker.setPath(this);
			marker.setLocation(checkpoint);
			pathList.add(marker);
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
	 * Saves all markers in the path list into the Parse database.
	 * @see com.parse.ParseObject#saveEventually()
	 */
	public void save() {
		// save path node
		parseObject.saveEventually();
		
		// TO DO: delete markers that refer to this path but are NOT in the path list
		
		// save checkpoint locations
		for (Marker marker : pathList) {
			if (marker.getLocation() != null)
				marker.getLocation().save();
			marker.save();
		}
		
	}
	
	/**
	 * Synchronizes this Path object to include the Marker objects associated with it into the path list.
	 * Synchronizes with local datastore if offline.
	 * @throws ParseException 
	 */
	public void syncMarkers() throws ParseException {
		getPathList().clear();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MARKER");
		query.whereEqualTo("path", getParseObject());
		query.orderByAscending("createdAt");
		List<ParseObject> list = query.find();
		for (ParseObject markerObject : list) {
			getPathList().add(new Marker(markerObject));
		}
	}
}