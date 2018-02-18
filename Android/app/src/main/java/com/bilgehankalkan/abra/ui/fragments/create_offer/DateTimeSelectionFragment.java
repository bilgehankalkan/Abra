package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.ui.activities.FindCourierFilterActivity;
import com.bilgehankalkan.abra.ui.activities.MainActivity;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class DateTimeSelectionFragment extends CreateOfferBaseFragment {

    TimePicker timePicker;
    DatePicker datePicker;
    FrameLayout layoutPickerContainer;

    String selectedTime, selectedDate;
    int mode;

    /**
     * @param mode 0: OriginTime, 1: DestinationTime, 2: SearchOriginTime
     *             3: OriginDate, 4: DestinationDate, 5: SearchOriginDate
     *
     * @return @DateTimeSelectionFragment;
     */
    public static DateTimeSelectionFragment newInstance(int mode) {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        DateTimeSelectionFragment dateTimeSelectionFragment = new DateTimeSelectionFragment();
        dateTimeSelectionFragment.setArguments(bundle);
        return dateTimeSelectionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_date_time_selection, container, false);

        CardView cardViewContinue = rootView.findViewById(R.id.card_view_continue_date_time_fragment);
        TextView textViewExplanation = rootView.findViewById(R.id.text_view_explanation_date_time_selection_fragment);
        layoutPickerContainer = rootView.findViewById(R.id.layout_container_date_time_fragment);

        assert getArguments() != null;
        mode = getArguments().getInt("mode");

        if (mode < 3) {
            timePicker = new TimePicker(getContext());
            layoutPickerContainer.addView(timePicker);
        } else {
            datePicker = new DatePicker(getContext());
            layoutPickerContainer.addView(datePicker);
        }

        switch (mode) {
            case 0:
                textViewExplanation.setText(R.string.explanation_time_origin);
                break;
            case 1:
                textViewExplanation.setText(R.string.explanation_time_destination);
                break;
            case 2:
                textViewExplanation.setText(R.string.explanation_time_search);
                break;
            case 3:
                textViewExplanation.setText(R.string.explanation_date_origin);
                break;
            case 4:
                textViewExplanation.setText(R.string.explanation_date_destination);
                break;
            case 5:
                textViewExplanation.setText(R.string.explanation_date_search);
                break;
        }
        cardViewContinue.setOnClickListener(v -> {
            if (mode < 3)
                getSelectedTime();
            else
                getSelectedDate();
        });

        return rootView;
    }

    private void getSelectedTime() {
        selectedTime = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
        if (selectedTime.length() < 3) {
            Toast.makeText(getActivity(), getString(R.string.select_valid_argument, "time"), Toast.LENGTH_SHORT).show();
            return;
        }
        if (getContext() instanceof FindCourierFilterActivity) {
            onSearchOptionListener.onOriginTimeSelected(selectedTime);
        } else {
            if (mode == 0)
                onOfferChosenListener.onOriginTimeSelected(selectedTime);
            else {
                onOfferChosenListener.onDestinationTimeSelected(selectedTime);
            }
        }
    }

    private void getSelectedDate() {
        selectedDate = datePicker.getYear() + "-" + ((int) datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
        if (selectedDate.length() < 8) {
            Toast.makeText(getActivity(), getString(R.string.select_valid_argument, "date"), Toast.LENGTH_SHORT).show();
            return;
        }
        if (getContext() instanceof FindCourierFilterActivity) {
            onSearchOptionListener.onOriginDateSelected(selectedDate);
        } else {
            if (mode == 3)
                onOfferChosenListener.onOriginDateSelected(selectedDate);
            else
                onOfferChosenListener.onDestinationDateSelected(selectedDate);
        }
    }
}
