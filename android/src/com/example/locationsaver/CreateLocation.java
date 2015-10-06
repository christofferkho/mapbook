package com.example.locationsaver;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 
 * @author Kho, Purswani, Ramos, Tiongson
 * @version 10/5/15
 * 
 * class CreateLocation gets information from editTexts. This will also save GPS coordinates for a location, and
 * will go to googleMaps when the tracker is used to save a path (not yet implemented)
 *
 */
public class CreateLocation extends Activity {
	
	private Button save;
	private Button newPath;
	
	private TextView latitude;
	private TextView longitude;

	//intent data keys
	public static final String LOC_NAME = "LOC_NAME";
	public static final String LOC_ADD = "LOC_ADD";
	public static final String LOC_CONTACT = "LOC_CONTACT";
	public static final String LOC_NOTES = "LOC_NOTES";
	public static final String LATITUDE = "LATITUDE";
	public static final String LONGITUDE = "LONGTITUDE";
	
	//resultCodes
	public static final int LOC_SAVED = 0;
	public static final int PATH_SAVED = 1;
	
	//GPS coordinates, default 0.0 indicates a broken gps device or errors in code
	private double currentLat = 0.0;
	private double currentLong = 0.0;
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_location);
		
		//save and newPath buttons both call the same method but with different resultCodes
		save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				close(LOC_SAVED);				
			}
		});
        
        newPath = (Button) findViewById(R.id.newPath);
        newPath.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				close(PATH_SAVED);				
			}
		});
        
        //latitude longitude views
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        
        
        //this is needed to get GPS coordinates
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        //these are the initial coordinates, if they are available
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
        	double lat = location.getAltitude();
        	double longit = location.getLongitude();
        	
        	String sLat = Double.toString(lat);
			String sLong = Double.toString(longit);
        	
			latitude.setText(sLat);
			longitude.setText(sLong);
		}
        
        //listener class allows GPS coordinates to change once the device has changed location in the physical world
        LocationListener listener = new LocationListener(){

			/**
			 * method onLocationChanged will update the latitude and longitude textviews
			 * @param location
			 */
        	@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if(location != null){
					currentLat = location.getLatitude();
					currentLong = location.getLongitude();
					
					String sLat = Double.toString(currentLat);
					String sLong = Double.toString(currentLong);
					
					latitude.setText(sLat);
					longitude.setText(sLong);
				}
				
			}

			/**
			 * 
			 * @param provider
			 */
        	@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			/**
			 * 
			 * @param provider
			 */
        	@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			/**
			 * 
			 * @param provider
			 * @param status
			 * @param extras
			 */
        	@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
	}
	/**
	 * methode close will finish this activity and send information back to the main activity, or the database tester
	 * for this prototype
	 * @param resultCode
	 */
	private void close(int resultCode)
    {
		Intent data = getIntent();
        EditText nameText = (EditText) findViewById(R.id.editName);
        EditText addText = (EditText) findViewById(R.id.editAddress);
        EditText contactText = (EditText) findViewById(R.id.editContact);
        EditText noteText = (EditText) findViewById(R.id.editNotes);
        
        data.putExtra(LOC_NAME, nameText.getText().toString());
    	data.putExtra(LOC_ADD, addText.getText().toString());
    	data.putExtra(LOC_CONTACT, contactText.getText().toString());
    	data.putExtra(LOC_NOTES, noteText.getText().toString());
        
    	//test gps coords
    	if(resultCode == LOC_SAVED){
	    	//save 1 GPS coordinate in 1 path_list row
    		data.putExtra(LATITUDE, currentLat);
        	data.putExtra(LONGITUDE, currentLong);
	    	setResult(resultCode, data);
    	}
    	else if(resultCode == PATH_SAVED){
    		//track GPS until ceased and save all path_list rows
    		data.putExtra(LATITUDE, 22.1);
        	data.putExtra(LONGITUDE,22.1);
	    	setResult(resultCode, data);
    	}
    	else{}
    	finish();
    }
}
