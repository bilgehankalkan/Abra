package com.bilgehankalkan.abra.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bilgehan on 18.02.2018.
 */

public class OrderListResult extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<Order> orderList = new ArrayList<>();

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
