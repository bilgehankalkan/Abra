package com.bilgehankalkan.abra.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.ApiInterface;
import com.bilgehankalkan.abra.service.models.Order;
import com.bilgehankalkan.abra.service.models.OrderListResult;
import com.bilgehankalkan.abra.ui.activities.BaseActivity;
import com.bilgehankalkan.abra.ui.activities.OrderDetailActivity;
import com.bilgehankalkan.abra.ui.adapters.OrderAdapter;
import com.bilgehankalkan.abra.utils.EndlessRecyclerViewScrollListener;
import com.bilgehankalkan.abra.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import icepick.Icepick;
import icepick.State;
import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class OrdersFragment extends BaseFragment {

    RecyclerView recyclerView;
    SegmentedGroup segmentedGroup;

    OrderAdapter orderAdapter;
    List<Order> listOrders;
    @State
    boolean isPast = true, isCourier = true;
    private int pageIndex = 0;

    public static OrdersFragment newInstance(@Nullable Bundle params) {
        OrdersFragment ordersFragment = new OrdersFragment();
        if (params != null)
            ordersFragment.setArguments(params);
        return ordersFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

        Icepick.restoreInstanceState(this, savedInstanceState);

        recyclerView = rootView.findViewById(R.id.recycler_view_orders_fragment);
        segmentedGroup = rootView.findViewById(R.id.segmented_group_orders_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(
                ((LinearLayoutManager) recyclerView.getLayoutManager())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                page = pageIndex;
                if (getArguments() != null)
                    if (getArguments().getStringArray("params") != null) {
                        getSearchResult(getArguments().getStringArray("params"), page);
                        segmentedGroup.setVisibility(View.GONE);
                    } else if (getArguments().getBoolean("isYourDeliveries", false)) {
                        isCourier = false;
                        getOrders();
                    } else
                        getOrders();
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(mActivity, OrderDetailActivity.class);
                        intent.putExtra("id", listOrders.get(position).getId());
                        startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        listOrders = new ArrayList<>();
        orderAdapter = new OrderAdapter(mActivity, listOrders);
        recyclerView.setAdapter(orderAdapter);

        if (getArguments() != null) {
            if (getArguments().getStringArray("params") != null) {
                getSearchResult(getArguments().getStringArray("params"), 0);
                segmentedGroup.setVisibility(View.GONE);
            } else if (getArguments().getBoolean("isYourDeliveries", false)) {
                isCourier = false;
                getOrders();
            }
        } else
            getOrders();

        segmentedGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_button_past:
                    isPast = true;
                    break;
                case R.id.radio_button_current:
                    isPast = false;
                    break;
            }
            listOrders.clear();
            pageIndex = 0;
            getOrders();
        });

        return rootView;
    }

    private void getSearchResult(String[] params, int pageIndex) {
        Map<String, String> map = new HashMap<>();
        map.put("originLocation", params[0]);
        map.put("destinationLocation", params[1]);
        map.put("originDate", params[2]);
        map.put("originTime", params[3]);
        map.put("weight", params[4]);
        Call<OrderListResult> call = BaseActivity.apiInterface.getSearchResult(pageIndex, 20, map);
        call.enqueue(callback);
    }

    private void getOrders() {
        Call<OrderListResult> call = BaseActivity.apiInterface.getOrders(BaseActivity.USER_ID,
                isCourier ? ApiInterface.COURIER : ApiInterface.CARRY,
                isPast ? ApiInterface.PAST : ApiInterface.CURRENT, pageIndex, 20);
        call.enqueue(callback);
    }

    Callback<OrderListResult> callback = new Callback<OrderListResult>() {
        @Override
        public void onResponse(@NonNull Call<OrderListResult> call, @NonNull Response<OrderListResult> response) {
            if (response.isSuccessful() && response.body() != null) {
                OrderListResult orderListResult = response.body();
                if (orderListResult.getCode() == 200) {
                    listOrders.addAll(orderListResult.getOrderList());
                    mActivity.runOnUiThread(() -> orderAdapter.notifyDataSetChanged());
                } else
                    mActivity.showError(orderListResult.getMsg());
            } else
                mActivity.showError(R.string.connection_error);
        }

        @Override
        public void onFailure(@NonNull Call<OrderListResult> call, @NonNull Throwable t) {
            mActivity.showError(R.string.connection_error);
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
