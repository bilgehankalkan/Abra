package com.bilgehankalkan.abra.interfaces;

import com.bilgehankalkan.abra.service.models.Location;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public interface OnSearchOptionListener {

    void onOriginSelected(Location originLocation);
    void onOriginDateSelected(String date);
    void onOriginTimeSelected(String time);
    void onDestinationSelected(Location destinationLocation);
}
