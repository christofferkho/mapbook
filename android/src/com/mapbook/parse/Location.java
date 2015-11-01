package com.mapbook.parse;

import java.io.ByteArrayOutputStream;

import com.parse.ParseFile;
import com.parse.ParseObject;

import android.graphics.Bitmap;

/**
 * Abstracts manipulation of 
 * @author Rico Tiongson
 * @version 1.0
 * 11/01/15
 */
public class Location {

	/**
	 * Creates information-loaded location ready to be uploaded to Parse.
	 * Don't forget to call save() to save it to the database.
	 * @param name
	 * @param address
	 * @param description
	 * @param contact
	 * @param notes
	 * @param image
	 * To create a Bitmap image from your activity, locate the image using BitmapFactory.<br>
	 * {@code Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.IMAGE_NAME);}
	 */
	public Location(String name, String address, String description, String contact, String notes, Bitmap image) {		// add basic properties
		setName(name);
		setAddress(address);
		setDescription(description);
		setContact(contact);
		setNotes(notes);
		setImage(image);
	}

	/**
	 * Creates information-loaded location ready to be uploaded to Parse.
	 * Assigns empty strings to members by default.
	 * Don't forget to call save() to save it to the database.
	 */
	public Location() {
		setName("");
		setAddress("");
		setDescription("");
		setContact("");
		setNotes("");
		setImage(null);
	}
	
	/**
	 * Saves the location object in the Parse database locally, then eventually online if connected to the internet.
	 * @see com.parse.ParseObject#saveEventually()
	 */
	public void save() {
		getParseObject().saveEventually();
	}
	
	
	// members
	private ParseObject parseObject = new ParseObject("LOCATION");

	// getters
	public ParseObject getParseObject() {return parseObject;}
	public String getName() {return parseObject.getString("name");}
	public String getAddress() {return parseObject.getString("address");}
	public String getDescription() {return parseObject.getString("description");}
	public String getContact() {return parseObject.getString("contact");}
	public String getNotes() {return parseObject.getString("notes");}
	public Object getImage() {return parseObject.get("image");}
	
	// setters
	public void setName(String name) {parseObject.put("name", name);}
	public void setAddress(String address) {parseObject.put("address", address);}
	public void setDescription(String description) {parseObject.put("description", description);}
	public void setContact(String contact) {parseObject.put("contact", contact);}
	public void setNotes(String notes) {parseObject.put("notes", notes);}
	
	/** @see http://www.androidbegin.com/tutorial/android-parse-com-image-upload-tutorial/ **/
	public void setImage(Bitmap image) {
		// check first if null
		if (image == null) {
			parseObject.put("image", null);
			return;
		}
		
		// convert image to bytes
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] imageBytes = stream.toByteArray();
		
		// Create the parse file
		ParseFile file = new ParseFile("location-" + getName() + ".png", imageBytes);
		parseObject.put("image", file);
	}

	@Override
	public String toString() {
		return "Location [name=" + getName() + ", address=" + getAddress() + ", description=" + getDescription() + ", contact="
				+ getContact() + ", notes=" + getNotes() + ", image=" + (getImage() == null ? "null" : ("location-" + getName() + ".png"));
	}

}