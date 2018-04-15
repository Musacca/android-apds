package com.example.musabir.apds.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
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
import com.example.musabir.apds.Activities.RegisterationActivity;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by Musabir on 3/1/2018.
 */

public class FirstScreenFragment extends PreferenceFragment{

    View view;
    TextView co,co2,hidro,methane,smoke,lpg,temp,humidity;
    private Realm realm;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.first_screen_layout, container, false);

        view.findViewById(R.id.graph_lyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayView(new LineChartFragment(),"LineChartFragment");

            }
        });
        realm = Realm.getDefaultInstance();
        methane = view.findViewById(R.id.methane_value);
        lpg = view.findViewById(R.id.lpg_value);
        smoke = view.findViewById(R.id.smoke_value);
        co = view.findViewById(R.id.degree_ppm_carbon_monoxide_txt);
        hidro = view.findViewById(R.id.hdrogen_value);
        co2 = view.findViewById(R.id.degree_ppm_txt);
        temp = view.findViewById(R.id.degree_temperature_txt);
        humidity = view.findViewById(R.id.degree_humidity_txt);
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
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_GET_LAST_DATA,
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

                return param;
            }
        };
        queue.add(postRequest);



    }

}
