package com.example.musabir.apds.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musabir.apds.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;

/**
 * Created by Musabir on 3/2/2018.
 */

public class CalendarViewFragment extends PreferenceFragment {
    View view;
    String d;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.calendar_layout, container, false);
        MaterialCalendarView calendarView = view.findViewById(R.id.calendarView);
       calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
           @Override
           public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
               Log.d("----><>>>>>>",date.toString()+" date");
               SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
               d = format1.format(date.getDate());


               displayView(new CalendarViewEachElementFragment(),"CalendarViewEachElementFragment");
           }
       });

        return view;
    }

    private void displayView(Fragment fragment, String fragmentTags) {


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           // fragmentTransaction.addToBackStack(fragmentTags);
            fragmentTransaction.replace(R.id.content_frame, fragment, fragmentTags);
            bundle.putString("date", d);

            fragment.setArguments(bundle);
            fragmentTransaction.commit();


        }
    }
}
