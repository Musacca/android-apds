package com.example.musabir.apds.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.musabir.apds.Activities.Main2Activity;
import com.example.musabir.apds.Activities.RegisterationActivity;
import com.example.musabir.apds.Activities.SensorIDEnterActivity;
import com.example.musabir.apds.Defaults.Defaults;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Musabir on 3/8/2018.
 */

public class LogoutDialog extends Dialog {
    private Realm realm;

    public LogoutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_dialog);
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
                logout();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        realm.beginTransaction();
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
                    }
            }, 300);

            dismiss();
            }
        });


    }

    private void logout() {
        realm.beginTransaction();
        Log.d("-------><>>>><>",realm.where(UserModel.class).findAll().size()+" size");

        UserModel userModels = realm.where(UserModel.class).equalTo("uuid", Defaults.uuid)
                .equalTo("sensorId", Defaults.sensorId).findFirst();
        userModels.deleteFromRealm();
        realm.commitTransaction();

    }
}
