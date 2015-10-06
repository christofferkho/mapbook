package com.example.locationsaver;

public class Path {
	int id;
	String pathName;
	String pathAdd;
	String pathImg;
	String pathContacts;
	String pathNotes;
	
	public Path(){
		
	}
	
	public Path(int id, String pathName, String pathAdd, String pathImg, String pathContacts, String pathNotes){
		this.id = id;
		this.pathName = pathName;
		this.pathAdd = pathAdd;
		this.pathImg = pathImg;
		this.pathContacts = pathContacts;
		this.pathNotes = pathNotes;
	}
	
	public Path(String pathName, String pathAdd, String pathImg, String pathContacts, String pathNotes){
		this.pathName = pathName;
		this.pathAdd = pathAdd;
		this.pathImg = pathImg;
		this.pathContacts = pathContacts;
		this.pathNotes = pathNotes;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocName() {
		return pathName;
	}
	public void setLocName(String locName) {
		this.pathName = locName;
	}
	public String getLocAdd() {
		return pathAdd;
	}
	public void setLocAdd(String locAdd) {
		this.pathAdd = locAdd;
	}
	public String getLocImg() {
		return pathImg;
	}
	public void setLocImg(String locImg) {
		this.pathImg = locImg;
	}
	public String getLocContacts() {
		return pathContacts;
	}
	public void setLocContacts(String locContacts) {
		this.pathContacts = locContacts;
	}
	public String getLocNotes() {
		return pathNotes;
	}
	public void setLocNotes(String locNotes) {
		this.pathNotes = locNotes;
	}

}
