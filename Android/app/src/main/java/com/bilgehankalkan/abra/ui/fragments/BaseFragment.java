package com.bilgehankalkan.abra.ui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.bilgehankalkan.abra.ui.activities.BaseActivity;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class BaseFragment extends Fragment {

    public BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (BaseActivity) context;
    }
}
