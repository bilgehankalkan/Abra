package com.bilgehankalkan.abra.ui.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.interfaces.OnOfferChosenListener;
import com.bilgehankalkan.abra.interfaces.OnSearchOptionListener;
import com.bilgehankalkan.abra.service.models.Location;
import com.bilgehankalkan.abra.ui.fragments.FindCourierFragment;
import com.bilgehankalkan.abra.ui.fragments.InboxFragment;
import com.bilgehankalkan.abra.ui.fragments.MyProfileFragment;
import com.bilgehankalkan.abra.ui.fragments.OrdersFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.CreateOfferBaseFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.OriginDestinationFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static FragNavController fragNavController;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view_main);

        List<Fragment> listRootFragments = new ArrayList<>();
        listRootFragments.add(new FindCourierFragment());
        listRootFragments.add(OrdersFragment.newInstance(null));
        listRootFragments.add(OrdersFragment.newInstance(null));

        fragNavController = FragNavController.newBuilder(savedInstanceState,
                getSupportFragmentManager(), R.id.container_main)
                .rootFragments(listRootFragments)
                .build();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_find_courier:
                        fragNavController.switchTab(FragNavController.TAB1);
                        break;
                    case R.id.action_your_orders:
                        fragNavController.switchTab(FragNavController.TAB2);
                        break;
                    case R.id.action_your_deliveries:
                        fragNavController.switchTab(FragNavController.TAB3);
                        break;
                    case R.id.action_offer_to_carry:
                        bottomNavigationView.setSelectedItemId(R.id.action_your_orders);
                        startActivity(new Intent(getApplicationContext(), CreateOfferActivity.class));
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        break;
                }
                return true;
            });
    }
}
