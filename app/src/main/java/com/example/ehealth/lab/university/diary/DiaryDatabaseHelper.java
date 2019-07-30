package com.example.ehealth.lab.university.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database of personal reporting.
 *
 * @author Stavroula Kousparou
 */

public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PersonalReporting.db";

    private static final String SYMPTOMS_TABLE_NAME = "SymptomsTableDB";
    private static final int DB_VERSION = 2;
    private static final String Symptoms_ID_date = "id";
    private static final String Symptoms_ID_unique = "uniqueKey";
    private static final String Drooping = "drooping";
    private static final String DoubleVision = "doubleVision";
    private static final String Speaking = "speaking";
    private static final String Swallowing = "swallowing";
    private static final String Headache = "headache";
    private static final String Balance = "balance";
    private static final String Breathing = "breathing";
    private static final String Memory = "memory";


    public DiaryDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableSymptoms = "CREATE TABLE " + SYMPTOMS_TABLE_NAME + "(id DATE, uniqueKey INTEGER AUTOINCREMENT, drooping INTEGER, " +
                "doubleVision INTEGER, speaking INTEGER, swallowing INTEGER, headache INTEGER, balance INTEGER, " +
                "breathing INTEGER, memory INTEGER, UNIQUE(unique), PRIMARY KEY(id))";

        // execute the query
        db.execSQL(createTableSymptoms);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion == 1) {
            // drop PRIMARY KEY constraint
            db.execSQL("CREATE TABLE " + SYMPTOMS_TABLE_NAME + "2 (id DATE, uniqueKey INTEGER, drooping INTEGER, doubleVision INTEGER, speaking INTEGER, swallowing INTEGER, headache INTEGER, balance INTEGER, breathing INTEGER, memory INTEGER)");

            db.execSQL("INSERT INTO " + SYMPTOMS_TABLE_NAME + "2 (id, drooping, doubleVision, speaking, swallowing, headache, balance, breathing, memory) SELECT id, drooping, doubleVision, speaking, swallowing, headache, balance, breathing, memory FROM " +
                    SYMPTOMS_TABLE_NAME);

            db.execSQL("DROP TABLE " + SYMPTOMS_TABLE_NAME);
            db.execSQL("ALTER TABLE " + SYMPTOMS_TABLE_NAME + "2 RENAME TO " + SYMPTOMS_TABLE_NAME + "");
        }
    }


    void insertNewRecordForSymptoms(int drooping, int doubleVision, int speaking, int swallowing, int headache, int balance, int breathing, int memory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("drooping", drooping);
        contentValues.put("doubleVision", doubleVision);
        contentValues.put("speaking", speaking);
        contentValues.put("swallowing", swallowing);
        contentValues.put("headache", headache);
        contentValues.put("balance", balance);
        contentValues.put("breathing", breathing);
        contentValues.put("memory", memory);

        //db.insert(SYMPTOMS_TABLE_NAME)




    }










    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Drooping, item);

      //  Log.d(TAG, "addData: adding" + item +" to " + SYMPTOMS_TABLE_NAME);
        long result = db.insert(SYMPTOMS_TABLE_NAME, null, contentValues);

        // if data as inserted incorrectly it will return -1
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }






}
