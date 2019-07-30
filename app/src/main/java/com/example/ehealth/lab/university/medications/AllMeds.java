package com.example.ehealth.lab.university.medications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class contains the list view with all medications.
 * In every item in list view, there is the name and the dosage
 * of medication. The list view is clickable.
 *
 * @author Stavroula Kousparou
 */

public class AllMeds extends AppCompatActivity {

    // Medication Database
    private MedicationDatabase myMedicationDB;

    private ListView allMedList;
    private FloatingActionButton addMed;
    private Context context;
    private SwipeRefreshLayout refreshMedicationList;
    private myAdapterMedication myAdapterMedication;
    private Medication medication;
    private Schedule schedule;

    private ArrayList<Medication> list_med = new ArrayList<>();
    private ArrayList<Schedule> list_schedule = new ArrayList<>();

    private String refillState = "OFF";

    private TextView mainMedButton, allMedButton, noMedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_meds);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_meds);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = this;

        allMedList = (ListView) findViewById(R.id.list_all_med);

        // Medication Database
        myMedicationDB = new MedicationDatabase(this);
        list_med = new ArrayList<>();

        // for refresh the list
        refreshMedicationList = (SwipeRefreshLayout) findViewById(R.id.refreshMedicationList);
        refreshMedicationList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        //go to layout for add a new medication
        addMed = (FloatingActionButton)findViewById(R.id.addMedButton);
        addMed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                Intent intent = new Intent(context, AddMed.class);
                startActivity(intent);

            }
        });

        loadDataInListView();

        menuBar();

        allMedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.vibrate();

                // Get position when click on list item
                medication = myAdapterMedication.getItemMed(position);
                schedule = myAdapterMedication.getItemSch(position);

                int numDosage = medication.getDosage();

                if(medication.getCurrentStock().compareTo("0") == 0){
                    refillState = "OFF";
                }else{
                    refillState = "ON";
                }

                list_schedule = myMedicationDB.getAllDataSchedule();


                // Call to dialog to display the details
                Intent intent = new Intent(getApplicationContext(), ViewMedicationDetail.class);
                // Medication Table
                intent.putExtra("medId", medication.getId());
                intent.putExtra("medname", medication.getName());
                intent.putExtra("dosage", String.valueOf(numDosage));
                intent.putExtra("dosageType", medication.getDosageType());
                intent.putExtra("instructions", medication.getInstructions());
                intent.putExtra("refillState", refillState);
                intent.putExtra("refill_cur", medication.getCurrentStock());
                intent.putExtra("refill_rem", medication.getRemindStock());
                intent.putExtra("refill_remType", medication.getCurrentStockType());
                intent.putExtra("timeRefill", medication.getTimeRef());
                intent.putExtra("medType", medication.getMedType());
                intent.putExtra("doctorId", medication.getDoctorId());

                // Schedule Table
                intent.putExtra("schId", String.valueOf(schedule.getId()));
                intent.putExtra("startDate", schedule.getStartDate());
                intent.putExtra("duration", String.valueOf(schedule.getDuration()));
                intent.putExtra("days", String.valueOf(schedule.getDays()));
                intent.putExtra("timesInDay", String.valueOf(schedule.getTimesInDay()));
                intent.putExtra("time1", schedule.getTime1());
                intent.putExtra("time2", schedule.getTime2());
                intent.putExtra("time3", schedule.getTime3());
                intent.putExtra("time4", schedule.getTime4());
                intent.putExtra("time5", schedule.getTime5());
                intent.putExtra("time6", schedule.getTime6());
                intent.putExtra("time7", schedule.getTime7());
                intent.putExtra("time8", schedule.getTime8());
                intent.putExtra("time9", schedule.getTime9());
                intent.putExtra("time10", schedule.getTime10());
                intent.putExtra("time11", schedule.getTime11());
                intent.putExtra("time12", schedule.getTime12());

                startActivity(intent);

            }
        });

    }

    // the menu bar in the bottom of page
    private void menuBar() {
        mainMedButton = (TextView) findViewById(R.id.btn_MainMed);
        allMedButton = (TextView) findViewById(R.id.btn_AllMed);
        noMedButton = (TextView) findViewById(R.id.btn_noMed);

        mainMedButton.setTextColor(Color.parseColor("#3C515C"));
        allMedButton.setTextColor(Color.parseColor("#00BCD4"));
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

    // load the data from Medication and Schedule Tables into list view
    private void loadDataInListView() {
        list_med = myMedicationDB.getAllData();
        list_schedule = myMedicationDB.getAllDataSchedule();
        myAdapterMedication = new myAdapterMedication(context, list_med, list_schedule);
        allMedList.setAdapter(myAdapterMedication);
        myAdapterMedication.notifyDataSetChanged();

    }

    private void refreshContent(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                list_med.clear();
                loadDataInListView();
                refreshMedicationList.setRefreshing(false);

            }
        });
    }

}





