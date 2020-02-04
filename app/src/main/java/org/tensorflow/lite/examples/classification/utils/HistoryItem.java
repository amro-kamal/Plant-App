package org.tensorflow.lite.examples.classification.utils;

import android.graphics.Bitmap;

public class HistoryItem {
    private String title;
    private String date;
    private String plantImgUrl;

    public HistoryItem(){

    }
    public HistoryItem(String t , String d , String url){
        title = t;
        date = d;
        plantImgUrl = url;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getPlantImgUrl() {
        return plantImgUrl;
    }

    public void setPlantImgUrl(String plantImgUrl) {
        this.plantImgUrl = plantImgUrl;
    }
}
