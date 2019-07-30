package com.example.ehealth.lab.university.profile.information;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.SharedPreferencesInformation;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author  Stelios Maimaris
 */
class PersonalizeCalculationsUtils {

    private static final NumberFormat FORMATTER = NumberFormat.getInstance();
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");


    void updateBodyMassIndexView(TextView bodyMassIndexView, Context context) {

        SharedPreferences profileInformationPrefs = context
                .getSharedPreferences(SharedPreferencesInformation.MY_PROFILE_INFORMATION,
                        Context.MODE_PRIVATE);

        if (profileInformationPrefs.contains(SharedPreferencesInformation.HEIGHT) &&
                profileInformationPrefs.contains(SharedPreferencesInformation.WEIGHT)) {

            int height = convertHeightToInteger(profileInformationPrefs);
            int weight = convertWeightToInteger(profileInformationPrefs);

            float converterHeightToMeters = height / 100F;
            float calculateBodyMassIndex = ((float) (weight) /
                    (converterHeightToMeters * converterHeightToMeters));

            String categoryBMI = returnBodyMassIndexCategory(calculateBodyMassIndex, context);

            String updateBodyMassIndex = context.getString(R.string.bodyMassIndex)
                    .replace("0", "<b>" + FORMATTER.format(Double.parseDouble
                            (String.valueOf(DECIMAL_FORMAT.format(calculateBodyMassIndex)))) + "<b>")
                    .concat(" (" + categoryBMI + ")");

            bodyMassIndexView.setText(Html.fromHtml(updateBodyMassIndex));
            bodyMassIndexView.setVisibility(View.VISIBLE);

        } else {
            bodyMassIndexView.setVisibility(View.GONE);
        }
    }

    private String returnBodyMassIndexCategory(float bodyMassIndex, Context context) {

        String categoryBMI = null;

        if (bodyMassIndex < 18.5)
            categoryBMI = context.getString(R.string.underweightBodyMassIndex);
        else if (bodyMassIndex >= 18.5 && bodyMassIndex <= 24.9)
            categoryBMI = context.getString(R.string.normalWeightBodyMassIndex);
        else if (bodyMassIndex >= 25 && bodyMassIndex <= 29.9)
            categoryBMI = context.getString(R.string.overweightBodyMassIndex);
        else if (bodyMassIndex >= 30)
            categoryBMI = context.getString(R.string.obesityBodyMassIndex);

        return categoryBMI;
    }


    private int convertHeightToInteger(SharedPreferences profileInformationPrefs) {

        return Integer.parseInt(profileInformationPrefs
                .getString(SharedPreferencesInformation.HEIGHT, "0"));
    }

    private int convertWeightToInteger(SharedPreferences profileInformationPrefs) {

        return Integer.parseInt(profileInformationPrefs
                .getString(SharedPreferencesInformation.WEIGHT, "0"));
    }
}
