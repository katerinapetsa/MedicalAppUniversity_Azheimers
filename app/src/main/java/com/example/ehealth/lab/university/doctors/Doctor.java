package com.example.ehealth.lab.university.doctors;

/**
 * Create the type Doctor.
 *
 * @author Stavroula Kousparou
 */

public class Doctor {

    private String id, name, speciality, type_phone1, type_phone2, email, notes;
    private int phone1,phone2;

    public Doctor(String id, String name, String speciality, int phone1, String type_phone1, int phone2, String type_phone2, String email, String notes){
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.phone1 = phone1;
        this.type_phone1 = type_phone1;
        this.phone2 = phone2;
        this.type_phone2 = type_phone2;
        this.email = email;
        this.notes = notes;
    }


    public Doctor(){}


    // get methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public int getPhone1() {
        return phone1;
    }

    public String getType_phone1() {
        return type_phone1;
    }

    public int getPhone2() {
        return phone2;
    }

    public String getType_phone2() {
        return type_phone2;
    }

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }



    // set methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPhone1(int phone1) {
        this.phone1 = phone1;
    }

    public void setType_phone1(String type_phone1) {this.type_phone1 = type_phone1;}

    public void setPhone2(int phone2) {
        this.phone2 = phone2;
    }

    public void setType_phone2(String type_phone2) {
        this.type_phone2 = type_phone2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

