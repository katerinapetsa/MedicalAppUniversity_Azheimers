package com.example.ehealth.lab.university.diary;

import java.util.HashMap;
import java.util.Map;

import com.example.ehealth.lab.university.R;

/**
 * @author Stavroula Kousparou
 */

public enum DiarySymptoms{

    DROOPING_OF_EYELIDS("drooping_eyelids", R.string.drooping_of_eyelids),
    DOUBLE_VISION("double_vision", R.string.double_vision),
    SPEAKING("altered_speaking", R.string.speaking),
    SWALLOWING_CHEWING("swallowing_chewing",R.string.swallowing_chewing),
    HEADACHE("headache",R.string.headache),
    BALANCE("balance",R.string.balance),
    BREATHING("breathing",R.string.balance),
    CONCENTRATION_MEMORY("concentration_memory",R.string.balance);


    private String stringValue;
    private int resourceID;
    private static Map<String, DiarySymptoms> map = new HashMap<>();

    DiarySymptoms(String stringValue, int resourceID){
        this.stringValue = stringValue;
        this.resourceID = resourceID;
    }

    static {
        for (DiarySymptoms s : DiarySymptoms.values()) {
            map.put(s.stringValue, s);
        }
    }

    public static DiarySymptoms fromString(String quality) {
        return map.get(quality);
    }

    public String toString() {
        return stringValue;
    }

    public int getResourceID() {
        return resourceID;
    }



}
