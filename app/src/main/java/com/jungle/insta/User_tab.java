package com.jungle.insta;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class User_tab extends Fragment {

    private ListView listView;
    private ArrayList userlist;
    private ArrayAdapter listAdapter;
    public User_tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_tab, container, false);

        listView= view.findViewById(R.id.listView);
        userlist = new ArrayList();

        listAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,userlist);



        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e==null){
                    if (users.size()>0){
                        for (ParseUser user:users){

                            userlist.add(user.getUsername());

                        }
                        listView.setAdapter(listAdapter);
                    }
                }
            }
        });



        return view;
    }

}
