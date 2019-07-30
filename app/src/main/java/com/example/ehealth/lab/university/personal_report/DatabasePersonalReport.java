package com.example.ehealth.lab.university.personal_report;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


/**
 * Database to create the personal report.
 *
 * @author Stavroula Kousparou
 */

public class DatabasePersonalReport extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Diary.db";
    private static final int DB_VERION = 1;

    public static final String TABLE_NAME = "personalReport";
    public static final String ID_DATE = "date";
    public static final String EMOTION = "emotion";
    public static final String STRESS = "stress";
    public static final String MENSTRUATION = "menstruation";
    public static final String SYMPTOMS = "symptoms";
    public static final String WEAKNESS = "weakness";
    public static final String MOMENT = "moment";
    public static final String COMMENT = "comment";


    private DatabasePersonalReport(final Context context){
        super(context, DATABASE_NAME, null, DB_VERION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String create_table = "CREATE TABLE " + TABLE_NAME  +
                "(date TEXT PRIMARY KEY, emotion TEXT, stress INTEGER, menstruation TEXT, " +
                "symptoms TEXT, weakness TEXT, moment TEXT, comment TEXT)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }



}
