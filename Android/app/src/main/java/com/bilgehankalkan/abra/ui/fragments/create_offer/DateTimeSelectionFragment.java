package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class DateTimeSelectionFragment extends CreateOfferBaseFragment {

    DatePicker datePicker;
    TimePicker timePicker;

    String selectedDate;
    String selectedTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_date_time_selection, container, false);
        CardView cardViewContinue = rootView.findViewById(R.id.card_view_continue_date_time_fragment);
        datePicker = rootView.findViewById(R.id.date_picker_date_time_fragment);
        timePicker = rootView.findViewById(R.id.time_picker_date_time_fragment);

        cardViewContinue.setOnClickListener(v -> getSelectedDateTime());
        return rootView;
    }

    private void getSelectedDateTime() {
        selectedDate = datePicker.getYear() + "-" + ((int) datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
        if (selectedDate.length() < 8) {
            Toast.makeText(getActivity(), getString(R.string.select_valid_argument, "date"), Toast.LENGTH_SHORT).show();
            return;
        }
        selectedTime = timePicker.getCurrentHour() + "-" + timePicker.getCurrentMinute();
        if (selectedTime.length() < 3) {
            Toast.makeText(getActivity(), getString(R.string.select_valid_argument, "time"), Toast.LENGTH_SHORT).show();
            return;
        }
        onOfferChosenListener.onDateTimeSelected(selectedDate + "t" + selectedTime);
    }
}
