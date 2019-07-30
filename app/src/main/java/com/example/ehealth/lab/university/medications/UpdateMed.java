package com.example.ehealth.lab.university.medications;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
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
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.doctors.CustomSpinners;
import com.example.ehealth.lab.university.doctors.Doctor;
import com.example.ehealth.lab.university.doctors.DoctorsDatabase;
import com.example.ehealth.lab.university.doctors.DoctorsMain;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

/**
 * Update the data of a medication record.
 * (Medication Table and Schedule Table)
 *
 * @author Stavroula Kousparou
 */

public class UpdateMed extends AppCompatActivity {

    private Context context;
    private static final int COLOR_PRIMARY_DARK = Color.parseColor("#142E3C");

    // views
    private EditText up_medName, up_dosageText;
    private Spinner up_dosageSpinner, up_instructionsSpinner;
    private CustomSpinnersMed dosage = new CustomSpinnersMed();
    private CustomSpinnersMed instructions = new CustomSpinnersMed();

    private String startDate_txt;
    private DatePickerDialog.OnDateSetListener startDateSetListenerFrom;
    private Calendar start_date_cal = new GregorianCalendar();
    private TextView up_startDate,up_someDaysText, up_intervalText;
    private RadioGroup up_radioGroupDuration;
    private RadioButton up_radioContinuous, up_radioSomeDays;
    private RadioGroup up_radioGroupDays;
    private RadioButton up_radioEveryDay, up_radioInterval;

    // times in a day
    private TextView up_timesText;
    private int times;
    private ImageView plusBtn, minusBtn;
    private String times_str;
    private TextView time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11, time12;
    private TextView line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12;

    // med type
    private ViewFlipper viewFlipperImage, viewFlipperText;
    private ImageView next_btn, prev_btn;
    private int med_type = 0;

    private TextView up_reminderStockText, up_currentlyStockText, up_timeTxt;
    private Spinner up_curTypeSpinner, up_remTypeSpinner;
    private CustomSpinnersMed curType = new CustomSpinnersMed();
    private CustomSpinnersMed remType = new CustomSpinnersMed();
    private LinearLayout linearHor1, linearHor2, linearHor3, timeLinear;
    private TextView up_remindMe, up_currStock;
    private Switch refillSwitch;
    private String status_refill_switch;


    private MedicationDatabase myMedicationDB;
    protected Doctor doctor;
    private int numOfDoctors = 0;
    private ArrayList<String> doctorList = new ArrayList<>();
    private Spinner doctorSpinner;
    private CustomSpinnersMed doc_id = new CustomSpinnersMed();
    private String doc_id_pos;
    private List<String> docId;
    private String docId_str = "";

    private Button updateMed_btn;

    // Strings for medication table
    private String id_str, name_str, dosage_str, dosage_type_str, instructions_str, med_type_str;
    private String rem_stock_str,cur_stock_str, rem_type_str, refill_state_str,time_refill;
    private String doc_id_str = "0";

    // Strings for schedule table
    private String startDate_str, duration_str, days_str, timesInDay_str;
    private String time1_str, time2_str, time3_str, time4_str, time5_str, time6_str, time7_str, time8_str,
            time9_str, time10_str, time11_str, time12_str;

    // strings to add in database
    private String name, numOfDosage, dosageType, instr, currentStock, currentStockType, reminderStock, reminderStockType, medType;
    private String timeTxt1, timeTxt2, timeTxt3, timeTxt4, timeTxt5, timeTxt6, timeTxt7,timeTxt8, timeTxt9,timeTxt10,timeTxt11, timeTxt12;
    private String startDate, duration, interval;
    private int duration_int, interval_int;

    private String msg = " ";




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.editMed);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.vibrate();
                Intent intent = new Intent(context, AllMeds.class);
                startActivity(intent);
            }
        });

        declareViews();
        context = this;
        myMedicationDB = new MedicationDatabase(this);

        doctorSpinner = (Spinner) findViewById(R.id.doctorSpinner);
        loadSpinnerData();

        up_dosageSpinner.setOnItemSelectedListener(dosage);
        up_instructionsSpinner.setOnItemSelectedListener(instructions);
        up_curTypeSpinner.setOnItemSelectedListener(curType);
        up_remTypeSpinner.setOnItemSelectedListener(remType);
        doctorSpinner.setOnItemSelectedListener(doc_id);

        // get data
        Intent update_intent = getIntent();
            // Medication Table
        id_str = update_intent.getStringExtra("medId");
        name_str = update_intent.getStringExtra("medname");
        dosage_str = update_intent.getStringExtra("dosage");
        dosage_type_str = update_intent.getStringExtra("dosageType");
        instructions_str = update_intent.getStringExtra("instructions");
        refill_state_str = update_intent.getStringExtra("refillState");
        cur_stock_str = update_intent.getStringExtra("refill_cur");
        rem_stock_str = update_intent.getStringExtra("refill_rem");
        rem_type_str = update_intent.getStringExtra("refill_remType");
        time_refill = update_intent.getStringExtra("timeRefill");
        med_type_str = update_intent.getStringExtra("medType");
        doc_id_str = update_intent.getStringExtra("doctorId");

        // Schedule Table
        startDate_str = update_intent.getStringExtra("startDate");
        duration_str = update_intent.getStringExtra("duration");
        days_str = update_intent.getStringExtra("days");
        timesInDay_str = update_intent.getStringExtra("timesInDay");
        time1_str = update_intent.getStringExtra("time1");
        time2_str = update_intent.getStringExtra("time2");
        time3_str = update_intent.getStringExtra("time3");
        time4_str = update_intent.getStringExtra("time4");
        time5_str = update_intent.getStringExtra("time5");
        time6_str = update_intent.getStringExtra("time6");
        time7_str = update_intent.getStringExtra("time7");
        time8_str = update_intent.getStringExtra("time8");
        time9_str = update_intent.getStringExtra("time9");
        time10_str = update_intent.getStringExtra("time10");
        time11_str = update_intent.getStringExtra("time11");
        time12_str = update_intent.getStringExtra("time12");

        // Set texts
        up_medName.setText(name_str);
        up_dosageText.setText(dosage_str);

        checkSpinnerDosageType(dosage_type_str, up_dosageSpinner);

        // Instructions
        if(instructions_str.equals(getString(R.string.noInstructions))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(0);
                }
            });
        }else if(instructions_str.equals(getString(R.string.beforeFood))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(1);
                }
            });
        }else if(instructions_str.equals(getString(R.string.withFood))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(2);
                }
            });
        }else if(instructions_str.equals(getString(R.string.afterFood))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(3);
                }
            });
        }else if(instructions_str.equals(getString(R.string.avoidSugar))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(4);
                }
            });
        }else if(instructions_str.equals(getString(R.string.avoidSaltyFood))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(5);
                }
            });
        }else if(instructions_str.equals(getString(R.string.neverTakeWithMilk))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(6);
                }
            });
        }else if(instructions_str.equals(getString(R.string.emptyStomach))){
            up_instructionsSpinner.post(new Runnable() {
                public void run() {
                    up_instructionsSpinner.setSelection(7);
                }
            });
        }

        up_startDate.setText(startDate_str);
        startDateFun();  // this class contain the start date of the medication


        // Duration
        if(duration_str.compareTo("0") == 0){
            up_radioContinuous.setChecked(true);
        }else if (duration_str.compareTo("0") != 0){
            up_someDaysText.setVisibility(View.VISIBLE);
            up_someDaysText.setText(duration_str);
            up_radioSomeDays.setChecked(true);
        }

        // Days
        if(days_str.compareTo("0") == 0){
            up_radioEveryDay.setChecked(true);
        }else if (days_str.compareTo("0") != 0){
            up_intervalText.setVisibility(View.VISIBLE);
            up_intervalText.setText(days_str);
            up_radioInterval.setChecked(true);
        }


        // duration radio buttons
        up_radioSomeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_someDaysText.setVisibility(View.VISIBLE);
                up_someDaysText.setText(duration_str);
            }
        });

        up_radioContinuous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_someDaysText.setVisibility(View.INVISIBLE);
            }
        });

        // days radio buttons
        up_radioEveryDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_intervalText.setVisibility(View.INVISIBLE);
            }
        });


        up_radioInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_intervalText.setVisibility(View.VISIBLE);
                up_intervalText.setText(days_str);
            }
        });






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

        up_timesText.setText(timesInDay_str);
        times_str = up_timesText.getText().toString();
        times = Integer.parseInt(times_str);
        timesInDay(Integer.parseInt(timesInDay_str));
        //setVisibleInTimes();
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(times < 12){
                    times += 1;
                    timesInDay(times);
                    times_str = String.valueOf(times);
                    up_timesText.setText(times_str);
                }else if (times == 12){
                    times = 12;
                    timesInDay(times);
                    up_timesText.setText("12");
                }

            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(times > 0){
                    times -= 1;
                    timesInDay(times);
                    times_str = String.valueOf(times);
                    up_timesText.setText(times_str);
                }else{
                    times = 0;
                    timesInDay(times);
                    up_timesText.setText("0");
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






        // refill
        if(refill_state_str.compareTo("ON") == 0){
            up_currentlyStockText.setText(cur_stock_str);
            up_reminderStockText.setText(rem_stock_str);
            checkSpinnerDosageType(rem_type_str, up_curTypeSpinner);
            checkSpinnerDosageType(rem_type_str, up_remTypeSpinner);
            setTime(up_timeTxt);
            up_timeTxt.setText(time_refill);
            up_timeTxt.setTextColor(COLOR_PRIMARY_DARK);
            refillSwitch.setChecked(true);


        }else {
            linearHor1.setVisibility(View.GONE);
            linearHor2.setVisibility(View.GONE);
            linearHor3.setVisibility(View.GONE);
            timeLinear.setVisibility(View.GONE);
            up_remindMe.setVisibility(View.GONE);
            up_currStock.setVisibility(View.GONE);
        }



        // switch for refill
        refillSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // msg = switchON();
                    up_currStock.setVisibility(View.VISIBLE);
                    linearHor1.setVisibility(View.VISIBLE);
                    linearHor2.setVisibility(View.VISIBLE);
                    linearHor3.setVisibility(View.VISIBLE);
                    timeLinear.setVisibility(View.VISIBLE);
                    up_remindMe.setVisibility(View.VISIBLE);
                    //currStock.setVisibility(View.VISIBLE);

                } else {
                    //  msg = switchOff();
                    up_currStock.setVisibility(View.GONE);
                    linearHor1.setVisibility(View.GONE);
                    linearHor2.setVisibility(View.GONE);
                    linearHor3.setVisibility(View.GONE);
                    timeLinear.setVisibility(View.GONE);
                    up_remindMe.setVisibility(View.GONE);
                }
            }
        });



        updateMed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = up_medName.getEditableText().toString();
                numOfDosage = up_dosageText.getEditableText().toString();
                dosageType = String.valueOf(dosage.getDosage());
                instr = String.valueOf(instructions.getInstructions());
                currentStockType = String.valueOf(curType.getCurStock());
                reminderStockType = String.valueOf(remType.getRemStock());
                med_type = viewFlipperText.indexOfChild(viewFlipperText.getCurrentView());

                startDate = up_startDate.getText().toString();
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



                // duration
                if(up_radioContinuous.isChecked()){
                    duration = "0";
                }else if(up_radioSomeDays.isChecked()){
                    duration = up_someDaysText.getText().toString();
                }
                duration_int = Integer.parseInt(duration);

                // days
                if(up_radioEveryDay.isChecked()){
                    interval = "0";
                }else if(up_radioInterval.isChecked()){
                    interval = up_intervalText.getText().toString();
                    if(interval.compareTo("1") == 0){
                        interval = "0";
                    }else{
                        interval = up_intervalText.getText().toString();
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


                time_refill = up_timeTxt.getText().toString();
                if(time_refill.equals(getString(R.string.clickForTime))){time_refill = "0"; }



                // take the id of doctor
                doc_id_pos = doc_id.getPosition();
                if (Integer.parseInt(doc_id.getPosition()) == (numOfDoctors - 1)){
                    docId_str = getString(R.string.noneDoctor);
                }else {
                    docId_str = docId.get(Integer.parseInt(doc_id_pos));
                }

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

                // when click in SAVE button
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.giveNameForMed), Toast.LENGTH_LONG).show();

                }else if(msg.compareTo(" ") != 0){
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                } else{
                    // update the data in Medication Table
                    myMedicationDB.updateDataMed(id_str, name, Integer.parseInt(numOfDosage), dosageType, instr, medic_type, currentStock, currentStockType, reminderStock, reminderStockType, time_refill, docId_str);

                    // update the data in Schedule Table
                    myMedicationDB.updateDataSch(id_str, startDate, duration_int, interval_int, times, timeTxt1, timeTxt2, timeTxt3, timeTxt4, timeTxt5, timeTxt6, timeTxt7, timeTxt8, timeTxt9, timeTxt10, timeTxt11, timeTxt12);

                    Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();

                    MainActivity.vibrate();
                    Intent intent_med = new Intent(context, AllMeds.class);
                    startActivity(intent_med);
                }
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



    private void timesInDay(int time) {

        if(time1_str.compareTo("0") == 0){
            time1_str = getString(R.string.clickForTime);
        }else  if(time2_str.compareTo("0") == 0){
            time2_str = getString(R.string.clickForTime);
        }else if(time3_str.compareTo("0") == 0){
            time3_str = getString(R.string.clickForTime);
        }else if(time4_str.compareTo("0") == 0){
            time4_str = getString(R.string.clickForTime);
        }else if(time5_str.compareTo("0") == 0){
            time5_str = getString(R.string.clickForTime);
        }else if(time6_str.compareTo("0") == 0){
            time6_str = getString(R.string.clickForTime);
        }else if(time7_str.compareTo("0") == 0){
            time7_str = getString(R.string.clickForTime);
        }else if(time8_str.compareTo("0") == 0){
            time8_str = getString(R.string.clickForTime);
        }else if(time9_str.compareTo("0") == 0){
            time9_str = getString(R.string.clickForTime);
        }else if(time10_str.compareTo("0") == 0){
            time10_str = getString(R.string.clickForTime);
        }else if(time11_str.compareTo("0") == 0){
            time11_str = getString(R.string.clickForTime);
        }else if(time12_str.compareTo("0") == 0){
            time12_str = getString(R.string.clickForTime);
        }

        if (time == 0) {
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

        }else if (time == 1){
            time1.setVisibility(View.VISIBLE); line1.setVisibility(View.VISIBLE);
            time2.setVisibility(View.INVISIBLE);    line2.setVisibility(View.INVISIBLE);
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
        }else if(time == 2){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }

        }else if(time == 3) {
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }



        }else if(time == 4) {
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }


        }else if(time == 5) {
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }


        }else if(time == 6){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }


        }else if(time == 7){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time7_str.compareTo(getString(R.string.clickForTime)) == 0){
                time7.setText(time7_str);
            }else {
                time7.setText(time7_str);
                time7.setTextColor(COLOR_PRIMARY_DARK);
            }

        }else if(time == 8){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time7_str.compareTo(getString(R.string.clickForTime)) == 0){
                time7.setText(time7_str);
            }else {
                time7.setText(time7_str);
                time7.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time8_str.compareTo(getString(R.string.clickForTime)) == 0){
                time8.setText(time8_str);
            }else {
                time8.setText(time8_str);
                time8.setTextColor(COLOR_PRIMARY_DARK);
            }

        }else if(time == 9){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time7_str.compareTo(getString(R.string.clickForTime)) == 0){
                time7.setText(time7_str);
            }else {
                time7.setText(time7_str);
                time7.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time8_str.compareTo(getString(R.string.clickForTime)) == 0){
                time8.setText(time8_str);
            }else {
                time8.setText(time8_str);
                time8.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time9_str.compareTo(getString(R.string.clickForTime)) == 0){
                time9.setText(time9_str);
            }else {
                time9.setText(time9_str);
                time9.setTextColor(COLOR_PRIMARY_DARK);
            }

        }else if(time == 10){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time7_str.compareTo(getString(R.string.clickForTime)) == 0){
                time7.setText(time7_str);
            }else {
                time7.setText(time7_str);
                time7.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time8_str.compareTo(getString(R.string.clickForTime)) == 0){
                time8.setText(time8_str);
            }else {
                time8.setText(time8_str);
                time8.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time9_str.compareTo(getString(R.string.clickForTime)) == 0){
                time9.setText(time9_str);
            }else {
                time9.setText(time9_str);
                time9.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time10_str.compareTo(getString(R.string.clickForTime)) == 0){
                time10.setText(time10_str);
            }else {
                time10.setText(time10_str);
                time10.setTextColor(COLOR_PRIMARY_DARK);
            }


        }else if(time == 11){
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

            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time7_str.compareTo(getString(R.string.clickForTime)) == 0){
                time7.setText(time7_str);
            }else {
                time7.setText(time7_str);
                time7.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time8_str.compareTo(getString(R.string.clickForTime)) == 0){
                time8.setText(time8_str);
            }else {
                time8.setText(time8_str);
                time8.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time9_str.compareTo(getString(R.string.clickForTime)) == 0){
                time9.setText(time9_str);
            }else {
                time9.setText(time9_str);
                time9.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time10_str.compareTo(getString(R.string.clickForTime)) == 0){
                time10.setText(time10_str);
            }else {
                time10.setText(time10_str);
                time10.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time11_str.compareTo(getString(R.string.clickForTime)) == 0){
                time11.setText(time11_str);
            }else {
                time11.setText(time11_str);
                time11.setTextColor(COLOR_PRIMARY_DARK);
            }

        }else if (time == 12){
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
            if(time1_str.compareTo(getString(R.string.clickForTime)) == 0){
                time1.setText(time1_str);
            }else {
                time1.setText(time1_str);
                time1.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time2_str.compareTo(getString(R.string.clickForTime)) == 0){
                time2.setText(time2_str);
            }else {
                time2.setText(time2_str);
                time2.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time3_str.compareTo(getString(R.string.clickForTime)) == 0){
                time3.setText(time3_str);
            }else {
                time3.setText(time3_str);
                time3.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time4_str.compareTo(getString(R.string.clickForTime)) == 0){
                time4.setText(time4_str);
            }else {
                time4.setText(time4_str);
                time4.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time5_str.compareTo(getString(R.string.clickForTime)) == 0){
                time5.setText(time5_str);
            }else {
                time5.setText(time5_str);
                time5.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time6_str.compareTo(getString(R.string.clickForTime)) == 0){
                time6.setText(time6_str);
            }else {
                time6.setText(time6_str);
                time6.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time7_str.compareTo(getString(R.string.clickForTime)) == 0){
                time7.setText(time7_str);
            }else {
                time7.setText(time7_str);
                time7.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time8_str.compareTo(getString(R.string.clickForTime)) == 0){
                time8.setText(time8_str);
            }else {
                time8.setText(time8_str);
                time8.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time9_str.compareTo(getString(R.string.clickForTime)) == 0){
                time9.setText(time9_str);
            }else {
                time9.setText(time9_str);
                time9.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time10_str.compareTo(getString(R.string.clickForTime)) == 0){
                time10.setText(time10_str);
            }else {
                time10.setText(time10_str);
                time10.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time11_str.compareTo(getString(R.string.clickForTime)) == 0){
                time11.setText(time11_str);
            }else {
                time11.setText(time11_str);
                time11.setTextColor(COLOR_PRIMARY_DARK);
            }
            if(time12_str.compareTo(getString(R.string.clickForTime)) == 0){
                time12.setText(time12_str);
            }else {
                time12.setText(time12_str);
                time12.setTextColor(COLOR_PRIMARY_DARK);
            }
        }

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
                mTimePicker = new TimePickerDialog(UpdateMed.this, new TimePickerDialog.OnTimeSetListener() {
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

    // the date that the user start the med
    private void startDateFun() {
        Calendar cal1 = Calendar.getInstance();
        final int year = cal1.get(Calendar.YEAR);
        final int month = cal1.get(Calendar.MONTH);
        final int day = cal1.get(Calendar.DAY_OF_MONTH);

        up_startDate.setOnClickListener(new View.OnClickListener() {
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

                startDate_txt = day1 + "/" + month1 + "/" + year1;
                up_startDate.setText(startDate_txt);
            }
        };
    }
    private void checkSpinnerDosageType(String type_str, final Spinner type_spinner) {

        // Dosage Type
        if(type_str.equals(getString(R.string.dos_type_mg))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(0);
                }
            });
        }else if(type_str.equals(getString(R.string.g))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(1);
                }
            });
        }else if(type_str.equals(getString(R.string.ml))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(2);
                }
            });
        }else if(type_str.equals(getString(R.string.tablets))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(3);
                }
            });
        }else if(type_str.equals(getString(R.string.pills))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(4);
                }
            });
        }else if(type_str.equals(getString(R.string.dos_type_drops))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(5);
                }
            });
        }else if(type_str.equals(getString(R.string.sprays))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(6);
                }
            });
        }else if(type_str.equals(getString(R.string.ampules))){
            type_spinner.post(new Runnable() {
                public void run() {
                    type_spinner.setSelection(7);
                }
            });
        }

    }

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
     * When the user select on in switch - for refill switch
     *
     * @return String
     * */
    public String switchON(){
        String message = " ";
        int curStock, remStock;

        currentStock = up_currentlyStockText.getEditableText().toString();
        reminderStock = up_reminderStockText.getEditableText().toString();

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

        up_currentlyStockText.setText("0");
        up_reminderStockText.setText("0");
        up_timeTxt.setText("11:00");
        up_timeTxt.setTextColor(COLOR_PRIMARY_DARK);

        currentStock = "0";
        currentStockType = "0";
        reminderStockType = "0";
        reminderStock = "0";

        time_refill = "0";

        return " ";
    }

    private void declareViews() {

        up_medName = findViewById(R.id.medicationNameText);
        up_dosageText = findViewById(R.id.dosageText);
        up_dosageSpinner = findViewById(R.id.spinnerDosage);
        up_instructionsSpinner = findViewById(R.id.spinnerInstructions);

        up_startDate = (TextView)findViewById(R.id.startDateText);

        // duration to take the med
        up_radioGroupDuration = (RadioGroup)findViewById(R.id.radioGroupDuration);
        up_radioContinuous = (RadioButton)findViewById(R.id.radioContinuous);
        up_radioSomeDays = (RadioButton)findViewById(R.id.radioSomeDays);
        up_someDaysText = (EditText) findViewById(R.id.someDaysText);

        // every day - interval
        up_radioGroupDays = (RadioGroup)findViewById(R.id.radioGroupDays);
        up_radioEveryDay = (RadioButton)findViewById(R.id.radioEveryDay);
        up_radioInterval = (RadioButton)findViewById(R.id.radioInterval);
        up_intervalText = (EditText) findViewById(R.id.intervalText);

        // times in a day
        up_timesText = (TextView)findViewById(R.id.timesText);
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

        // refill views
        linearHor1 = (LinearLayout)findViewById(R.id.linearHorizontal1);
        linearHor2 = (LinearLayout)findViewById(R.id.linearHorizontal2);
        linearHor3 = (LinearLayout)findViewById(R.id.linearHorizontal3);
        timeLinear = (LinearLayout)findViewById(R.id.timeLinear);
        up_remindMe = (TextView)findViewById(R.id.remindMe);
        up_currStock = (TextView)findViewById(R.id.currentStock);
        up_reminderStockText = (TextView) findViewById(R.id.reminderStockText);
        up_currentlyStockText = (TextView) findViewById(R.id.currentlyStockText);
        up_curTypeSpinner = findViewById(R.id.spinneCurrentlyStock);
        up_remTypeSpinner = findViewById(R.id.spinneRemindStock);
        up_timeTxt = (TextView) findViewById(R.id.whatTimeText);
        refillSwitch = (Switch) findViewById(R.id.refillSwitch);

        // View flipper for medications images
        viewFlipperImage = (ViewFlipper) findViewById(R.id.viewFlipperImage);
        viewFlipperText = (ViewFlipper) findViewById(R.id.viewFlipperText);
        next_btn = (ImageView) findViewById(R.id.next);
        prev_btn = (ImageView) findViewById(R.id.prev);

        updateMed_btn = findViewById(R.id.btnSaveMed);
    }


}
