package com.example.musabir.apds.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Dialog.DeleteAccountDialog;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;
import com.example.musabir.apds.SharedPrederenceHandler.GetFromSharedPreference;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterationActivity extends AppCompatActivity {

    private static final int PLACE_PICKER = 1;
    private EditText name, place_name, number2, number3;
    private TextView location;
    private String token;
    private ImageView delete;
    private String sLat;
    private String address;
    private String sLng;
    private Realm realm;
    private Gson gson;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.registeration_layout);
        TextView textView = findViewById(R.id.here);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        name = findViewById(R.id.name);
        place_name = findViewById(R.id.place_name);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        location = findViewById(R.id.location);

        gson = new Gson();
        realm = Realm.getDefaultInstance();
        uuid = getIntent().getStringExtra("uuid");


        token = GetFromSharedPreference.getDefaults("token", RegisterationActivity.this);
        if (name.getText().toString().trim().length() > 0 && place_name.getText().toString().trim().length() > 0
                && number2.getText().toString().trim().length() > 0 && number3.getText().toString().trim().length() > 0
                && location.getText().toString().trim().length() > 0) {
            registerUser();

        }
        RelativeLayout top_layout = findViewById(R.id.top_layout);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterationActivity.this, EnterMsisdnActivity.class);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterationActivity.this, VerifyNumberActivity.class);
                startActivity(intent);
            }
        });
        top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;

                try {
                    intent = builder.build(RegisterationActivity.this);

                    startActivityForResult(intent, PLACE_PICKER);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getIntent().hasExtra("coming"))
        {
            Intent intent = new Intent(RegisterationActivity.this,SensorIDEnterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);

        }
        else
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, RegisterationActivity.this);

                String lnglat = place.getLatLng().toString();
                String[] latLng = lnglat.substring(10, lnglat.length() - 1).split(",");
                sLat = latLng[0];
                sLng = latLng[1];
                if (place != null) {
                    address = (String) place.getAddress();
                    location.setText(address);


                }
            }
        }

    }

    private void registerUser() {


        final ProgressDialog pDialog = new ProgressDialog(RegisterationActivity.this);
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
        pDialog.setContentView(R.layout.custom_progress_dialog);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_REGISTER,
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

                                    meCurrentSensor();
                                } else
                                    Helper.showCustomAlert(RegisterationActivity.this, getString(R.string.something_goes_wrong));
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
                param.put("fullName", name.getText().toString().trim());
                param.put("placeName", place_name.getText().toString().trim());
                param.put("phoneNumber1", number2.getText().toString().trim());
                param.put("phoneNumber2", number3.getText().toString().trim());
                param.put("locationName", location.getText().toString().trim());
                param.put("location", location.getText().toString().trim());
                param.put("uuid", token);

                return param;
            }
        };
        queue.add(postRequest);


    }


    private void meCurrentSensor() {

        final ProgressDialog pDialog = new ProgressDialog(RegisterationActivity.this);
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
                            success = obj.getBoolean("success");
                            if (success) {
                                JSONObject obj1 = obj.getJSONObject("sensor");
                                realm.beginTransaction();
                                UserModel userModel = gson.fromJson(obj1.toString(), UserModel.class);
                                userModel.setUuid(uuid);


                                RealmResults<UserModel> realmResults = realm.where(UserModel.class).findAll();
                                if (realmResults != null)
                                    for (int i = 0; i < realmResults.size(); i++) {
                                        realmResults.get(i).setActive(false);
                                        realm.copyToRealmOrUpdate(realmResults.get(i));
                                    }
                                userModel.setActive(true);
                                Intent intent = new Intent(RegisterationActivity.this, Main2Activity.class);
                                Defaults.msisdn = userModel.getMsisdn();
                                Defaults.sensorId = userModel.getSensorId();
                                Defaults.uuid = userModel.getUuid();
                                startActivity(intent);

                                realm.copyToRealmOrUpdate(userModel);
                                realm.commitTransaction();

                            } else {
                                JSONObject obj1 = obj.getJSONObject("error");
                                Helper.showCustomAlert(RegisterationActivity.this, getString(R.string.something_goes_wrong));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("Error.Response", error.getMessage() + " message");
                if (pDialog != null)
                    pDialog.dismiss();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("uuid", getIntent().getStringExtra("uuid"));
                param.put("sensorId", getIntent().getStringExtra("sensorId"));
                return param;
            }
        };
        queue.add(postRequest);


    }


}
