package com.example.musabir.apds.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.musabir.apds.Adapter.WidgetListAdapter;
import com.example.musabir.apds.Mapper.WidgetListMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 3/8/2018.
 */

public class WidgetDialog extends Dialog {

    Switch aSwitch;
    RelativeLayout cancel, confirm,overlay_color,lyt;
    ListView language_list;
    private ArrayList<WidgetListMapper> widgetListMappers;
    public WidgetDialog(@NonNull Context context, ArrayList<WidgetListMapper> widgetListMappers) {
        super(context);
        this.widgetListMappers = widgetListMappers;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_dialog);

        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        aSwitch = findViewById(R.id.switchh);
        language_list = findViewById(R.id.language_list);
        overlay_color = findViewById(R.id.overlay_color);
        lyt = findViewById(R.id.lyt);
        overlay_color.setVisibility(View.GONE);
        lyt.setClickable(true);
        WidgetListAdapter widgetListAdapter = new WidgetListAdapter(getContext(), widgetListMappers);
        language_list.setAdapter(widgetListAdapter);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    overlay_color.setVisibility(View.VISIBLE);
                    lyt.setClickable(false);

                }
                else {
                    overlay_color.setVisibility(View.GONE);
                    lyt.setClickable(true);

                }

            }

        });
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
