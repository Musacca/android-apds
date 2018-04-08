package com.example.musabir.apds;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linechart_layout);

//
//        MaterialCalendarView mcv = findViewById(R.id.calendarView);
//        mcv.state().edit()
//                .setFirstDayOfWeek(Calendar.WEDNESDAY)
//                .setMinimumDate(CalendarDay.from(2016, 4, 3))
//                .setMaximumDate(CalendarDay.from(2016, 5, 12))
//                .setCalendarDisplayMode(CalendarMode.MONTHS)
//                .commit();
//
//        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//                Log.d("----->><><><>>>",date.toString());
//            }
//        });
        LineChart graph = (LineChart) findViewById(R.id.graph);
//        ArrayList<Entry> entries = new ArrayList<>();
//                entries.add(new Entry(4f, 0));
//                entries.add(new Entry(8f, 1));
//                entries.add(new Entry(6f, 2));
//                entries.add(new Entry(12f, 3));
//                entries.add(new Entry(18f, 4));
//                entries.add(new Entry(9f, 5));
//                entries.add(new Entry(9f, 6));
//        ArrayList<String > labels = new ArrayList<>();
//                labels.add("January");
//                labels.add( "February ");
//                labels.add( "March ");
//                labels.add( "April ");
//                labels.add( "May ");
//                labels.add( "June ");
//
//        LineDataSet lineDataSet = new LineDataSet(entries,"# of Calls");
//        Description description = new Description();
//        graph.setBorderColor(Color.RED);
//        description.setText("Hours");
//        graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        graph.setDescription(description);
//        LineData data = new LineData(lineDataSet);
//        graph.setData(data);


        int[] numArr = {1,2,3,4,5,6};

        final HashMap<Integer, String> numMap = new HashMap<>();
        numMap.put(1, "first");
        numMap.put(2, "second");
        numMap.put(3, "third");
        numMap.put(4, "fourth");
        numMap.put(5, "fifth");
        numMap.put(6, "sixth");

        List<Entry> entries1 = new ArrayList<Entry>();

        for(int num : numArr){
            entries1.add(new Entry(num, num));
        }

        LineDataSet dataSet = new LineDataSet(entries1, "Numbers");


        LineData data = new LineData(dataSet);

        XAxis xAxis = graph.getXAxis();
        xAxis.setValueFormatter(new AxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return numMap.get((int)value);
            }
        });
        graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        graph.setData(data);
        graph.invalidate();

    }

}
