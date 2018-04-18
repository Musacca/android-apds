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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.musabir.apds.Activities.EnterMsisdnActivity;
import com.example.musabir.apds.Activities.Main2Activity;
import com.example.musabir.apds.Activities.RegisterationActivity;
import com.example.musabir.apds.Activities.SensorIDEnterActivity;
import com.example.musabir.apds.Defaults.APIResponseCodes;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Defaults.URLHeaders;
import com.example.musabir.apds.Helper.Helper;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Musabir on 3/8/2018.
 */

public class DeleteAccountDialog extends Dialog {
    public DeleteAccountDialog(@NonNull Context context) {
        super(context);
    }
    private Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account_dialog);
        realm = Realm.getDefaultInstance();
        findViewById(R.id.no_txt_lyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        findViewById(R.id.yes_txt_lyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });




    }


    private void deleteAccount() {
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setIndeterminate(true);
        pDialog.show();
        pDialog.setCancelable(false);
        pDialog.setContentView(R.layout.custom_progress_dialog);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLHeaders.URL_CHANGE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        dismiss();
                        pDialog.dismiss();
                        String appData = null;
                        int code = 0;

                        try {
                            JSONObject obj = new JSONObject(response);
                            boolean obj1 = obj.getBoolean("success");
                            if(obj1) {
                                if ((obj.getJSONObject("payload").getInt("code")== APIResponseCodes.OK)) {
                                    realm.beginTransaction();
                                    UserModel userModels = realm.where(UserModel.class).equalTo("uuid",Defaults.uuid)
                                            .equalTo("sensorId",Defaults.sensorId).findFirst();
                                    userModels.deleteFromRealm();

                                    Log.d("-------><>>>><>",realm.where(UserModel.class).findAll().size()+" size");
                                    UserModel s = realm.where(UserModel.class).findFirst();
                                    if (s == null) {
                                        Intent intent = new Intent(getContext(), SensorIDEnterActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        Defaults.uuid = null;
                                        Defaults.sensorId = null;
                                        Defaults.msisdn = null;
                                        getContext().startActivity(intent);
                                    } else {

                                        s.setActive(true);
                                        Defaults.uuid = s.getUuid();
                                        Defaults.sensorId = s.getSensorId();
                                        Defaults.msisdn = s.getMsisdn();
                                        Intent intent = new Intent(getContext(), Main2Activity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        getContext().startActivity(intent);
                                        realm.copyToRealmOrUpdate(s);
                                    }
                                    realm.commitTransaction();

                                } else
                                    Helper.showCustomAlert(getContext(), getContext().getString(R.string.something_goes_wrong));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismiss();
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
                param.put("status", -5+"");

                return param;
            }
        };
        queue.add(postRequest);


    }

}
