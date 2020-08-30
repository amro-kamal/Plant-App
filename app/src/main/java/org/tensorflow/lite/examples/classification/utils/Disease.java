package org.tensorflow.lite.examples.classification.utils;

public class Disease {
    private String id;
    private String title;
    private String category;
    private String hosts;
    private String summary;
    private String symptoms;
    private String treatment;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }
    public String getImageUrl2() {
        return imageUrl2;
    }
    public String getImageUrl3() {
        return imageUrl3;
    }


    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }
    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }
    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

}
