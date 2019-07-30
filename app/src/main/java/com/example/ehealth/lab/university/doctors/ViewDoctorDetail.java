package com.example.ehealth.lab.university.doctors;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.Objects;

/**
 * This class shows all data for one specific doctor that the user select.
 *
 * @author Stavroula Kousparou
 */

public class ViewDoctorDetail extends AppCompatActivity {

    private DoctorsDatabase myDoctorDB;

    private TextView name, speciality, phone1, phone2, email, notes;
    private TextView phoneType1, phoneType2, id;

    private String name_str, speciality_str, phone1_str, phone2_str, email_str, notes_str;
    private String phoneType1_str, phoneType2_str, id_str;

    private ImageView deleteBtn, editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.myDoctorsInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        name = (TextView)findViewById(R.id.nameText);
        speciality = (TextView)findViewById(R.id.specialityText);
        phone1 = (TextView)findViewById(R.id.phone1Text);
        phoneType1 = (TextView)findViewById(R.id.typePhone1);
        phone2 = (TextView)findViewById(R.id.phone2Text);
        phoneType2 = (TextView)findViewById(R.id.typePhone2);
        email = (TextView)findViewById(R.id.emailText);
        notes = (TextView)findViewById(R.id.notesText);

        myDoctorDB = new DoctorsDatabase(this);

       //Get data from Doctors Main class
        Intent intent = getIntent();
        if(intent != null){
            id_str = intent.getStringExtra("id");
            name_str = intent.getStringExtra("name");
            speciality_str = intent.getStringExtra("speciality");
            phone1_str = intent.getStringExtra("phone1");
            phoneType1_str = intent.getStringExtra("type_phone1");
            phone2_str = intent.getStringExtra("phone2");
            phoneType2_str = intent.getStringExtra("type_phone2");
            email_str = intent.getStringExtra("email");
            notes_str = intent.getStringExtra("notes");
        }

        // set texts in viewDoctorDetail layout
        name.setText(name_str);
        speciality.setText(speciality_str);

        if(phone1_str.compareTo("0") == 0){
            phone1.setText(" - ");
        }else{
            phone1.setText(phone1_str);
        }

        phoneType1.setText(phoneType1_str);

        if(phone2_str.compareTo("0") == 0){
            phone2.setText(" - ");
        }else{
            phone2.setText(phone2_str);
        }

        phoneType2.setText(phoneType2_str);


        if(email_str.isEmpty()){
            email.setText(" - ");
        }else{
            email.setText(email_str);
        }

        if(notes_str.isEmpty()){
            notes.setText(" - ");
        }else{
            notes.setText(notes_str);
        }


        deleteBtn = (ImageView) findViewById(R.id.binImage) ;
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = deleteConfirm();
                alertDialog.show();
            }
        });


        editBtn = (ImageView) findViewById(R.id.editImage);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intEdit = new Intent(ViewDoctorDetail.this, UpdateDoctor.class);
                intEdit.putExtra("id", id_str);
                intEdit.putExtra("name", name_str);
                intEdit.putExtra("speciality", speciality_str);
                intEdit.putExtra("phone1", phone1_str);
                intEdit.putExtra("type_phone1", phoneType1_str);
                intEdit.putExtra("phone2", phone2_str);
                intEdit.putExtra("type_phone2", phoneType2_str);
                intEdit.putExtra("email", email_str);
                intEdit.putExtra("notes", notes_str);
                startActivity(intEdit);
            }
        });


    }


    // delete confirmation
    private AlertDialog deleteConfirm(){
        AlertDialog alertDialogDelete = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete the doctor with name " + name_str + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDoctorDB.deleteDoctor(id_str);
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        return alertDialogDelete;
    }






}
