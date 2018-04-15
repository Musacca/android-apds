package com.example.musabir.apds.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class EditProfile extends AppCompatActivity {

    private static final int PLACE_PICKER = 1;

    TextView back,phone_number_txt,name_txt,location;
    EditText name,place_name,number2,number3;
    ImageView submit;
    private String token;
    private Realm realm;
    private String sLat,sLng;
    private String address;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.edit_profile_layout);
        phone_number_txt = findViewById(R.id.phone_number_txt);
        name_txt = findViewById(R.id.name_txt);
        name = findViewById(R.id.name);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        location = findViewById(R.id.location);
        place_name = findViewById(R.id.place_name);

        back = findViewById(R.id.back);
        submit = findViewById(R.id.submit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        realm.beginTransaction();
        userModel = realm.where(UserModel.class).equalTo("isActive",true).findFirst();
        realm.commitTransaction();
        token = userModel.getUuid();
        phone_number_txt.setText(userModel.getMsisdn());
        name.setText(userModel.getName());
        name_txt.setText(userModel.getName());
        number2.setText(userModel.getPhoneNumber1());
        number3.setText(userModel.getPhoneNumber2());
        location.setText(userModel.getLocationName());
        address = userModel.getLocationName();
        List<String> ss = Arrays.asList(userModel.getLocation().split(","));
        if(ss!=null) {
            sLat = ss.get(0);
            sLng = ss.get(1);
        }
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;

                try {
                    intent = builder.build(EditProfile.this);

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, EditProfile.this);

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



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText()!=null && place_name.getText()!=null && number2.getText()!=null
                        && number3.getText()!=null && location.getText()!=null){
                    editUser();
                }
            }
        });


    }

    private void editUser(){

        final ProgressDialog pDialog = new ProgressDialog(EditProfile.this);
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
                            JSONObject obj1 = obj.getJSONObject("success");
                            JSONArray objArray = null;
                            code = obj1.getInt("code");
                            if(code == APIResponseCodes.OK)
                            {
                                boolean status  = obj1.getBoolean("status");
                                if(status) {
                                    Helper.showCustomAlert(EditProfile.this,getString(R.string.successfully_changed));
                                    realm.beginTransaction();
                                    userModel.setName(name.getText().toString().trim());
                                    userModel.setPhoneNumber1(number2.getText().toString().trim());
                                    userModel.setPhoneNumber2(number3.getText().toString().trim());
                                    userModel.setLocationName(location.getText().toString().trim());
                                    userModel.setLocation(sLat+","+sLng);
                                    realm.copyToRealmOrUpdate(userModel);

                                    realm.commitTransaction();

                                }
                                else
                                    Helper.showCustomAlert(EditProfile.this,getString(R.string.wrong_sensor_id));
                            }
                            else Helper.showCustomAlert(EditProfile.this,getString(R.string.something_goes_wrong));



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
                param.put("fullName",name.getText().toString().trim());
                param.put("placeName",place_name.getText().toString().trim());
                param.put("phoneNumber1",number2.getText().toString().trim());
                param.put("phoneNumber2",number3.getText().toString().trim());
                param.put("locationName",address);
                param.put("location",sLat+","+sLng);
                param.put("uuid",token);

                return param;
            }
        };
        queue.add(postRequest);


    }

}
