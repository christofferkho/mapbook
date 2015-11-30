package com.mapbook.ui;

import com.mapbook.locationsaver.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
		grabUsername();
		comingSoonFacebook();
		handleSignup();
	}
	
	void prepareComponents() {
		header = (TextView) findViewById(R.id.register_header);
		
		username = (EditText) findViewById(R.id.register_username);
		password = (EditText) findViewById(R.id.register_password);
		confirm = (EditText) findViewById(R.id.register_confirm_password);
		email = (EditText) findViewById(R.id.register_email);
		
		signup = (Button) findViewById(R.id.register_signup);
		signupFacebook = (Button) findViewById(R.id.register_signup_facebook);
		
		loading = (ProgressBar) findViewById(R.id.register_loading);
	}
	
	void grabUsername() {
		Intent intent = getIntent();
		if (intent.hasExtra("username")) username.setText(intent.getStringExtra("username"));
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
	
	void comingSoonFacebook() {
		signupFacebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert("This feature is coming soon!", "Sign Up with Facebook");	
			}
		});
	}
	
	void handleSignup() {
		signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				
				final ParseUser user = new ParseUser();
				final String userName = username.getText().toString().trim().toLowerCase();
				final String passWord = password.getText().toString();
				final String eMail = email.getText().toString().trim().toString();
				
				// check first if passwords match
				if (!passWord.equals(confirm.getText().toString())) {
					alert("Passwords don't match");
					confirm.requestFocus();
					return;
				}
				
				// verify that entries are not empty
				if (userName.isEmpty()) {
					alert("Please provide username.");
					username.requestFocus();
					return;
				}
				
				if (passWord.isEmpty()) {
					alert("Cannot have empty password.");
					password.requestFocus();
					return;
				}
				
				if (eMail.isEmpty()) {
					alert("Please provide e-mail address.");
					email.requestFocus();
					return;
				}
				
				// try signing up new user to Parse database
				user.setUsername(userName);
				user.setPassword(passWord);
				user.setEmail(eMail);
				
				// show loading
				signup.setVisibility(View.INVISIBLE);
				loading.setVisibility(View.VISIBLE);
				
				user.signUpInBackground(new SignUpCallback() {
					@Override
					public void done(ParseException ex) {
						if (ex == null && user != null) {
							final Intent intent = new Intent(Register.this, Login.class);
							intent.putExtra("username", userName);
							intent.putExtra("password", passWord);
							alert("Signed up successfully!");
							startActivity(intent);
							finish();
						}
						else switch (ex.getCode()) {
							case ParseException.USERNAME_TAKEN:
								alert("Username was already taken.");
								username.requestFocus();
								break;
							case ParseException.EMAIL_TAKEN:
								alert("E-mail was already taken.");
								email.requestFocus();
								break;
							case ParseException.INVALID_EMAIL_ADDRESS:
								alert("Please provide a valid e-mail address.");
								email.requestFocus();
								break;
							default:
								alert(ex.getMessage(), "Unknown error " + ex.getCode());
								break;
						}
						signup.setVisibility(View.VISIBLE);
						loading.setVisibility(View.GONE);
					}
				});
			}
		});
	}
	
}
		