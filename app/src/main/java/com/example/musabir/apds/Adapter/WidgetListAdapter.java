package com.example.musabir.apds.Adapter;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musabir.apds.Mapper.WidgetListMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 3/8/2018.
 */

public class WidgetListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<WidgetListMapper> widgetListMapper;
    public WidgetListAdapter(Context context, ArrayList<WidgetListMapper> widgetListMapper){
        this.context = context;
        this.widgetListMapper = widgetListMapper;

    }
    @Override
    public int getCount() {
        return widgetListMapper.size();
    }

    @Override
    public Object getItem(int i) {
        return widgetListMapper.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LinearLayout contactsView;
        if (view == null) {
            contactsView = new LinearLayout(context);
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) context.getSystemService(inflater);
            vi.inflate(R.layout.widget_list_item, contactsView, true);
        } else {
            contactsView = (LinearLayout) view;

        }

        TextView sensor_name = contactsView.findViewById(R.id.sensor_name);
        SwitchCompat sensor_switch = contactsView.findViewById(R.id.sensor_switch);

        sensor_name.setText(widgetListMapper.get(i).getName());

        return contactsView;
    }
}

