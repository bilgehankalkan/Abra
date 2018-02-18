package com.bilgehankalkan.abra.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.interfaces.OnSearchOptionListener;
import com.bilgehankalkan.abra.service.models.Location;
import com.bilgehankalkan.abra.ui.fragments.FindCourierFragment;
import com.bilgehankalkan.abra.ui.fragments.OrdersFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.DateTimeSelectionFragment;
import com.bilgehankalkan.abra.ui.fragments.create_offer.OriginDestinationFragment;

/**
 * Created by Bilgehan on 18.02.2018.
 */

public class FindCourierFilterActivity extends BaseActivity implements OnSearchOptionListener {

    FindCourierFragment findCourierFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_courier_filter);

        assert getIntent().getExtras() != null;
        int mode = getIntent().getExtras().getInt("mode");

        findCourierFragment = (FindCourierFragment) MainActivity.fragNavController.getCurrentFrag();

        switch (mode) {
            case 0:
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container_find_courier_filter_activity,
                        OriginDestinationFragment.newInstance(true)).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container_find_courier_filter_activity,
                        OriginDestinationFragment.newInstance(false)).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container_find_courier_filter_activity,
                        DateTimeSelectionFragment.newInstance(5)).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container_find_courier_filter_activity,
                        DateTimeSelectionFragment.newInstance(2)).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().add(R.id.layout_container_find_courier_filter_activity,
                        OrdersFragment.newInstance(getIntent().getExtras())).commit();
        }
    }

    @Override
    public void onOriginSelected(Location originLocation) {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(0)).commit();
        onBackPressed();
        findCourierFragment.onOriginSelected(originLocation);
    }

    @Override
    public void onOriginDateSelected(String date) {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(0)).commit();
        onBackPressed();
        findCourierFragment.onOriginDateSelected(date);
    }

    @Override
    public void onOriginTimeSelected(String time) {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(0)).commit();
        onBackPressed();
        findCourierFragment.onOriginTimeSelected(time);
    }

    @Override
    public void onDestinationSelected(Location destinationLocation) {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(0)).commit();
        onBackPressed();
        findCourierFragment.onDestinationSelected(destinationLocation);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
    }
}
