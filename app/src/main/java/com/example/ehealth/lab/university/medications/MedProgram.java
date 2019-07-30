package com.example.ehealth.lab.university.medications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * Created by Stavroula Kousparou on 3/16/2019.
 */

public class MedProgram extends AppCompatActivity {

    private MedicationDatabase myMedicationDB;

    private TextView allMedButton, mainMedButton, noMedButton;
    private Context context;

    private SwipeRefreshLayout refreshList;
    private ListView listDayMed;
    private ArrayList<Schedule> list_day_med = new ArrayList<>();
    private ArrayList<Medication> list_med = new ArrayList<>();

    private myAdapterSchedule myAdapterSchedule;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.all_meds);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                Intent intent = new Intent(context, AllMeds.class);
                startActivity(intent);            }
        });

        context = this;

        listDayMed = (ListView) findViewById(R.id.list_day_med);
        // Medication Database
        myMedicationDB = new MedicationDatabase(this);
        list_day_med = new ArrayList<>();
        refreshList = (SwipeRefreshLayout) findViewById(R.id.refreshMedicationList);
        refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                ///////////////////////
            }
        });


        loadDataInListView();
        menuBar();

    }

    private void loadDataInListView() {
        list_day_med = myMedicationDB.getAllDataSchedule();
        for (int i = 0; i < list_day_med.size(); i++){

            if(list_day_med.get(i).getTime1().compareTo("0") != 0) {
                list_med = myMedicationDB.getAllData();
                myAdapterSchedule = new myAdapterSchedule(context, list_day_med, list_med);
                listDayMed.setAdapter(myAdapterSchedule);
                myAdapterSchedule.notifyDataSetChanged();
            }
            if(list_day_med.get(i).getTime2().compareTo("0") != 0) {
                list_med = myMedicationDB.getAllData();
                myAdapterSchedule = new myAdapterSchedule(context, list_day_med, list_med);
                listDayMed.setAdapter(myAdapterSchedule);
                myAdapterSchedule.notifyDataSetChanged();
            }
        }


    }

    private void menuBar(){
        mainMedButton = (TextView) findViewById(R.id.btn_MainMed);
        allMedButton = (TextView) findViewById(R.id.btn_AllMed);
        noMedButton = (TextView) findViewById(R.id.btn_noMed);

        mainMedButton.setTextColor(Color.parseColor("#00BCD4"));
        allMedButton.setTextColor(Color.parseColor("#3C515C"));
        noMedButton.setTextColor(Color.parseColor("#3C515C"));

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
    private void refreshContent(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                list_day_med.clear();
                loadDataInListView();
                refreshList.setRefreshing(false);

            }
        });
    }

}
