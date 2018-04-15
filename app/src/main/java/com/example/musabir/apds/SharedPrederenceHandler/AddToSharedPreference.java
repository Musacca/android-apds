package com.example.musabir.apds.SharedPrederenceHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Musabir on 12/11/2017.
 */

public class AddToSharedPreference {


    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setDefaults(String key, int value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
