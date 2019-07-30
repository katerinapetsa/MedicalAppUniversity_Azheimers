package com.example.ehealth.lab.university.personal_report;

import com.example.ehealth.lab.university.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stavroula Kousparou on 1/24/2019.
 */

public enum DiaryMoment {
    MORNING("morning", R.string.moment_morning),
    BEFORE_LUNCH("before_lunch", R.string.moment_before_lunch),
    AFTER_LUNCH("after_lunch", R.string.moment_after_lunch),
    AFTERNOON("afternoon",R.string.moment_afternoon),
    EVENING("evening", R.string.moment_evening);


    private String stringValue;
    private int resourceID;
    private static Map<String, DiaryMoment> map = new HashMap<>();

    DiaryMoment(String stringValue, int resourceID){
        this.stringValue = stringValue;
        this.resourceID = resourceID;
    }

    static {
        for (DiaryMoment m : DiaryMoment.values()) {
            map.put(m.stringValue, m);
        }
    }

    public static DiaryMoment fromString(String quality) {
        return map.get(quality);
    }

    public String toString() {
        return stringValue;
    }

    /**
     * Returns the resource ID of the corresponding string resource.
     *
     * @return resource ID
     */
    public int getResourceID() {
        return resourceID;
    }

}
