package com.jungle.insta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private TextView wlcm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_leyout);
        wlcm = findViewById(R.id.welcome);
        Intent intent = getIntent();
        String str = intent.getStringExtra("user");
        wlcm.setText("Wellcme Back "+str);
    }
}
