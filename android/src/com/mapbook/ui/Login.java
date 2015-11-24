package com.mapbook.ui;

import com.mapbook.locationsaver.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
		prepareComponents();
		handleMessageHiding();
		handleParseLogin();
		handleRegister();
		showMessage();
	}
	
	// components
	public static EditText username, password;
	public static TextView logo, forgotPassword, message;
	public static Button login, register;
	public static ProgressBar loading, loadingRegister;
	
	// prepare component fonts and variables
	void prepareComponents() {
		username = (EditText) findViewById(R.id.login_username);
		password = (EditText) findViewById(R.id.login_password);
		
		logo = (TextView) findViewById(R.id.login_logo);
		forgotPassword = (TextView) findViewById(R.id.login_forgot_password);
		message = (TextView) findViewById(R.id.login_message);
		
		login = (Button) findViewById(R.id.login_submit);
		register = (Button) findViewById(R.id.login_register);
		
		loading = (ProgressBar) findViewById(R.id.login_loading);
		loadingRegister = (ProgressBar) findViewById(R.id.login_loading_register);
	}
	
	// hide "Invalid username/password" message when textboxes are tapped
	void handleMessageHiding() {
		// bind message hider with text boxes
		View.OnClickListener messageHider = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message.setVisibility(View.GONE);
			}
		};
		username.setOnClickListener(messageHider);
		password.setOnClickListener(messageHider);
	}
	
	// handle login check with Parse thru Login button
	void handleParseLogin() {
		// connect to Parse on Login
		final LogInCallback onParseLogin = new LogInCallback() {
			public void done(ParseUser user, ParseException ex) {
				if (ex == null && user != null) {
					// login success
					launchActivity(LocationList.class, "Login success");
					return;
				} else {
					// cannot login, show message
					ex.printStackTrace();
					message.setVisibility(View.VISIBLE);
				}
				loading.setVisibility(View.GONE);
				login.setVisibility(View.VISIBLE);
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
		
		// get parameters from register, if any
		Intent intent = getIntent();
		if (intent.hasExtra("username"))
			username.setText(intent.getStringExtra("username"));
		if (intent.hasExtra("password"))
			password.setText(intent.getStringExtra("password"));
	}
	
	// handle clicking the register button
	void handleRegister() {
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				register.setVisibility(View.INVISIBLE);
				loadingRegister.setVisibility(View.VISIBLE);
				final Intent intent = new Intent(Login.this, Register.class);
				String userName = username.getText().toString();
				if (!userName.isEmpty()) intent.putExtra("username", userName);
				startActivity(intent);
				finish();
			}
		});
	}
}