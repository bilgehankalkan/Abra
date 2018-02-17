package com.bilgehankalkan.abra.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgehankalkan.abra.R;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class InboxFragment extends BaseFragment {

    RecyclerView recyclerViewInbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        recyclerViewInbox = rootView.findViewById(R.id.recycler_view_inbox_fragment);

        recyclerViewInbox.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}
