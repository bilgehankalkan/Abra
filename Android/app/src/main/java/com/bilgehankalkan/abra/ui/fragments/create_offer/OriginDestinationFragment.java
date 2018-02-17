package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.interfaces.OnOfferChosenListener;

import java.util.List;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class OriginDestinationFragment extends CreateOfferBaseFragment {

    MultiAutoCompleteTextView multiAutoCompleteTextView;

    OnOfferChosenListener onOfferChosenListener;

    ArrayAdapter<String> searchSuggestionAdapter;
    List<String> suggestions;
    List<String> suggestionIds;
    String selectedId;

    public static OriginDestinationFragment newInstance(boolean isOriginSelect) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isOriginSelect", isOriginSelect);
        OriginDestinationFragment fragment = new OriginDestinationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_origin_destination, container, false);

        multiAutoCompleteTextView = rootView.findViewById(R.id.auto_complete_text_view_origin_destination_fragment);
        TextView textViewQuestion = rootView.findViewById(R.id.text_view_origin_destination_fragment);
        CardView cardViewContinue = rootView.findViewById(R.id.card_view_continue_origin_destination_fragment);

        assert getArguments() != null;
        Boolean isOriginSelect = getArguments().getBoolean("isOriginSelect");

        textViewQuestion.setText(getString(R.string.origin_destination_explanation_text,
                isOriginSelect ?  getString(R.string.origin) : getString(R.string.destination)));

        multiAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSuggestions(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        assert getContext() != null;
        searchSuggestionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, suggestions);
        multiAutoCompleteTextView.setAdapter(searchSuggestionAdapter);
        multiAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedId = suggestionIds.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                for (int i = 0; i < suggestions.size(); i++) {
                    if (multiAutoCompleteTextView.getText().toString().equals(suggestions.get(i))) {
                        selectedId = suggestionIds.get(i);
                        break;
                    }
                }
            }
        });

        cardViewContinue.setOnClickListener(v -> {
            if (selectedId != null) {
                if (isOriginSelect)
                    onOfferChosenListener.onOriginSelected(selectedId);
                else
                    onOfferChosenListener.onDestinationSelected(selectedId);
            } else
                Toast.makeText(getContext(), getString(R.string.please_select_origin_destination,
                        isOriginSelect ? getString(R.string.origin) : getString(R.string.destination)),
                        Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }

    private void getSuggestions(String query) {

    }
}
