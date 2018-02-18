package com.bilgehankalkan.abra.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CourierRequest {

    @SerializedName("ownerId")
    @Expose
    private String ownerId;
    @SerializedName("originDate")
    @Expose
    private String originDate;
    @SerializedName("destinationDate")
    @Expose
    private String destinationDate;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("instantBooking")
    @Expose
    private Boolean instantBooking;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOriginDate() {
        return originDate;
    }

    public void setOriginDate(String originDate) {
        this.originDate = originDate;
    }

    public String getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(String destinationDate) {
        this.destinationDate = destinationDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getInstantBooking() {
        return instantBooking;
    }

    public void setInstantBooking(Boolean instantBooking) {
        this.instantBooking = instantBooking;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
