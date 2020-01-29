package org.tensorflow.lite.examples.classification.utils;

import android.graphics.Bitmap;

public class HistoryItem {
    private String title;
    private String date;
    private Bitmap plantImg;

    public HistoryItem(){

    }
    public HistoryItem(String t , String d , Bitmap img){
        title = t;
        date = d;
        plantImg = img;
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

    public Bitmap getPlantImg() {
        return plantImg;
    }

    public void setPlantImg(Bitmap plantImg) {
        this.plantImg = plantImg;
    }
}
