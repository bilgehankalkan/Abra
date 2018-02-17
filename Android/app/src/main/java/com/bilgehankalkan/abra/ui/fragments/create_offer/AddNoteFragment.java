package com.bilgehankalkan.abra.ui.fragments.create_offer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bilgehankalkan.abra.R;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class AddNoteFragment extends CreateOfferBaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_note, container, false);

        EditText editTextNote = rootView.findViewById(R.id.edit_text_add_note_fragment);
        CardView cardViewPost = rootView.findViewById(R.id.card_view_add_note_fragment);

        cardViewPost.setOnClickListener(v -> onOfferChosenListener.onNotesAdded(editTextNote.getText().toString()));

        return rootView;
    }
}
