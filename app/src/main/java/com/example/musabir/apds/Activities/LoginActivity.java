package com.example.musabir.apds.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.musabir.apds.Main2Activity;
import com.example.musabir.apds.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.sign_in);

        Button next_btn_enternumber = findViewById(R.id.next_btn_enternumber);
        RelativeLayout top_layout = findViewById(R.id.top_layout);
        next_btn_enternumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,VerifyNumberActivity.class);
                startActivity(intent);
            }
        });

        top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
