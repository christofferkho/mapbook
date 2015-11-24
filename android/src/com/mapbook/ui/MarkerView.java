package com.mapbook.ui;

import com.mapbook.locationsaver.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	private LinearLayout markers;
	
	// prepare component fonts and variables
	private void prepareComponents() {
		
		header = (TextView) findViewById(R.id.marker_view_header);
		markers = (LinearLayout) findViewById(R.id.marker_view);
		
		Button button = (Button) findViewById(R.id.buttonz);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				MarkerView.this.addEntry();
			}
		});
		button.setText("Add sample marker");
	}
	
	// adds an empty entry to the marker view list
	public LinearLayout addEntry() {
		LinearLayout entry = (LinearLayout) getLayoutInflater().inflate(R.layout.marker_view_entry, null);
		markers.addView(entry); // empty entry
		return entry;
	}
	
}