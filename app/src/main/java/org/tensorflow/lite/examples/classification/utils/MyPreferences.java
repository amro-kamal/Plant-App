package org.tensorflow.lite.examples.classification.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class MyPreferences  {


        private static final String MY_PREFERENCES = "my_preferences";
        public static final String MODEL_ID = "model_id";

    private static final String IS_ONLINE = "IS_ONLINE";

    public static String getModelOpMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(IS_ONLINE, null);
    }
    public static void setModelOpMode(Context context,String is_online) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(IS_ONLINE, is_online)
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
