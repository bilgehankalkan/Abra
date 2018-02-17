package com.bilgehankalkan.abra.service;

import com.bilgehankalkan.abra.service.models.CourierRequest;
import com.bilgehankalkan.abra.service.models.CourierResponse;
import com.bilgehankalkan.abra.service.models.LocationSearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public interface ApiInterface {

    String COURIER_URL = "/courier";
    String SEARCH_URL = "/location/search";

    @POST(COURIER_URL)
    Call<CourierResponse> postCourier(@HeaderMap Map<String, String> headerMap,
                                      @Body CourierRequest courierRequest);

    @GET(SEARCH_URL)
    Call<LocationSearchResponse> getLocationSearch(@HeaderMap Map<String, String> headerMap,
                                                   @Query("q") String query);
}
