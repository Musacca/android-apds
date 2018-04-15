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
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnterMsisdnActivity extends AppCompatActivity {
    private int status =-1;
    private String sensorId;
    private AppCompatEditText msisdn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        status = getIntent().getIntExtra("status",-1);
        //status 1 = register; status 0= login
        if(status==1)
            sensorId = getIntent().getStringExtra("sensorId");
        else if(status==0)
            sensorId=null;
        setContentView(R.layout.sign_in);
        msisdn = findViewById(R.id.phone_number_edittext);

        Button next_btn_enternumber = findViewById(R.id.next_btn_enternumber);
        RelativeLayout top_layout = findViewById(R.id.top_layout);
        next_btn_enternumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(msisdn.getText().toString()!=null&&msisdn.getText().toString().trim().length()>5)
                  checkMsisdn();
            }
        });

        top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void checkMsisdn() {


        final ProgressDialog pDialog = new ProgressDialog(EnterMsisdnActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "";
        if(status==1)
            url = URLHeaders.URL_REGISTER_SEND_MSISDN;
        else url = URLHeaders.URL_LOGIN_SEND_MSISDN;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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

                                if(obj1) {
                                    Intent intent = new Intent(EnterMsisdnActivity.this, VerifyNumberActivity.class);
                                    if(status==1) {
                                        intent.putExtra("sensorId", sensorId);
                                        intent.putExtra("msisdn",msisdn.getText().toString().trim());
                                    }
                                    intent.putExtra("status",status);//register

                                    startActivity(intent);
                                }
                                else {
                                    JSONObject error = obj.getJSONObject("error");
                                        Helper.showCustomAlert(EnterMsisdnActivity.this,error.getString("message"));

                                }

                            } catch (JSONException e1) {
                            e1.printStackTrace();

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
                param.put("msisdn",msisdn.getText().toString().trim());
                return param;
            }
        };
        queue.add(postRequest);


    }

}
