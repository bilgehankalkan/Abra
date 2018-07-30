package com.bilgehankalkan.abra.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.models.Location;
import com.bilgehankalkan.abra.ui.activities.FindCourierFilterActivity;

import icepick.Icepick;
import icepick.State;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class FindCourierFragment extends BaseFragment {

    LinearLayout layoutOrigin, layoutDestination, layoutDate, layoutTime;
    public TextView textViewOrigin, textViewDestination, textViewDate, textViewTime;
    ImageView imageMinus, imagePlus, imageSwitch;
    TextView textCapacity;
    CardView cardViewSearch;
    @State
    String date, time, originLocationName, destinationLocationName, originLocationId, destinationLocationId;
    @State
    int capacity = 3;

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
        textViewOrigin = rootView.findViewById(R.id.edit_text_origin_find_fragment);
        textViewDestination = rootView.findViewById(R.id.edit_text_destination_find_fragment);
        textViewDate = rootView.findViewById(R.id.edit_text_date_find_fragment);
        textViewTime = rootView.findViewById(R.id.edit_text_time_find_fragment);
        imageMinus = rootView.findViewById(R.id.image_view_minus_find_fragment);
        imagePlus = rootView.findViewById(R.id.image_view_plus_find_fragment);
        imageSwitch = rootView.findViewById(R.id.image_switch_find_fragment);
        textCapacity = rootView.findViewById(R.id.text_view_capacity_find_fragment);
        cardViewSearch = rootView.findViewById(R.id.card_view_search_find_fragment);

        layoutOrigin.setOnClickListener(v -> startFilterActivity(0));
        layoutDestination.setOnClickListener(v -> startFilterActivity(1));
        layoutDate.setOnClickListener(v -> startFilterActivity(2));
        layoutTime.setOnClickListener(v -> startFilterActivity(3));

        cardViewSearch.setOnClickListener(v -> {
            if (checkFields())
                startFilterActivity(4);
        });
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
            String tempText1 = textViewOrigin.getText().toString();
            String tempText2 = textViewDestination.getText().toString();
            textViewOrigin.setText(tempText2);
            textViewDestination.setText(tempText1);
            String originLocationTemp = originLocationId;
            originLocationId = destinationLocationId;
            destinationLocationId = originLocationTemp;
        });

        textViewOrigin.setText(originLocationName);
        textViewDestination.setText(destinationLocationName);
        if (date != null)
            textViewDate.setText(date);
        if (time != null)
            textViewTime.setText(time);
        textCapacity.setText(capacity + "");

        return rootView;
    }

    private void startFilterActivity(int mode) {
        Intent intent = new Intent(mActivity, FindCourierFilterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        if (mode == 4)
            bundle.putStringArray("params", new String[]{originLocationId, destinationLocationId, date, time, "" + capacity});
        intent.putExtras(bundle);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private boolean checkFields() {
        if (textViewOrigin.getText().toString().length() == 0) {
            mActivity.showWarning(getString(R.string.warning_X_not_selected, getString(R.string.origin)));
            return false;
        }
        if (textViewDestination.getText().toString().length() == 0) {
            mActivity.showWarning(getString(R.string.warning_X_not_selected, getString(R.string.destination)));
            return false;
        }
        if (textViewDate.getText().toString().length() == 0) {
            mActivity.showWarning(getString(R.string.warning_X_not_selected, getString(R.string.date)));
            return false;
        }
        if (textViewTime.getText().toString().length() == 0) {
            mActivity.showWarning(getString(R.string.warning_X_not_selected, getString(R.string.time)));
            return false;
        }
        return true;
    }

    public void onOriginSelected(Location originLocation) {
        originLocationName = originLocation.getName();
        originLocationId = originLocation.getId();
        textViewOrigin.setText(originLocationName);
    }

    public void onDestinationSelected(Location destinationLocation) {
        destinationLocationName = destinationLocation.getName();
        destinationLocationId = destinationLocation.getId();
        textViewDestination.setText(destinationLocationName);
    }

    public void onOriginDateSelected(String date) {
        this.date = date;
        textViewDate.setText(date);
    }

    public void onOriginTimeSelected(String time) {
        this.time = time;
        textViewTime.setText(time);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
