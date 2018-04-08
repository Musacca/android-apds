package com.example.musabir.apds.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musabir.apds.R;

public class SensorIDEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.insert_sensor_id);


        TextView textView = findViewById(R.id.here);
        Button enter = findViewById(R.id.enter);
        Button barcode_reader = findViewById(R.id.barcode_reader);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SensorIDEnterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SensorIDEnterActivity.this,RegisterationActivity.class);
                startActivity(intent);
            }
        });
        barcode_reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SensorIDEnterActivity.this,"Not available yet...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
