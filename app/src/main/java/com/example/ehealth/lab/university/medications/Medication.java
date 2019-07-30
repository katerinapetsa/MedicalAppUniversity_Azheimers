package com.example.ehealth.lab.university.medications;

/**
 * Create the type Medication.
 *
 * @author Stavroula Kousparou
 */

public class Medication {

    private String id, name, dosageType, instructions, medType;
    private String currentStock, currentStockType, remindStock, remindStockType, timeRef;
    private String doctorId;

    int dosage;

    public Medication(String id, String name, int dosage, String dosageType, String instructions,
                      String medType, String currentStock, String currentStockType,
                      String remindStock, String remindStockType, String timeRef, String docId){
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.dosageType = dosageType;
        this.instructions = instructions;
        this.medType = medType;

        //refill
        this.currentStock = currentStock;
        this.currentStockType = currentStockType;
        this.remindStock = remindStock;
        this.timeRef = timeRef;

        this.doctorId = docId;
    }

    public Medication(){}

    // get methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDosage() {
        return dosage;
    }

    public String getDosageType() {
        return dosageType;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getMedType() {
        return medType;
    }

    public String getCurrentStock() {
        return currentStock;
    }

    public String getCurrentStockType() {
        return currentStockType;
    }

    public String getRemindStock() {
        return remindStock;
    }

    public String getRemindStockType() {
        return remindStockType;
    }

    public String getTimeRef() {
        return timeRef;
    }

    public String getDoctorId() {
        return doctorId;
    }


    // set methods
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public void setDosageType(String dosageType) {
        this.dosageType = dosageType;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }
}
