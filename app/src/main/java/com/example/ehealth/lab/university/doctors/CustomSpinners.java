package com.example.ehealth.lab.university.doctors;

import android.view.View;
import android.widget.AdapterView;

/**
 * Create custom spinners for first and second phone type.
 *
 * @author Stavroula Kousparu
 */

public class CustomSpinners implements AdapterView.OnItemSelectedListener {
    private String phoneType1;
    private String phoneType2;



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        phoneType1 = parent.getItemAtPosition(pos).toString();
        phoneType2 = parent.getItemAtPosition(pos).toString();

    }

    /**
     * Get the type of the first phone.
     *
     * @return String
     * */
    public String getPhoneType1(){
        return this.phoneType1;
    }

    /**
     * Get the type of the second phone.
     *
     * @return String
     * */
    public String getPhoneType2(){
        return this.phoneType2;
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub

    }
}
