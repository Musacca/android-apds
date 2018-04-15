package com.example.musabir.apds.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class SensorIDEnterActivity extends AppCompatActivity {
    EditText sensor_id ;
    TextView here;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.insert_sensor_id);

        sensor_id = findViewById(R.id.sensor_id);
        here = findViewById(R.id.here);
        enter = findViewById(R.id.enter);
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SensorIDEnterActivity.this,EnterMsisdnActivity.class);
                intent.putExtra("status",0);//login
                startActivity(intent);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sensor_id.getText()!=null)
               checkSensorID(sensor_id.getText().toString().trim());
                else Helper.showCustomAlert(SensorIDEnterActivity.this,getString(R.string.please_enter_a_sensor_id));
            }
        });

    }

    private void checkSensorID(final String id) {
        final ProgressDialog pDialog = new ProgressDialog(SensorIDEnterActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_CHECK_SENSOR_BY_ID,
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
                                if ((obj.getJSONObject("payload").getBoolean("sensorExist"))) {
                                    Intent intent = new Intent(SensorIDEnterActivity.this, EnterMsisdnActivity.class);
                                    intent.putExtra("status", 1);//register
                                    intent.putExtra("sensorId", sensor_id.getText().toString().trim());
                                    startActivity(intent);
                                } else
                                    Helper.showCustomAlert(SensorIDEnterActivity.this, getString(R.string.wrong_sensor_id));

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
                param.put("sensorId",id);

                return param;
            }
        };
        queue.add(postRequest);


    }

}
