package com.example.ehealth.lab.university.personal_report;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import com.example.ehealth.lab.university.personal_report.Interface.DiaryDBInterface;


/**
 * Created by Android_Development on 3/14/2019.
 */

public class DatabaseDiary extends SQLiteOpenHelper {

    private static final String TAG = DatabaseDiary.class.getSimpleName();

    private static final String DATABASE_NAME = "DiaryDB";

    private DatabaseDiary(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DiaryDB.CREATE_TABLE);
        Log.i(TAG, "Created Database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + DiaryDB.TABLE_NAME);
        onCreate(db);
    }

    public void initializeDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DiaryDB.CREATE_TABLE);

    }

    public void insertDiaryEntry(DiaryDBInterface myDiary){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(DiaryDB.ID_DATE + date);

        if (myDiary.getEmotion() != null){
            values.put(DiaryDB.EMOTION, myDiary.getEmotion().getValue());

        }

        values.put(DiaryDB.STRESS, myDiary.getStressLevel());

        values.put(DiaryDB.MENSTRUATION, myDiary.getMenstruation());

        if (myDiary.getSymptoms() != null){
            values.put(DiaryDB.SYMPTOMS, myDiary.getSymptoms().toString());
        }

        if (myDiary.getEmotion() != null){
            values.put(DiaryDB.EMOTION, myDiary.getEmotion().getValue());

        }

        values.put(DiaryDB.COMMENTS, myDiary.getComments());


        if (myDiary.getWeakness() != null){
            values.put(DiaryDB.WEAKNESS, myDiary.getWeakness().getValue());
        }

        if (myDiary.getMoment() != null){
            values.put(DiaryDB.MOMENT, myDiary.getMoment().toString());
        }

    }


}
