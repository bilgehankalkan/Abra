package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.models.Location;
import com.bilgehankalkan.abra.service.models.LocationSearchResponse;
import com.bilgehankalkan.abra.ui.activities.MainActivity;
import com.bilgehankalkan.abra.ui.adapters.SearchAdapter;
import com.bilgehankalkan.abra.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class OriginDestinationFragment extends CreateOfferBaseFragment {

    SearchView searchView;
    RecyclerView recyclerViewSuggestions;

    SearchAdapter searchAdapter;
    List<Location> suggestionsList = new ArrayList<>();
    Location selectedLocation;

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

        searchView = rootView.findViewById(R.id.search_view_origin_destination_fragment);
        recyclerViewSuggestions = rootView.findViewById(R.id.recycler_view_origin_destination_fragment);
        TextView textViewQuestion = rootView.findViewById(R.id.text_view_origin_destination_fragment);
        CardView cardViewContinue = rootView.findViewById(R.id.card_view_continue_origin_destination_fragment);

        assert getArguments() != null;
        Boolean isOriginSelect = getArguments().getBoolean("isOriginSelect");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0)
                    getSuggestions(newText);
                return true;
            }
        });
        searchView.onActionViewExpanded();

        searchAdapter = new SearchAdapter(mActivity, suggestionsList);
        recyclerViewSuggestions.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerViewSuggestions.setAdapter(searchAdapter);
        recyclerViewSuggestions.addOnItemTouchListener(new RecyclerItemClickListener(mActivity,
                recyclerViewSuggestions, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedLocation = suggestionsList.get(position);
                searchView.setQuery(suggestionsList.get(position).getName(), false);
                mActivity.closeSoftKeyboard(searchView);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        textViewQuestion.setText(getString(R.string.origin_destination_explanation_text,
                isOriginSelect ?  getString(R.string.origin) : getString(R.string.destination)));

        cardViewContinue.setOnClickListener(v -> {
            if (selectedLocation != null) {
                if (onOfferChosenListener != null) {
                    if (isOriginSelect)
                        onOfferChosenListener.onOriginSelected(selectedLocation.getId());
                    else
                        onOfferChosenListener.onDestinationSelected(selectedLocation.getId());
                } else if (onSearchOptionListener != null) {
                    if (isOriginSelect)
                        onSearchOptionListener.onOriginSelected(selectedLocation);
                    else
                        onSearchOptionListener.onDestinationSelected(selectedLocation);
                    getFragmentManager().beginTransaction().detach(this).commit();
                }
            } else
                Toast.makeText(getContext(), getString(R.string.please_select_origin_destination,
                        isOriginSelect ? getString(R.string.origin) : getString(R.string.destination)),
                        Toast.LENGTH_SHORT).show();
        });

        return rootView;
    }

    private void getSuggestions(String query) {
        Call<LocationSearchResponse> call = apiInterface.getLocationSearch(mActivity.getHeader(), query);
        call.enqueue(new Callback<LocationSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<LocationSearchResponse> call, @NonNull Response<LocationSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LocationSearchResponse locationSearchResponse = response.body();
                    if (locationSearchResponse.getCode() == 200) {
                        suggestionsList.clear();
                        suggestionsList.addAll(locationSearchResponse.getData());
                        mActivity.runOnUiThread(() -> searchAdapter.notifyDataSetChanged());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LocationSearchResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
