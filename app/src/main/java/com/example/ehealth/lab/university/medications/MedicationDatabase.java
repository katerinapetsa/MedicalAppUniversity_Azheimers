package com.example.ehealth.lab.university.medications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Medication Database.
 *
 * @author Stavroula Kousparou
 */

public class MedicationDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Mediaction.db";
    private static final int DB_VERION = 2;

    // table medication
    private static final String MEDICATION_TABLE = "medications";
    private static final String MED_ID = "id";
    private static final String NAME = "name";
    private static final String DOSAGE = "dosage";
    private static final String DOSAGE_TYPE = "dosageType";
    private static final String INSTRUCTIONS = "instructions";
    private static final String MED_TYPE = "medType";

    //refill
    private static final String CURRENTLY_STOCK = "currently";
    private static final String CURRENTLY_STOCK_TYPE = "currentlyType";
    private static final String REMIND_STOCK = "remind";
    private static final String REMIND_STOCK_TYPE = "remindType";
    private static final String TIME = "timeRefill";

    private static final String DOCTOR_ID = "doctorId";

    // table schedule
    private static final String SCHEDULE_TABLE = "schedule";
    private static final String SCH_ID = "sch_id";
    private static final String START_DATE = "startDate";
    private static final String DURATION = "duration";
    private static final String DAYS = "days";
    private static final String TIMES_IN_DAY = "timesInDay";
    private static final String TIME_1 = "time1";
    private static final String TIME_2 = "time2";
    private static final String TIME_3 = "time3";
    private static final String TIME_4 = "time4";
    private static final String TIME_5 = "time5";
    private static final String TIME_6 = "time6";
    private static final String TIME_7 = "time7";
    private static final String TIME_8 = "time8";
    private static final String TIME_9 = "time9";
    private static final String TIME_10 = "time10";
    private static final String TIME_11 = "time11";
    private static final String TIME_12 = "time12";


    public MedicationDatabase(final Context context){
        super(context, DATABASE_NAME, null, DB_VERION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_medication_table = "CREATE TABLE " + MEDICATION_TABLE  +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, dosage INTEGER, dosageType TEXT, " +
                "instructions TEXT, medType TEXT, currently TEXT, currentlyType TEXT, " +
                "remind TEXT, remindType TEXT, timeRefill TEXT, doctorId TEXT)";
        db.execSQL(create_medication_table);

        String create_schedule_table = "CREATE TABLE " + SCHEDULE_TABLE +
                "(sch_id INTEGER PRIMARY KEY AUTOINCREMENT, startDate TEXT, duration INTEGER, days INTEGER, " +
                "timesInDay INTEGER, time1 TEXT, time2 TEXT, time3 TEXT, time4 TEXT, time5 TEXT, time6 TEXT, " +
                "time7 TEXT, time8 TEXT, time9 TEXT, time10 TEXT, time11 TEXT, time12 TEXT)";
        db.execSQL(create_schedule_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEDICATION_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);

    }

    /**
     * Insert the data of medication in the medication table in database.
     *
     * @param name The name of med.
     * @param dosage The dosage of med.
     * @param dosageType The type of dosage.
     * @param instructions The instructions of med.
     * @param medType The type of med, ex. ampules, pills etc.
     * @param currentStock The current stock(for refill).
     * @param currentType The type of current stock(for refill).
     * @param remainStock The remain stock(for refill).
     * @param remainType The type of remain stock(for refill).
     * @param timeRefill The time of remind(for refill).
     * @param docId The doctor.
     * @return boolean
     *
     * */
    public boolean insertRecordForMedication(String name, int dosage, String dosageType, String instructions,
                                             String medType, String currentStock, String currentType,
                                             String remainStock, String remainType, String timeRefill, String docId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("dosage", dosage);
        contentValues.put("dosageType", dosageType);
        contentValues.put("instructions", instructions);
        contentValues.put("medType", medType);
        contentValues.put("currently", currentStock);
        contentValues.put("currentlyType", currentType);
        contentValues.put("remind", remainStock);
        contentValues.put("remindType", remainType);
        contentValues.put("timeRefill", timeRefill);
        contentValues.put("doctorId", docId);

        long result = db.insert(MEDICATION_TABLE, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    /**
     * Insert the data of schedule in the schedule table in database.
     *
     * @param startDate The date that the user start to take this med.
     * @param duration Fow how many days?
     * @param days Interval of days?
     * @param timesInDay The number of times that the user must take his meds in a day.
     * @param time1,time2,time3,time4,time5,time6,time7,time8,time9,time10,time11,time12 Time that the user must take his meds.
     * */
    public boolean insertRecordForSchedule(String startDate, int duration, int days, int timesInDay, String time1,
                                           String time2, String time3, String time4, String time5, String time6,
                                           String time7, String time8, String time9, String time10, String time11,
                                           String time12){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("startDate", startDate);
        contentValues.put("duration", duration);
        contentValues.put("days", days);
        contentValues.put("timesInDay", timesInDay);
        contentValues.put("time1", time1);
        contentValues.put("time2", time2);
        contentValues.put("time3", time3);
        contentValues.put("time4", time4);
        contentValues.put("time5", time5);
        contentValues.put("time6", time6);
        contentValues.put("time7", time7);
        contentValues.put("time8", time8);
        contentValues.put("time9", time9);
        contentValues.put("time10", time10);
        contentValues.put("time11", time11);
        contentValues.put("time12", time12);

        long result = db.insert(SCHEDULE_TABLE, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    /**
     * This function get and return the data from the 'medication database'.
     *
     * @return ArrayList<Medication>
     *
     * */
    public ArrayList<Medication> getAllData(){
        ArrayList<Medication> arraylist_medications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM medications", null);

        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String dosage = cursor.getString(2);
            String dosageType = cursor.getString(3);
            String instructions = cursor.getString(4);
            String medType = cursor.getString(5);
            String currentlyStock = cursor.getString(6);
            String currentlyType = cursor.getString(7);
            String remindStock = cursor.getString(8);
            String remindType = cursor.getString(9);
            String time = cursor.getString(10);
            String doctorId = cursor.getString(11);

            int dosage_num = Integer.parseInt(dosage);
            Medication medication = new Medication(id, name, dosage_num, dosageType, instructions, medType, currentlyStock, currentlyType, remindStock, remindType, time, doctorId);

            arraylist_medications.add(medication);
        }

        return arraylist_medications;
    }

    /**
     * This function get and return the data from the 'medication database'.
     *
     * @return ArrayList<Schedule>
     *
     * */
    public ArrayList<Schedule> getAllDataSchedule(){
        ArrayList<Schedule> arraylist_schedule = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM schedule", null);

        while (cursor.moveToNext()){
            int sch_id = cursor.getInt(0);
            String startDate = cursor.getString(1);
            int duration = cursor.getInt(2);
            int days = cursor.getInt(3);
            int timesInDay = cursor.getInt(4);
            String time1 = cursor.getString(5);
            String time2 = cursor.getString(6);
            String time3 = cursor.getString(7);
            String time4 = cursor.getString(8);
            String time5 = cursor.getString(9);
            String time6 = cursor.getString(10);
            String time7 = cursor.getString(11);
            String time8 = cursor.getString(12);
            String time9 = cursor.getString(13);
            String time10 = cursor.getString(14);
            String time11 = cursor.getString(15);
            String time12 = cursor.getString(16);

            Schedule schedule = new Schedule(sch_id, startDate, duration, days, timesInDay, time1, time2, time3, time4, time5, time6, time7, time8, time9,time10,time11, time12);

            arraylist_schedule.add(schedule);
        }

        return arraylist_schedule;
    }

    /**
     * Delete all data of a specific medication (and the accordingly row from schedule table).
     *
     * @param id The unique number of a medication.
     *
     * */
    public void deleteMedication(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDICATION_TABLE, MED_ID + "=" + id, null);
        db.delete(SCHEDULE_TABLE, SCH_ID + "=" + id, null);
        db.close();
    }

    /**
     *  Update all data of a specific medication (the schedule).
     *
     * @param id The id of medication.
     * @param name The name of med.
     * @param dosage The dosage of med.
     * @param dosageType The type of dosage.
     * @param instructions The instructions of med.
     * @param medType The type of med, ex. ampules, pills etc.
     * @param currentStock The current stock(for refill).
     * @param currentType The type of current stock(for refill).
     * @param remainStock The remain stock(for refill).
     * @param remainType The type of remain stock(for refill).
     * @param timeRefill The time of remind(for refill).
     * @param docId The doctor.
     *
     * */
    public void updateDataMed(String id, String name, int dosage, String dosageType, String instructions,
                              String medType, String currentStock, String currentType,
                              String remainStock, String remainType, String timeRefill, String docId){

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(DOSAGE, dosage);
        contentValues.put(DOSAGE_TYPE, dosageType);
        contentValues.put(INSTRUCTIONS, instructions);
        contentValues.put(MED_TYPE, medType);
        contentValues.put(CURRENTLY_STOCK, currentStock);
        contentValues.put(CURRENTLY_STOCK_TYPE, currentType);
        contentValues.put(REMIND_STOCK, remainStock);
        contentValues.put(REMIND_STOCK_TYPE, remainType);
        contentValues.put(TIME, timeRefill);
        contentValues.put(DOCTOR_ID, docId);


        SQLiteDatabase db = this.getWritableDatabase();
        db.update(MEDICATION_TABLE, contentValues, MED_ID + "=" + id, null);
        db.close();

    }


    /**
     * Update all data of a specific medication (the schedule).
     *
     * @param id The id of medication.
     * @param startDate The date that the user start to take this med.
     * @param duration Fow how many days?
     * @param days Interval of days?
     * @param timesInDay The number of times that the user must take his meds in a day.
     * @param time1,time2,time3,time4,time5,time6,time7,time8,time9,time10,time11,time12 Time that the user must take his meds.
     * */
    public void updateDataSch(String id, String startDate, int duration, int days, int timesInDay, String time1,
                              String time2, String time3, String time4, String time5, String time6,
                              String time7, String time8, String time9, String time10, String time11,
                              String time12){

        ContentValues contentValues = new ContentValues();
        contentValues.put(START_DATE, startDate);
        contentValues.put(DURATION, duration);
        contentValues.put(DAYS, days);
        contentValues.put(TIMES_IN_DAY, timesInDay);
        contentValues.put(TIME_1, time1);
        contentValues.put(TIME_2, time2);
        contentValues.put(TIME_3, time3);
        contentValues.put(TIME_4, time4);
        contentValues.put(TIME_5, time5);
        contentValues.put(TIME_6, time6);
        contentValues.put(TIME_7, time7);
        contentValues.put(TIME_8, time8);
        contentValues.put(TIME_9, time9);
        contentValues.put(TIME_10, time10);
        contentValues.put(TIME_11, time11);
        contentValues.put(TIME_12, time12);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(SCHEDULE_TABLE, contentValues, SCH_ID + "=" + id, null);
        db.close();

    }

    /**
     *  Update all data of a specific medication (the schedule).
     *
     * @param id The id of medication.
     * @param currentStock The current stock - refill.
     *
     * */
    public void updateRefill(String id, String currentStock){

        ContentValues contentValues = new ContentValues();
        contentValues.put(CURRENTLY_STOCK, currentStock);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(MEDICATION_TABLE, contentValues, MED_ID + "=" + id, null);
        db.close();

    }



}
