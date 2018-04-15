package com.example.musabir.apds.SharedPrederenceHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Musabir on 12/11/2017.
 */

public class GetFromSharedPreference {


    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static int getDefaultsAsInt(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }
}
