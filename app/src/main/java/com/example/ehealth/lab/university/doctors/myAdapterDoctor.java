package com.example.ehealth.lab.university.doctors;

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
 * This class creates the custom list view.
 *
 * @author Stavroula Kousparou
 */

public class myAdapterDoctor extends BaseAdapter {

    Context context;
    ArrayList<Doctor> list_doctor;
    Doctor doctor;

    public myAdapterDoctor(Context context, ArrayList<Doctor> list_doctor){
        this.context = context;
        this.list_doctor = list_doctor;
    }


    @Override
    public int getCount() {
        return this.list_doctor.size();
    }

    @Override
    public Doctor getItem(int position) {
        return list_doctor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_my_doctors, null);
        TextView name = (TextView)convertView.findViewById(R.id.nameDoctor);
        TextView speciality = (TextView)convertView.findViewById(R.id.specialityDoctor);
        TextView phone1 = (TextView) convertView.findViewById(R.id.phoneDoctor);
        ImageView doctor_img = (ImageView)convertView.findViewById(R.id.doctor_img);

        doctor = list_doctor.get(position);

        name.setText(doctor.getName());
        speciality.setText(doctor.getSpeciality());

        int phone1_int = doctor.getPhone1();
        if (phone1_int == 0){
            phone1.setText(String.valueOf(""));
        }else{
            phone1.setText(String.valueOf(doctor.getPhone1()));
        }
        doctor_img.setImageResource(R.drawable.doctor_face);

        return convertView;
    }






}
