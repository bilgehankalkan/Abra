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
    @SerializedName("luggageWeight")
    @Expose
    private Integer luggageWeight;
    @SerializedName("price")
    @Expose
    private Integer price;

    /**
     * No args constructor for use in serialization
     *
     */
    public CourierRequest() {
    }

    /**
     *
     * @param luggageWeight
     * @param instantBooking
     * @param destinationDate
     * @param price
     * @param ownerId
     * @param origin
     * @param note
     * @param destination
     * @param originDate
     */
    public CourierRequest(String ownerId, String originDate, String destinationDate, String note, Boolean instantBooking, String destination, String origin, Integer luggageWeight, Integer price) {
        super();
        this.ownerId = ownerId;
        this.originDate = originDate;
        this.destinationDate = destinationDate;
        this.note = note;
        this.instantBooking = instantBooking;
        this.destination = destination;
        this.origin = origin;
        this.luggageWeight = luggageWeight;
        this.price = price;
    }

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

    public Integer getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(Integer luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
