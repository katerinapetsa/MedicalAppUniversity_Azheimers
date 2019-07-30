package com.example.ehealth.lab.university.personal_report;

import com.example.ehealth.lab.university.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for body region - Personal Reporting - slide 3
 *
 * @author Stavroula Kousparou
 */

public enum BodyRegion {

    BELLY_LEFT(0, R.drawable.paindiary_person_belly_left),
    BELLY_RIGHT(1, R.drawable.paindiary_person_belly_right),
    GROIN_LEFT(2, R.drawable.paindiary_person_groin_left),
    GROIN_RIGHT(3, R.drawable.paindiary_person_groin_right),
    THIGH_LEFT(4, R.drawable.paindiary_person_thigh_left),
    THIGH_RIGHT(5, R.drawable.paindiary_person_thigh_right),
    KNEE_LEFT(6, R.drawable.paindiary_person_knee_left),
    KNEE_RIGHT(7, R.drawable.paindiary_person_knee_right),
    LOWER_LEG_LEFT(8, R.drawable.paindiary_person_leg_left),
    LOWER_LEG_RIGHT(9, R.drawable.paindiary_person_leg_right),
    FOOT_LEFT(10, R.drawable.paindiary_person_foot_left),
    FOOT_RIGHT(11, R.drawable.paindiary_person_foot_right),
    CHEST_LEFT(12, R.drawable.paindiary_person_chest_left),
    CHEST_RIGHT(13, R.drawable.paindiary_person_chest_right),
    NECK(14, R.drawable.paindiary_person_neck),
    HEAD(15, R.drawable.paindiary_person_head),
    UPPER_ARM_LEFT(16, R.drawable.paindiary_person_upperarm_left),
    UPPER_ARM_RIGHT(17, R.drawable.paindiary_person_upperarm_right),
    LOWER_ARM_LEFT(18, R.drawable.paindiary_person_lowerarm_left),
    LOWER_ARM_RIGHT(19, R.drawable.paindiary_person_lowerarm_right),
    HAND_LEFT(20, R.drawable.paindiary_person_hand_left),
    HAND_RIGHT(21, R.drawable.paindiary_person_hand_right);


    private int valueOfRegion;
    private int resourceID;
    private static Map<Integer, BodyRegion> map = new HashMap<>();
    public static final int LOWEST_BACK_INDEX = 22;

    BodyRegion(int valueofRegion, int resourceID){
        this.valueOfRegion = valueofRegion;
        this.resourceID = resourceID;
    }

    static {
        for(BodyRegion r : BodyRegion.values()){
            map.put(r.valueOfRegion, r);
        }
    }

    public static BodyRegion valueOf(int region){
        return map.get(region);
    }

    public int getValue(){
        return valueOfRegion;
    }

    public int getResourceID(){
        return resourceID;
    }

}




