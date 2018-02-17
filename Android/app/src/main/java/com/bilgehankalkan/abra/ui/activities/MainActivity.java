package com.bilgehankalkan.abra.ui.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.ui.fragments.FindCourierFragment;
import com.bilgehankalkan.abra.ui.fragments.InboxFragment;
import com.bilgehankalkan.abra.ui.fragments.MyProfileFragment;
import com.bilgehankalkan.abra.ui.fragments.OrdersFragment;
import com.ncapdevi.fragnav.FragNavController;

public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view_main);

        FragNavController fragNavController = FragNavController.newBuilder(savedInstanceState,
                getSupportFragmentManager(), R.id.container_main)
                .rootFragmentListener(this, 4)
                .build();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_find_courier:
                        fragNavController.switchTab(FragNavController.TAB1);
                        break;
                    case R.id.action_your_orders:
                        fragNavController.switchTab(FragNavController.TAB2);
                        break;
                    case R.id.action_offer_to_carry:
                        startActivity(new Intent(getApplicationContext(), CreateOfferActivity.class));
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        fragNavController.switchTab(FragNavController.TAB2);
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
    public Fragment getRootFragment(int i) {
        switch (i) {
            case 0:
                return new FindCourierFragment();
            case 1:
                return new OrdersFragment();
            case 2:
                return new InboxFragment();
            case 3:
                return new MyProfileFragment();
        }
        return null;
    }
}
