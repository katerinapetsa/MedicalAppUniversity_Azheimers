package com.example.ehealth.lab.university.doctors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
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
 * Update the data of a doctor record.
 *
 * @author Stavroula Kousparou
 */

public class UpdateDoctor extends AppCompatActivity {

    private DoctorsDatabase myDoctorDB;

    private Context context;
    private TextInputEditText edName, edSpeciality, edPhone1, edPhone2, edEmail, edNotes;
    private String phoneType1_str, phoneType2_str;
    private Spinner spinnerPhone1, spinnerPhone2;
    private CustomSpinners typePhone1 = new CustomSpinners();
    private CustomSpinners typePhone2 = new CustomSpinners();


    private String id_str, name_str, speciality_str, phone1_str, phone2_str, email_str, notes_str;
    private Button updateSave;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_doctor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.updateDoctorInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                finish();
            }
        });

        context = this;

        myDoctorDB = new DoctorsDatabase(this);

        edName = (TextInputEditText)findViewById(R.id.edName);
        edSpeciality = (TextInputEditText)findViewById(R.id.edSpeciality);
        edPhone1 = (TextInputEditText)findViewById(R.id.edPhone1);
        spinnerPhone1 = (Spinner)findViewById(R.id.phoneSpinner1);
        edPhone2 = (TextInputEditText)findViewById(R.id.edPhone2);
        spinnerPhone2 = (Spinner)findViewById(R.id.phoneSpinner2);
        edEmail = (TextInputEditText)findViewById(R.id.edEmail);
        edNotes = (TextInputEditText)findViewById(R.id.edNotes);

        spinnerPhone1.setOnItemSelectedListener(typePhone1);
        spinnerPhone2.setOnItemSelectedListener(typePhone2);

        Intent update_intent = getIntent();
        id_str = update_intent.getStringExtra("id");
        name_str = update_intent.getStringExtra("name");
        speciality_str = update_intent.getStringExtra("speciality");
        phone1_str = update_intent.getStringExtra("phone1");
        phoneType1_str = update_intent.getStringExtra("type_phone1");
        phone2_str = update_intent.getStringExtra("phone2");
        phoneType2_str = update_intent.getStringExtra("type_phone2");
        email_str = update_intent.getStringExtra("email");
        notes_str = update_intent.getStringExtra("notes");

        edName.setText(name_str);
        edSpeciality.setText(speciality_str);

        if (phone1_str.compareTo("0") == 0){
            edPhone1.setText("");
        }else {
            edPhone1.setText(phone1_str);
        }

        if (phone2_str.compareTo("0") == 0){
            edPhone2.setText("");
        }else {
            edPhone2.setText(phone2_str);
        }
        edEmail.setText(email_str);
        edNotes.setText(notes_str);

        if(phoneType1_str.equals(getString(R.string.phoneMobile))){
            spinnerPhone1.post(new Runnable() {
                public void run() {
                    spinnerPhone1.setSelection(0);
                }
            });
        }else if(phoneType1_str.equals(getString(R.string.phoneWork))){
            spinnerPhone1.post(new Runnable() {
                public void run() {
                    spinnerPhone1.setSelection(1);
                }
            });
        }

        if(phoneType2_str.equals(getString(R.string.phoneMobile))){
            spinnerPhone2.post(new Runnable() {
                public void run() {
                    spinnerPhone2.setSelection(0);
                }
            });
        }else if(phoneType1_str.equals(getString(R.string.phoneWork))){
            spinnerPhone2.post(new Runnable() {
                public void run() {
                    spinnerPhone2.setSelection(1);
                }
            });

        }

        updateSave = (Button)findViewById(R.id.btnSaveUpdate);
        updateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();

                myDoctorDB.updateData(id_str, edName.getText().toString().trim(),
                        edSpeciality.getText().toString().trim(),
                        edPhone1.getText().toString().trim(),
                        String.valueOf(typePhone1.getPhoneType1()),
                        edPhone2.getText().toString().trim(),
                        String.valueOf(typePhone2.getPhoneType1()),
                        edEmail.getText().toString().trim(),
                        edNotes.getText().toString().trim());

                Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();

                MainActivity.vibrate();
                Intent intent_med = new Intent(context, DoctorsMain.class);
                startActivity(intent_med);

            }
        });
    }
}
