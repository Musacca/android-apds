package com.example.musabir.apds.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.PushNotificationMapper;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 4/12/2018.
 */

public class ChangeAccountAdapter extends BaseAdapter {
    Context context;
    static ArrayList<UserModel> userModels;

    public ChangeAccountAdapter(Context context, ArrayList<UserModel> userModels) {
        this.context = context;
        this.userModels = userModels;
    }

    public void addAll(ArrayList<UserModel> userModels) {
        this.userModels = userModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public UserModel getItem(int i) {
        return userModels.get(i);
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
            vi.inflate(R.layout.adapter_change_profile, contactsView, true);
        } else {
            contactsView = (LinearLayout) view;

        }

        TextView profile_name = contactsView.findViewById(R.id.profile_name);
        TextView status_id = contactsView.findViewById(R.id.status_id);
        ImageView checked = contactsView.findViewById(R.id.checked);
        contactsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int l = 0; l < userModels.size(); l++) {
                    if (userModels.get(l).get()==true)
                    userModels.sett(false);
                }
                userModels.;//set login;

                addAll(userModels);
            }
        });

        return contactsView;
    }
}
