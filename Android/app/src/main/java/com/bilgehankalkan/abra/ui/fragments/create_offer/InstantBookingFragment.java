package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgehankalkan.abra.R;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class InstantBookingFragment extends CreateOfferBaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instant_booking, container, false);

        CardView cardViewPostWithout = rootView.findViewById(R.id.card_view_post_without_instant_booking_fragment);
        CardView cardViewPostWith = rootView.findViewById(R.id.card_view_post_with_instant_booking_fragment);

        cardViewPostWith.setOnClickListener(v -> onOfferChosenListener.onInstantBookingSelected(true));
        cardViewPostWithout.setOnClickListener(v -> onOfferChosenListener.onInstantBookingSelected(false));

        return rootView;
    }
}
