package com.example.musabir.apds.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.musabir.apds.Adapter.PushNotificationAdapter;
import com.example.musabir.apds.Mapper.PushNotificationMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 4/12/2018.
 */

public class PushNotificationDialog extends Dialog {
    RelativeLayout cancel, confirm,lyt;
    ListView list;
    private PushNotificationAdapter pushNotificationAdapter;
    private static ArrayList<PushNotificationMapper> pushNotificationMappers;
    public PushNotificationDialog(@NonNull Context context, ArrayList<PushNotificationMapper> widgetListMappers) {
        super(context);
        this.pushNotificationMappers = widgetListMappers;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_push_notification);

        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        list = findViewById(R.id.list);
        pushNotificationAdapter = new PushNotificationAdapter(getContext(), pushNotificationMappers);
        list.setAdapter(pushNotificationAdapter);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}


