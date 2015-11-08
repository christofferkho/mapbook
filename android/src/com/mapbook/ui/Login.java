package com.mapbook.ui;

import com.mapbook.locationsaver.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Login extends MapbookActivity {
	
	/**
	 * method onCreate
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		// prepare components
		username = (EditText) findViewById(R.id.login_username);
		password = (EditText) findViewById(R.id.login_password);
		
		logo = (TextView) findViewById(R.id.login_logo);
		forgotPassword = (TextView) findViewById(R.id.login_forgot_password);
		message = (TextView) findViewById(R.id.login_message);
		
		login = (Button) findViewById(R.id.login_submit);
		register = (Button) findViewById(R.id.login_register);
		
		loading = (ProgressBar) findViewById(R.id.login_loading);
		
		// hide message if unnecessary
		View.OnClickListener messageHider = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
			}
		};

		// bind message hider with text boxes
		username.setOnClickListener(messageHider);
		password.setOnClickListener(messageHider);
		
		// setup callback on login
		final LogInCallback onParseLogin = new LogInCallback() {
			public void done(ParseUser user, ParseException ex) {
				if (ex == null && user != null) {
					// login success
					setContentView(R.layout.register);
				} else {
					// cannot login, show message
					ex.printStackTrace();
					
					message.setVisibility(View.VISIBLE);
					loading.setVisibility(View.GONE);
					login.setVisibility(View.VISIBLE);
				}
			}
		};
		
		// bind parse login to login button
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// show progress bar
				loading.setVisibility(View.VISIBLE);
				message.setVisibility(View.GONE);
				login.setVisibility(View.INVISIBLE);
				
				// get data
				String user = username.getText().toString();
				String pass = password.getText().toString();
				
				// login to parse
				ParseUser.logInInBackground(user, pass, onParseLogin);
			}
		});
	}
	
	// components
	public EditText username, password;
	public TextView logo, forgotPassword, message;
	public Button login, register;
	public ProgressBar loading;
	
	
}