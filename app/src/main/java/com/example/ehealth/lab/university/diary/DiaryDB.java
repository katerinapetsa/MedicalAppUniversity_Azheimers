package com.example.ehealth.lab.university.diary;

import java.util.EnumSet;

/**
 * @author Stavroula Kousparou
 */

public class DiaryDB {

    public static final String TABLE_NAME = "DiaryTableDB";
    public static final String ID_DATE = "date";
    public static final String EMOTION = "emotion";
    public static final String STRESS = "stress";
    public static final String MENSTRUATION = "menstruation";
    public static final String SYMPTOMS = "symptoms";
    public static final String COMMENTS = "comments";
    public static final String WEAKNESS = "weakness";
    public static final String MOMENT = "moment";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            ID_DATE + " STRING PRIMARY KEY, " +
            EMOTION + " STRING, " +
            STRESS + "INTEGER NOT NULL, " +
            MENSTRUATION + "INTEGER NOT NULL, " +
            SYMPTOMS + " STRING, " +
            COMMENTS + " STRING, " +
            WEAKNESS + " STRING, " +
            MOMENT + " STRING);";

    private EnumSet<DiaryEmotion> emotion;
    private int stressLevel;
    private int menstruation;
    private EnumSet<DiarySymptoms> symptoms;
    private String comments;
    private EnumSet<DiaryMoment> moment;
    private EnumSet<BodyRegion> weakness;


    public DiaryDB(EnumSet<DiaryEmotion> emotion, int stressLevel, int menstruation, EnumSet<DiarySymptoms> symptoms, String comments, EnumSet<DiaryMoment> moment, EnumSet<BodyRegion> weakness){
        this.emotion = emotion;
        this.stressLevel = stressLevel;
        this.menstruation = menstruation;
        this.symptoms = symptoms;
        this.comments = comments;
        this.moment = moment;
        this.weakness = weakness;

    }


    public EnumSet<DiaryEmotion> getEmotion(){
        return emotion;
    }
    public void setEmotion(EnumSet<DiaryEmotion> emotion){
        this.emotion = emotion;
    }

    public int getStressLevel(){
        return stressLevel;
    }
    public void setStressLevel(int stessLevel){
        this.stressLevel = stessLevel;
    }

    public int getMenstruation(){
        return menstruation;
    }
    public void setMenstruation(int menstruation){
        this.menstruation = menstruation;
    }

    public EnumSet<DiarySymptoms> getSymptoms(){
        return symptoms;
    }
    public void setSymptoms(EnumSet<DiarySymptoms> symptoms){
        this.symptoms = symptoms;
    }

    public String getComments(){
        return comments;
    }
    public void setComments(String  comments){
        this.comments = comments;
    }


    public EnumSet<DiaryMoment> getMoment(){
        return moment;
    }
    public void setMoment(EnumSet<DiaryMoment> symptoms){
        this.moment = moment;
    }

    public EnumSet<BodyRegion> getWeakness(){
        return weakness;
    }
    public void setWeakness(EnumSet<BodyRegion> weakness){
        this.weakness = weakness;
    }


}