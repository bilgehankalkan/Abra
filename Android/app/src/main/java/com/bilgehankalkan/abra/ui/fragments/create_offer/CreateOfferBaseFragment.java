package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bilgehankalkan.abra.interfaces.OnOfferChosenListener;
import com.bilgehankalkan.abra.interfaces.OnSearchOptionListener;
import com.bilgehankalkan.abra.ui.activities.CreateOfferActivity;
import com.bilgehankalkan.abra.ui.activities.MainActivity;
import com.bilgehankalkan.abra.ui.fragments.BaseFragment;
import com.bilgehankalkan.abra.ui.fragments.FindCourierFragment;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CreateOfferBaseFragment extends BaseFragment {

    OnOfferChosenListener onOfferChosenListener;
    OnSearchOptionListener onSearchOptionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof CreateOfferActivity)
                onOfferChosenListener = (OnOfferChosenListener) context;
            else if (context instanceof MainActivity)
                onSearchOptionListener = (OnSearchOptionListener) context;

        } catch (Exception e) {
            Log.e(getTag(), "cannot cast.");
        }
    }
}
