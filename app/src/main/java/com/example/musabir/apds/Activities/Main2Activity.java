package com.example.musabir.apds.Activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.musabir.apds.Dialog.FirstPagePopupDialog;
import com.example.musabir.apds.Fragments.CalendarViewFragment;
import com.example.musabir.apds.Fragments.FilterFragment;
import com.example.musabir.apds.Fragments.FirstScreenFragment;
import com.example.musabir.apds.Fragments.SettingFragment;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.R;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;
    private boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.first_screen:
                    displayView(new FirstScreenFragment(),"FirstScreenFragment");
                    return true;
                case R.id.calendar_screen:
                    displayView(new CalendarViewFragment(),"CalendarViewFragment");
                    return true;
                case R.id.filter_screen:
                    displayView(new FilterFragment(),"FilterFragment");
                    return true;
                case R.id.setting_screen:
                    displayView(new SettingFragment(),"SettingFragment");

                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        Menu menu = navigation.getMenu();
        if (getFragmentManager().getBackStackEntryCount() > 1) {
        if ((getSupportFragmentManager().findFragmentByTag("LineChartFragmentCalendarView") != null
                && getSupportFragmentManager().findFragmentByTag("LineChartFragmentCalendarView").isVisible())||
                (getSupportFragmentManager().findFragmentByTag("CalendarViewFragment") != null
                        && getSupportFragmentManager().findFragmentByTag("CalendarViewFragment").isVisible())||
                (getSupportFragmentManager().findFragmentByTag("CalendarViewEachElementFragment") != null
                        && getSupportFragmentManager().findFragmentByTag("CalendarViewEachElementFragment").isVisible())) {
            navigation.setSelectedItemId(R.id.calendar_screen);
            Log.d("------><>>>><>","----------heree");

        }
        else if((getSupportFragmentManager().findFragmentByTag("FirstScreenFragment") != null
                && getSupportFragmentManager().findFragmentByTag("FirstScreenFragment").isVisible())||
                (getSupportFragmentManager().findFragmentByTag("LineChartFragment") != null
                        && getSupportFragmentManager().findFragmentByTag("LineChartFragment").isVisible())) {
            Log.d("------><>>>><>", "----------heree111");

            navigation.setSelectedItemId(R.id.first_screen);
        }
        else if((getSupportFragmentManager().findFragmentByTag("FilterFragment") != null
                        && getSupportFragmentManager().findFragmentByTag("FilterFragment").isVisible()))
            navigation.setSelectedItemId(R.id.filter_screen);
        else if((getSupportFragmentManager().findFragmentByTag("SettingFragment") != null
                        && getSupportFragmentManager().findFragmentByTag("SettingFragment").isVisible()))

            navigation.setSelectedItemId(R.id.setting_screen);

            super.onBackPressed();
        }
        else {

            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Helper.showCustomAlert(Main2Activity.this,"Double click to close the application");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);

        displayView(new FirstScreenFragment(),"FirstScreenFragment");

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void displayView(Fragment fragment, String fragmentTags) {


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.addToBackStack(fragmentTags);
            fragmentTransaction.replace(R.id.content_frame, fragment, fragmentTags);

            fragment.setArguments(bundle);
            fragmentTransaction.commit();
            //fragmentManager.executePendingTransactions();

        }
    }
}
