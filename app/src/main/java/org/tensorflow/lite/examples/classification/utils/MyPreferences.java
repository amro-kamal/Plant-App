package org.tensorflow.lite.examples.classification.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class MyPreferences  {


        private static final String MY_PREFERENCES = "my_preferences";
    public static final String MODEL_ID = "model_id";


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
