package com.example.musabir.apds.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
                dismiss();
            }
        });


    }

    private void logout(){
        realm.beginTransaction();
        UserModel userModels = realm.where(UserModel.class).equalTo("uuid", Defaults.uuid)
                .equalTo("sensorId",Defaults.sensorId).findFirst();
        userModels.deleteFromRealm();

        RealmResults<UserModel> s  = realm.where(UserModel.class).findAll();
        if(s==null) {
            Intent intent = new Intent(getContext(), SensorIDEnterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Defaults.uuid=null;
            Defaults.sensorId = null;
            Defaults.msisdn = null;
            getContext().startActivity(intent);
        }
        else {
            for(int i=0;i<s.size();i++){
                if(s.get(i).getName()!=null){
                    s.get(i).setActive(true);
                    Defaults.uuid=s.get(i).getUuid();
                    Defaults.sensorId = s.get(i).getSensorId();
                    Defaults.msisdn = s.get(i).getMsisdn();
                    Intent intent = new Intent(getContext(), Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                    realm.copyToRealmOrUpdate(s.get(i));
                    break;
                }
                else if (i==s.size()-1){
                    Intent intent = new Intent(getContext(), RegisterationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("uuid",s.get(i).getUuid());
                    intent.putExtra("msisdn",s.get(i).getMsisdn());
                    intent.putExtra("sensorId",s.get(i).getSensorId());
                    intent.putExtra("coming",1);
                    Defaults.uuid=null;
                    Defaults.sensorId = null;
                    Defaults.msisdn = null;
                    getContext().startActivity(intent);
                }
            }
        }
        realm.commitTransaction();
    }
}
