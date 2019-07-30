package com.example.ehealth.lab.university.personal_report.Interface;

import com.example.ehealth.lab.university.personal_report.BodyRegion;
import com.example.ehealth.lab.university.personal_report.Emotion;
import com.example.ehealth.lab.university.personal_report.DiaryMoment;
import com.example.ehealth.lab.university.personal_report.DiarySymptoms;

/**
 * Created by Stavroula Kousparou on 3/15/2019.
 */

public interface DiaryDBInterface {

    Emotion getEmotion();
    void setEmotion(Emotion emotion);

    int getStressLevel();
    void setStressLevel(int stessLevel);

    int getMenstruation();
    void setMenstruation(int menstruation);

    DiarySymptoms getSymptoms();
    void setSymptoms(DiarySymptoms symptoms);

    String getComments();
    void setComments(String  comments);


    DiaryMoment getMoment();
    void setMoment(DiaryMoment symptoms);

    BodyRegion getWeakness();
    void setWeakness(BodyRegion weakness);


}
