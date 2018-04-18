package com.example.musabir.apds.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Activities.Main2Activity;
import com.example.musabir.apds.Activities.RegisterationActivity;
import com.example.musabir.apds.Activities.VerifyNumberActivity;
import com.example.musabir.apds.Adapter.FilterListAdapter;
import com.example.musabir.apds.Adapter.PushNotificationAdapter;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Helper.TypeToNameConverter;
import com.example.musabir.apds.Mapper.DaysAllLogMapper;
import com.example.musabir.apds.Mapper.FilterMapper;
import com.example.musabir.apds.Mapper.PushNotificationMapper;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Musabir on 4/12/2018.
 */

public class PushNotificationDialog extends Dialog {
    RelativeLayout cancel, confirm,lyt;
    ListView list;
    Realm realm;
    Gson gson;
    static Context contextt;
    private PushNotificationAdapter pushNotificationAdapter;
    public static ArrayList<PushNotificationMapper> pushNotificationMappers;
   // private  ArrayList<PushNotificationMapper> pushNotificationMappersCopy;
    public PushNotificationDialog(@NonNull Context context) {
        super(context);
        contextt = context;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_push_notification);
        gson = new Gson();
        pushNotificationMappers = new ArrayList<>();
       // pushNotificationMappersCopy = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        cancel = findViewById(R.id.cancel);
        confirm = findViewById(R.id.confirm);
        list = findViewById(R.id.list);
        pushNotificationAdapter = new PushNotificationAdapter(getContext(),pushNotificationMappers);
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
                for(int i=0;i<pushNotificationMappers.size();i++) {

                    PushNotificationMapper p = pushNotificationMappers.get(i);
                    PushNotificationMapper p1 = pushNotificationMappers.get(i);
                    if(i==0)
                    Log.d("------><><>>>>",p.toString()+"                 "+p1.toString());

                    if(p.getNotifyStatus()!=p1.getNotifyStatus()||p.getMaxValueForNotify()!=p1.getMaxValueForNotify()
                            ||p.getMinValueForNotify()!=p1.getMinValueForNotify())
                        updateValues(i);
                    else Log.d("------><><>>>>","sameeee");

                }
                dismiss();
            }
        });
        meCurrentSensor();

    }
    private void meCurrentSensor() {

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_ME_CURRENT_SENSOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("sssssssss", response);

                        pDialog.dismiss();
                        String appData = null;
                        boolean success = false;

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray objArray = null;
                            success = obj.getBoolean("success");
                            if(success) {
                                JSONObject payload = obj.getJSONObject("payload");

                                JSONObject obj1 = payload.getJSONObject("sensor");
                                JSONArray t =  obj1.getJSONArray("typeList");
                                pushNotificationMappers.clear();
                                pushNotificationMappers.clear();
                                for(int i=0;i<t.length();i++){
                                    PushNotificationMapper pushNotificationMapper = gson.fromJson(t.get(i).toString(), PushNotificationMapper.class);
                                    pushNotificationMappers.add(pushNotificationMapper);
                                }
                               if(pushNotificationMappers.size()>0){
                                   pushNotificationAdapter = new PushNotificationAdapter(getContext(),pushNotificationMappers);
                                   list.setAdapter(pushNotificationAdapter);
                                   pushNotificationMappers.addAll(pushNotificationMappers);


                               }
                            }
                            else {
                                JSONObject obj1 = obj.getJSONObject("error");
                                Helper.showCustomAlert(getContext(),getContext().getString(R.string.something_goes_wrong));
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
                param.put("uuid",Defaults.uuid);
                param.put("sensorId",Defaults.sensorId);
                return param;
            }
        };
        queue.add(postRequest);




    }



    private void updateValues(final int l){

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(contextt);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_UPDATE_NOTIFY,
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
                param.put("minValue", pushNotificationMappers.get(l).getMinValueForNotify()+"");
                param.put("maxValue", pushNotificationMappers.get(l).getMaxValueForNotify()+"");
                param.put("status", pushNotificationMappers.get(l).getNotifyStatus()+"");
                param.put("type", pushNotificationMappers.get(l).getType()+"");

                return param;
            }
        };
        queue.add(postRequest);




    }
}


