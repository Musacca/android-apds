package com.example.musabir.apds.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.musabir.apds.Adapter.FilterListAdapter;
import com.example.musabir.apds.Dialog.FilterPopupDialog;
import com.example.musabir.apds.Mapper.FilterMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 3/2/2018.
 */

public class FilterFragment extends PreferenceFragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.filter_layout, container, false);

        ListView listView = view.findViewById(R.id.filter_list);
        ImageView calendar_icon = view.findViewById(R.id.calendar_icon);
        FilterListAdapter filterListAdapter = new FilterListAdapter(getActivity(),new ArrayList<FilterMapper>());
        listView.setAdapter(filterListAdapter);
        calendar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog= new FilterPopupDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        return view;
    }


}
