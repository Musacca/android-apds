package com.example.musabir.apds.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musabir.apds.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

/**
 * Created by Musabir on 3/2/2018.
 */

public class CalendarViewEachElementFragment extends PreferenceFragment {
    View view;
    TextView co_min,co_avg,co_max,co2_min,co2_avg,co2_max,hidro_min,hidro_avg,hidro_max,methane_min,methane_avg,methane_max,
            smoke_min,smoke_avg,smoke_max,lpg_min,lpg_max,lpg_avg,temp_min,temp_avg,temp_max,humidity_min,humidity_avg,humidity_max;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.calendar_first_screen_layout, container, false);

        view.findViewById(R.id.graph_lyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayView(new LineChartFragmentCalendarView(), "LineChartFragmentCalendarView");

            }
        });
        initView(view);
        return view;
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

    private void initView(View view) {
        co_min = view.findViewById(R.id.car_mono_min_degree);
        co_avg = view.findViewById(R.id.car_mono_avg_degree);
        co_max = view.findViewById(R.id.car_mono_max_degree);

        co2_min = view.findViewById(R.id.car_diox_min_degree);
        co2_avg = view.findViewById(R.id.car_diox_avg_degree);
        co2_max = view.findViewById(R.id.car_diox_max_degree);

        hidro_min = view.findViewById(R.id.hydrogen_min_degree);
        hidro_avg = view.findViewById(R.id.hydrogen_avg_degree);
        hidro_max = view.findViewById(R.id.hydrogen_max_degree);

        methane_min = view.findViewById(R.id.methane_min_degree);
        methane_avg = view.findViewById(R.id.methane_avg_degree);
        methane_max = view.findViewById(R.id.methane_max_degree);

        smoke_min = view.findViewById(R.id.smoke_min_degree);
        smoke_avg = view.findViewById(R.id.smoke_avg_degree);
        smoke_max = view.findViewById(R.id.smoke_max_degree);

        lpg_min = view.findViewById(R.id.lpg_min_degree);
        lpg_avg = view.findViewById(R.id.lpg_avg_degree);
        lpg_max = view.findViewById(R.id.lpg_max_degree);

        temp_min = view.findViewById(R.id.temp_min_degree);
        temp_avg = view.findViewById(R.id.temp_avg_degree);
        temp_max = view.findViewById(R.id.temp_max_degree);

        humidity_min = view.findViewById(R.id.hum_min_degree);
        humidity_avg = view.findViewById(R.id.hum_avg_degree);
        humidity_max = view.findViewById(R.id.hum_max_degree);




    }

}