package com.example.ehealth.lab.university.doctors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Doctor Database.
 *
 * @author Stavroula Kousparou
 */

public class DoctorsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DoctorsDatabase.db";
    private static final String DOCTORS_TABLE = "doctors";
    private static final int DB_VERION = 1;
    private static final String DOCTOR_ID = "id";
    private static final String NAME = "name";
    private static final String SPECIALITY = "speciality";
    private static final String PHONE_NUMBER_1 = "phoneNumber1";
    private static final String TYPE_PHONE_1 = "typePhone1";
    private static final String PHONE_NUMBER_2 = "phoneNumber2";
    private static final String TYPE_PHONE_2 = "typePhone2";
    private static final String EMAIL = "email";
    private static final String NOTES = "notes";


    public DoctorsDatabase(final Context context) {

        super(context, DATABASE_NAME, null, DB_VERION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + DOCTORS_TABLE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, speciality TEXT, phoneNumber1 ΙΝΤ, " +
                "typePhone1 TEXT, phoneNumber2 ΙΝΤ, typePhone2 TEXT, email TEXT, " +
                "notes TEXT)";
        db.execSQL(create_table);

    }


    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DOCTORS_TABLE);

    }


    /**
     * Insert the data of doctor in doctor table in database.
     *
     * @param name The name of doctor.
     * @param speciality The speciality of doctor.
     * @param phoneNumber1 The first phone.
     * @param typePhone1 The type of first phone.
     * @param phoneNumber2 The second phone.
     * @param typePhone2 The type of second type.
     * @param email The doctor's email.
     * @param notes Extra notes.
     * @return boolean
     *
     * */
    public boolean insertRecordForDoctor(String name, String speciality, int phoneNumber1, String typePhone1, int phoneNumber2, String typePhone2, String email, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("speciality", speciality);
        contentValues.put("phoneNumber1", phoneNumber1);
        contentValues.put("typePhone1", typePhone1);
        contentValues.put("phoneNumber2", phoneNumber2);
        contentValues.put("typePhone2", typePhone2);
        contentValues.put("email", email);
        contentValues.put("notes", notes);


        long result = db.insert(DOCTORS_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * This function get and return the data from the database.
     *
     * @return ArrayList<Doctor>
     *
     * */
    public ArrayList<Doctor> getAllData() {
        ArrayList<Doctor> arraylist_doctor = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM doctors", null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String speciality = cursor.getString(2);
            int phoneNumber1 = cursor.getInt(3);
            String typePhone1 = cursor.getString(4);
            int phoneNumber2 = cursor.getInt(5);
            String typePhone2 = cursor.getString(6);
            String email = cursor.getString(7);
            String notes = cursor.getString(8);

            Doctor doctor = new Doctor(id, name, speciality, phoneNumber1, typePhone1, phoneNumber2, typePhone2, email, notes);

            arraylist_doctor.add(doctor);
        }
        return arraylist_doctor;

    }

   /**
    * This function get and return the names of all doctors.
    *
    * @return ArrayList<String>
    * */
    public ArrayList<String> getDoctor() {
        ArrayList<String> arraylist_doctorSpinner = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM doctors", null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String speciality = cursor.getString(2);

            arraylist_doctorSpinner.add(name + ", " + speciality);
        }
        return arraylist_doctorSpinner;

    }

    /**
     * Get the name and speciality of a doctor accordingly his unique number (id).
     *
     * @param idDoc The unique number of doctor.
     * @return String
     * */
    public String getDoctorById(String idDoc) {
        String docName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM doctors WHERE " + DOCTOR_ID + " = " + idDoc, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String speciality = cursor.getString(2);

            // Doctor doctorName = new Doctor(name);

            docName = name + ", " + speciality;
        }
        return docName;

    }

    /**
     * Get the unique number of doctor.
     *
     * @return ArrayList<String>
     **/
    public ArrayList<String> getIdDoctor() {
        ArrayList<String> arraylist_doctorId = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM doctors", null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);

            arraylist_doctorId.add(id);
        }
        return arraylist_doctorId;

    }


    /**
     * Delete all data of a specific doctor.
     *
     * @param id The unique number of a doctor.
     *
     * */
    public void deleteDoctor(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DOCTORS_TABLE, DOCTOR_ID + "=" + id, null);
        db.close();
    }


    /**
     * Update all data of a specific doctor.
     *
     * @param id
     * @param name The name of doctor.
     * @param speciality The speciality of doctor.
     * @param phoneNumber1 The first phone.
     * @param type1 The type of first phone.
     * @param phoneNumber2 The second phone.
     * @param type2 The type of second type.
     * @param email The doctor's email.
     * @param notes Extra notes.
     * */
    public void updateData(String id, String name, String speciality, String phoneNumber1, String type1, String phoneNumber2, String type2, String email, String notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(SPECIALITY, speciality);
        contentValues.put(PHONE_NUMBER_1, phoneNumber1);
        contentValues.put(TYPE_PHONE_1, type1);
        contentValues.put(PHONE_NUMBER_2, phoneNumber2);
        contentValues.put(TYPE_PHONE_2, type2);
        contentValues.put(EMAIL, email);
        contentValues.put(NOTES, notes);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(DOCTORS_TABLE, contentValues, DOCTOR_ID + "=" + id, null);
        db.close();

    }


}
