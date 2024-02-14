package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignupLoginActivity extends AppCompatActivity {

    private EditText mEditTextSignupUsername, mEditTextSignupPassword, mEditTextLoginUsername, mEditTextLoginPassword;
    private Button mButtonSignup, mButtonLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        mEditTextSignupUsername = findViewById(R.id.editTextSignupUsername);
        mEditTextSignupPassword = findViewById(R.id.editTextSignupPassword);
        mEditTextLoginUsername = findViewById(R.id.editTextLoginUsername);
        mEditTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        mButtonSignup = findViewById(R.id.buttonSignUp);
        mButtonLogin = findViewById(R.id.buttonLogin);

        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser appUser = new ParseUser();
                appUser.setUsername(mEditTextSignupUsername.getText().toString());
                appUser.setPassword(mEditTextSignupPassword.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            FancyToast.makeText(SignupLoginActivity.this, appUser.get("username") + " is signed up successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                        } else {

                            FancyToast.makeText(SignupLoginActivity.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(mEditTextLoginUsername.getText().toString(), mEditTextLoginPassword.getText().toString(),
                        new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null && e == null) {

                            FancyToast.makeText(SignupLoginActivity.this, user.get("username") + " is logged in successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                        } else {

                            FancyToast.makeText(SignupLoginActivity.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                        }
                    }
                });
            }
        });

    }
}
