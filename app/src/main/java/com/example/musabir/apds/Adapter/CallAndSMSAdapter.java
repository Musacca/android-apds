package com.example.musabir.apds.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.CallAndSmsMapper;
import com.example.musabir.apds.Mapper.PushNotificationMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 4/12/2018.
 */

public class CallAndSMSAdapter extends BaseAdapter {
    Context context;
   static ArrayList<CallAndSmsMapper> callAndSMS;

    public CallAndSMSAdapter(Context context, ArrayList<CallAndSmsMapper> pushNotificationMappers){
        this.context = context;
        this.callAndSMS = pushNotificationMappers;
    }
    @Override
    public int getCount() {
        return callAndSMS.size();
    }

    @Override
    public CallAndSmsMapper getItem(int i) {
        return callAndSMS.get(i);
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
            vi.inflate(R.layout.adapter_push_notifcation, contactsView, true);
        } else {
            contactsView = (LinearLayout) view;

        }

        TextView sensorName = contactsView.findViewById(R.id.sensor_name);
        Switch aSwitch= contactsView.findViewById(R.id.switchh);
        EditText minValue = contactsView.findViewById(R.id.min_edittext);
        EditText maxValue = contactsView.findViewById(R.id.max_edittext);

        aSwitch.setEnabled(getItem(i).isEnable());
        minValue.setText(getItem(i).getMinValue()+"");
        maxValue.setText(getItem(i).getMaxValue()+"");
        sensorName.setText(TypeToNameConverter.typeToNameConvert(getItem(i).getType()));

        minValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                getItem(i).setMinValue(Double.parseDouble(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        maxValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                getItem(i).setMaxValue(Double.parseDouble(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return contactsView;
    }
}
