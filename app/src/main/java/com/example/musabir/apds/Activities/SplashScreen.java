package com.example.musabir.apds.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;

import io.realm.Realm;

public class SplashScreen extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
        realm = Realm.getDefaultInstance();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                UserModel userModel = realm.where(UserModel.class).equalTo("isActive",true).findFirst();
                if (userModel!=null && userModel.getName()!=null)
                {
                    Intent intent = new Intent(SplashScreen.this,Main2Activity.class);
                    Defaults.sensorId = userModel.getSensorId();
                    Defaults.msisdn = userModel.getMsisdn();
                    Defaults.uuid = userModel.getUuid();
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,SensorIDEnterActivity.class);

                    startActivity(intent);
                }
            }

        }, 1000);

    }

}
