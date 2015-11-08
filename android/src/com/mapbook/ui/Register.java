package com.mapbook.ui;

import com.mapbook.locationsaver.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Register extends MapbookActivity {

	// components
	public TextView header;
	public EditText username, password, confirm, email;
	public Button signup, signupFacebook;
	public ProgressBar loading, loadingFacebook;
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		prepareComponents();
	}
	
	void prepareComponents() {
		header = (TextView) findViewById(R.id.register_header);
		
		username = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);
		confirm = (EditText) findViewById(R.id.register_confirm_password);
		email = (EditText) findViewById(R.id.register_email);
		
		signup = (Button) findViewById(R.id.register_signup);
		signupFacebook = (Button) findViewById(R.id.register_signup_facebook);
	}

	@Override
	// go back to login page when back button is clicked
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			super.onKeyDown(keyCode, event);
			launchActivity(Login.class);
			return true;
		}
		return false;
	}
	
}
		