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

public class CapacityPriceSelectionFragment extends CreateOfferBaseFragment {
    /**
     *
     * @param mode 0: Capacity, 1:Price
     *
     * @return @CapacityPriceSelectionFragment
     */
    public static CapacityPriceSelectionFragment newInstance(int mode) {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        CapacityPriceSelectionFragment capacityPriceSelectionFragment = new CapacityPriceSelectionFragment();
        capacityPriceSelectionFragment.setArguments(bundle);
        return capacityPriceSelectionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_capacity_selection, container, false);

        TextView textCapacity = rootView.findViewById(R.id.text_view_capacity_selection_fragment);
        TextView textExplanation = rootView.findViewById(R.id.text_view_explanation_capacity_selection_fragment);
        ImageView imageCapacityMinus = rootView.findViewById(R.id.image_minus_capacity_selection);
        ImageView imageCapacityPlus = rootView.findViewById(R.id.image_plus_capacity_selection);
        CardView cardViewContinue = rootView.findViewById(R.id.card_view_capacity_selection_fragment);

        assert getArguments() != null;
        int mode = getArguments().getInt("mode");

        if (mode == 0)
            textExplanation.setText(R.string.capacity_selection_explanation);
        else
            textExplanation.setText(R.string.price_selection_explanation);

        imageCapacityMinus.setOnClickListener(v -> {
            if (Integer.valueOf(textCapacity.getText().toString()) > 1)
                textCapacity.setText("" + (Integer.valueOf(textCapacity.getText().toString()) - 1));
        });
        imageCapacityPlus.setOnClickListener(v -> {
            if (mode == 1) {
                if (Integer.valueOf(textCapacity.getText().toString()) < 100)
                    textCapacity.setText("" + (Integer.valueOf(textCapacity.getText().toString()) + 1));
            } else {
                if (Integer.valueOf(textCapacity.getText().toString()) < 20)
                    textCapacity.setText("" + (Integer.valueOf(textCapacity.getText().toString()) + 1));
            }
        });

        cardViewContinue.setOnClickListener(v -> {
            if (mode == 0)
                onOfferChosenListener.onLoadCapacitySelected(Integer.valueOf(textCapacity.getText().toString()));
            else
                onOfferChosenListener.onPriceSelected(Integer.valueOf(textCapacity.getText().toString()));
        });

        return rootView;
    }
}
