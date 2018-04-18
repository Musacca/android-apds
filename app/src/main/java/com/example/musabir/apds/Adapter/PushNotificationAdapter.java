package com.example.musabir.apds.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.musabir.apds.Dialog.PushNotificationDialog;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.PushNotificationMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 4/12/2018.
 */

public class PushNotificationAdapter extends BaseAdapter {
    Context context;

    public PushNotificationAdapter(Context context, ArrayList<PushNotificationMapper> pushNotificationMappers){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public PushNotificationMapper getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
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
        if(PushNotificationDialog.pushNotificationMappers.size()>0) {
            TextView sensorName = contactsView.findViewById(R.id.sensor_name);
            Switch aSwitch = contactsView.findViewById(R.id.switchh);
            EditText minValue = contactsView.findViewById(R.id.min_edittext);
            EditText maxValue = contactsView.findViewById(R.id.max_edittext);
            if (PushNotificationDialog.pushNotificationMappers.get(i).getNotifyStatus() == 1)
                aSwitch.setEnabled(true);
            else aSwitch.setEnabled(false);
            minValue.setText(PushNotificationDialog.pushNotificationMappers.get(i).getMaxValueForNotify() + "");
            maxValue.setText(PushNotificationDialog.pushNotificationMappers.get(i).getMinValueForNotify() + "");
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                        PushNotificationDialog.pushNotificationMappers.get(i).setNotifyStatus(1);
                    else
                        PushNotificationDialog.pushNotificationMappers.get(i).setNotifyStatus(0);

                }
            });
            sensorName.setText(TypeToNameConverter.typeToNameConvert(PushNotificationDialog.pushNotificationMappers.get(i).getType()));

            minValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int k, int i1, int i2) {

                    if (charSequence.toString().trim().isEmpty())
                        if (i != 8)

                            PushNotificationDialog.pushNotificationMappers.get(i).setMinValueForNotify(0);
                        else if (i != 8)

                            PushNotificationDialog.pushNotificationMappers.get(i).setMinValueForNotify(Double.parseDouble(charSequence.toString()));
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
                public void onTextChanged(CharSequence charSequence, int l, int i1, int i2) {
                    if (charSequence.toString().trim().isEmpty())
                        if (i != 8)

                            PushNotificationDialog.pushNotificationMappers.get(i).setMaxValueForNotify(0);
                        else if (i != 8)
                            PushNotificationDialog.pushNotificationMappers.get(i).setMaxValueForNotify(Double.parseDouble(charSequence.toString()));
                    Log.d("-----><> changsasaed11 ", PushNotificationDialog.pushNotificationMappers.get(i).toString() + "   " + charSequence);

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Log.d("-----><>>><><> changed ",PushNotificationDialog.pushNotificationMappers.get(i).toString());
                }
            });
        }
        return contactsView;
    }
}
