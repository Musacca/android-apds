package com.example.musabir.apds.Activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.musabir.apds.Dialog.FirstPagePopupDialog;
import com.example.musabir.apds.Fragments.CalendarViewFragment;
import com.example.musabir.apds.Fragments.FilterFragment;
import com.example.musabir.apds.Fragments.FirstScreenFragment;
import com.example.musabir.apds.Fragments.SettingFragment;
import com.example.musabir.apds.R;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);

        displayView(new FirstScreenFragment(),"FirstScreenFragment");
        Dialog dialog= new FirstPagePopupDialog(Main2Activity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void displayView(Fragment fragment, String fragmentTags) {


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(fragmentTags);
            fragmentTransaction.replace(R.id.content_frame, fragment, fragmentTags);

            fragment.setArguments(bundle);
            fragmentTransaction.commit();
            //fragmentManager.executePendingTransactions();

        }
    }
}
