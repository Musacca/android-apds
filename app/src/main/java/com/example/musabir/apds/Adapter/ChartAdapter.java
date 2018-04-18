package com.example.musabir.apds.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.DaysLogMapper;
import com.example.musabir.apds.Mapper.LogModel;
import com.example.musabir.apds.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Musabir on 4/17/2018.
 */

public class ChartAdapter extends BaseAdapter {
    ArrayList<DaysLogMapper> logModel;
    Context context;
    private GraphicalView mChart;

    public ChartAdapter(Context context,ArrayList<DaysLogMapper> logModel){
        this.context = context;
        this.logModel = logModel;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public DaysLogMapper getItem(int i) {
        return logModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        final LinearLayout contactsView;
        if (view == null) {
            contactsView = new LinearLayout(context);
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) context.getSystemService(inflater);
            vi.inflate(R.layout.adapter_chart, contactsView, true);
        } else {
            contactsView = (LinearLayout) view;

        }
        float min=100000,max=-100000,avg=0,sum = 0;
        int count=0;
        TextView tem =  contactsView.findViewById(R.id.tem);
        TextView value =  contactsView.findViewById(R.id.value);
        if(logModel.size()>0) {
            DaysLogMapper l = getItem(i);
            tem.setText(TypeToNameConverter.typeToNameConvert(l.getType()));
            LineChart graph = (LineChart) contactsView.findViewById(R.id.graph);
            ArrayList<Entry> entries = new ArrayList<>();

            if (logModel != null)
                for (int m = 0; m < logModel.size(); m++) {
                    if (logModel.get(m).getType() == i + 1) {
                        count++;
                        if (logModel.get(m).getValue() > max)
                            max = logModel.get(m).getValue();
                        if (logModel.get(m).getValue() < min)
                            min = logModel.get(m).getValue();
                        sum += logModel.get(m).getValue();
                        entries.add(new Entry(logModel.get(m).getDate(), logModel.get(m).getValue()));

                    }
                }
            if (count > 0)
                value.setText("Max:   " + max + "    Avg:   " + (float)(Math.round((sum / count)*10) / 10.0) + "    Min" + min);

            LineDataSet lineDataSet = new LineDataSet(entries, "# Logs");
            Description description = new Description();
            graph.setBorderColor(Color.RED);
            description.setText("Hours");
            graph.getAxisRight().setDrawLabels(false);

            graph.setDescription(description);
            LineData data = new LineData(lineDataSet);
            graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            graph.setData(data);
            graph.invalidate();
        }
        return contactsView;
    }
}

