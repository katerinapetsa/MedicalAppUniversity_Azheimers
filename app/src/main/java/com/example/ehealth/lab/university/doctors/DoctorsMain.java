package com.example.ehealth.lab.university.doctors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class shows a list with all doctors. In every item in list view,
 * there is the name, speciality and the first phone number of doctor.
 * The list view is clickable.
 *
 * @author Stavroula Kousparou
 *
 */

public class DoctorsMain extends AppCompatActivity {

    private DoctorsDatabase myDoctorDB;
    private ListView allDoctorsList;
    private ArrayList<Doctor> list_doctor = new ArrayList<>();
    private myAdapterDoctor myAdapterDoctor;
    private Doctor doctor;

    private SwipeRefreshLayout refreshDoctorList;

    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_doctors);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.myDoctorsInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = this;


        FloatingActionButton addDoctor = findViewById(R.id.floatingAddDoctor);
        allDoctorsList = (ListView)findViewById(R.id.list_view_doctor);

        // Doctor's database
        myDoctorDB = new DoctorsDatabase(this);
        list_doctor = new ArrayList<>();

        // refresh the list view
        refreshDoctorList = (SwipeRefreshLayout)findViewById(R.id.refreshDoctorList);
        refreshDoctorList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        addDoctor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                Intent intent = new Intent(context, AddDoctor.class);
                startActivity(intent);

            }
        });


        loadDataInListView();


        allDoctorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get position when click on list item
                doctor = myAdapterDoctor.getItem(position);

                // Call to dialog to display the details
                Intent intent = new Intent(getApplicationContext(), ViewDoctorDetail.class);
                intent.putExtra("id", doctor.getId());
                intent.putExtra("name", doctor.getName());
                intent.putExtra("speciality", doctor.getSpeciality());
                intent.putExtra("phone1", String.valueOf(doctor.getPhone1()));
                intent.putExtra("type_phone1", doctor.getType_phone1());
                intent.putExtra("phone2", String.valueOf(doctor.getPhone2()));
                intent.putExtra("type_phone2", doctor.getType_phone2());
                intent.putExtra("email", doctor.getEmail());
                intent.putExtra("notes", doctor.getNotes());
                startActivity(intent);
            }
        });

    }

    // load the data from database to list view
    private void loadDataInListView() {
        list_doctor = myDoctorDB.getAllData();
        myAdapterDoctor = new myAdapterDoctor(this, list_doctor);
        allDoctorsList.setAdapter(myAdapterDoctor);
        myAdapterDoctor.notifyDataSetChanged();

    }

    // to refresh the list view
    private void refreshContent(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                list_doctor.clear();
                loadDataInListView();
                refreshDoctorList.setRefreshing(false);

            }
        });
    }

}

