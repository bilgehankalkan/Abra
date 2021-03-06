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

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.ui.activities.FindCourierFilterActivity;

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
                textViewExplanation.setText(getString(R.string.explanation_time_date, getString(R.string.departure), getString(R.string.time)));
                break;
            case 1:
                textViewExplanation.setText(getString(R.string.explanation_time_date, getString(R.string.arrival), getString(R.string.time)));
                break;
            case 2:
                textViewExplanation.setText(getString(R.string.explanation_time_date, getString(R.string.initial), getString(R.string.time)));
                break;
            case 3:
                textViewExplanation.setText(getString(R.string.explanation_time_date, getString(R.string.departure), getString(R.string.date)));
                break;
            case 4:
                textViewExplanation.setText(getString(R.string.explanation_time_date, getString(R.string.arrival), getString(R.string.date)));
                break;
            case 5:
                textViewExplanation.setText(getString(R.string.explanation_time_date, getString(R.string.initial), getString(R.string.date)));
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
        String hour, minute;
        if (timePicker.getCurrentHour() < 10)
            hour = "0" + timePicker.getCurrentHour();
        else
            hour = "" + timePicker.getCurrentHour();

        if (timePicker.getCurrentMinute() < 10)
            minute = "0" + timePicker.getCurrentMinute();
        else
            minute = "" + timePicker.getCurrentMinute();

        selectedTime = hour + ":" + minute;
        if (selectedTime.length() < 3) {
            mActivity.showWarning(getString(R.string.select_valid_argument, "time"));
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
        String month, day;
        if (datePicker.getMonth() < 9)
            month = "0" + (datePicker.getMonth() + 1);
        else
            month = "" + (datePicker.getMonth() + 1);

        if (datePicker.getDayOfMonth() < 10)
            day = "0" + datePicker.getDayOfMonth();
        else
            day = "" + datePicker.getDayOfMonth();

        selectedDate = datePicker.getYear() + "-" + month + "-" + day;
        if (selectedDate.length() < 8) {
            mActivity.showWarning(getString(R.string.select_valid_argument, "date"));
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
