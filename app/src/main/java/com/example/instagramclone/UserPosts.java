package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserPosts extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_posts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mLinearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String receivedUsername = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this, receivedUsername, FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS, true).show();

        setTitle(receivedUsername + "'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Photo");
        parseQuery.whereEqualTo("username", receivedUsername);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        parseQuery.findInBackground((objects, e) -> {

            if (objects.size() > 0 && e == null) {

                for (ParseObject post : objects){
                    TextView postDescription = new TextView(UserPosts.this);
                    postDescription.setText(post.get("image_des") + "");
                    ParseFile postPicture = (ParseFile) post.get("picture");

                    postPicture.getDataInBackground((data, e1) -> {

                        if (data != null && e1 == null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ImageView postImageView = new ImageView(UserPosts.this);
                            LinearLayout.LayoutParams imageview_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            imageview_params.setMargins(5, 135, 5, 5);
                            postImageView.setLayoutParams(imageview_params);
                            postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            postImageView.setImageBitmap(bitmap);

                            LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            des_params.setMargins(5, 5, 5, 15);
                            postDescription.setLayoutParams(des_params);
                            postDescription.setGravity(Gravity.CENTER);
                            postDescription.setBackgroundColor(Color.RED);
                            postDescription.setTextColor(Color.WHITE);
                            postDescription.setTextSize(30f);

                            mLinearLayout.addView(postImageView);
                            mLinearLayout.addView(postDescription);
                        }
                    });
                }

            } else {

                FancyToast.makeText(UserPosts.this, receivedUsername + " doesn't have any posts!",
                        FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                finish();
            }
        });
        dialog.dismiss();


    }

}