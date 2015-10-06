package com.example.locationsaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDBAdapter {
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static final String DATABASE_NAME = "CS123A";
		
	//Table names
	private static final String TABLE_LOC = "Locations";
	private static final String TABLE_PATH_LIST = "Path_List";
	
	//Primary key
	public static final String KEY_ROWID = "_id";

	//Columns for location table
	public static final String KEY_LOC_NAME = "loc_name";
	public static final String KEY_LOC_ADD = "loc_address";
	public static final String KEY_LOC_IMG = "loc_img";
	public static final String KEY_LOC_CONTACTS = "loc_contacts";
	public static final String KEY_LOC_NOTES = "loc_notes";
	
	//Columns for path list table
	public static final String KEY_LOCATION_ID = "location_referenced";
	public static final String KEY_LAT = "latitude";
	public static final String KEY_LONG = "longitude";

	//create table queries
	private static final String CREATE_TABLE_LOC = 
			
			"CREATE TABLE if not exists " + TABLE_LOC + " ("  + KEY_ROWID + " integer PRIMARY KEY autoincrement," 
					+ KEY_LOC_NAME + "," + KEY_LOC_ADD + ","+ KEY_LOC_IMG + "," 
					+ KEY_LOC_CONTACTS + "," + KEY_LOC_NOTES + ");";
	
	private static final String CREATE_TABLE_PATH_LIST = 

			"CREATE TABLE if not exists " + TABLE_PATH_LIST + " ("  + KEY_ROWID + " integer PRIMARY KEY autoincrement," 
					+ KEY_LOCATION_ID + "," + KEY_LONG + "," + KEY_LAT + ");";

	
	// UTILITY TABLE HELPER CLASS -- LEAVE THIS ALONE
	// Table creation and updating
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		//Create the database with the tables
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_LOC);
			db.execSQL(CREATE_TABLE_PATH_LIST);
		}

		//database changing
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOC);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATH_LIST);
			onCreate(db);
		}
	}
	
	
	// LIFE CYCLE
	
	public LocationDBAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public LocationDBAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		
		
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}
	
	// ACTIONS FOR LOCATION:
	
	/**
	 * createLoc
	 * @param locName
	 * @param locadd
	 * @param img
	 * @param contact
	 * @param notes
	 * @return
	 */
	public long createLoc(String locName, String locadd, String img, String contact, String notes) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_LOC_NAME, locName);
		initialValues.put(KEY_LOC_ADD, locadd);
		initialValues.put(KEY_LOC_IMG, img);
		initialValues.put(KEY_LOC_CONTACTS, contact);
		initialValues.put(KEY_LOC_NOTES, notes);
		
		return mDb.insert(TABLE_LOC, null, initialValues);
	}

	
	/**
	 * deleteAllInfo() for locations
	 * @return
	 */
	//deleting paths (and thus GPS coordinates) with locations NOT YET IMPLEMENTED!!!
	public boolean deleteAllInfo() {
		int doneDelete = 0;
		doneDelete = mDb.delete(TABLE_LOC, null, null);
		return doneDelete > 0;
	}

	/**
	 * fetchInfoByLocation
	 * @param tableName
	 * @param inputText
	 * @return
	 * @throws SQLException
	 */
	public Cursor fetchInfoByLocation(String tableName, String inputText) throws SQLException {
		Cursor mCursor = null;
		if (inputText == null || inputText.length() == 0) {
			
			mCursor = mDb.query(TABLE_LOC, 
								new String[] { KEY_ROWID,
					
											   // CHANGE COLUMNS -- May be all or just a few
					KEY_LOC_NAME, KEY_LOC_ADD, KEY_LOC_IMG, KEY_LOC_CONTACTS, KEY_LOC_NOTES}, 
											   
								null, null, null, null, null);

		} else {
			mCursor = mDb.query(true, 
								TABLE_LOC, 
								new String[] { KEY_ROWID,
					
											   // CHANGE COLUMNS -- May be all or just a few					
					KEY_LOC_NAME, KEY_LOC_ADD, KEY_LOC_IMG, KEY_LOC_CONTACTS, KEY_LOC_NOTES},
											   
								// searching similar			   
								KEY_LOC_NAME + " like '%" + inputText+ "%'",
								
								// DO NOT CHANGE THIS
								null, null, null, null, null);
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	
	/**
	 * fetchInfoByContactName
	 * @param tableName
	 * @param inputText
	 * @return
	 * @throws SQLException
	 */
	public Cursor fetchInfoByContactName(String tableName, String inputText) throws SQLException {
		Cursor mCursor = null;
		if (inputText == null || inputText.length() == 0) {
			
			mCursor = mDb.query(TABLE_LOC, 
								new String[] { KEY_ROWID,
					
											   // CHANGE COLUMNS -- May be all or just a few
					KEY_LOC_NAME, KEY_LOC_ADD,KEY_LOC_IMG, KEY_LOC_CONTACTS, KEY_LOC_NOTES}, 
											   
								null, null, null, null, null);

		} else {
			mCursor = mDb.query(true, 
								TABLE_LOC, 
								new String[] { KEY_ROWID,
					
											   // CHANGE COLUMNS -- May be all or just a few					
					KEY_LOC_NAME, KEY_LOC_ADD,KEY_LOC_IMG, KEY_LOC_CONTACTS, KEY_LOC_NOTES},
											   
								// searching similar			   
								KEY_LOC_CONTACTS + " like '%" + inputText+ "%'",
								
								// DO NOT CHANGE THIS
								null, null, null, null, null);
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	/**
	 * fetchAllInfo() for locations
	 * @return mCursor
	 */
	public Cursor fetchAllInfo() {

		// parameter descriptions
		// mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)

		Cursor mCursor = mDb.query(TABLE_LOC, new String[] { KEY_ROWID,
				KEY_LOC_NAME, KEY_LOC_ADD,KEY_LOC_IMG, KEY_LOC_CONTACTS, KEY_LOC_NOTES},
				null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	//ACTIONS FOR PATH_LIST
	/**
	 * createPath creates coordinates with the same location primary key, indicating a path
	 * @param id
	 * @param latitude
	 * @param longitude
	 * @return primary key
	 */
	public long createPath(long id, double latitude, double longitude) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_LOCATION_ID, id);
		initialValues.put(KEY_LAT, latitude);
		initialValues.put(KEY_LONG, longitude);
		return mDb.insert(TABLE_PATH_LIST, null, initialValues);
	}
	
	/**
	 * fetchPathId finds paths with a specific location primary key
	 * @param tableName
	 * @param inputText
	 * @return mCursor
	 * @throws SQLException
	 */
	public Cursor fetchPathId(String tableName, String inputText) throws SQLException {
		Cursor mCursor = null;
		if (inputText == null || inputText.length() == 0) {
			
			mCursor = mDb.query(TABLE_PATH_LIST, 
								new String[] { KEY_ROWID, KEY_LOCATION_ID, KEY_LONG, KEY_LAT}, 
								null, null, null, null, null);

		} else {
			mCursor = mDb.query(true, 
								TABLE_PATH_LIST, 
								new String[] { KEY_ROWID, KEY_LOCATION_ID, KEY_LONG, KEY_LAT},
								//search for similar
								KEY_LOCATION_ID + " like '%" + inputText+ "%'",
								null, null, null, null, null);
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

}
