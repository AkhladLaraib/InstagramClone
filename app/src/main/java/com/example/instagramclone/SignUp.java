package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
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

public class SignUp extends AppCompatActivity implements View.OnClickListener {


    //ui components
    private EditText mEditTextEnterUserName, mEditTextEnterPassword, mEditTextEnterEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("Sign Up");

        mEditTextEnterUserName = findViewById(R.id.editTextEnterUserName);
        mEditTextEnterPassword = findViewById(R.id.editTextEnterPassword);
        mEditTextEnterEmail = findViewById(R.id.editTextEnterEmail);

        Button mButtonSignUp = findViewById(R.id.buttonSignUp);
        Button mButtonLogin = findViewById(R.id.buttonLogin);

        mEditTextEnterPassword.setOnKeyListener((v, keyCode, event) ->  {

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                onClick(mButtonSignUp);
            }
            return false;
        });


        mButtonSignUp.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) transitionToSocialMediaActivity();    // ParseUser.logOut();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonSignUp) {

            if (mEditTextEnterEmail.getText().toString().isEmpty() ||
                    mEditTextEnterUserName.getText().toString().isEmpty() ||
                    mEditTextEnterPassword.getText().toString().isEmpty()) {

                FancyToast.makeText(SignUp.this, "Email, UserName and Password required", FancyToast.LENGTH_LONG,
                        FancyToast.INFO, true).show();

            } else {

                final ParseUser appUser = new ParseUser();
                appUser.setEmail(mEditTextEnterEmail.getText().toString());
                appUser.setUsername(mEditTextEnterUserName.getText().toString());
                appUser.setPassword(mEditTextEnterPassword.getText().toString());

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
                alertdialog.setMessage("Signing up " + mEditTextEnterUserName.getText().toString());
                alertdialog.setCancelable(false);
                AlertDialog dialog = alertdialog.create();
                dialog.show();
                appUser.signUpInBackground(e -> {

                    if (e == null) {

                        FancyToast.makeText(SignUp.this, appUser.getUsername() + " signed up successfully", FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS, true).show();
                        transitionToSocialMediaActivity();

                    } else {

                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();

                    }
                    dialog.dismiss();
                });
            }

        } else if (v.getId() == R.id.buttonLogin) {

            Intent intent = new Intent(SignUp.this, LoginActivity.class);
            startActivity(intent);

        }
    }

    public void rootLayoutTapped(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void transitionToSocialMediaActivity() {

        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}