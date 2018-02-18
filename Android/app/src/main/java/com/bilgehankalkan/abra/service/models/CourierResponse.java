package com.bilgehankalkan.abra.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CourierResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private CourierRequest data;

    public CourierRequest getData() {
        return data;
    }

    public void setData(CourierRequest data) {
        this.data = data;
    }
}
