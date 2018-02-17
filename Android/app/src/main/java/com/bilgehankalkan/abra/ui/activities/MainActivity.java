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
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnSearchOptionListener {

    public FragNavController fragNavController;
    FindCourierFragment findCourierFragment;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view_main);

        findCourierFragment = new FindCourierFragment();
        List<Fragment> listRootFragments = new ArrayList<>();
        listRootFragments.add(findCourierFragment);
        listRootFragments.add(new OrdersFragment());
        listRootFragments.add(new InboxFragment());
        listRootFragments.add(new MyProfileFragment());

        fragNavController = FragNavController.newBuilder(savedInstanceState,
                getSupportFragmentManager(), R.id.container_main)
                .rootFragments(listRootFragments)
                .build();

        bottomNavigationView.setSelectedItemId(R.id.action_find_courier);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_find_courier:
                        fragNavController.switchTab(FragNavController.TAB1);
                        break;
                    case R.id.action_your_orders:
                        fragNavController.switchTab(FragNavController.TAB2);
                        break;
                    case R.id.action_offer_to_carry:
                        bottomNavigationView.setSelectedItemId(R.id.action_your_orders);
                        startActivity(new Intent(getApplicationContext(), CreateOfferActivity.class));
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        break;
                    case R.id.action_inbox:
                        fragNavController.switchTab(FragNavController.TAB3);
                        break;
                    case R.id.action_profile:
                        fragNavController.switchTab(FragNavController.TAB4);
                        break;
                }
                return true;
            });
    }

    @Override
    public void onBackPressed() {
        if (fragNavController.getCurrentFrag() instanceof CreateOfferBaseFragment)
            fragNavController.popFragment();
        else
            onBackPressed();
    }

    @Override
    public void onOriginSelected(Location originLocation) {
        findCourierFragment.onOriginSelected(originLocation);
    }

    @Override
    public void onOriginDateSelected(String date) {
        findCourierFragment.onOriginDateSelected(date);
    }

    @Override
    public void onOriginTimeSelected(String time) {
        findCourierFragment.onOriginTimeSelected(time);
    }

    @Override
    public void onDestinationSelected(Location destinationLocation) {
        findCourierFragment.onDestinationSelected(destinationLocation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragNavController.onSaveInstanceState(outState);
    }
}
