package com.bilgehankalkan.abra.interfaces;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public interface OnOfferChosenListener {

    void onOriginSelected(String originId);
    void onOriginDateSelected(String date);
    void onOriginTimeSelected(String time);
    void onDestinationSelected(String destinationId);
    void onDestinationDateSelected(String date);
    void onDestinationTimeSelected(String time);
    void onLoadCapacitySelected(int capacity);
    void onInstantBookingSelected(boolean isInstantBookingSelected);
    void onPriceSelected(int price);
    void onNotesAdded(String notes);
}
