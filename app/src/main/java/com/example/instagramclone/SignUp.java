package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private EditText mEditTextName, mEditTextPunchPower, mEditTextPunchSpeed, mEditTextKickSpeed, mEditTextKickPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mButton = findViewById(R.id.button);

        mButton.setOnClickListener(SignUp.this);

        mEditTextName = findViewById(R.id.editTextName);
        mEditTextPunchPower = findViewById(R.id.editTextPunchPower);
        mEditTextPunchSpeed = findViewById(R.id.editTextPunchSpeed);
        mEditTextKickPower = findViewById(R.id.editTextKickPower);
        mEditTextKickSpeed = findViewById(R.id.editTextKickSpeed);

    }

    @Override
    public void onClick(View v) {

        ParseObject object2 = new ParseObject("Object2");
        object2.put("name", mEditTextName.getText().toString());
        object2.put("punchSpeed", Integer.parseInt(mEditTextPunchSpeed.getText().toString()));
        object2.put("punchPower", Integer.parseInt(mEditTextPunchPower.getText().toString()));
        object2.put("kickSpeed", Integer.parseInt(mEditTextKickSpeed.getText().toString()));
        object2.put("kickPower", Integer.parseInt(mEditTextKickPower.getText().toString()));
        object2.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                try {

                    if (e == null) {

                        FancyToast.makeText(SignUp.this, object2.get("name") + " is saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {

                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                } catch (Exception ee) {

                    FancyToast.makeText(SignUp.this, ee.getMessage(), FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                }

            }
        });


    }

//    public void helloWorldTapped(View view) {
//
////        ParseObject object = new ParseObject("Object");
////        object.put("punch_speed", 200);
////        object.saveInBackground(new SaveCallback() {
////            @Override
////            public void done(ParseException e) {
////
////                if (e == null) {
////
////                    Toast.makeText(SignUp.this, "boxer object is saved successfully", Toast.LENGTH_LONG).show();
////                }
////            }
////        });
//

//    }

}