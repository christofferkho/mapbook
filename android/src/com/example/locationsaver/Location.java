package com.example.locationsaver;

public class Location {
	
	int id;
	String locName;
	String locAdd;
	String locImg;
	String locContacts;
	String locNotes;
	double latitude;
	double longitude;
	
	public Location(){
		
	}
	
	public Location(int id, String locName, String locAdd, String locImg, String locContacts, String locNotes,
			double latitude, double longitude){
		this.id = id;
		this.locName = locName;
		this.locAdd = locAdd;
		this.locImg = locImg;
		this.locContacts = locContacts;
		this.locNotes = locNotes;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Location(String locName, String locAdd, String locImg, String locContacts, String locNotes,
			double latitude, double longitude){
		this.locName = locName;
		this.locAdd = locAdd;
		this.locImg = locImg;
		this.locContacts = locContacts;
		this.locNotes = locNotes;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getLocAdd() {
		return locAdd;
	}
	public void setLocAdd(String locAdd) {
		this.locAdd = locAdd;
	}
	public String getLocImg() {
		return locImg;
	}
	public void setLocImg(String locImg) {
		this.locImg = locImg;
	}
	public String getLocContacts() {
		return locContacts;
	}
	public void setLocContacts(String locContacts) {
		this.locContacts = locContacts;
	}
	public String getLocNotes() {
		return locNotes;
	}
	public void setLocNotes(String locNotes) {
		this.locNotes = locNotes;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
