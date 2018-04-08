package com.example.musabir.apds.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.musabir.apds.Mapper.FilterMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 3/2/2018.
 */

public class FilterListAdapter extends BaseAdapter {

    Context context;
    ArrayList<FilterMapper> filterMappers;
    public FilterListAdapter(Context context, ArrayList<FilterMapper> filterListAdapters){
        this.filterMappers = filterListAdapters;
        this.context = context;
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 10;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LinearLayout contactsView;
        if (view == null) {
            contactsView = new LinearLayout(context);
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) context.getSystemService(inflater);
            vi.inflate(R.layout.custom_list_item, contactsView, true);
        } else {
            contactsView = (LinearLayout) view;

        }
        return contactsView;
    }
}
