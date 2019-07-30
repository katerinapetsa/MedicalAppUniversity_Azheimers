package com.example.ehealth.lab.university.personal_report;

import java.util.HashMap;
import java.util.Map;

import com.example.ehealth.lab.university.R;

/**
 * Enum for emotion - Personal Reporting - slide 2
 *
 * @author Stavroula Kousparou
 */

public enum Emotion {
    VERY_BAD(0, R.drawable.ic_sentiment_very_dissatisfied),
    BAD(1, R.drawable.ic_sentiment_dissatisfied),
    OKAY(2, R.drawable.ic_sentiment_neutral),
    GOOD(3, R.drawable.ic_sentiment_satisfied),
    VERY_GOOD(4, R.drawable.ic_sentiment_very_satisfied);

    private int value;
    private int resourceID;
    private static Map<Integer, Emotion> map = new HashMap<>();

    Emotion(int value, int resourceID) {
        this.value = value;
        this.resourceID = resourceID;
    }

    static {
        for (Emotion e: Emotion.values()){
            map.put(e.value, e);
        }
    }

    public static Emotion valueOf(int emotion) {
        return map.get(emotion);
    }

    public int getValue() {
        return value;
    }

    public int getResourceID() {
        return resourceID;
    }


}

