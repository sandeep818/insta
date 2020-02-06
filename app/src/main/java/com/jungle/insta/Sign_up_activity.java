package com.jungle.insta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class Sign_up_activity extends AppCompatActivity {
    private Button sign_up;
    private TextView fullname;
    private TextView username;
    private TextView password;
    private TextView email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_leyout);
        sign_up = findViewById(R.id.sign_up);
        fullname = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        password= findViewById(R.id.password);
        email = findViewById(R.id.email);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(username.getText().toString());
                parseUser.setPassword(password.getText().toString());
                parseUser.setEmail(email.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){

                            Toast.makeText(Sign_up_activity.this, "Sign up Successfully !", Toast.LENGTH_SHORT).show();
                           new Handler().postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   // Magic here
                                 Intent intent = new Intent(Sign_up_activity.this,Login_activity.class);
                                   startActivity(intent);
                             }
                         }, 2000); // Millisecond 1000 = 1 sec

                        }else {
                            Toast.makeText(Sign_up_activity.this, "Please Check Your Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });





                //*************Anothr type to sign up to send data to parse server******************************************************************************************************
//                ParseObject parseObject= new ParseObject("Fake_user");
//                parseObject.put("name",fullname.getText().toString());
//                parseObject.put("email",email.getText().toString());
//                parseObject.put("username",username.getText().toString());
//                parseObject.put("password",password.getText().toString());
//                parseObject.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e==null){
//                            Toast.makeText(Sign_up_activity.this, "Sign up Successfully !", Toast.LENGTH_SHORT).show();
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // Magic here
//                                    Intent intent = new Intent(Sign_up_activity.this,Login_activity.class);
//                                    startActivity(intent);
//                                }
//                            }, 2000); // Millisecond 1000 = 1 sec
//                        }else{
//                            Toast.makeText(Sign_up_activity.this, "Please Check Your Details", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });

            }
        });
    }
}
