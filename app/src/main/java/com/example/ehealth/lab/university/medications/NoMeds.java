package com.example.ehealth.lab.university.medications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.Objects;

/**
 * This class shows the Prohibitively Medication.
 *
 * @author Stavroula Kousparou
 */

public class NoMeds extends AppCompatActivity {

    private Context context;
    private TextView mainMedButton, allMedButton, noMedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_med);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.no_meds);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = this;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                Intent intent = new Intent(context, AllMeds.class);
                startActivity(intent);
            }
        });

        menuBar();

    }

    // the menu bar in the bottom of this page
    private void menuBar() {
        mainMedButton = (TextView) findViewById(R.id.btn_MainMed);
        allMedButton = (TextView) findViewById(R.id.btn_AllMed);
        noMedButton = (TextView) findViewById(R.id.btn_noMed);

        mainMedButton.setTextColor(Color.parseColor("#3C515C"));
        allMedButton.setTextColor(Color.parseColor("#3C515C"));
        noMedButton.setTextColor(Color.parseColor("#00BCD4"));

        mainMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                mainMedButton.setTextColor(Color.parseColor("#00BCD4"));
                allMedButton.setTextColor(Color.parseColor("#3C515C"));
                noMedButton.setTextColor(Color.parseColor("#3C515C"));

                Intent intent = new Intent(context, MedProgram.class);
                startActivity(intent);
            }
        });

        allMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                mainMedButton.setTextColor(Color.parseColor("#3C515C"));
                allMedButton.setTextColor(Color.parseColor("#00BCD4"));
                noMedButton.setTextColor(Color.parseColor("#3C515C"));

                Intent intent = new Intent(context, AllMeds.class);
                startActivity(intent);
            }
        });

        noMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                mainMedButton.setTextColor(Color.parseColor("#3C515C"));
                allMedButton.setTextColor(Color.parseColor("#3C515C"));
                noMedButton.setTextColor(Color.parseColor("#00BCD4"));

                Intent intent = new Intent(context, NoMeds.class);
                startActivity(intent);
            }
        });
    }
}
