package com.bilgehankalkan.abra.service;

import com.bilgehankalkan.abra.service.models.CourierRequest;
import com.bilgehankalkan.abra.service.models.CourierResponse;
import com.bilgehankalkan.abra.service.models.LocationSearchResponse;
import com.bilgehankalkan.abra.service.models.OrderListResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public interface ApiInterface {

    String COURIER_URL = "/courier";
    String LOCATION_SEARCH_URL = "/location/search";
    String SEARCH_URL = COURIER_URL + "/Search/{pageIndex}/{pageSize}";
    String ORDER_URL = "/User/{userId}/courier/book/{mode}/{pageIndex}/{pageSize}";

    @POST(COURIER_URL)
    Call<CourierResponse> postCourier(@HeaderMap Map<String, String> headerMap,
                                      @Body CourierRequest courierRequest);

    @GET(LOCATION_SEARCH_URL)
    Call<LocationSearchResponse> getLocationSearch(@HeaderMap Map<String, String> headerMap,
                                                   @Query("q") String query);

    @GET(SEARCH_URL)
    Call<OrderListResult> getSearchResult(@Path("pageIndex") int pageIndex,
                                          @Path("pageSize") int pageSize,
                                          @QueryMap Map<String, String> mapQuery);

    @GET(ORDER_URL)
    Call<OrderListResult> getOrders(@Path("userId") String userId,
                                    @Path("mode") String mode,
                                    @Path("pageIndex") int pageIndex,
                                    @Path("pageSize") int pageSize);
}
