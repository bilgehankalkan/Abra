package com.bilgehankalkan.abra.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.interfaces.OnSearchOptionListener;
import com.bilgehankalkan.abra.service.models.Location;
import com.bilgehankalkan.abra.ui.activities.MainActivity;
import com.bilgehankalkan.abra.ui.fragments.create_offer.OriginDestinationFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.DateTimeSelectionFragment;
import com.ncapdevi.fragnav.FragNavController;

import icepick.Icepick;
import icepick.State;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class FindCourierFragment extends BaseFragment {

    LinearLayout layoutOrigin, layoutDestination, layoutDate, layoutTime;
    public EditText editTextOrigin, editTextDestination, editTextDate, editTextTime;
    ImageView imageMinus, imagePlus, imageSwitch;
    TextView textCapacity;
    CardView cardViewSearch;
    @State String date, time, originLocationName, destinationLocationName, originLocationId, destinationLocationId;
    @State int capacity = 3;

    FragNavController fragNavController;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_courier, container, false);

        Icepick.restoreInstanceState(this, savedInstanceState);

        layoutOrigin = rootView.findViewById(R.id.layout_origin_find_fragment);
        layoutDestination = rootView.findViewById(R.id.layout_destination_find_fragment);
        layoutDate = rootView.findViewById(R.id.layout_date_find_fragment);
        layoutTime = rootView.findViewById(R.id.layout_time_find_fragment);
        editTextOrigin = rootView.findViewById(R.id.edit_text_origin_find_fragment);
        editTextDestination = rootView.findViewById(R.id.edit_text_destination_find_fragment);
        editTextDate = rootView.findViewById(R.id.edit_text_date_find_fragment);
        editTextTime = rootView.findViewById(R.id.edit_text_time_find_fragment);
        imageMinus = rootView.findViewById(R.id.image_view_minus_find_fragment);
        imagePlus = rootView.findViewById(R.id.image_view_plus_find_fragment);
        imageSwitch = rootView.findViewById(R.id.image_switch_find_fragment);
        textCapacity = rootView.findViewById(R.id.text_view_capacity_find_fragment);
        cardViewSearch = rootView.findViewById(R.id.card_view_search_find_fragment);

        /*fragNavController = FragNavController.newBuilder(savedInstanceState, ((MainActivity) getActivity()).getSupportFragmentManager(), R.id.container_main)
                //.rootFragmentListener(rootFragmentListener, 4)
                .rootFragment(this)
                .build();*/

        fragNavController = ((MainActivity) getActivity()).fragNavController;

        layoutOrigin.setOnClickListener(v -> fragNavController.pushFragment(OriginDestinationFragment.newInstance(true)));
        layoutDestination.setOnClickListener(v -> fragNavController.pushFragment(OriginDestinationFragment.newInstance(false)));
        layoutDate.setOnClickListener(v -> fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(5)));
        layoutTime.setOnClickListener(v -> fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(2)));
        editTextOrigin.setOnTouchListener((v, event) -> {
            fragNavController.popFragment();
            fragNavController.pushFragment(OriginDestinationFragment.newInstance(true));,
            return false;
        });
        editTextDestination.setOnTouchListener((v, event) -> {
            fragNavController.pushFragment(OriginDestinationFragment.newInstance(false));
            return true;
        });
        editTextDate.setOnTouchListener((v, event) -> {
            fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(5));
            return false;
        });
        editTextTime.setOnTouchListener((v, event) -> {
            fragNavController.pushFragment(DateTimeSelectionFragment.newInstance(2));
            return false;
        });

        editTextOrigin.setText(originLocationName);
        editTextDestination.setText(destinationLocationName);
        editTextDate.setText(date);
        editTextTime.setText(time);
        textCapacity.setText(String.valueOf(capacity));

        imageMinus.setOnClickListener(v -> {
            if (Integer.valueOf(textCapacity.getText().toString()) > 1)
                textCapacity.setText(Integer.valueOf(textCapacity.getText().toString()) - 1 + "");
            capacity = Integer.valueOf(textCapacity.getText().toString());
        });
        imagePlus.setOnClickListener(v -> {
            if (Integer.valueOf(textCapacity.getText().toString()) < 20)
                textCapacity.setText(Integer.valueOf(textCapacity.getText().toString()) + 1 + "");
            capacity = Integer.valueOf(textCapacity.getText().toString());
        });
        imageSwitch.setOnClickListener(v -> {
            String tempText1 = editTextOrigin.getText().toString();
            String tempText2 = editTextDestination.getText().toString();
            editTextOrigin.setText(tempText2);
            editTextDestination.setText(tempText1);
        });

        return rootView;
    }

    public void onOriginSelected(Location originLocation) {
        originLocationName = originLocation.getName();
        originLocationId = originLocation.getId();
        ((MainActivity) mActivity).fragNavController.pushFragment(new FindCourierFragment());
        mActivity.runOnUiThread(() -> editTextOrigin.setText(originLocationName));
    }

    public void onDestinationSelected(Location destinationLocation) {
        destinationLocationName = destinationLocation.getName();
        destinationLocationId = destinationLocation.getId();
        editTextDestination.setText(destinationLocationName);
        //mActivity.onBackPressed();
    }

    public void onOriginDateSelected(String date) {
        this.date = date;
        //editTextDate.setText(date);
        //mActivity.onBackPressed();
    }

    public void onOriginTimeSelected(String time) {
        this.time = time;
        //editTextDate.setText(time);
        //mActivity.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
