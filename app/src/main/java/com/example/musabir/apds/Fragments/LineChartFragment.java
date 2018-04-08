package com.example.musabir.apds.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musabir.apds.AxisValueFormatter;
import com.example.musabir.apds.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Musabir on 3/2/2018.
 */

public class LineChartFragment extends PreferenceFragment {


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.linechart_layout, container, false);

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(new FirstScreenFragment(),"FirstScreenFragment");


            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        LineChart graph = (LineChart) view.findViewById(R.id.graph);
        LineChart graph1 = (LineChart) view.findViewById(R.id.graph1);
        LineChart graph2 = (LineChart) view.findViewById(R.id.graph2);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(2f, 15));
        entries.add(new Entry(4f, 15));
        entries.add(new Entry(6f, 14));
        entries.add(new Entry(8f, 14.5f));
        entries.add(new Entry(10f, 14.5f));
        entries.add(new Entry(12f, 15));
        entries.add(new Entry(14f, 15));
        entries.add(new Entry(18f, 15));
        entries.add(new Entry(20f, 13));
        entries.add(new Entry(22f, 12.8f));
        entries.add(new Entry(24f, 12.7f));
        ArrayList<String > labels = new ArrayList<>();
        labels.add("January");
        labels.add( "February ");
        labels.add( "March ");
        labels.add( "April ");
        labels.add( "May ");
        labels.add( "June ");

        LineDataSet lineDataSet = new LineDataSet(entries,"# of Calls");
        Description description = new Description();
        graph.setBorderColor(Color.RED);
        description.setText("Hours");
        graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        graph.setDescription(description);
        LineData data = new LineData(lineDataSet);
        graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        graph.setData(data);
        graph.invalidate();

        graph1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        graph1.setData(data);
        graph1.invalidate();

        graph2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        graph2.setData(data);
        graph2.invalidate();


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

}
