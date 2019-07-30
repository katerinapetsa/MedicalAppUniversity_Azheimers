package com.example.ehealth.lab.university.medications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;

import java.util.ArrayList;

/**
 * Created by Android_Development on 4/24/2019.
 */

public class myAdapterSchedule extends BaseAdapter{

    Context context;
    ArrayList<Schedule> list_schedule;
    Schedule schedule;

    ArrayList<Medication> list_medication;
    Medication med;

    public myAdapterSchedule(Context context, ArrayList<Schedule> list_schedule, ArrayList<Medication> list_medication){
        this.context = context;
        this.list_schedule = list_schedule;
        this.list_medication = list_medication;
    }


    @Override
    public int getCount() {
        return list_schedule.size();
    }

    @Override
    public Schedule getItem(int position) {
        return list_schedule.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.list_day_medication, null);
        ImageView imageMed = (ImageView) convertView.findViewById(R.id.imageDayMed);
        TextView nameMed = (TextView) convertView.findViewById(R.id.nameDayMed);
        TextView dosageMed = (TextView) convertView.findViewById(R.id.dosageDayMed);
        TextView instructions = (TextView) convertView.findViewById(R.id.instructionsDay);
        TextView timeToTake = (TextView) convertView.findViewById(R.id.timeTakeMed);

        med = list_medication.get(position);
        schedule = list_schedule.get(position);

        nameMed.setText(med.getName());
        dosageMed.setText(med.getDosage() + " " + med.getDosageType());

        if(med.getInstructions().equals("No instructions")){
            instructions.setText("");
        }else{
            instructions.setText(med.getInstructions());
        }

        if(med.getMedType().equals("Tablet")){
            imageMed.setImageResource(R.drawable.tablets);

        }else if(med.getMedType().equals("Capsule")){
            imageMed.setImageResource(R.drawable.capsule);

        }else if(med.getMedType().equals("Syrup")){
            imageMed.setImageResource(R.drawable.syrup);

        }else if(med.getMedType().equals("Drops")){
            imageMed.setImageResource(R.drawable.drops);

        }else if(med.getMedType().equals("Injection")){
            imageMed.setImageResource(R.drawable.injection);

        }else if(med.getMedType().equals("Spray")){
            imageMed.setImageResource(R.drawable.spray);

        }else{
            imageMed.setImageResource(R.drawable.tablets);

        }


        if(schedule.getTime1().compareTo("0") != 0){
            timeToTake.setText(schedule.getTime1());
        }
        if(schedule.getTime2().compareTo("0") != 0){
            timeToTake.setText(schedule.getTime2());
        }






        return convertView;


    }





}
