package com.example.musabir.apds.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class VerifyNumberActivity extends AppCompatActivity {
    private AppCompatEditText verification_code_edittext;
    int status;
    String number;
    private Gson gson;
    private String uuid;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        realm = Realm.getDefaultInstance();
        gson = new Gson();
        setContentView(R.layout.verify_number);
        verification_code_edittext = findViewById(R.id.verification_code_edittext);
        RelativeLayout top_layout = findViewById(R.id.top_layout);
        Button next_btn_verification = findViewById(R.id.next_btn_verification);
        status = getIntent().getIntExtra("status",-1);
        top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next_btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verification_code_edittext.getText()!=null) {
                    checkOTP();

                }
            }
        });


    }

    private void checkOTP() {

        final ProgressDialog pDialog = new ProgressDialog(VerifyNumberActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="";
        if(status==1)
            url = URLHeaders.URL_REGISTER_VERIFY_SMS;
        else url = URLHeaders.URL_LOGIN_VERIFY_SMS;
        Log.d("-------<><>>><>>","  saas"+ url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        pDialog.dismiss();
                        String appData = null;
                        boolean success = false;

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray objArray = null;
                            success = obj.getBoolean("success");
                            if(success) {
                                JSONObject obj1 = obj.getJSONObject("payload");
                                realm.beginTransaction();
                                UserModel userModel = gson.fromJson(obj1.toString(), UserModel.class);
                                userModel.setMsisdn(getIntent().getStringExtra("msisdn"));
                                uuid = obj1.getString("uuid");
                                userModel.setUuid(uuid);
                                realm.copyToRealmOrUpdate(userModel);
                                realm.commitTransaction();
                                if(status==1)
                                    meCurrentSensor();
                                else meGeneral();
                            }
                            else {
                                JSONObject obj1 = obj.getJSONObject("error");
                                Helper.showCustomAlert(VerifyNumberActivity.this,obj1.getString("message"));
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
                param.put("msisdn",getIntent().getStringExtra("msisdn"));
                param.put("otp",verification_code_edittext.getText().toString().trim());
                if(status==1)
                    param.put("sensorId",getIntent().getStringExtra("sensorId"));
                return param;
            }
        };
        queue.add(postRequest);


    }


    private void meCurrentSensor() {

        final ProgressDialog pDialog = new ProgressDialog(VerifyNumberActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_ME_CURRENT_SENSOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        pDialog.dismiss();
                        String appData = null;
                        boolean success = false;

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray objArray = null;
                            success = obj.getBoolean("success");
                            if(success) {
                                JSONObject obj1 = obj.getJSONObject("sensor");
                                realm.beginTransaction();
                                UserModel userModel = gson.fromJson(obj1.toString(), UserModel.class);
                                userModel.setUuid(uuid);

                                if(userModel.getName()==null)
                                {
                                    Intent intent = new Intent(VerifyNumberActivity.this, RegisterationActivity.class);
                                    intent.putExtra("uuid",uuid);
                                    intent.putExtra("msisdn",getIntent().getStringExtra("msisdn"));
                                    startActivity(intent);
                                }
                                else {
                                    RealmResults<UserModel> realmResults = realm.where(UserModel.class).findAll();
                                    if(realmResults!=null)
                                        for(int i=0;i<realmResults.size();i++)
                                        {
                                            realmResults.get(i).setActive(false);
                                            realm.copyToRealmOrUpdate(realmResults.get(i));
                                        }
                                    userModel.setActive(true);
                                    Intent intent = new Intent(VerifyNumberActivity.this, Main2Activity.class);
                                    Defaults.msisdn = userModel.getMsisdn();
                                    Defaults.sensorId = userModel.getSensorId();
                                    Defaults.uuid = userModel.getUuid();
                                    startActivity(intent);
                                }
                                realm.copyToRealmOrUpdate(userModel);
                                realm.commitTransaction();

                            }
                            else {
                                JSONObject obj1 = obj.getJSONObject("error");
                                Helper.showCustomAlert(VerifyNumberActivity.this,getString(R.string.something_goes_wrong));
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
                param.put("uuid",uuid);
                param.put("sensorId",getIntent().getStringExtra("sensorId"));
                return param;
            }
        };
        queue.add(postRequest);


    }

    private void meGeneral() {

        final ProgressDialog pDialog = new ProgressDialog(VerifyNumberActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_ME_GENERAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        pDialog.dismiss();
                        String appData = null;
                        boolean success = false;

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray objArray = null;
                            success = obj.getBoolean("success");
                            if(success) {
                                int count = 0;
                                JSONArray obj1 = obj.getJSONArray("sensors");
                                realm.beginTransaction();
                                if(obj1.length()>0)
                                for (int i = 0; i < obj1.length(); i++) {
                                    UserModel userModel = gson.fromJson(obj1.get(i).toString(), UserModel.class);
                                    userModel.setUuid(uuid);

                                    if (userModel.getName() == null)
                                        count++;

                                    if (count == obj1.length()) {
                                        Intent intent = new Intent(VerifyNumberActivity.this, RegisterationActivity.class);
                                        intent.putExtra("uuid", uuid);
                                        intent.putExtra("msisdn", userModel.getMsisdn());
                                        startActivity(intent);
                                    } else if (i == obj1.length() - 1) {

                                        RealmResults<UserModel> realmResults = realm.where(UserModel.class).findAll();
                                        if (realmResults != null)
                                            for (int k = 0; k < realmResults.size(); k++) {
                                                realmResults.get(k).setActive(false);
                                                realm.copyToRealmOrUpdate(realmResults.get(k));
                                            }
                                        userModel.setActive(true);
                                        Intent intent = new Intent(VerifyNumberActivity.this, Main2Activity.class);
                                        Defaults.msisdn = userModel.getMsisdn();
                                        Defaults.sensorId = userModel.getSensorId();
                                        Defaults.uuid = userModel.getUuid();
                                        startActivity(intent);
                                    }
                                    realm.copyToRealmOrUpdate(userModel);
                                    realm.commitTransaction();

                                }
                                else Helper.showCustomAlert(VerifyNumberActivity.this,getString(R.string.no_any_sensor_found));
                            }
                            else{
                                    JSONObject obj1 = obj.getJSONObject("error");
                                    Helper.showCustomAlert(VerifyNumberActivity.this, getString(R.string.something_goes_wrong));
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
                param.put("uuid",uuid);

                return param;
            }
        };
        queue.add(postRequest);


    }

}
