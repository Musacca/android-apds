package com.example.musabir.apds.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Fragments.FilterFragment;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Musabir on 3/2/2018.
 */

public class FilterPopupDialog extends Dialog {
    Spinner spinner;
    EditText min_edittext,max_edittext;
    TextView end_date_edittext,start_date_edittext;
    ImageView start_date_calendar,end_date_calendar;
    private SimpleDateFormat dateFormatter;
    DatePickerDialog fromDatePickerDialog;
    DatePickerDialog toDatePickerDialog;
    int type=1;

    public FilterPopupDialog(@NonNull Context context) {
        super(context);
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.filter_dialog);
            spinner = findViewById(R.id.spinner);
            min_edittext = findViewById(R.id.min_edittext);
            max_edittext = findViewById(R.id.max_edittext);
            end_date_calendar = findViewById(R.id.end_date_calendar);
            start_date_calendar = findViewById(R.id.start_date_calendar);
            end_date_edittext = findViewById(R.id.end_date_edittext);
            start_date_edittext = findViewById(R.id.start_date_edittext);
            Calendar newCalendar = Calendar.getInstance();
            if(Defaults.startDate!=null)
                start_date_edittext.setText(Defaults.startDate);
            if(Defaults.endDate!=null)
                end_date_edittext.setText(Defaults.endDate);
            if(Defaults.minValue!=-1221)
                min_edittext.setText(Defaults.minValue+"");
            if(Defaults.maxValue!=-1221)
                max_edittext.setText(Defaults.maxValue+"");

            fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String day;
                    if(dayOfMonth<=9)
                        day="0"+dayOfMonth;
                    else day = dayOfMonth+"";
                    if(monthOfYear<9)
                        start_date_edittext.setText(year+"-0"+(int)(monthOfYear+1)+"-"+day);
                    else
                        start_date_edittext.setText(year+"-"+(int)(monthOfYear+1)+"-"+dayOfMonth);                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            toDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String day;
                    if(dayOfMonth<=9)
                        day="0"+dayOfMonth;
                    else day = dayOfMonth+"";
                    if(monthOfYear<9)
                    end_date_edittext.setText(year+"-0"+(int)(monthOfYear+1)+"-"+day);
                    else
                        end_date_edittext.setText(year+"-"+(int)(monthOfYear+1)+"-"+day);

                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!min_edittext.getText().toString().trim().isEmpty()&&!max_edittext.getText().toString().trim().isEmpty()&&
                            !start_date_edittext.getText().toString().trim().isEmpty() && !end_date_edittext.getText().toString().trim().isEmpty()){
                        Defaults.maxValue = Double.parseDouble(max_edittext.getText().toString().trim());
                        Defaults.minValue = Double.parseDouble(min_edittext.getText().toString().trim());
                        Defaults.startDate =(start_date_edittext.getText().toString().trim());
                        Defaults.endDate = (end_date_edittext.getText().toString().trim());
                        Defaults.selectedFilterType = type+1;
                        FilterFragment.filter();
                        Log.d("-------><<<><>","CLICKEDDD");
                        dismiss();
                    }
                    else Helper.showCustomAlert(getContext(),"Enter all needed information");
                }
            });

            ArrayList<String> arraySpinner = new ArrayList<>();
            for(int i=0;i<8;i++)
                arraySpinner.add(TypeToNameConverter.typeToNameConvert(i));
            ArrayAdapter<String> adaptera = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraySpinner) {


                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    v.setMinimumHeight((int) (40*getContext().getResources().getDisplayMetrics().density));
                    type = position;

                    return v;
                }

            };
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinner.setAdapter(adaptera);


        start_date_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();


            }
        });
            end_date_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toDatePickerDialog.show();


                }
            });
            spinner.setSelection(Defaults.selectedFilterType-1);



        }
}
