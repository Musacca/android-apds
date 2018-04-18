package com.example.musabir.apds.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Adapter.FilterListAdapter;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Dialog.FilterPopupDialog;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.DaysAllLogMapper;
import com.example.musabir.apds.Mapper.DaysLogMapper;
import com.example.musabir.apds.Mapper.FilterMapper;
import com.example.musabir.apds.R;
import com.google.gson.Gson;

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

public class FilterFragment extends PreferenceFragment {

    View view;
    static ArrayList<FilterMapper> filterMappers;
    private static Gson gson;
    static ArrayList<DaysLogMapper> daysLogMappers;
    private static FilterListAdapter filterListAdapter;
    private static ListView listView;
    static Context context;
    private static TextView sensor_name;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.filter_layout, container, false);
        gson = new Gson();
        daysLogMappers = new ArrayList<>();
        filterMappers = new ArrayList<FilterMapper>();
        context = getActivity();
        listView = view.findViewById(R.id.filter_list);
        sensor_name = view.findViewById(R.id.sensor_name);
        ImageView calendar_icon = view.findViewById(R.id.calendar_icon);
        filterListAdapter = new FilterListAdapter(getActivity(),filterMappers);
        listView.setAdapter(filterListAdapter);
        sensor_name.setText(TypeToNameConverter.typeToNameConvert(Defaults.selectedFilterType));
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

    public static void filter(){
        getLastDayLog();
        sensor_name.setText(TypeToNameConverter.typeToNameConvert(Defaults.selectedFilterType));

        Log.d(")0-0-0-","hereeeeeeee");
    }

    private static void getLastDayLog(){

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_FILTER,
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
                                    filterMappers.clear();
                                    for (int i=0;i<lastLogs.length();i++) {
                                        FilterMapper filterMapper = new FilterMapper();
                                        DaysAllLogMapper daysAllLogMapper = gson.fromJson(lastLogs.get(i).toString(), DaysAllLogMapper.class);

                                        daysAllLogMapper.setLogTypeName(TypeToNameConverter.typeToNameConvert(daysAllLogMapper.getLogType()));

                                        filterMapper.setValue(daysAllLogMapper.getLogValue());
                                        filterMapper.setType(daysAllLogMapper.getLogType());
                                        filterMapper.setTime(dateToHoursAndMin(daysAllLogMapper.getLogTime()));
                                        filterMapper.setDate(dateToYearMonthDay(daysAllLogMapper.getLogTime()));
                                        filterMappers.add(filterMapper);
                                    }
                                    if(filterMappers==null|| filterMappers.size()==0)
                                        Helper.showCustomAlert(context, "No any result found");
                                    else
                                    {
                                        filterListAdapter = new FilterListAdapter(context,filterMappers);
                                        listView.setAdapter(filterListAdapter);
                                    }



                                } else
                                    Helper.showCustomAlert(context, context.getString(R.string.something_goes_wrong));
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
                param.put("sensorId",Defaults.sensorId);
                param.put("startDate", Defaults.startDate);
                param.put("endDate", Defaults.endDate);
                param.put("minValue", Defaults.minValue+"");
                param.put("maxValue", Defaults.maxValue+"");
                param.put("type", Defaults.selectedFilterType+"");
                param.put("page",1+"");

                Log.d("--->>?>?????",param.toString());
                return param;
            }
        };
        queue.add(postRequest);




    }
    private static String dateToHoursAndMin(String date){
        Log.d("------>><><>>>",date.substring(11,13));
        Log.d("------>><><>>>",date.substring(14,16));

        return (Integer.parseInt(date.substring(11,13))+4)%24+":"+date.substring(14,16);
    }

    private static String dateToYearMonthDay(String date){
        return date.substring(0,10);
    }

}
