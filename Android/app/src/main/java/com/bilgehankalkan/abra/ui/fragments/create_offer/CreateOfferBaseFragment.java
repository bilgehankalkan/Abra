package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.content.Context;
import android.util.Log;

import com.bilgehankalkan.abra.interfaces.OnOfferChosenListener;
import com.bilgehankalkan.abra.ui.fragments.BaseFragment;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class CreateOfferBaseFragment extends BaseFragment {

    OnOfferChosenListener onOfferChosenListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onOfferChosenListener = (OnOfferChosenListener) context;
        } catch (Exception e) {
            Log.e(getTag(), "cannot cast.");
        }
    }
}
