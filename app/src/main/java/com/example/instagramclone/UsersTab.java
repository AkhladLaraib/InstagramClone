package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;

public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    public UsersTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        ListView listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                arrayList);

        listView.setOnItemClickListener(UsersTab.this);
        listView.setOnItemLongClickListener(UsersTab.this);

        TextView txtLoadingUsers = view.findViewById(R.id.txtLoadingUsers);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground((users, e) -> {
            if (e == null) {

                if (users.size() > 0) {
                    for (ParseUser user : users) {

                        arrayList.add(user.getUsername());
                    }
                    listView.setAdapter(arrayAdapter);
                    txtLoadingUsers.animate().alpha(0).setDuration(2000);
                    listView.setVisibility(View.VISIBLE);

                }
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(), UserPosts.class);
        intent.putExtra("username", arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground((user, e) -> {

            if (user != null && e == null) {

                PrettyDialog prettyDialog = new PrettyDialog(getContext());
                prettyDialog.setTitle(user.getUsername() + "'s Info")
                        .setMessage(user.get("profileBio") + "\n"
                                + user.get("profileProfession") + "\n"
                                + user.get("profileHobby") + "\n"
                                + user.get("profileFavSport"))
                        .setIcon(R.drawable.person)
                        .addButton(
                                "OK",
                                R.color.pdlg_color_white,
                                libs.mjn.prettydialog.R.color.pdlg_color_blue,
                                prettyDialog::dismiss
                        ).show();

            }
        });

        return true;
    }
}