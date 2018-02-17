package com.bilgehankalkan.abra.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class LocationSearchResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<Location> data = null;

    public List<Location> getData() {
        return data;
    }

    public void setData(List<Location> data) {
        this.data = data;
    }
}
