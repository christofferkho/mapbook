package com.mapbook.ui;

import com.mapbook.locationsaver.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MarkerView extends MapbookActivity {
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_view);
		prepareComponents();
		// launchActivity(Login.class, getIntent().getStringExtra("message"));
	}
	
	// components
	private TextView header;
	
	// prepare component fonts and variables
	void prepareComponents() {
		header = (TextView) findViewById(R.id.marker_view_header);
	}
	
}