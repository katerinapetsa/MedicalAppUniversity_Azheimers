package com.example.ehealth.lab.university.medications;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.doctors.Doctor;
import com.example.ehealth.lab.university.doctors.DoctorsDatabase;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * This function shows to the user a form to add a new medication. The name of med is required.
 * Also, the user can gives the dosage, the instructions, the type, the start date, the times that
 * the patient must take the med, the refill information.
 *
 * @author Stavroula Kousparou
 *
 */

public class AddMed extends AppCompatActivity {

    private static final int COLOR_PRIMARY_DARK = Color.parseColor("#142E3C");

    private MedicationDatabase myMedicationDB;
    protected Doctor doctor;
    private int numOfDoctors = 0;
    private Context context;
    private Button save_btn;

    private EditText medNameText, dosageText;
    private String medName, numOfdosage;
    private String dosageType, instr_str;
    private Spinner dosageSpinner, instructionsSpinner;
    private CustomSpinnersMed dosage = new CustomSpinnersMed();
    private CustomSpinnersMed instructions = new CustomSpinnersMed();

    // start date
    private TextView startDate;
    private String start_date_txt;
    private DatePickerDialog.OnDateSetListener startDateSetListenerFrom;
    private Calendar start_date_cal = new GregorianCalendar();

    // schedule - duration
    private RadioGroup radioGroupDuration;
    private RadioButton radioContinuous, radioSomeDays;
    private EditText someDaysText;
    private String duration;
    private int duration_int;

    // schedule - days
    private RadioGroup radioGroupDays;
    private RadioButton radioEveryDay, radioInterval;
    private EditText intervalText;
    private String interval;
    private int interval_int;

    // schedule - times
    private TextView timesText;
    private ImageView plusBtn, minusBtn;
    private String times_str;
    private int times;
    private TextView time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11, time12;
    private TextView line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12;
    private String timeTxt1="0", timeTxt2="0", timeTxt3="0", timeTxt4="0", timeTxt5="0", timeTxt6="0", timeTxt7="0",
                   timeTxt8="0", timeTxt9="0", timeTxt10="0", timeTxt11="0", timeTxt12="0";


    // declaration for medication's type
    private ViewFlipper viewFlipperImage, viewFlipperText;
    private ImageView next_btn, prev_btn;
    private int med_type = 0;

    // doctor spinner
    private ArrayList<String> doctorList = new ArrayList<>();
    private Spinner doctorSpinner;
    private CustomSpinnersMed doc_id = new CustomSpinnersMed();
    private String doc_id_pos;
    private List<String> docId;
    private String docId_str = "";

    // switches
    private Switch reminderSwitch, refillSwitch;
    private String reminderStock, reminderStockType, currentStock, currentStockType;
    private String time_r = "0";
    private TextView reminderStockText, currentlyStockText, timeTxt;
    private Spinner curTypeSpinner, remTypeSpinner;
    private CustomSpinnersMed curType = new CustomSpinnersMed();
    private CustomSpinnersMed remType = new CustomSpinnersMed();
    private LinearLayout linearHor1, linearHor2, linearHor3, timeLinear;
    private TextView remindMe, currStock;

    private String msg = " ";
    private String status_refill_switch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.addNewMed);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        declareVariables();
        context = this;
        myMedicationDB = new MedicationDatabase(this);

        doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);
        loadSpinnerData();

        // spinners
        dosageSpinner.setOnItemSelectedListener(dosage);
        instructionsSpinner.setOnItemSelectedListener(instructions);
        curTypeSpinner.setOnItemSelectedListener(curType);
        remTypeSpinner.setOnItemSelectedListener(remType);
        doctorSpinner.setOnItemSelectedListener(doc_id);

        startDateFun();  // this function contain the start date of the medication
/*
        // 24h Time picker
        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(AddMed.this)
                        .setForced24hFormat();
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });
*/
        // duration radio buttons
        radioSomeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someDaysText.setVisibility(View.VISIBLE);
                someDaysText.setText("30");
            }
        });

        radioContinuous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                someDaysText.setVisibility(View.INVISIBLE);
            }
        });

        // days radio buttons
        radioEveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intervalText.setVisibility(View.INVISIBLE);
            }
        });


        radioInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intervalText.setVisibility(View.VISIBLE);
                intervalText.setText("2");
            }
        });

        times_str = timesText.getText().toString();
        times = Integer.parseInt(times_str);
        setVisibleInTimes();
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(times < 12){
                    times += 1;
                    timesInDay();
                    times_str = String.valueOf(times);
                    timesText.setText(times_str);
                }else if (times == 12){
                    times = 12;
                    timesInDay();
                    timesText.setText("12");
                }

            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(times > 0){
                    times -= 1;
                    timesInDay();
                    times_str = String.valueOf(times);
                    timesText.setText(times_str);
                }else{
                    times = 0;
                    timesInDay();
                    timesText.setText("0");
                }
            }
        });

        setTime(time1);
        setTime(time2);
        setTime(time3);
        setTime(time4);
        setTime(time5);
        setTime(time6);
        setTime(time7);
        setTime(time8);
        setTime(time9);
        setTime(time10);
        setTime(time11);
        setTime(time12);
        setTime(timeTxt);

        /*
        // switch for remind
        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // remind
                } else {
                    // don't remind
                }
            }
        });
*/

        // switch for refill
        refillSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // msg = switchON();
                    currStock.setVisibility(View.VISIBLE);
                    linearHor1.setVisibility(View.VISIBLE);
                    linearHor2.setVisibility(View.VISIBLE);
                    linearHor3.setVisibility(View.VISIBLE);
                    timeLinear.setVisibility(View.VISIBLE);
                    remindMe.setVisibility(View.VISIBLE);
                } else {
                  //  msg = switchOff();
                    remindMe.setVisibility(View.GONE);
                    currStock.setVisibility(View.GONE);
                    linearHor1.setVisibility(View.GONE);
                    linearHor2.setVisibility(View.GONE);
                    linearHor3.setVisibility(View.GONE);
                    timeLinear.setVisibility(View.GONE);
                }
            }
        });

        // Save button
        save_btn = findViewById(R.id.btnSaveMed);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medName = medNameText.getEditableText().toString();
                numOfdosage = dosageText.getEditableText().toString();
                dosageType = String.valueOf(dosage.getDosage());
                instr_str = String.valueOf(instructions.getInstructions());
                currentStockType = String.valueOf(curType.getCurStock());
                reminderStockType = String.valueOf(remType.getRemStock());
                med_type = viewFlipperText.indexOfChild(viewFlipperText.getCurrentView());

                // get the time
                timeTxt1 = time1.getText().toString();
                if(timeTxt1.equals(getString(R.string.clickForTime))){timeTxt1 = "0"; }

                timeTxt2 = time2.getText().toString();
                if(timeTxt2.equals(getString(R.string.clickForTime))){timeTxt2 = "0"; }

                timeTxt3 = time3.getText().toString();
                if(timeTxt3.equals(getString(R.string.clickForTime))){timeTxt3 = "0"; }

                timeTxt4 = time4.getText().toString();
                if(timeTxt4.equals(getString(R.string.clickForTime))){timeTxt4 = "0"; }

                timeTxt5 = time5.getText().toString();
                if(timeTxt5.equals(getString(R.string.clickForTime))){timeTxt5 = "0"; }

                timeTxt6 = time6.getText().toString();
                if(timeTxt6.equals(getString(R.string.clickForTime))){timeTxt6 = "0"; }

                timeTxt7 = time7.getText().toString();
                if(timeTxt7.equals(getString(R.string.clickForTime))){timeTxt7 = "0"; }

                timeTxt8 = time8.getText().toString();
                if(timeTxt8.equals(getString(R.string.clickForTime))){timeTxt8 = "0"; }

                timeTxt9 = time9.getText().toString();
                if(timeTxt9.equals(getString(R.string.clickForTime))){timeTxt9 = "0"; }

                timeTxt10 = time10.getText().toString();
                if(timeTxt10.equals(getString(R.string.clickForTime))){timeTxt10 = "0"; }

                timeTxt11 = time11.getText().toString();
                if(timeTxt11.equals(getString(R.string.clickForTime))){timeTxt11 = "0"; }

                timeTxt12 = time12.getText().toString();
                if(timeTxt12.equals(getString(R.string.clickForTime))){timeTxt12 = "0"; }

                time_r = timeTxt.getText().toString();
                if(time_r.equals(getString(R.string.clickForTime))){time_r = "0"; }

                // duration
                if(radioContinuous.isChecked()){
                    duration = "0";
                }else if(radioSomeDays.isChecked()){
                    duration = someDaysText.getText().toString();
                }
                duration_int = Integer.parseInt(duration);

                // days
                if(radioEveryDay.isChecked()){
                    interval = "0";
                }else if(radioInterval.isChecked()){
                    interval = intervalText.getText().toString();
                    if(interval.compareTo("1") == 0){
                        interval = "0";
                    }else{
                        interval = intervalText.getText().toString();
                    }

                }
                interval_int = Integer.parseInt(interval);


                switchRefill();
                if(status_refill_switch.compareTo("ON") == 0){
                    msg = switchON();
                }else {
                    msg = " ";
                }

                if(status_refill_switch.compareTo("OFF") == 0){
                    msg = switchOff();
                }


                // take the id of doctor
                doc_id_pos = doc_id.getPosition();
                if (Integer.parseInt(doc_id.getPosition()) == (numOfDoctors - 1)){
                    docId_str = getString(R.string.noneDoctor);
                }else {
                    docId_str = docId.get(Integer.parseInt(doc_id_pos));
                }


                // when click in SAVE button
                if (medName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.giveNameForMed), Toast.LENGTH_LONG).show();
                   // Toast.makeText(getApplicationContext(), currentStock, Toast.LENGTH_LONG).show();

                }else if(msg.compareTo(" ") != 0){
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                } else{
                    // insert the date in Medication Table
                    insertMedData(medName, numOfdosage, dosageType, instr_str, med_type, currentStock, currentStockType, reminderStock, reminderStockType, time_r, docId_str);

                    // insert the data in Schedule Table
                    insertScheduleData(start_date_txt, duration_int, interval_int, times, timeTxt1, timeTxt2, timeTxt3, timeTxt4, timeTxt5, timeTxt6, timeTxt7, timeTxt8, timeTxt9, timeTxt10, timeTxt11, timeTxt12);

                    MainActivity.vibrate();
                    Intent intent_med = new Intent(context, AllMeds.class);
                    startActivity(intent_med);
                }

            }

        });

        // View flipper for medications images
        viewFlipperImage = (ViewFlipper) findViewById(R.id.viewFlipperImage);
        viewFlipperText = (ViewFlipper) findViewById(R.id.viewFlipperText);
        next_btn = (ImageView) findViewById(R.id.next);
        prev_btn = (ImageView) findViewById(R.id.prev);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipperImage.showNext();
                viewFlipperText.showNext();

            }
        });

        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipperImage.showPrevious();
                viewFlipperText.showPrevious();
            }
        });


    }

    // get the status of switch - refill
    public void switchRefill() {
        if (refillSwitch.isChecked()) {
            status_refill_switch = refillSwitch.getTextOn().toString();

        } else {
            status_refill_switch = refillSwitch.getTextOff().toString();

        }
    }


    // set visibility in times for the first time
    private void setVisibleInTimes() {
        time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
        time2.setVisibility(View.GONE); line2.setVisibility(View.GONE);
        time3.setVisibility(View.GONE); line3.setVisibility(View.GONE);
        time4.setVisibility(View.GONE); line4.setVisibility(View.GONE);
        time5.setVisibility(View.GONE); line5.setVisibility(View.GONE);
        time6.setVisibility(View.GONE); line6.setVisibility(View.GONE);
        time7.setVisibility(View.GONE); line7.setVisibility(View.GONE);
        time8.setVisibility(View.GONE); line8.setVisibility(View.GONE);
        time9.setVisibility(View.GONE); line9.setVisibility(View.GONE);
        time10.setVisibility(View.GONE); line10.setVisibility(View.GONE);
        time11.setVisibility(View.GONE); line11.setVisibility(View.GONE);
        time12.setVisibility(View.GONE); line12.setVisibility(View.GONE);
    }

    private void timesInDay() {

        if (times == 0) {
            time1.setVisibility(View.GONE); line1.setVisibility(View.GONE);
            time2.setVisibility(View.GONE); line2.setVisibility(View.GONE);
            time3.setVisibility(View.GONE); line3.setVisibility(View.GONE);
            time4.setVisibility(View.GONE); line4.setVisibility(View.GONE);
            time5.setVisibility(View.GONE); line5.setVisibility(View.GONE);
            time6.setVisibility(View.GONE); line6.setVisibility(View.GONE);
            time7.setVisibility(View.GONE); line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE); line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE); line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE); line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE); line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE); line12.setVisibility(View.GONE);

        }else if (times == 1){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.GONE);    line2.setVisibility(View.GONE);
            time3.setVisibility(View.GONE);    line3.setVisibility(View.GONE);
            time4.setVisibility(View.GONE);    line3.setVisibility(View.GONE);
            time5.setVisibility(View.GONE);    line5.setVisibility(View.GONE);
            time6.setVisibility(View.GONE);    line6.setVisibility(View.GONE);
            time7.setVisibility(View.GONE);    line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 2){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.GONE);    line3.setVisibility(View.GONE);
            time4.setVisibility(View.GONE);    line4.setVisibility(View.GONE);
            time5.setVisibility(View.GONE);    line5.setVisibility(View.GONE);
            time6.setVisibility(View.GONE);    line6.setVisibility(View.GONE);
            time7.setVisibility(View.GONE);    line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 3) {
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.GONE);    line4.setVisibility(View.GONE);
            time5.setVisibility(View.GONE);    line5.setVisibility(View.GONE);
            time6.setVisibility(View.GONE);    line6.setVisibility(View.GONE);
            time7.setVisibility(View.GONE);    line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 4) {
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.GONE);    line5.setVisibility(View.GONE);
            time6.setVisibility(View.GONE);    line6.setVisibility(View.GONE);
            time7.setVisibility(View.GONE);    line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);

        }else if(times == 5) {
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.GONE);    line6.setVisibility(View.GONE);
            time7.setVisibility(View.GONE);    line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 6){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.GONE);    line7.setVisibility(View.GONE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 7){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.VISIBLE);    line7.setVisibility(View.VISIBLE);
            time8.setVisibility(View.GONE);    line8.setVisibility(View.GONE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 8){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.VISIBLE);    line7.setVisibility(View.VISIBLE);
            time8.setVisibility(View.VISIBLE);    line8.setVisibility(View.VISIBLE);
            time9.setVisibility(View.GONE);    line9.setVisibility(View.GONE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 9){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.VISIBLE);    line7.setVisibility(View.VISIBLE);
            time8.setVisibility(View.VISIBLE);    line8.setVisibility(View.VISIBLE);
            time9.setVisibility(View.VISIBLE);    line9.setVisibility(View.VISIBLE);
            time10.setVisibility(View.GONE);    line10.setVisibility(View.GONE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 10){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.VISIBLE);    line7.setVisibility(View.VISIBLE);
            time8.setVisibility(View.VISIBLE);    line8.setVisibility(View.VISIBLE);
            time9.setVisibility(View.VISIBLE);    line9.setVisibility(View.VISIBLE);
            time10.setVisibility(View.VISIBLE);    line10.setVisibility(View.VISIBLE);
            time11.setVisibility(View.GONE);    line11.setVisibility(View.GONE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if(times == 11){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.VISIBLE);    line7.setVisibility(View.VISIBLE);
            time8.setVisibility(View.VISIBLE);    line8.setVisibility(View.VISIBLE);
            time9.setVisibility(View.VISIBLE);    line9.setVisibility(View.VISIBLE);
            time10.setVisibility(View.VISIBLE);    line10.setVisibility(View.VISIBLE);
            time11.setVisibility(View.VISIBLE);    line11.setVisibility(View.VISIBLE);
            time12.setVisibility(View.GONE);    line12.setVisibility(View.GONE);
        }else if (times == 12){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.VISIBLE); line2.setVisibility(View.VISIBLE);
            time3.setVisibility(View.VISIBLE); line3.setVisibility(View.VISIBLE);
            time4.setVisibility(View.VISIBLE); line4.setVisibility(View.VISIBLE);
            time5.setVisibility(View.VISIBLE);   line5.setVisibility(View.VISIBLE);
            time6.setVisibility(View.VISIBLE);    line6.setVisibility(View.VISIBLE);
            time7.setVisibility(View.VISIBLE);    line7.setVisibility(View.VISIBLE);
            time8.setVisibility(View.VISIBLE);    line8.setVisibility(View.VISIBLE);
            time9.setVisibility(View.VISIBLE);    line9.setVisibility(View.VISIBLE);
            time10.setVisibility(View.VISIBLE);    line10.setVisibility(View.VISIBLE);
            time11.setVisibility(View.VISIBLE);    line11.setVisibility(View.VISIBLE);
            time12.setVisibility(View.VISIBLE);    line12.setVisibility(View.VISIBLE);
        }

    }

    // the date that the user start the med
    private void startDateFun() {
        Calendar cal1 = Calendar.getInstance();
        final int year = cal1.get(Calendar.YEAR);
        final int month = cal1.get(Calendar.MONTH);
        final int day = cal1.get(Calendar.DAY_OF_MONTH);

        start_date_txt = getString(R.string.today)+ " " + day + "/" + (month + 1) + "/" + year;

        startDate.setText(start_date_txt);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListenerFrom, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //The month in calendar starts from the zero, that's why we plus 1 for the print
        startDateSetListenerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                int day_int = day1;
                int month_int = month1;
                int year_int = year1;

                start_date_cal.set(year1, month1, day1);
                month1 = month1 + 1;

                start_date_txt = day1 + "/" + month1 + "/" + year1;
                startDate.setText(start_date_txt);
            }
        };
    }

    // time picker
    private void setTime(final TextView text){

        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMed.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String h = String.valueOf(selectedHour);
                        String m = String.valueOf(selectedMinute);
                        String time = "";
                        if(selectedHour <= 9){
                            h = "0" + h;
                        }
                        if(selectedMinute <= 9){
                            m = "0" + m;
                        }

                        if(selectedHour <= 9 || selectedMinute <= 9){
                            time = h + ":" + m;
                        }else{
                            time = selectedHour + ":" + selectedMinute;
                        }
                        text.setText(time);
                        text.setTextColor(COLOR_PRIMARY_DARK);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }


/*
    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        RadialTimePickerDialogFragment rtpd = (RadialTimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }
*/
    // load the names of doctors in the accordingly spinner
    private void loadSpinnerData() {
        DoctorsDatabase db = new DoctorsDatabase(getApplicationContext());

        // Spinner Drop down elements
        List<String> data = db.getDoctor();
        data.add(getString(R.string.noneDoctor));
        numOfDoctors = data.size();
        docId = db.getIdDoctor();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        doctorSpinner.setAdapter(dataAdapter);


    }

    /**
     * Insert the data in the Medication table in Medication Database.
     *
     * @param name The name of medication.
     * @param dosage The dosage of medication.
     * @param dosageType The type of dosage ex. mg, ampules etc.
     * @param instruct The instructions of the medication.
     * @param med_type The type of med, ex. tablets, injection etc.
     * @param currentStock The current stock of meds (for refill).
     * @param currentStockType The type of current stock of meds (for refill).
     * @param reminderStock The remain stock of meds (for refill).
     * @param reminderStockType The type of remain stock(for refill).
     * @param timeRefill The time for reminder to refill the med.
     * @param doctorId The doctor for this med.
     *
     * */
    public void insertMedData(String name, String dosage, String dosageType, String instruct, int med_type,
                              String currentStock, String currentStockType, String reminderStock,
                              String reminderStockType, String timeRefill, String doctorId) {
        int dos = Integer.parseInt(dosage);
        String medic_type = "Tablet";

        if (med_type == 0) {
            medic_type = "Tablet";
        } else if (med_type == 1) {
            medic_type = "Capsule";
        } else if (med_type == 2) {
            medic_type = "Syrup";
        } else if (med_type == 3) {
            medic_type = "Drops";
        } else if (med_type == 4) {
            medic_type = "Injection";
        } else if (med_type == 6) {
            medic_type = "Spray";
        }

        boolean insertData = myMedicationDB.insertRecordForMedication(name, dos, dosageType, instruct, medic_type, currentStock, currentStockType, reminderStock, reminderStockType, timeRefill, doctorId);

        String text = "";
        if (insertData) {
            text = getString(R.string.saved);
        } else {
            text = getString(R.string.somethingWentWrong);
        }
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

    }

    /**
     * Insert the data in the Schedule table in Medication Database.
     *
     * @param startDate The date that the user start to take this med.
     * @param duration Fow how many days?
     * @param days Interval of days?
     * @param timesInDay The number of times that the user must take his meds in a day.
     * @param time1,time2,time3,time4,time5,time6,time7,time8,time9,time10,time11,time12 Time that the user must take his meds.
     * */
    public void insertScheduleData(String startDate, int duration, int days, int timesInDay, String time1, String time2,
                                  String time3, String time4, String time5, String time6, String time7, String time8,
                                  String time9, String time10, String time11, String time12) {


        boolean insertSchData = myMedicationDB.insertRecordForSchedule(startDate, duration, days, timesInDay, time1, time2, time3, time4, time5, time6, time7, time8, time9,time10,time11, time12);

        String text = "";
        if (insertSchData) {
            text = getString(R.string.saved);
        } else {
            text = getString(R.string.somethingWentWrong);
        }
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

    }


    // declare all views of the layout
    private void declareVariables() {
        medNameText = findViewById(R.id.medicationNameText);
        dosageText = findViewById(R.id.dosageText);
        dosageSpinner = findViewById(R.id.spinnerDosage);
        instructionsSpinner = findViewById(R.id.spinnerInstructions);

        radioGroupDuration = (RadioGroup)findViewById(R.id.radioGroupDuration);
        radioContinuous = (RadioButton)findViewById(R.id.radioContinuous);
        radioSomeDays = (RadioButton)findViewById(R.id.radioSomeDays);
        someDaysText = (EditText) findViewById(R.id.someDaysText);
        radioGroupDays = (RadioGroup)findViewById(R.id.radioGroupDays);
        radioEveryDay = (RadioButton)findViewById(R.id.radioEveryDay);
        radioInterval = (RadioButton)findViewById(R.id.radioInterval);
        intervalText = (EditText) findViewById(R.id.intervalText);
        startDate = (TextView)findViewById(R.id.startDateText);

        timesText = (TextView)findViewById(R.id.timesText);
        plusBtn = (ImageView)findViewById(R.id.plus);
        minusBtn = (ImageView)findViewById(R.id.minus);
        time1 = (TextView)findViewById(R.id.timeText1);
        time2 = (TextView)findViewById(R.id.timeText2);
        time3 = (TextView)findViewById(R.id.timeText3);
        time4 = (TextView)findViewById(R.id.timeText4);
        time5 = (TextView)findViewById(R.id.timeText5);
        time6 = (TextView)findViewById(R.id.timeText6);
        time7 = (TextView)findViewById(R.id.timeText7);
        time8 = (TextView)findViewById(R.id.timeText8);
        time9 = (TextView)findViewById(R.id.timeText9);
        time10 = (TextView)findViewById(R.id.timeText10);
        time11 = (TextView)findViewById(R.id.timeText11);
        time12 = (TextView)findViewById(R.id.timeText12);

        line1 = (TextView)findViewById(R.id.timeLine1);
        line2 = (TextView)findViewById(R.id.timeLine2);
        line3 = (TextView)findViewById(R.id.timeLine3);
        line4 = (TextView)findViewById(R.id.timeLine4);
        line5 = (TextView)findViewById(R.id.timeLine5);
        line6 = (TextView)findViewById(R.id.timeLine6);
        line7 = (TextView)findViewById(R.id.timeLine7);
        line8 = (TextView)findViewById(R.id.timeLine8);
        line9 = (TextView)findViewById(R.id.timeLine9);
        line10 = (TextView)findViewById(R.id.timeLine10);
        line11 = (TextView)findViewById(R.id.timeLine11);
        line12 = (TextView)findViewById(R.id.timeLine12);

        reminderSwitch = (Switch) findViewById(R.id.reminderSwitch);
        refillSwitch = (Switch) findViewById(R.id.refillSwitch);

        reminderStockText = (TextView) findViewById(R.id.reminderStockText);
        currentlyStockText = (TextView) findViewById(R.id.currentlyStockText);
        curTypeSpinner = findViewById(R.id.spinneCurrentlyStock);
        remTypeSpinner = findViewById(R.id.spinneRemindStock);

        linearHor1 = (LinearLayout)findViewById(R.id.linearHorizontal1);
        linearHor2 = (LinearLayout)findViewById(R.id.linearHorizontal2);
        linearHor3 = (LinearLayout)findViewById(R.id.linearHorizontal3);
        timeLinear = (LinearLayout)findViewById(R.id.timeLinear);
        remindMe = (TextView)findViewById(R.id.remindMe);
        currStock = (TextView)findViewById(R.id.currentStock);

        linearHor1.setVisibility(View.GONE);
        linearHor2.setVisibility(View.GONE);
        linearHor3.setVisibility(View.GONE);
        timeLinear.setVisibility(View.GONE);
        remindMe.setVisibility(View.GONE);
        currStock.setVisibility(View.GONE);

        timeTxt = (TextView) findViewById(R.id.whatTimeText);

    }


    /**
     * When the user select on in switch - for refill switch
     *
     * @return String
     * */
    public String switchON(){
        String message = " ";
        int curStock, remStock;

        currentStock = currentlyStockText.getEditableText().toString();
        reminderStock = reminderStockText.getEditableText().toString();

        if(currentStock.compareTo("") == 0){
            curStock = 0;
        }else{
            curStock = Integer.parseInt(currentStock);
        }
        if(reminderStock.compareTo("") == 0){
            remStock = 0;
        }else{
            remStock = Integer.parseInt(reminderStock);
        }

        if (curStock <= 2 || remStock <= 1) {
            message = getString(R.string.giveAnotherNumbers);
        } else if (curStock <= remStock) {
            message = getString(R.string.giveANumberBigger);
        } else if ((currentStockType != reminderStockType) || (dosageType != reminderStockType) || (currentStockType != dosageType)) {
            message = getString(R.string.giveTheSameType);
        }else{
            message = " ";
        }


        return message;


    }

    /**
     * When the user select off in switch - for refill switch.
     *
     * @return String
     * */
    public String switchOff(){

        currentlyStockText.setText("0");
        reminderStockText.setText("0");
        timeTxt.setText("11:00");
        timeTxt.setTextColor(COLOR_PRIMARY_DARK);



        currentStock = "0";
        currentStockType = "0";
        reminderStockType = "0";
        reminderStock = "0";

        time_r = "0";

        return " ";
    }
}
