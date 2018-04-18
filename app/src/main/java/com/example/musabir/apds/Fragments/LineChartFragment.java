package com.example.musabir.apds.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Adapter.ChartAdapter;
import com.example.musabir.apds.AxisValueFormatter;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.DaysAllLogMapper;
import com.example.musabir.apds.Mapper.DaysLogMapper;
import com.example.musabir.apds.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Musabir on 3/2/2018.
 */

public class LineChartFragment extends PreferenceFragment {


    View view;
    private Gson gson;
    private ListView listView;
    ChartAdapter chartAdapter;
    ArrayList<DaysLogMapper> daysLogMappers;
    String d;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.linechart_layout, container, false);

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(new FirstScreenFragment(),"FirstScreenFragment");

            }
        });
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        d = df.format(c);

        listView = view.findViewById(R.id.list);
        gson = new Gson();
        daysLogMappers = new ArrayList<>();
        ChartAdapter chartAdapter = new ChartAdapter(getActivity(),daysLogMappers);
        listView.setAdapter(chartAdapter);
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

            fragment.setArguments(bundle);
            fragmentTransaction.commit();
            //fragmentManager.executePendingTransactions();

        }
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
                                    if(daysLogMappers!=null|| daysLogMappers.size()>0)
                                    {
                                        chartAdapter = new ChartAdapter(getContext(),daysLogMappers);
                                        listView.setAdapter(chartAdapter);
                                    }
                                    else
                                        Helper.showCustomAlert(getActivity(), "No any result found");



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
