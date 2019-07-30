package com.example.ehealth.lab.university.disease.description;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;

/**
 * This class contains information about the Cyprus Association of MG.
 *
 * @author Stavroula Kousparou
 *
 */

public class AssociationFragment extends Fragment {

    private TextView link_website;
    private TextView link_facebook;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        link_website = (TextView)getView().findViewById(R.id.website);
        link_website.setMovementMethod(LinkMovementMethod.getInstance());

        link_facebook = (TextView)getView().findViewById(R.id.facebook);
        link_facebook.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
