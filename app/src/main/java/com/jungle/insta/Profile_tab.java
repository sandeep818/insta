package com.jungle.insta;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_tab extends Fragment {

  private TextView profile_name,profile_bio,profile_profession,profile_hobbies,profile_sport;
  private Button update;
    public Profile_tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);
        profile_name = view.findViewById(R.id.profile_name);
        profile_bio = view.findViewById(R.id.profile_bio);
        profile_profession = view.findViewById(R.id.profile_profession);
        profile_hobbies=view.findViewById(R.id.profile_hobbies);
        profile_sport=view.findViewById(R.id.profile_sport);
        update = view.findViewById(R.id.update);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName")==null){
            profile_name.setText("");
        }else {
            profile_name.setText(parseUser.get("profileName").toString());
        }
        if (parseUser.get("profileBio")==null){
            profile_bio.setText("");
        }else {
            profile_bio.setText(parseUser.get("profileBio").toString());
        }
        if (parseUser.get("profileProfession")==null){
            profile_profession.setText("");
        }else {
            profile_profession.setText(parseUser.get("profileProfession").toString());
        }
        if (parseUser.get("profileHobbies")==null){
            profile_hobbies.setText("");
        }else {
            profile_hobbies.setText(parseUser.get("profileHobbies").toString());
        }
        if (parseUser.get("profileSport")==null){
            profile_sport.setText("");
        }else {
            profile_sport.setText(parseUser.get("profileSport").toString());
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName",profile_name.getText().toString());
                parseUser.put("profileBio",profile_bio.getText().toString());
                parseUser.put("profileProfession",profile_profession.getText().toString());
                parseUser.put("profileHobbies",profile_hobbies.getText().toString());
                parseUser.put("profileSport",profile_sport.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Toast.makeText(getContext(), "Info Updated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



        return view;
    }


}
