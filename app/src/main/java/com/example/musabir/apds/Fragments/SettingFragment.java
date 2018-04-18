package com.example.musabir.apds.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.musabir.apds.Activities.EnterMsisdnActivity;
import com.example.musabir.apds.Activities.SensorIDEnterActivity;
import com.example.musabir.apds.Dialog.ChangeAccountDialog;
import com.example.musabir.apds.Dialog.DeleteAccountDialog;
import com.example.musabir.apds.Dialog.LogoutDialog;
import com.example.musabir.apds.Dialog.PushNotificationDialog;
import com.example.musabir.apds.Dialog.WidgetDialog;
import com.example.musabir.apds.Activities.EditProfile;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.Mapper.WidgetListMapper;
import com.example.musabir.apds.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Musabir on 3/8/2018.
 */

public class SettingFragment extends PreferenceFragment {
    View view;
    TextView edit_profile,logout;
    RelativeLayout call_rly,notification_rly,widget_rly,delete_rly,accounts_rly;
    private Realm realm;
    private ImageView ic_add;
    private ArrayList<UserModel> userModels;
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.setting, container, false);
        realm = Realm.getDefaultInstance();
        edit_profile = view.findViewById(R.id.edit_profile);
        logout = view.findViewById(R.id.logout);
        call_rly = view.findViewById(R.id.call_rly);
        notification_rly = view.findViewById(R.id.notification_rly);
        widget_rly = view.findViewById(R.id.widget_rly);
        delete_rly = view.findViewById(R.id.delete_rly);
        accounts_rly = view.findViewById(R.id.accounts_rly);
        ic_add = view.findViewById(R.id.add);

        final ArrayList<WidgetListMapper> widgetListMappers = new ArrayList<>();
        userModels = new ArrayList(realm.where(UserModel.class).findAll());
        widgetListMappers.add(new WidgetListMapper("O2",false));
        widgetListMappers.add(new WidgetListMapper("Carbon Dioxid",false));
        widgetListMappers.add(new WidgetListMapper("Carbon Monoxid",false));
        widgetListMappers.add(new WidgetListMapper("Temperatur",false));
        widgetListMappers.add(new WidgetListMapper("Humidity",false));

        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SensorIDEnterActivity.class);
                intent.putExtra("back",true);
                startActivity(intent);
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog= new LogoutDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        delete_rly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog= new DeleteAccountDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });

//        widget_rly.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog= new WidgetDialog(getActivity(),widgetListMappers);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                dialog.show();
//            }
//        });
        accounts_rly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userModels.size()>1) {
                    Dialog dialog = new ChangeAccountDialog(getActivity(), userModels);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }
            }
        });
        notification_rly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userModels.size()>1) {
                    Dialog dialog = new PushNotificationDialog(getActivity());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }
            }
        });
        return view;
    }
}
