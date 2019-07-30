package com.example.ehealth.lab.university.disease.description;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ehealth.lab.university.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Objects;

/**
 * This class contains the Adapter of the disease information.
 *
 * @author Stavroula Kousparou
 *
 * */

public class DiseaseMain extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_pager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.mg);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.what, WhatFragment.class)
                .add(R.string.causes, CauseFragment.class)
                .add(R.string.symp, SymptomsFragment.class)
                .add(R.string.diagnosis, DiagnosisFragment.class)
                .add(R.string.treatment, TreatmentFragment.class)
                .add(R.string.complication, ComplicationsFragment.class)
                .add(R.string.epidemio, EpidemiologyFragment.class)
                .add(R.string.long_outlook, LongTermFragment.class)
                .add(R.string.association, AssociationFragment.class)
                .create());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }



    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
}
