package com.example.ehealth.lab.university.doctors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.Objects;

/**
 * This class shows the layout that the user gives the information of a doctor
 * and then save this information in database (DoctorDatabase). The doctor's name
 * is a required field.
 *
 * @author Stavroula Kousparou
 */

public class AddDoctor extends AppCompatActivity {

    DoctorsDatabase myDoctorDB;

    Context context;
    private TextInputLayout textInputName, textInputSpeciality;
    private TextInputLayout textInputEmail, textInputNotes;
    private TextInputLayout textInputPhone1, textInputPhone2;
    private Button saveButton;

    private String name, speciality, email, notes;
    private String phone1, type1, phone2, type2;

    private Spinner spinnerPhone1, spinnerPhone2;
    private CustomSpinners typePhone1 = new CustomSpinners();
    private CustomSpinners typePhone2 = new CustomSpinners();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_doctor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.add_doctor_title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                Intent intent = new Intent(context, DoctorsMain.class);
                startActivity(intent);            }
        });

        // declare the views
        textInputName = findViewById(R.id.doctor_name);
        textInputSpeciality = findViewById(R.id.speciality);

        textInputEmail = findViewById(R.id.emailText);
        textInputNotes = findViewById(R.id.notes);

        textInputPhone1 = findViewById(R.id.phone1Text);
        textInputPhone2 = findViewById(R.id.phone2Text);

        spinnerPhone1 = findViewById(R.id.phoneSpinner1);
        spinnerPhone1.setOnItemSelectedListener(typePhone1);

        spinnerPhone2 = findViewById(R.id.phoneSpinner2);
        spinnerPhone2.setOnItemSelectedListener(typePhone2);

        context = this;
        saveButton = findViewById(R.id.btnSaveDoc);

        myDoctorDB = new DoctorsDatabase(this);


        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                name = textInputName.getEditText().getText().toString().trim();
                speciality = textInputSpeciality.getEditText().getText().toString().trim();
                phone1 = textInputPhone1.getEditText().getText().toString().trim();
                type1 = String.valueOf(typePhone1.getPhoneType1());
                phone2 = textInputPhone2.getEditText().getText().toString().trim();
                type2 = String.valueOf(typePhone2.getPhoneType2());
                email = textInputEmail.getEditText().getText().toString().trim();
                notes = textInputNotes.getEditText().getText().toString().trim();

                int phone1_int;
                int phone2_int;
                if (phone1.compareTo("") == 0){
                    phone1_int = 0;
                }else{
                    phone1_int = Integer.parseInt(phone1);
                }
                if (phone2.compareTo("") == 0){
                    phone2_int = 0;
                }else {
                    phone2_int = Integer.parseInt(phone2);
                }


                if (validateNameOfDoctor() == false) {
                    Toast.makeText(getApplicationContext(), getString(R.string.TheNameIsRequires_Doctor), Toast.LENGTH_LONG).show();
                }else{
                    // function that insert the data in database
                    insertData(name, speciality, phone1_int, type1, phone2_int, type2, email, notes);

                }

                confirmDoctor(v);

            }
        });
    }


    /**
     * This function insert the doctor's data in the accordingly database.
     *
     * @param name The name of doctor.
     * @param speciality The speciality of doctor.
     * @param phoneNumber1 The first phone.
     * @param typeNumber1 The type of first phone.
     * @param phoneNumber2 The second phone.
     * @param typeNumber2 The type of second type.
     * @param email The doctor's email.
     * @param notes Extra notes.
     *
     * */
    public void insertData(String name, String speciality, int phoneNumber1, String typeNumber1, int phoneNumber2, String typeNumber2, String email, String notes){
        boolean insert_data = myDoctorDB.insertRecordForDoctor(name, speciality, phoneNumber1, typeNumber1, phoneNumber2, typeNumber2, email, notes);
        String text = "";
        if(insert_data){
            text = getString(R.string.saved);
        } else{
            text = getString(R.string.somethingWentWrong);
        }

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

    }


    // checks if the field of name is empty
    private boolean validateNameOfDoctor() {
        String name = textInputName.getEditText().getText().toString();

        if (name.isEmpty()) {
            textInputName.setError(getString(R.string.fieldCanNotBeEmpty));
            return false;
        } else {
            textInputName.setError(null);
            return true;
        }
    }

    /**
     * Confirm if the user didn't give a name for a doctor.
     *
     * @param v
     * */
    public void confirmDoctor(View v) {
        if (!validateNameOfDoctor()) {   // | we want to show all the error message
            return;
        }else{
            Intent intent = new Intent(context, DoctorsMain.class);
            startActivity(intent);
        }
    }


}
