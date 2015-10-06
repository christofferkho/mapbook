package com.example.locationsaver;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 
 * @author Kho, Purswani, Ramos, Tiongson
 * @version 10/5/15
 * 
 * class DatabaseTester tests if the database works
 *
 */
public class DatabaseTester extends Activity {
	private SimpleCursorAdapter dataAdapter;
	private LocationDBAdapter dbHelper;
	private Button button;
	private ListView listView;
	private Cursor cursorAllInfo;
	
    /**
     * onCreate
     * @param savedInstanceState
     */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_tester);
        
        //This is the create button. If you press this, you will go to a new activity
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	create();
            }
        });
        //Call the database adapter to use methods that use SQLite queries
     	dbHelper = new LocationDBAdapter(this);
     	dbHelper.open();
     	
     	//selects all locations in the database
     	cursorAllInfo = dbHelper.fetchAllInfo();
     		
     	//listview which will list the tested information.
     	listView = (ListView) findViewById(R.id.listView1);
     	
     	//Cursor adapter to fill in a row in the list. Flag is set to default
     	dataAdapter = new SimpleCursorAdapter(this, R.layout.row_layout, cursorAllInfo, 
     			new String[] { LocationDBAdapter.KEY_LOC_NAME, LocationDBAdapter.KEY_LOC_ADD, LocationDBAdapter.KEY_LOC_CONTACTS, LocationDBAdapter.KEY_LOC_NOTES}, // columns to be bound  
     			new int[] { R.id.loctext, R.id.addtext, R.id.contacttext, R.id.notetext}, 0// views id which the data will be bound to 
    			); 
     		
     		
     	listView.setAdapter(dataAdapter);
     		
     		

     	// clicking an item in the listview
     	listView.setOnItemClickListener(new OnItemClickListener() {
     		@Override
     		public void onItemClick(AdapterView<?> listView, View view,
     				int position, long id) {
     					//nothing happens YET!
     		}
     	});
    }
    
	/**
	 * create() method invoked when button is pressed. Goes to a new activity
	 */
    public void create(){
    	Intent intent = new Intent(this, CreateLocation.class);
		startActivityForResult(intent, 0);
    }
    
    /**
     * onActivityResult creates a new location based on information sent from the intent activity
     * saves a single gps point for locations
     * saves several gps points for paths
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	//get information from intent
    	String name = data.getStringExtra(CreateLocation.LOC_NAME);
		String add = data.getStringExtra(CreateLocation.LOC_ADD);
		String contact = data.getStringExtra(CreateLocation.LOC_CONTACT);
		String notes = data.getStringExtra(CreateLocation.LOC_NOTES);
		
    	if (resultCode == CreateLocation.LOC_SAVED){
    		double latitude = data.getDoubleExtra(CreateLocation.LATITUDE, 0.0);
    		double longitude = data.getDoubleExtra(CreateLocation.LONGITUDE, 0.0);
    		
    		//save location information
    		long id = dbHelper.createLoc(name, add, "test", contact, notes);
    		
    		//save single GPS point
    		dbHelper.createPath(id, latitude, longitude);
    		
    		//updates listview
    		Cursor newCursor = dbHelper.fetchAllInfo();
    		dataAdapter.changeCursor(newCursor);
    		dataAdapter.notifyDataSetChanged();
    	}
    	
    	else if(resultCode == CreateLocation.PATH_SAVED){
    		//save several GPS points here, not implemented yet
    		double latitude = data.getDoubleExtra(CreateLocation.LATITUDE, 0.0);
    		double longitude = data.getDoubleExtra(CreateLocation.LONGITUDE, 0.0);
    		long id = dbHelper.createLoc(name, add, "test", contact, notes);
    		dbHelper.createPath(id, latitude, longitude);
    		
    		//updates listview
    		Cursor newCursor = dbHelper.fetchAllInfo();
    		dataAdapter.changeCursor(newCursor);
    		dataAdapter.notifyDataSetChanged();
    	}
    	else{}
    }
}
