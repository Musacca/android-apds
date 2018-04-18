package com.example.musabir.apds.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.musabir.apds.Adapter.ChangeAccountAdapter;
import com.example.musabir.apds.Adapter.PushNotificationAdapter;
import com.example.musabir.apds.Mapper.PushNotificationMapper;
import com.example.musabir.apds.Mapper.UserModel;
import com.example.musabir.apds.R;

import java.util.ArrayList;

/**
 * Created by Musabir on 4/12/2018.
 */

public class ChangeAccountDialog extends Dialog {
    ListView list;
    private ChangeAccountAdapter changeAccountAdapter;
    private static ArrayList<UserModel> userModels;
    public ChangeAccountDialog(@NonNull Context context, ArrayList<UserModel> userModels) {
        super(context);
        this.userModels = userModels;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_account);

        list = findViewById(R.id.list);
        changeAccountAdapter = new ChangeAccountAdapter(getContext(), userModels);
        list.setAdapter(changeAccountAdapter);



    }
}


