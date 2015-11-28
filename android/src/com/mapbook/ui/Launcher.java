package com.mapbook.ui;

import com.mapbook.locationsaver.R;
import com.mapbook.parse.User;
import com.parse.ParseUser;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

public class Launcher extends MapbookActivity {
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		findViewById(R.id.launcher_logo);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (ParseUser.getCurrentUser() == null) 
					launchActivity(Login.class);
				else
					launchActivity(LocationList.class, "Welcome back, " + User.getUsername() + "!");
			}			
		}, 1500);

	}
	
}