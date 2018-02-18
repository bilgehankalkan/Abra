package com.bilgehankalkan.abra.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bilgehan on 18.02.2018.
 */

public class Origin {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
