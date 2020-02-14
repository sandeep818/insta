package com.jungle.insta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView gettext;
    private Button getall;
    private int a=1;
    private String name;
    private String nae;
    private  Button switch_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gettext = findViewById(R.id.gettext);
        getall = findViewById(R.id.getall);
        switch_activity = findViewById(R.id.switch_activity);
        if(ParseUser.getCurrentSessionToken()!= null){

            Toast.makeText(this, "log in  : "+ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);

        }

        switch_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, Login_activity.class);
                startActivity(intent);
            }
        });

        getall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> getdataall = ParseQuery.getQuery("Fake");
                getdataall.whereEqualTo("name","sandeep");
                getdataall.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null){
                        if (objects.size()>0){
                            a=1;
                            for (ParseObject getit:objects){

                                Log.i("get", "done: "+getit.get("name"));
                                if (getit.get("name").toString().equals("sandy")){
                                    name=getit.get("name").toString();
                                    nae = getit.get("nae").toString();
                                    Toast.makeText(MainActivity.this, ""+name+" :"+nae, Toast.LENGTH_SHORT).show();
                                    break;
                                }else{
                                    Log.i("get", "done: not get "+a);
                                    a=a+1;
                                }

                            }

                        }

                    }
                    }
                });
            }
        });

        gettext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject>parsedata = ParseQuery.getQuery("Fake");
                parsedata.getInBackground("865MBqmBvf", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object!=null && e ==null){
                            gettext.setText(object.get("name")+"");
                        }else {

                            Log.i("ok", "done: "+object.toString());
                            Toast.makeText(MainActivity.this, "not done"+object.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
    public void clicked(View v){

        ParseObject fake = new ParseObject("Fake");
        fake.put("name","sandeep");
        fake.put("nae","seep1");
        fake.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null ){
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("ok ", "done: not done");
                }
            }
        });

    }
}
