package org.tensorflow.lite.examples.classification;

import android.content.Context;

public class ModelSingleton {
    private static ModelSingleton mSelectedModelInstance;
    private static Context mContext;
    private static String sModel = "";
    private static boolean isOnline = true;
    public static final String TOMATO_MODEL = "TOMATO";
    public static final String GRAPE_MODEL = "GRAPE";
    public static final String POTATO_MODEL = "POTATO";


    private ModelSingleton(Context context){
        mContext = context;
    }
    public static ModelSingleton getInstance(Context context){
        if(mSelectedModelInstance==null){
            mSelectedModelInstance = new ModelSingleton(context);
        }
        return mSelectedModelInstance;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public  void setIsOnline(boolean online) {
        isOnline = online;
    }

    public void setCurrentModel(String model){
        sModel = model;
    }
    public String getCurrentModel(){
        return  sModel;
    }
}
