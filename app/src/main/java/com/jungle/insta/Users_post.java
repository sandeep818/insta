package com.jungle.insta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Users_post extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        linearLayout = findViewById(R.id.linearLayout);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        setTitle(username+"'s Post");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",username);
        parseQuery.orderByDescending("createdAt");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size()>0 && e==null){
                    for (ParseObject post:objects){
                        final TextView postView = new TextView(Users_post.this);
                        postView.setText(post.get("image_des")+"");
                        ParseFile parseFile = (ParseFile) post.get("image");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data !=null && e==null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImage= new ImageView(Users_post.this);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5,5,5,5);
                                    postImage.setLayoutParams(params);
                                    postImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImage.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(5,5,5,15);
                                    postView.setLayoutParams(layoutParams);
                                    postView.setTextColor(Color.RED);
                                    postView.setBackgroundColor(Color.BLACK);
                                    postView.setTextSize(28f);
                                    postView.setGravity(Gravity.CENTER);
                                    linearLayout.addView(postImage);
                                    linearLayout.addView(postView);
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(Users_post.this, username+"  doesn't have any posts yet !", Toast.LENGTH_SHORT).show();
                    finish();
                }
               progressDialog.dismiss();
            }


        });

    }
}
