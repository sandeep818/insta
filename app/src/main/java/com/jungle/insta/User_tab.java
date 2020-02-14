package com.jungle.insta;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class User_tab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> userlist;
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
        listView.setOnItemClickListener(User_tab.this);
        listView.setOnItemLongClickListener(User_tab.this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),Users_post.class);
        intent.putExtra("username",userlist.get(position) );
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

      ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
      parseQuery.whereEqualTo("username",userlist.get(position));

      parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
          @Override
          public void done(ParseUser object, ParseException e) {

              if (object!=null && e==null){
               Toast.makeText(getContext(), " "+object.get("profileSport"), Toast.LENGTH_SHORT).show();

                 if (object.get("profileName")!=null){
                     final PrettyDialog prettyDialog= new PrettyDialog(getContext());
                     prettyDialog.setTitle("User Info").setIcon(R.drawable.person).setMessage("Profile Name : "+object.get("profileName")+"\n"+"Bio : "+object.get("profileBio")+"\n"+"Hobbies : "+
                             object.get("profileHobbies")+"\n"+" Profession : "+object.get("profileProfession")+"\n"+"Sport : "+object.get("profileSport")).addButton("ok", R.color.pdlg_color_black,
                             R.color.pdlg_color_yellow, new PrettyDialogCallback() {
                                 @Override
                                 public void onClick() {
                                     prettyDialog.dismiss();
                                 }
                             }).show();
                 }else {
                     final PrettyDialog prettyDialog= new PrettyDialog(getContext());
                     prettyDialog.setTitle("User Info").setIcon(R.drawable.person).setMessage("Information Not Updated ").addButton("ok", R.color.pdlg_color_blue,
                             R.color.pdlg_color_yellow, new PrettyDialogCallback() {
                                 @Override
                                 public void onClick() {
                                     prettyDialog.dismiss();
                                 }
                             }).show();
                 }


              }
          }
      });


        return false;
    }
}
