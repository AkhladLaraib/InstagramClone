package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio,
            edtProfileProfession, edtProfileHobbies, edtProfileFavSport;

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport = view.findViewById(R.id.edtProfileFavSport);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);

        Button buttonUpdateInfo = view.findViewById(R.id.buttonUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null) edtProfileName.setText("");
        else edtProfileName.setText(parseUser.get("profileName").toString());

        if (parseUser.get("profileBio") == null) edtProfileBio.setText("");
        else edtProfileBio.setText(parseUser.get("profileBio").toString());

        if (parseUser.get("profileHobbies") == null) edtProfileHobbies.setText("");
        else edtProfileHobbies.setText(parseUser.get("profileHobbies").toString());

        if (parseUser.get("profileFavSport") == null) edtProfileFavSport.setText("");
        else edtProfileFavSport.setText(parseUser.get("profileFavSport").toString());

        if (parseUser.get("profileProfession") == null) edtProfileProfession.setText("");
        else edtProfileProfession.setText(parseUser.get("profileProfession").toString());

        buttonUpdateInfo.setOnClickListener(v -> {

            parseUser.put("profileName", edtProfileName.getText().toString());
            parseUser.put("profileBio", edtProfileBio.getText().toString());
            parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
            parseUser.put("profileFavSport", edtProfileFavSport.getText().toString());
            parseUser.put("profileProfession", edtProfileProfession.getText().toString());

            parseUser.saveInBackground(e -> {
                if (e == null) {

                    FancyToast.makeText(getContext(), "Info Updated",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                } else {
                    FancyToast.makeText(getContext(), e.getMessage(),
                            FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
            });
        });

        return view;
    }
}