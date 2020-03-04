package org.tensorflow.lite.examples.classification.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class MyPreferences  {


        public static final String MY_PREFERENCES = "my_preferences";
        public static final String MODEL_ID = "model_id";

    private static final String IS_ONLINE = "IS_ONLINE";



    public static boolean getModelOpMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(IS_ONLINE, false);
    }
    public static void setModelOpMode(Context context,boolean is_online) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(IS_ONLINE, is_online)
                .apply();
    }


    public static boolean isFirstSelection(Context context){
            final SharedPreferences Prefreader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final boolean first = Prefreader.getBoolean("is_first_selection", true);
            if(first){
                final SharedPreferences.Editor editor = Prefreader.edit();
                editor.putBoolean("is_first_selection", false);
                editor.commit();
            }
            return first;
        }

    }
