package com.example.ehealth.lab.university.medications;

import android.view.View;
import android.widget.AdapterView;

/**
 * This class create the custom spinners for the form 'Add Medication'
 *
 * @author Stavroula Kousparou
 */

public class CustomSpinnersMed implements AdapterView.OnItemSelectedListener{
    private String dosage;
    private String instructions;
    private String doctor_id;
    private String curStock;
    private String remStock;

    private String position;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        dosage = parent.getItemAtPosition(pos).toString();
        instructions = parent.getItemAtPosition(pos).toString();
        doctor_id = parent.getItemAtPosition(pos).toString();
        curStock = parent.getItemAtPosition(pos).toString();
        remStock = parent.getItemAtPosition(pos).toString();

        position = String.valueOf(pos);

    }

    /**
     * Get the type of dosage for a medication.
     *
     * @return String
     * */
    public String getDosage(){
        return this.dosage;
    }

    /**
     * Get the instructions of a medication.
     *
     * @return String
     * */
    public String getInstructions(){
        return this.instructions;
    }

    /**
     * Get the unique number of a doctor for a specific med.
     *
     * @return String
     * */
    public String getDoctor_id(){
        return this.doctor_id;
    }


    /**
     * Get the type of remain stock for a medication.
     *
     * @return String
     * */
    public String getCurStock() {
        return curStock;
    }

    /**
     * Get the type stock for a medication.
     *
     * @return String
     * */
    public String getRemStock() {
        return remStock;
    }


    /**
     * Get the position.
     *
     * @return String
     * */
    public String getPosition(){
        return position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }
}
