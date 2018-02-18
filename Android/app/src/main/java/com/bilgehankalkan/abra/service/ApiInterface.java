package com.bilgehankalkan.abra.service;

import com.bilgehankalkan.abra.service.models.CourierRequest;
import com.bilgehankalkan.abra.service.models.CourierResponse;
import com.bilgehankalkan.abra.service.models.LocationSearchResponse;
import com.bilgehankalkan.abra.service.models.OrderListResult;
import com.bilgehankalkan.abra.service.models.OrderResult;
import com.bilgehankalkan.abra.service.models.TokenRequest;

import java.util.Map;

import okhttp3.ResponseBody;
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
    String ORDER_URL = "/User/{userId}/{courier}/book/{mode}/{pageIndex}/{pageSize}";
    String NOTIFACTION_TOKEN_URL = "/{userId}/notification/token";

    String COURIER = "courier";
    String CARRY = "carry";
    String CURRENT = "current";
    String PAST = "past";

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
                                    @Path("courier") String courier,
                                    @Path("mode") String mode,
                                    @Path("pageIndex") int pageIndex,
                                    @Path("pageSize") int pageSize);

    @GET(COURIER_URL + "/{id}")
    Call<OrderResult> getOrderDetail(@HeaderMap Map<String, String> headerMap,
                                     @Path("id") String orderId);

    @POST(NOTIFACTION_TOKEN_URL)
    Call<ResponseBody> postFirebaseToken(@HeaderMap Map<String, String> headerMap,
                                         @Path("userId") String userId,
                                         @Body TokenRequest token);
}
