package com.jungle.insta;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class Home extends AppCompatActivity {
    private TextView wlcm;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_leyout);
        setTitle("Account");
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout= findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager,true);
//        wlcm = findViewById(R.id.welcome);
//        Intent intent = getIntent();
//        String str = intent.getStringExtra("user");
//       wlcm.setText("Wellcome Back "+ ParseUser.getCurrentUser().getUsername().toString()+" !");

       // findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         ParseUser.logOut();
        //        finish();
       //     }
      //  });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.post_image){

            if (Build.VERSION.SDK_INT>= 23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }else{
                openCamera();
            }


            Toast.makeText(this, "ok im here!", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==3000){

            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
        }

    }

    private void openCamera() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 4000 && resultCode == RESULT_OK && data != null){

            try{
                Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                ByteArrayOutputStream arrayOutputStream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,arrayOutputStream);
                byte[] bytes = arrayOutputStream.toByteArray();
                ParseFile parseFile = new ParseFile("img.png",bytes);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("image",parseFile);
                parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                final ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Loading..");
                dialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Toast.makeText(Home.this, "Successfuly Post", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Home.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.dismiss();

            }catch(Exception e){
                e.printStackTrace();

            }

        }
    }
}
