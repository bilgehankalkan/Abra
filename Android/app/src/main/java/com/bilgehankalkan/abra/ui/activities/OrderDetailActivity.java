package com.bilgehankalkan.abra.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.models.OrderResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilgehan on 18.02.2018.
 */

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        String id = getIntent().getStringExtra("id");

        getOrderDetails(id);
    }

    private void getOrderDetails(String id) {
        Call<OrderResult> call = apiInterface.getOrderDetail(getHeader(), id);
        call.enqueue(new Callback<OrderResult>() {
            @Override
            public void onResponse(@NonNull Call<OrderResult> call, @NonNull Response<OrderResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderResult orderResult = response.body();
                    if (orderResult.getCode() == 200)
                        showSuccess(R.string.successfully_retrieved);
                    else
                        showError(orderResult.getMsg());
                } else
                    showError(R.string.connection_error);
            }

            @Override
            public void onFailure(@NonNull Call<OrderResult> call, @NonNull Throwable t) {
                showError(R.string.connection_error);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
    }
}
