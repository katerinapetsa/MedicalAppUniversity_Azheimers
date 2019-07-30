package com.example.ehealth.lab.university.reporting;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.util.Log;

import java.util.GregorianCalendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;
import com.example.ehealth.lab.university.personal_report.CreateReport;

import java.util.Calendar;
import java.util.Objects;

/**
 * This class show the report layout and the accordingly date pickers.
 * Also, this class checks if the user gives a wrong date range or
 * doesn't give a range.
 *
 * @author Stavroula Kousparou
 */

public class ReportMain extends AppCompatActivity {
    private static final String TAG = "Personal Report";

    private TextView fromDate;
    private TextView toDate;
    private Button exportBtn;
    private Context context;

    // for date picker
    private DatePickerDialog.OnDateSetListener mDateSetListenerFrom;
    private DatePickerDialog.OnDateSetListener mDateSetListenerTo;
    private Calendar from_cal = new GregorianCalendar();
    private Calendar to_cal = new GregorianCalendar();

    // the dates that the user select
    private String date_from;
    private String date_to;

    // from
    private int fday;
    private int fmonth;
    private int fyear;

    // to
    private int tyear;
    private int tmonth;
    private int tday;

    // check
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.report);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = this;

        fromDate = findViewById(R.id.fromText);
        toDate = findViewById(R.id.toText);
        exportBtn = findViewById(R.id.buttonExport);

        // First date of personal report
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal1 = Calendar.getInstance();
                int from_year = cal1.get(Calendar.YEAR);
                int from_month = cal1.get(Calendar.MONTH);
                int from_day = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerFrom, from_year, from_month, from_day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //The month in calendar starts from the zero, that's why we plus 1 for the print
        mDateSetListenerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                fday = day1;
                fmonth = month1;
                fyear = year1;

                from_cal.set(year1, month1, day1);
                month1 = month1 + 1;

                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day1 + "/" + month1 + "/" + year1);
                date_from = day1 + "/" + month1 + "/" + year1;

                if (checkTheDate(day1, month1, year1) == true) {
                    fromDate.setText(date_from);
                    check = true;
                }
            }
        };


        //Last Date of report
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal1 = Calendar.getInstance();
                int from_year = cal1.get(Calendar.YEAR);
                int from_month = cal1.get(Calendar.MONTH);
                int from_day = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerTo, from_year, from_month, from_day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //The month in calendar starts from the zero, that's why we plus 1 for the print
        mDateSetListenerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                tday = day1;
                tmonth = month1;
                tyear = year1;

                to_cal.set(year1, month1, day1);
                month1 = month1 + 1;

                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day1 + "/" + month1 + "/" + year1);
                date_to = day1 + "/" + month1 + "/" + year1;

                if (checkTheDate(day1, month1, year1) == true) {
                    toDate.setText(date_to);
                    check = true;
                }
            }
        };

    }


    /**
     * Check if the date that the user select is bigger than the date-today.
     *
     * @param day   The day that the user select.
     * @param month The month that the user select.
     * @param year  The year that the user select.
     * @return boolean
     */
    public boolean checkTheDate(int day, int month, int year) {
        boolean correct_date = false;
        // today
        java.sql.Date DateNow = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String DateNowString = DateNow.toString();

        String[] DateSp = DateNowString.split("-");
        String yearDate = DateSp[0];
        String monthDate = DateSp[1];
        String dayDate = DateSp[2];

        int year_now = Integer.parseInt(yearDate);
        int month_now = Integer.parseInt(monthDate);
        int day_now = Integer.parseInt(dayDate);

        if (year > year_now) {
            correct_date = false;
            Toast.makeText(getApplicationContext(), getString(R.string.dateIsNotValid), Toast.LENGTH_SHORT).show();
        } else if (year < year_now) {
            correct_date = true;
        } else if (year == year_now) {
            if (month < month_now) {
                correct_date = true;
            } else if (month == month_now && day <= day_now) {
                correct_date = true;
            } else {
                correct_date = false;
                Toast.makeText(getApplicationContext(), getString(R.string.dateIsNotValid), Toast.LENGTH_SHORT).show();
            }
        }
        return correct_date;
    }

    // Checks if the first date is bigger than the second date
    private boolean bigDate() {
        boolean cor_date = false;

        if (fyear > tyear) {
            cor_date = false;
            Toast.makeText(getApplicationContext(), getString(R.string.dateIsNotValid), Toast.LENGTH_SHORT).show();
        } else if (fyear < tyear) {
            cor_date = true;
        } else if (fyear == tyear) {
            if (fmonth < tmonth) {
                cor_date = true;
            } else if (fmonth == tmonth && fday <= tday) {
                cor_date = true;
            } else {
                cor_date = false;
                Toast.makeText(getApplicationContext(), getString(R.string.dateIsNotValid), Toast.LENGTH_SHORT).show();
            }
        }
        return cor_date;

    }

    // Checks if the first field is empty
    private boolean validateFromDate() {
        String fromInput = fromDate.getText().toString().trim();

        if (fromInput.isEmpty()) {
            fromDate.setError("Field can not be empty");
            return false;
        } else {
            fromDate.setError(null);
            return true;
        }
    }

    // Checks if the second field is empty
    private boolean validateToDate() {
        String fromInput = toDate.getText().toString();

        if (fromInput.isEmpty()) {
            toDate.setError("Field can not be empty");
            return false;
        } else {
            toDate.setError(null);
            return true;
        }
    }

    /**
     * Confirm if the user didn't select a date.
     *
     * @param v
     */
    public void confirm(View v) {
        if (!validateFromDate() | !validateToDate() | !bigDate()) {   // | we want to show all the error message
            return;
        }

    }

    /**
     * Confirm if the user didn't select a date.
     * FOR EXPORT BUTTON.
     *
     * @param v
     */
    public void confirmExport(View v) {
        if (!validateFromDate() | !validateToDate() | !bigDate()) {   // | we want to show all the error message
           return;
        }else {
            // the view of r
            MainActivity.vibrate();
            Intent intent = new Intent(context, CreateReport.class);
            startActivity(intent);
        }

    }


}