package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextLoginEmail, mEditTextLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        mEditTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        mEditTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        Button mButtonSignUpActivity = findViewById(R.id.buttonSignupActivity);
        Button mButtonLoginActivity = findViewById(R.id.buttonLoginActivity);

        mEditTextLoginPassword.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                onClick(mButtonLoginActivity);
            }
            return false;
        });

        mButtonSignUpActivity.setOnClickListener(this);
        mButtonLoginActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) transitionToSocialMediaActivity();    //ParseUser.logOut();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonLoginActivity) {

            if (mEditTextLoginEmail.getText().toString().equals("") ||
                mEditTextLoginPassword.getText().toString().equals("")) {

                FancyToast.makeText(LoginActivity.this, "Email and Password required", FancyToast.LENGTH_LONG,
                        FancyToast.INFO, true).show();

            } else {

                ParseUser.logInInBackground(mEditTextLoginEmail.getText().toString(),
                        mEditTextLoginPassword.getText().toString(),
                        (user, e) -> {

                            if (user != null && e == null) {

                                FancyToast.makeText(LoginActivity.this, user.getUsername() + " logged in successfully", FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {

                                FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();
                            }
                        });

            }


        } else if (v.getId() == R.id.buttonSignupActivity) {

            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        }


    }

    public void loginLayoutTapped(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void transitionToSocialMediaActivity() {

        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}