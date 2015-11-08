package com.mapbook.ui;

import com.mapbook.locationsaver.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
		
		login = (Button) findViewById(R.id.login_submit);
		register = (Button) findViewById(R.id.login_register);
	}
	
	// components
	public EditText username, password;
	public TextView logo, forgotPassword;
	public Button login, register;
	
}