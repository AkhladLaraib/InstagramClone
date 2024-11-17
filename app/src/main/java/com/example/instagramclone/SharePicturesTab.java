package com.example.instagramclone;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SharePicturesTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private EditText edtImageDes;
    private Button buttonImageShare;
    private Bitmap bitmap;


    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);

                imgShare.setImageBitmap(bitmap);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    private final ActivityResultLauncher<String> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    result -> {
                        if (result) {
                            pickImageLauncher.launch("image/*");
                        }
                    });


    public SharePicturesTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_pictures_tab, container, false);

        imgShare = view.findViewById(R.id.imgShare);
        buttonImageShare = view.findViewById(R.id.buttonImageShare);
        edtImageDes = view.findViewById(R.id.edtImageDes);

        imgShare.setOnClickListener(SharePicturesTab.this);
        buttonImageShare.setOnClickListener(SharePicturesTab.this);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imgShare) {

            // Check and request permission if needed
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                activityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                // If permission is already granted, launch image picker
                pickImageLauncher.launch("image/*");
            }
        }
        if (v.getId() == R.id.buttonImageShare) {

            if (bitmap != null) {
                if (edtImageDes.getText().toString().equals("")) {

                    FancyToast.makeText(getContext(), "Error! You must specify a description", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                } else {

                    ByteArrayOutputStream byteArrayOutputStream =
                            new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    ParseFile parseFile = new ParseFile("image.png", imageBytes);
                    ParseObject parseObject = new ParseObject("Photo");
                    parseObject.put("picture", parseFile);
                    parseObject.put("image_des", edtImageDes.getText().toString());
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog dialog = new ProgressDialog(getContext());
                    dialog.setMessage("Loading...");
                    dialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getContext(), "Done!!!",
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(getContext(),
                                        "Unknown Error!!!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                            dialog.dismiss();
                        }
                    });

                }
            } else {

                FancyToast.makeText(getContext(), "Error! You must select an image", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            }
        }
    }


}