package com.mapbook.ui;

import com.mapbook.locationsaver.R;

import android.os.Bundle;

public class Dashboard extends MapbookActivity {
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		launchActivity(Login.class, getIntent().getStringExtra("message"));
	}
	
}