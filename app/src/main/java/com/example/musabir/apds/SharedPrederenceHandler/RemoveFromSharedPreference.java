package com.example.musabir.apds.SharedPrederenceHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



/**
 * Created by Musabir on 12/11/2017.
 */

public class RemoveFromSharedPreference {

    public static void removeDefaults(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(key);
        editor.commit();

    }
    public static void removeDefaultsFromNonMainActivity(String key, Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(key);
        editor.commit();

    }
}
