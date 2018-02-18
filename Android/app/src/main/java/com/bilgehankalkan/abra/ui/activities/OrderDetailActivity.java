package com.bilgehankalkan.abra.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.models.OrderResult;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilgehan on 18.02.2018.
 */

public class OrderDetailActivity extends BaseActivity {

    TextView textDate, textTime, textOrigin, textDestination,
            textPrice, textUsername, textAdditional, textRatingAverage;
    ImageView imageAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        textDate = findViewById(R.id.text_view_date_item_order);
        textTime = findViewById(R.id.text_view_time_item_order);
        textOrigin = findViewById(R.id.text_view_origin_item_order);
        textDestination = findViewById(R.id.text_view_destination_item_order);
        textPrice = findViewById(R.id.text_view_price_item_order);
        textUsername = findViewById(R.id.text_view_username_item_order);
        textAdditional = findViewById(R.id.text_view_additional_info_item_order);
        textRatingAverage = findViewById(R.id.text_view_rating_average_item_order);
        imageAvatar = findViewById(R.id.image_view_avatar_item_order);

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
                    if (orderResult.getCode() == 200) {
                        textOrigin.setText(orderResult.getOrder().getOrigin().getName());
                        textDestination.setText(orderResult.getOrder().getDestination().getName());
                        textDate.setText(orderResult.getOrder().getOrigin().getDate());
                        textTime.setText(orderResult.getOrder().getDestination().getDate());
                        textUsername.setText(orderResult.getOrder().getOwner().getName() + " "
                                + orderResult.getOrder().getOwner().getSurname());
                        textPrice.setText(orderResult.getOrder().getPrice() + "");
                        textRatingAverage.setText(String.valueOf(orderResult.getOrder().getRating()));
                        Picasso.with(getBaseContext()).load(orderResult.getOrder().getOwner().getAvatar()).into(imageAvatar);

                        if (orderResult.getOrder().getRating() > 4)
                            textRatingAverage.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_green));
                        else if (orderResult.getOrder().getRating() > 3)
                            textRatingAverage.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_orange));
                        else if (orderResult.getOrder().getRating() < 1.5)
                            textRatingAverage.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_red));
                    } else
                        Toast.makeText(getApplicationContext(), orderResult.getMsg(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<OrderResult> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
    }
}
