package com.mapbook.ui;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.R;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;

public class Launcher extends MapbookActivity {
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ParseUser.getCurrentUser() == null)
			launchActivity(Login.class);
		else
			launchActivity(Login.class, "Username: " + ParseUser.getCurrentUser().getUsername() + " was logged in.");
	}
	
}