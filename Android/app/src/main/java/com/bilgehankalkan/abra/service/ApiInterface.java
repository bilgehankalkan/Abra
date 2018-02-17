package com.bilgehankalkan.abra.service;

import com.bilgehankalkan.abra.service.models.CourierRequest;
import com.bilgehankalkan.abra.service.models.CourierResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public interface ApiInterface {

    String COURIER_URL = "/courier";

    @POST(COURIER_URL)
    Call<CourierResponse> postCourier(@HeaderMap Map<String, String> headerMap,
                                      @Body CourierRequest courierRequest);
}
