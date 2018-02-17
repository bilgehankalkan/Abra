package com.bilgehankalkan.abra.interfaces;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public interface OnOfferChosenListener {

    void onOriginSelected(String origin);
    void onDestinationSelected(String destination);
    void onDateTimeSelected(String dateTime);
    void onLoadCapacitySelected(int capacity);
    void onInstantBookingSelected(boolean isInstantBookingSelected);
}
