package com.bilgehankalkan.abra.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.interfaces.OnOfferChosenListener;
import com.bilgehankalkan.abra.ui.fragments.create_offer.CapacitySelectionFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.DateTimeSelectionFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.InstantBookingFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.OriginDestinationFragment;
import com.ncapdevi.fragnav.FragNavController;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CreateOfferActivity extends AppCompatActivity implements OnOfferChosenListener {

    FragNavController fragNavController;

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
    }

    @Override
    public void onOriginSelected(String origin) {

        fragNavController.pushFragment(OriginDestinationFragment.newInstance(false));
        fragNavController.switchTab(fragNavController.getSize() - 1);
    }

    @Override
    public void onDestinationSelected(String destination) {

        fragNavController.pushFragment(new DateTimeSelectionFragment());
        fragNavController.switchTab(fragNavController.getSize() - 1);
    }

    @Override
    public void onDateTimeSelected(String dateTime) {

        fragNavController.pushFragment(new CapacitySelectionFragment());
        fragNavController.switchTab(fragNavController.getSize() - 1);
    }

    @Override
    public void onLoadCapacitySelected(int capacity) {

        fragNavController.pushFragment(new InstantBookingFragment());
        fragNavController.switchTab(fragNavController.getSize() - 1);
    }

    @Override
    public void onInstantBookingSelected(boolean isInstantBookingSelected) {
        sendData();
        finish();
    }

    private void sendData() {

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
