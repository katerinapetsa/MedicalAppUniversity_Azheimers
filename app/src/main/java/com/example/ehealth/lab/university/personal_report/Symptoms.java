package com.example.ehealth.lab.university.personal_report;

import java.util.HashMap;
import java.util.Map;

import com.example.ehealth.lab.university.R;

/**
 * Enum for Symptoms - Personal Reporting - slide 5
 *
 * @author Stavroula Kousparou
 */


public enum Symptoms {

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
    private static Map<String, Symptoms> map = new HashMap<>();

    Symptoms(String stringValue, int resourceID){
        this.stringValue = stringValue;
        this.resourceID = resourceID;
    }

    static {
        for (Symptoms s : Symptoms.values()) {
            map.put(s.stringValue, s);
        }
    }

    public static Symptoms fromString(String quality) {
        return map.get(quality);
    }

    public String toString() {
        return stringValue;
    }

    public int getResourceID() {
        return resourceID;
    }



}
