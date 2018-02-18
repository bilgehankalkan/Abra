package com.bilgehankalkan.abra.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilgehankalkan.abra.R;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class MyProfileFragment extends BaseFragment {

    ImageView imageAvatar;
    TextView textUserName, textExtraInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        imageAvatar = rootView.findViewById(R.id.image_view_avatar_my_profile_fragment);
        textUserName = rootView.findViewById(R.id.text_view_username_my_profile_fragment);
        textExtraInfo = rootView.findViewById(R.id.text_view_extra_info_my_profile_fragment);

        textUserName.setText("lsgmlemgelmhels");
        textExtraInfo.setText("g nangjangaeg");
        imageAvatar.setImageResource(R.mipmap.ic_launcher_round);

        return rootView;
    }
}
