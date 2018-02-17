package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilgehankalkan.abra.R;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CapacitySelectionFragment extends CreateOfferBaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_capacity_selection, container, false);

        TextView textCapacity = rootView.findViewById(R.id.text_view_capacity_selection_fragment);
        ImageView imageCapacityMinus = rootView.findViewById(R.id.image_minus_capacity_selection);
        ImageView imageCapacityPlus = rootView.findViewById(R.id.image_plus_capacity_selection);
        CardView cardViewContinue = rootView.findViewById(R.id.card_view_capacity_selection_fragment);

        imageCapacityMinus.setOnClickListener(v -> {
            if (Integer.valueOf(textCapacity.getText().toString()) > 1)
                textCapacity.setText("" + (Integer.valueOf(textCapacity.getText().toString()) - 1));
        });
        imageCapacityPlus.setOnClickListener(v -> {
            if (Integer.valueOf(textCapacity.getText().toString()) < 20)
                textCapacity.setText("" + (Integer.valueOf(textCapacity.getText().toString()) + 1));
        });

        cardViewContinue.setOnClickListener(v -> onOfferChosenListener.onLoadCapacitySelected(Integer.valueOf(textCapacity.getText().toString())));

        return rootView;
    }
}
