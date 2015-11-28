package com.mapbook;

import com.mapbook.ui.MapbookActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * A class of static methods related to GPS tracking.
 * @author Rico
 *
 */
public class GPSTracker {
	
	// static members
	private static Activity currentActivity = null;
	private static LocationManager locationManager = null;
	private static LocationListener locationListener;
	private static double longitude, latitude;
	
	// getters
	public static Activity getCurrentActivity() {return currentActivity;}
	public static LocationManager getLocationManager() {return locationManager;}
	public static LocationListener getLocationListener() {return locationListener;}
	public static double getLongitude() {return longitude;}
	public static double getLatitude() {return latitude;}
	
	/**
	 * Tracks the GPS coordinates of the activity provided.
	 * @param activity
	 */
	public static void trackActivity(Activity activity) {
		// get the location manager
		currentActivity = activity;
		locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		

		// request updates from both wifi and GPS
		try {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, getLocationListener());
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, getLocationListener());

	        locationListener.onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	        locationListener.onLocationChanged(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		} catch (SecurityException ex) {
			((MapbookActivity) activity).alert("Location cannot be derived.");
		}
	}
	
	static {
		locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if(location != null){
					GPSTracker.latitude = location.getLatitude();
					GPSTracker.longitude = location.getLongitude();
				}
			}
			@Override public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override public void onProviderEnabled(String provider) {}
			@Override public void onProviderDisabled(String provider) {}
		};
	}
	
	public static String getString() {
		return latitude + " " + longitude;
	}
}
