package com.example.ehealth.lab.university.disease.description;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehealth.lab.university.R;

/**
 * This class contains information of the causes of MG.
 *
 * @author Stavroula Kousparou
 */

public class CauseFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mg_causes, container, false);
        return view;
    }


}
