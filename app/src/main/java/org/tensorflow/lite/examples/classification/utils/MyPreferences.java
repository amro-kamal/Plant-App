package org.tensorflow.lite.examples.classification.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class MyPreferences  {


        public static final String MY_PREFERENCES = "com.example.android.plantapp";
        public static final String MODEL_ID = "model_id";

    private static final String IS_ONLINE = "IS_ONLINE";
    public static  String USER_EMAIL = "";


    public static boolean getModelOpMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(IS_ONLINE, true);
    }
    public static void setModelOpMode(Context context,boolean is_online) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(IS_ONLINE, is_online)
                .apply();
    }

    public static int getModelType(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(MODEL_ID, 0);
    }
    public static void setModelType(Context context ,int index){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(MyPreferences.MODEL_ID, index).apply(); // Storing model_id
    }

    public static boolean isFirstSelection(Context context){
            final SharedPreferences Prefreader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final boolean first = Prefreader.getBoolean("is_first_selection", true);
            if(first){
                final SharedPreferences.Editor editor = Prefreader.edit();
                editor.putBoolean("is_first_selection", false);
                editor.apply();
            }
            return first;
        }

    public static void setUserEmail(Context context ,String user_email){
        final SharedPreferences Prefreader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = Prefreader.edit();
            editor.putString("user_email", user_email);
            editor.apply();
    }
    public static String getUserEmail(Context context ){
        final SharedPreferences Prefreader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String user_email=Prefreader.getString("user_email",MyPreferences.USER_EMAIL);
        return user_email;
    }

    public static void setIsLoggedIn(Context context ,Boolean is_logged_in){
        final SharedPreferences Prefreader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = Prefreader.edit();
        editor.putBoolean("is_logged_in", is_logged_in);
        editor.apply();
    }
    public static Boolean getIsLoggedIn(Context context ){
        final SharedPreferences Prefreader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        Boolean is_logged_in=Prefreader.getBoolean("is_logged_in",false);
        return is_logged_in;
    }

    }
