package com.bilgehankalkan.abra.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            String SERVICE_URL = "http://getir-hackathon.exlinetr.com";
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
