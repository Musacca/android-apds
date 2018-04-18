package com.example.musabir.apds.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Adapter.ChartAdapter;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.DaysAllLogMapper;
import com.example.musabir.apds.Mapper.DaysLogMapper;
import com.example.musabir.apds.R;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Musabir on 3/2/2018.
 */

public class CalendarViewEachElementFragment extends PreferenceFragment {
    View view;
    TextView co_min,co_avg,co_max,co2_min,co2_avg,co2_max,hidro_min,hidro_avg,hidro_max,methane_min,methane_avg,methane_max,
            smoke_min,smoke_avg,smoke_max,lpg_min,lpg_max,lpg_avg,temp_min,temp_avg,temp_max,humidity_min,humidity_avg,humidity_max;
    String d;

    ChartAdapter chartAdapter;
   static ArrayList<DaysLogMapper> daysLogMappers;
    private Gson gson;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.calendar_first_screen_layout, container, false);
        d =getArguments().getString("date");
        view.findViewById(R.id.graph_lyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayView(new LineChartFragmentCalendarView(), "LineChartFragmentCalendarView");

            }
        });
        daysLogMappers = new ArrayList<>();
        gson = new Gson();

        initView(view);
        getLastDayLog();
        return view;
    }

    private void displayView(Fragment fragment, String fragmentTags) {


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(fragmentTags);
            fragmentTransaction.replace(R.id.content_frame, fragment, fragmentTags);
            bundle.putString("date", d);
            fragment.setArguments(bundle);
            fragmentTransaction.commit();
            //fragmentManager.executePendingTransactions();

        }
    }

    private void initView(View view) {
        co_min = view.findViewById(R.id.car_mono_min_degree);
        co_avg = view.findViewById(R.id.car_mono_avg_degree);
        co_max = view.findViewById(R.id.car_mono_max_degree);

        co2_min = view.findViewById(R.id.car_diox_min_degree);
        co2_avg = view.findViewById(R.id.car_diox_avg_degree);
        co2_max = view.findViewById(R.id.car_diox_max_degree);

        hidro_min = view.findViewById(R.id.hydrogen_min_degree);
        hidro_avg = view.findViewById(R.id.hydrogen_avg_degree);
        hidro_max = view.findViewById(R.id.hydrogen_max_degree);

        methane_min = view.findViewById(R.id.methane_min_degree);
        methane_avg = view.findViewById(R.id.methane_avg_degree);
        methane_max = view.findViewById(R.id.methane_max_degree);

        smoke_min = view.findViewById(R.id.smoke_min_degree);
        smoke_avg = view.findViewById(R.id.smoke_avg_degree);
        smoke_max = view.findViewById(R.id.smoke_max_degree);

        lpg_min = view.findViewById(R.id.lpg_min_degree);
        lpg_avg = view.findViewById(R.id.lpg_avg_degree);
        lpg_max = view.findViewById(R.id.lpg_max_degree);

        temp_min = view.findViewById(R.id.temp_min_degree);
        temp_avg = view.findViewById(R.id.temp_avg_degree);
        temp_max = view.findViewById(R.id.temp_max_degree);

        humidity_min = view.findViewById(R.id.hum_min_degree);
        humidity_avg = view.findViewById(R.id.hum_avg_degree);
        humidity_max = view.findViewById(R.id.hum_max_degree);




    }



    private void getLastDayLog(){

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_GET_LOGS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        pDialog.dismiss();
                        String appData = null;
                        int code = 0;

                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean obj1 = obj.getBoolean("success");
                            JSONArray objArray = null;
                            if (obj1) {
                                JSONObject payload = obj.getJSONObject("payload");
                                if (payload.getInt("code") == APIResponseCodes.OK) {

                                    JSONArray lastLogs = payload.getJSONArray("logs");
                                    daysLogMappers = new ArrayList<>();
                                    for (int i=0;i<lastLogs.length();i++) {

                                        DaysAllLogMapper daysAllLogMapper = gson.fromJson(lastLogs.get(i).toString(), DaysAllLogMapper.class);

                                        daysAllLogMapper.setLogTypeName(TypeToNameConverter.typeToNameConvert(daysAllLogMapper.getLogType()));

                                        DaysLogMapper d = new DaysLogMapper();
                                        d.setValue(daysAllLogMapper.getLogValue());
                                        d.setType(daysAllLogMapper.getLogType());

                                        d.setDate(dateToDouble(daysAllLogMapper.getLogTime()));
                                        daysLogMappers.add(d);
                                    }
                                    if(daysLogMappers==null|| daysLogMappers.size()==0)
                                        Helper.showCustomAlert(getActivity(), "No any result found");


                                    else setValues(daysLogMappers);
                                } else
                                    Helper.showCustomAlert(getActivity(), getString(R.string.something_goes_wrong));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                //  Toast.makeText(SplashScreenActivity.this, "Error occurs", Toast.LENGTH_SHORT).show();
                Log.d("Error.Response", error.getMessage() + " message");
                if (pDialog != null)
                    pDialog.dismiss();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("uuid", Defaults.uuid);
                param.put("sensorId", Defaults.sensorId);
                param.put("date", d);

                Log.d("--->>?>?????",param.toString());
                return param;
            }
        };
        queue.add(postRequest);



    }

    private void setValues(ArrayList<DaysLogMapper> daysLogMappers){


        if (daysLogMappers != null)
            for(int i=0;i<8;i++){
                float min=100000,max=-100000,avg=0,sum = 0;
                int count=0;
            for (int m = 0; m < daysLogMappers.size(); m++) {
                if (daysLogMappers.get(m).getType() == i + 1) {
                    count++;
                    if (daysLogMappers.get(m).getValue() > max)
                        max = daysLogMappers.get(m).getValue();
                    if (daysLogMappers.get(m).getValue() < min)
                        min = daysLogMappers.get(m).getValue();
                    sum += daysLogMappers.get(m).getValue();


                } }
                if(i==0) {
                    temp_max.setText(max + "");
                    temp_min.setText(min + "");
                    temp_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==1){
                    humidity_max.setText(max + "");
                    humidity_min.setText(min + "");
                    humidity_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==2){
                    methane_max.setText(max + "");
                    methane_min.setText(min + "");
                    methane_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==3){
                    co_max.setText(max + "");
                    co_min.setText(min + "");
                    co_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==4){
                    co2_max.setText(max + "");
                    co2_min.setText(min + "");
                    co2_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==5){
                    smoke_max.setText(max + "");
                    smoke_min.setText(min + "");
                    smoke_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==6){
                    lpg_max.setText(max + "");
                    lpg_min.setText(min + "");
                    lpg_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }
                else if(i==7){
                    smoke_max.setText(max + "");
                    smoke_min.setText(min + "");
                    smoke_avg.setText((float) (Math.round((sum / count) * 10) / 10.0) + "");
                }

            }
    }

    private float dateToDouble(String date){
        Log.d("------>><><>>>",date.substring(11,13));
        Log.d("------>><><>>>",date.substring(14,16));
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        float val = (Float.parseFloat(date.substring(11,13))+4)%24;
        float m = Float.parseFloat(date.substring(14,16))/60;
        m = (float)(Math.round(m*10) / 10.0);
        return val+m;
    }


}