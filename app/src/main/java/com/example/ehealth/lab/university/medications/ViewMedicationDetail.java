package com.example.ehealth.lab.university.medications;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.doctors.DoctorsDatabase;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.Objects;

/**
 * Created by Stavroula Kousparou on 4/12/2019.
 */

public class ViewMedicationDetail extends AppCompatActivity implements DialogRefill.DialogRefillListener {

    private static final int COLOR_PRIMARY_DARK = Color.parseColor("#142E3C");

    private MedicationDatabase myMedicationDB;
    private DoctorsDatabase myDoctorDB;

    // medication
    private TextView name, dosage, dosageType, instructions, medType, refillTime, currentStock, newCurrent, curType, refillCur,dosRefill, dosTypeRefill, myDoctorDetail;
    private ImageView medTypeImage;

    private String id_str, name_str, dosage_str, dosage_type_str, instructions_str, med_type_str;
    private String cur_stock_str, rem_stock_str, rem_type_str, refill_state_str,time_refill;
    private String doc_id_str = "0";
    private int myDoctorId = 0;

    // View for schedule
    private TextView startDate, duration, days;
    private TextView time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11, time12;

    // Buttons - ImageView
    private ImageView btnEdit, btnDelete;
    private Button  btnRefill;

    // strings
    private String newRefill;
    private int newCur;
    private String startDate_str, duration_str, days_str, timesInDay_str;
    private String time1_str, time2_str, time3_str, time4_str, time5_str, time6_str, time7_str, time8_str,
                   time9_str, time10_str, time11_str, time12_str;
    private String am_pm;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_medication);


       declareViews();

        myMedicationDB = new MedicationDatabase(this);
        myDoctorDB = new DoctorsDatabase(this);


        // get data
        Intent intent = getIntent();
        if(intent != null){
            // Medication Table
            id_str = intent.getStringExtra("medId");
            name_str = intent.getStringExtra("medname");
            dosage_str = intent.getStringExtra("dosage");
            dosage_type_str = intent.getStringExtra("dosageType");
            instructions_str = intent.getStringExtra("instructions");
            cur_stock_str = intent.getStringExtra("refill_cur");
            refill_state_str = intent.getStringExtra("refillState");
            rem_stock_str = intent.getStringExtra("refill_rem");
            rem_type_str = intent.getStringExtra("refill_remType");
            time_refill = intent.getStringExtra("timeRefill");
            med_type_str = intent.getStringExtra("medType");
            doc_id_str = intent.getStringExtra("doctorId");

            // Schedule Table
            startDate_str = intent.getStringExtra("startDate");
            duration_str = intent.getStringExtra("duration");
            days_str = intent.getStringExtra("days");
            timesInDay_str = intent.getStringExtra("timesInDay");
            time1_str = intent.getStringExtra("time1");
            time2_str = intent.getStringExtra("time2");
            time3_str = intent.getStringExtra("time3");
            time4_str = intent.getStringExtra("time4");
            time5_str = intent.getStringExtra("time5");
            time6_str = intent.getStringExtra("time6");
            time7_str = intent.getStringExtra("time7");
            time8_str = intent.getStringExtra("time8");
            time9_str = intent.getStringExtra("time9");
            time10_str = intent.getStringExtra("time10");
            time11_str = intent.getStringExtra("time11");
            time12_str = intent.getStringExtra("time12");

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(name_str);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String myDoctor;
        if(doc_id_str.compareTo(getString(R.string.noneDoctor)) == 0){
            myDoctor = " - ";
        }else{
            DoctorsDatabase db = new DoctorsDatabase(getApplicationContext());
            myDoctor = db.getDoctorById(doc_id_str);
        }


        medType.setText(med_type_str);
        if(med_type_str.equals("Tablet")){
            medTypeImage.setImageResource(R.drawable.tablets);

        }else if(med_type_str.equals("Capsule")){
            medTypeImage.setImageResource(R.drawable.capsule);

        }else if(med_type_str.equals("Syrup")){
            medTypeImage.setImageResource(R.drawable.syrup);

        }else if(med_type_str.equals("Drops")){
            medTypeImage.setImageResource(R.drawable.drops);

        }else if(med_type_str.equals("Injection")){
            medTypeImage.setImageResource(R.drawable.injection);

        }else if(med_type_str.equals("Spray")){
            medTypeImage.setImageResource(R.drawable.spray);

        }else{
            medTypeImage.setImageResource(R.drawable.tablets);

        }

        dosage.setText(dosage_str);
        dosageType.setText(dosage_type_str);
        instructions.setText(instructions_str);
/*
        if(time_refill.compareTo("0") != 0 ){
            am_pm = AM_PM(time_refill);
        }else {
            am_pm = " ";
        }
*/

        // to make the text bold
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

        // refill
        if(refill_state_str.compareTo("ON") == 0){
            refillTime.setText(getString(R.string.remindMeAT) + " " + time_refill + " " + AM_PM(time_refill) + "!");
            newCurrent.setText("  " + cur_stock_str);
            newCurrent.setTypeface(boldTypeface);

            curType.setText(dosage_type_str);
            curType.setTypeface(boldTypeface);

            refillCur.setText(getString(R.string.whenCurrentStockIs));
            dosRefill.setText(rem_stock_str);
            dosRefill.setTypeface(boldTypeface);

            dosTypeRefill.setText(dosage_type_str + ".");
            dosTypeRefill.setTypeface(boldTypeface);

        }else if (refill_state_str.compareTo("OFF") == 0) {
            currentStock.setText(getString(R.string.dontRemindMe));
            refillTime.setVisibility(View.GONE);
            refillCur.setVisibility(View.GONE);
            newCurrent.setVisibility(View.GONE);
            curType.setVisibility(View.GONE);
            dosRefill.setText("");
            dosTypeRefill.setText("");
        }


        // to refill
        btnRefill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(cur_stock_str);
            }
        });

        //date that started the med
        startDate.setText(startDate_str);

        // how long take this med
        if(duration_str.compareTo("0") == 0){
            duration.setText("");
        }else if (duration_str.compareTo("0") != 0){
            duration.setText(getString(R.string.takeIt) +" " + duration_str + " " + getString(R.string.DaysView));
        }

        // every how many days
        if(days_str.compareTo("0") == 0){
            days.setText(getString(R.string.everyDay));
        }else if (days_str.compareTo("0") != 0){
            days.setText(getString(R.string.everyInterval) + " " + days_str + " " + getString(R.string.DaysView));
        }


        // times
        if(time1_str.compareTo("0") != 0){
            time1.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time1_str);
            time1.setText(time1_str + "  " + am_pm);
        }
        if(time2_str.compareTo("0") != 0){
            time2.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time2_str);
            time2.setText(time2_str + "  " + am_pm);
        }
        if(time3_str.compareTo("0") != 0){
            time3.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time3_str);
            time3.setText(time3_str + "  " + am_pm);
        }
        if(time4_str.compareTo("0") != 0){
            time4.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time4_str);
            time4.setText(time4_str + "  " + am_pm);
        }
        if(time5_str.compareTo("0") != 0){
            time5.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time5_str);
            time5.setText(time5_str + "  " + am_pm);
        }
        if(time6_str.compareTo("0") != 0){
            time6.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time6_str);
            time6.setText(time6_str + "  " + am_pm);
        }
        if(time7_str.compareTo("0") != 0){
            time7.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time7_str);
            time7.setText(time7_str + "  " + am_pm);
        }
        if(time8_str.compareTo("0") != 0){
            time8.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time8_str);
            time8.setText(time8_str + "  " + am_pm);

        } if(time9_str.compareTo("0") != 0) {
            time9.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time9_str);
            time9.setText(time9_str + "  " +am_pm);
        }
        if(time10_str.compareTo("0") != 0){
            time10.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time10_str);
            time10.setText(time10_str + "  " + am_pm);
        }
        if(time11_str.compareTo("0") != 0){
            time11.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time11_str);
            time11.setText(time11_str + "  " + am_pm);
        }
        if(time12_str.compareTo("0") != 0){
            time12.setVisibility(View.VISIBLE);
            am_pm = AM_PM(time12_str);
            time12.setText(time12_str + "  " + am_pm);
        }

        // Doctor - name and speciality
        myDoctorDetail.setText(myDoctor);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = deleteConfirm();
                alertDialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intEdit = new Intent(ViewMedicationDetail.this, UpdateMed.class);
                intEdit.putExtra("medId", id_str);
                intEdit.putExtra("medname", name_str);
                intEdit.putExtra("dosage", dosage_str);
                intEdit.putExtra("dosageType", dosage_type_str);
                intEdit.putExtra("instructions", instructions_str);
                intEdit.putExtra("refillState", refill_state_str);
                intEdit.putExtra("refill_cur", cur_stock_str);
                intEdit.putExtra("refill_rem", rem_stock_str);
                intEdit.putExtra("refill_remType", rem_type_str);
                intEdit.putExtra("timeRefill", time_refill);
                intEdit.putExtra("medType", med_type_str);
                intEdit.putExtra("doctorId", doc_id_str);

                // schedule
                intEdit.putExtra("startDate", startDate_str);
                intEdit.putExtra("duration", duration_str);
                intEdit.putExtra("days", days_str);
                intEdit.putExtra("timesInDay", timesInDay_str);
                intEdit.putExtra("time1", time1_str);
                intEdit.putExtra("time2", time2_str);
                intEdit.putExtra("time3", time3_str);
                intEdit.putExtra("time4", time4_str);
                intEdit.putExtra("time5", time5_str);
                intEdit.putExtra("time6", time6_str);
                intEdit.putExtra("time7", time7_str);
                intEdit.putExtra("time8", time8_str);
                intEdit.putExtra("time9", time9_str);
                intEdit.putExtra("time10", time10_str);
                intEdit.putExtra("time11", time11_str);
                intEdit.putExtra("time12", time12_str);

                startActivity(intEdit);
            }
        });


    }


    private void openDialog(String remStock) {
        DialogRefill dialogRefill = new DialogRefill();
        dialogRefill.takeNumRefill(remStock);
        dialogRefill.show(getSupportFragmentManager(), "refill dialog");

    }


    //  delete confirmation
    private AlertDialog deleteConfirm(){
        AlertDialog alertDialogDelete = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete the medication with name " + name_str + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myMedicationDB.deleteMedication(id_str);
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

    // define if the time is AM or PM
    private String AM_PM(String mytime) {

        // AM / PM for refill time
        String[] split_time = mytime.split(":");
        String hour = split_time[0];
        int hourInt = Integer.parseInt(hour);
        String am_pm = "";
        if(hourInt < 12){
            am_pm = getString(R.string.AM);
        }else if(hourInt > 12){
            am_pm = getString(R.string.PM);
        }
        return am_pm;
    }

    // declare the views
    private void declareViews() {

        //name = (TextView)findViewById(R.id.nameMedText);
        dosage = (TextView)findViewById(R.id.dosageNum);
        dosageType = (TextView)findViewById(R.id.dosageType);
        instructions = (TextView)findViewById(R.id.instructions);
        medType = (TextView)findViewById(R.id.medTypeView);
        refillTime = (TextView)findViewById(R.id.timeRefill);
        currentStock = (TextView)findViewById(R.id.CurrentStock);
        curType = (TextView)findViewById(R.id.dosTypeCur);
        newCurrent = (TextView)findViewById(R.id.NewCurrent);
        refillCur = (TextView)findViewById(R.id.curRefill);
        dosRefill = (TextView)findViewById(R.id.dosRefill);
        dosTypeRefill = (TextView)findViewById(R.id.dosTypeRefill);

        medTypeImage = (ImageView)findViewById(R.id.medImg);
        myDoctorDetail = (TextView)findViewById(R.id.myDoctorName);

        startDate = (TextView)findViewById(R.id.startDateText);
        duration = (TextView)findViewById(R.id.duration);
        days = (TextView)findViewById(R.id.days);
        time1 = (TextView)findViewById(R.id.time1);
        time2 = (TextView)findViewById(R.id.time2);
        time3 = (TextView)findViewById(R.id.time3);
        time4 = (TextView)findViewById(R.id.time4);
        time5 = (TextView)findViewById(R.id.time5);
        time6 = (TextView)findViewById(R.id.time6);
        time7 = (TextView)findViewById(R.id.time7);
        time8 = (TextView)findViewById(R.id.time8);
        time9 = (TextView)findViewById(R.id.time9);
        time10 = (TextView)findViewById(R.id.time10);
        time11 = (TextView)findViewById(R.id.time11);
        time12 = (TextView)findViewById(R.id.time12);

        time1.setVisibility(View.GONE);
        time2.setVisibility(View.GONE);
        time3.setVisibility(View.GONE);
        time4.setVisibility(View.GONE);
        time5.setVisibility(View.GONE);
        time6.setVisibility(View.GONE);
        time7.setVisibility(View.GONE);
        time8.setVisibility(View.GONE);
        time9.setVisibility(View.GONE);
        time10.setVisibility(View.GONE);
        time11.setVisibility(View.GONE);
        time12.setVisibility(View.GONE);



        btnEdit = (ImageView) findViewById(R.id.editImage);
        btnDelete = (ImageView) findViewById(R.id.binImage);
        btnRefill = (Button) findViewById(R.id.btnRefill);


    }


    @Override
    public void applyTexts(String refillMeds) {
        // take the num of meds from dialog box
        newRefill = refillMeds;
        newCur = Integer.parseInt(refillMeds) + Integer.parseInt(cur_stock_str);
        newCurrent.setText(" " + String.valueOf(newCur));
        myMedicationDB.updateRefill(id_str, String.valueOf(newCur));

    }
}
