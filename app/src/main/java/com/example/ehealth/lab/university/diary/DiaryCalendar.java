package com.example.ehealth.lab.university.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;


import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.Calendar;
import java.util.Objects;


/**
 * This class checks if the date that the user select is correct.
 *
 * @author Stavroula Kousparou
 */

public class DiaryCalendar extends AppCompatActivity {
    private CalendarView calendarView;
    public String myDate;
    private int correct_date = 0;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary1_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_report);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        slide1();

    }

    public void slide1() {

        Calendar calendar = Calendar.getInstance();

        int cur_year = calendar.get(calendar.YEAR);
        int cur_month = calendar.get(calendar.MONTH);
        int cur_day = calendar.get(calendar.DAY_OF_MONTH);
        correct_date = 2;

        checkDate(cur_day, cur_month, cur_year);

        context = this;
    }


    public void checkDate(final int cur_day, final int cur_month, final int cur_year) {


        calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                if (year > cur_year) {
                    correct_date = 0;
                    Toast.makeText(getApplicationContext(), "Wrong Date", Toast.LENGTH_SHORT).show();
                } else if (year < cur_year) {
                    correct_date = 1;
                } else if (year == cur_year) {
                    if (month < cur_month) {
                        correct_date = 1;
                    } else if(month == cur_month && day <= cur_day) {
                        correct_date = 1;
                    }else{
                        correct_date = 0;
                        Toast.makeText(getApplicationContext(), "Wrong Date", Toast.LENGTH_SHORT).show();
                    }
                }

                if(correct_date == 1){
                    myDate = day + "/" + (month + 1) + "/" + year;
                    //text2.setText(myDate);
                }

                if (correct_date != 0) {
                    MainActivity.vibrate();
                    Intent intent = new Intent(context, DiaryMain.class);
                    startActivity(intent);
                }
            }
        });
    }

}
