package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

public class UsersTab extends Fragment {

    private ArrayList arrayList;
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
}