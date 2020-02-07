package com.jungle.insta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseUser;

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
}
