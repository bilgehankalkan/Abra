package com.bilgehankalkan.abra.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.models.Order;
import com.bilgehankalkan.abra.service.models.OrderListResult;
import com.bilgehankalkan.abra.ui.activities.BaseActivity;
import com.bilgehankalkan.abra.ui.adapters.OrderAdapter;
import com.bilgehankalkan.abra.utils.EndlessRecyclerViewScrollListener;
import com.bilgehankalkan.abra.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class OrdersFragment extends BaseFragment {

    RecyclerView recyclerView;

    OrderAdapter orderAdapter;
    List<Order> listOrders;

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

        recyclerView = rootView.findViewById(R.id.recycler_view_orders_fragment);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(((LinearLayoutManager) recyclerView.getLayoutManager())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (getArguments() != null)
                    getSearchResult(getArguments().getStringArray("params"), page);
                else
                    getOrders(page);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        listOrders = new ArrayList<>();
        orderAdapter = new OrderAdapter(mActivity, listOrders);
        recyclerView.setAdapter(orderAdapter);

        if (getArguments() != null)
            getSearchResult(getArguments().getStringArray("params"), 0);
        else
            getOrders(0);

        return rootView;
    }

    private void getSearchResult(String[] params, int pageIndex) {
        Map<String, String> map = new HashMap<>();
        map.put("originLocation", params[0]);
        map.put("destinationLocation", params[1]);
        map.put("originDate", params[2]);
        map.put("originTime", params[3]);
        map.put("weight", params[4]);
        Call<OrderListResult> call = apiInterface.getSearchResult(pageIndex, 20, map);
        call.enqueue(callback);
    }

    private void getOrders(int pageIndex) {
        Call<OrderListResult> call = apiInterface.getOrders(BaseActivity.USER_ID, "current", pageIndex, 20);
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
                    Toast.makeText(mActivity, orderListResult.getMsg(), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(@NonNull Call<OrderListResult> call, @NonNull Throwable t) {
            Toast.makeText(mActivity, R.string.connection_error, Toast.LENGTH_SHORT).show();
        }
    };
}
