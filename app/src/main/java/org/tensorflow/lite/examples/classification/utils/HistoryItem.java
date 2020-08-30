package org.tensorflow.lite.examples.classification.utils;

import android.graphics.Bitmap;

public class HistoryItem {
    private String title;
    private String date;
    private String plantImgUrl;
    private String diseaseId;
    private String confidence;
    private String imgName;
private Bitmap historyImage;
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

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Bitmap getHistoryImage() {
        return historyImage;
    }

    public void setHisoryImage(Bitmap historyImage) {
        this.historyImage = historyImage;
    }
}
