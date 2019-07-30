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
 * Created by Stavroula Kousparou on 4/11/2019.
 */

public class myAdapterMedication extends BaseAdapter{

    Context context;
    ArrayList<Medication> list_medication;
    Medication med;
    ArrayList<Schedule> list_schedule;
    Schedule schedule;


    public myAdapterMedication(Context context, ArrayList<Medication> list_medication,  ArrayList<Schedule> list_schedule){
        this.context = context;
        this.list_medication = list_medication;
        this.list_schedule = list_schedule;

    }


    @Override
    public int getCount() {
        return list_medication.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public Medication getItemMed(int position) {
        return list_medication.get(position);
    }

    public Schedule getItemSch(int position) {
        return list_schedule.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.list_meds, null);
        ImageView imageMed = (ImageView) convertView.findViewById(R.id.imageMed);
        TextView nameMed = (TextView) convertView.findViewById(R.id.nameMed);
        TextView quantityMed = (TextView) convertView.findViewById(R.id.dosageMed);

        med = list_medication.get(position);
        schedule = list_schedule.get(position);

        nameMed.setText(med.getName());
        quantityMed.setText(med.getDosage() + " " + med.getDosageType());

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

        return convertView;


    }
}
