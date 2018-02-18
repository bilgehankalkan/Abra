package com.bilgehankalkan.abra.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.interfaces.OnOfferChosenListener;
import com.bilgehankalkan.abra.service.models.CourierRequest;
import com.bilgehankalkan.abra.service.models.CourierResponse;
import com.bilgehankalkan.abra.ui.fragments.create_offer.AddNoteFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.CapacityPriceSelectionFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.InstantBookingFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.OriginDestinationFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.DateTimeSelectionFragment;
import com.ncapdevi.fragnav.FragNavController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CreateOfferActivity extends BaseActivity implements OnOfferChosenListener {

    FragNavController fragNavController;
    CourierRequest courierRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        ImageView imageBack = findViewById(R.id.image_back_create_offer);
        ImageView imageNext = findViewById(R.id.image_next_create_offer);

        imageBack.setOnClickListener(v -> onBackPressed());
        imageNext.setOnClickListener(v -> {
            //if (fragNavController)
        });

        fragNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container_create_offer)
                .rootFragment(OriginDestinationFragment.newInstance(true))
                .build();

        courierRequest = new CourierRequest();
    }

    private void sendData() {
        courierRequest.setOwnerId(USER_ID);
        Call<CourierResponse> call = apiInterface.postCourier(getHeader(), courierRequest);
        call.enqueue(new Callback<CourierResponse>() {
            @Override
            public void onResponse(@NonNull Call<CourierResponse> call, @NonNull Response<CourierResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CourierResponse courierResponse = response.body();
                    if (courierResponse.getCode() == 201) {
                        finish();
                        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
                        Toast.makeText(getApplicationContext(), R.string.offer_created, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), courierResponse.getMsg(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<CourierResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOriginSelected(String originId) {
        fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(3));
        courierRequest.setOrigin(originId);
    }

    @Override
    public void onOriginDateSelected(String date) {
        fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(0));
        courierRequest.setOriginDate(date);
    }

    @Override
    public void onOriginTimeSelected(String time) {
        fragNavController.pushFragment(OriginDestinationFragment.newInstance(false));
        courierRequest.setOriginDate(courierRequest.getOriginDate() + "T" + time);
    }

    @Override
    public void onDestinationSelected(String destinationId) {
        fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(4));
        courierRequest.setDestination(destinationId);
    }

    @Override
    public void onDestinationDateSelected(String date) {
        fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(1));
        courierRequest.setDestinationDate(date);
    }

    @Override
    public void onDestinationTimeSelected(String time) {
        fragNavController.pushFragment(CapacityPriceSelectionFragment.newInstance(0));
        courierRequest.setDestinationDate(courierRequest.getDestinationDate() + "T" + time);
    }

    @Override
    public void onLoadCapacitySelected(int capacity) {
        fragNavController.pushFragment(new InstantBookingFragment());
        courierRequest.setWeight(capacity);
    }

    @Override
    public void onInstantBookingSelected(boolean isInstantBookingSelected) {
        fragNavController.pushFragment(CapacityPriceSelectionFragment.newInstance(1));
        courierRequest.setInstantBooking(isInstantBookingSelected);
    }

    @Override
    public void onPriceSelected(int price) {
        fragNavController.pushFragment(new AddNoteFragment());
        courierRequest.setPrice(price);
    }

    @Override
    public void onNotesAdded(String notes) {
        courierRequest.setNote(notes);
        sendData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragNavController.getSize() == 1)
            finish();
        else
            fragNavController.switchTab(fragNavController.getCurrentStackIndex() - 1);
    }
}
