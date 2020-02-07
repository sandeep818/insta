package com.jungle.insta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class Sign_up_activity extends AppCompatActivity{
    private Button sign_up;
    private TextView fullname;
    private TextView username;
    private TextView password;
    private TextView email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_leyout);
        setTitle("Sign Up");

        sign_up = findViewById(R.id.sign_up);
        fullname = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        password= findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (keyCode ==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                sign_up.performClick();
               }


                return false;
            }
        });
        email = findViewById(R.id.email);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (email.getText().toString().equals("")||username.getText().toString().equals("")||password.getText().toString().equals("")){
                   Toast.makeText(Sign_up_activity.this, "Please Enter all Fields", Toast.LENGTH_SHORT).show();
               }else {
                   final ParseUser parseUser = new ParseUser();
                   parseUser.setUsername(username.getText().toString());
                   parseUser.setPassword(password.getText().toString());
                   parseUser.setEmail(email.getText().toString());
                   final ProgressDialog progressDialog= new ProgressDialog(Sign_up_activity.this);
                   progressDialog.setMessage("Wait Process is Runing..");
                   progressDialog.show();

                   parseUser.signUpInBackground(new SignUpCallback() {
                       @Override
                       public void done(ParseException e) {
                           if (e==null){

                               Toast.makeText(Sign_up_activity.this, "Sign up Successfully !", Toast.LENGTH_SHORT).show();
                               ParseUser.getCurrentUser().logOut();
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
                           progressDialog.dismiss();
                       }
                   });
               }







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

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_up_activity.this,Login_activity.class);
                startActivity(intent);
            }
        });


    }

    public void  root_leyout_tapped( View view){
      try{
          InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
          inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
      }catch (Exception e){
          e.printStackTrace();
      }

    }


}
