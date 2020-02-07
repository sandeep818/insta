package com.jungle.insta;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Login_activity extends AppCompatActivity {
    private Button login;
    private  Button signup;
    private TextView username;
    private TextView password;
    private String user_name;
    private String user_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_leyout);
        setTitle("Log In");

        login = findViewById(R.id.log_in);
        signup = findViewById(R.id.sign_up);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    login.performClick();
                }


                return false;
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_activity.this,Sign_up_activity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name= username.getText().toString();
                user_pass= password.getText().toString();


                if (user_name =="" || user_pass==""){
                    Toast.makeText(Login_activity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    ParseUser.logInInBackground(user_name,user_pass, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user!=null){
                                Intent intent = new Intent(Login_activity.this,Home.class);
//                                intent.putExtra("user",user_name);
                                startActivity(intent);

                            }else{
                                Toast.makeText(Login_activity.this, "Wrong Password"+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

//*********************************************Another way to log in and get data from Parse server **************************************************************
//                ParseQuery<ParseObject> userlogin= ParseQuery.getQuery("Fake_user");
//                userlogin.whereEqualTo("name",user_name);
//                userlogin.getFirstInBackground(new GetCallback<ParseObject>() {
//                    @Override
//                    public void done(ParseObject object, ParseException e) {
//                        if (object!=null && e==null){
//                            if (object.get("password").toString().equals(user_pass)){
//                                Intent intent = new Intent(Login_activity.this,Home.class);
//                                intent.putExtra("user",user_name);
//                                startActivity(intent);
//
//
//                            }else{
//                                Toast.makeText(Login_activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }else{
//                            Toast.makeText(Login_activity.this, "User Dosen't Exist", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
      //****************************************************************************************************************************
//                userlogin.findInBackground(new FindCallback<ParseObject>() {
//                    @Override
//                    public void done(List<ParseObject> objects, ParseException e) {
//                        if (objects.size()>0 && e == null){
//                            for (ParseObject obj:objects){
//                                if (obj.get("name").toString().equals(user_name)){
//
//                                    if (obj.get("password").toString().equals(user_pass)){
//                                        Toast.makeText(Login_activity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        Toast.makeText(Login_activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            }
//                        }if (e!=null){
//                            Toast.makeText(Login_activity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//                });
            }
        });
    }
    public void  root_tapped( View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
