package com.example.ehealth.lab.university.profile.information;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.SharedPreferencesInformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Stelios Maimaris
 */
class ProfileDialogBoxUtils {

    /**
     * Dialog box to change profile name
     *
     * @param context           application context
     * @param activity          corresponding activity
     * @param profileName       text with profile name
     * @param sharedPreferences shared preferences
     */
    void changeProfileName(final Context context,
                           final Activity activity,
                           final TextView profileName,
                           final SharedPreferences sharedPreferences) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle(context.getString(R.string.profileDialogTitle));
        alertDialog.setMessage(context.getString(R.string.profileDialogDescription));

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.setHint(sharedPreferences.getString(SharedPreferencesInformation.PROFILE_NAME, context.getString(R.string.profileName)));
        input.setHintTextColor(context.getResources().getColor(R.color.gray_transparent));
        input.setTextColor(Color.DKGRAY);
        alertDialog.setView(input);
        alertDialog.setIcon(android.R.drawable.ic_menu_edit);

        alertDialog.setPositiveButton(context.getString(R.string.profileDialogButtonPositive),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        String newName = input.getText().toString();

                        if (newName.compareTo("") != 0) {

                            newName = input.getText().toString();
                            profileName.setText(newName);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(SharedPreferencesInformation.PROFILE_NAME, newName);
                            editor.apply();

                        } else {

                            Toast.makeText(context, context.getString(R.string.errorMessage),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    /**
     * Select gender
     *
     * @param context           application context
     * @param activity          corresponding activity
     * @param genderButton      button for gender
     * @param sharedPreferences shared preferences
     */
    void selectGender(final Context context, final Activity activity,
                      final TextView bodyMassIndexView, final Button genderButton, final SharedPreferences sharedPreferences) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle(context.getString(R.string.genderDialogTitle));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                activity, android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add(context.getString(R.string.male));
        arrayAdapter.add(context.getString(R.string.female));

        alertDialog.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        genderButton.setText(arrayAdapter.getItem(index));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPreferencesInformation.GENDER, arrayAdapter.getItem(index));
                        editor.putInt(SharedPreferencesInformation.GENDER_INDEX, index);
                        editor.apply();

                        updateBodyMassIndexView(bodyMassIndexView, context);
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    /**
     * @param context           application context
     * @param activity          corresponding activity
     * @param birthdayButton    button for birthday
     * @param sharedPreferences shared preferences
     */
    void selectBirthday(final Context context, final Activity activity,
                        final TextView bodyMassIndexView,
                        final Button birthdayButton, final SharedPreferences sharedPreferences) {

        DatePickerDialog pickBirthday;
        final SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        Calendar newCalendar = Calendar.getInstance();
        String extractDate[];

        if (!sharedPreferences.contains(SharedPreferencesInformation.BIRTHDAY)) {

            extractDate = context.getString(R.string.birthDateDefaultSetDate)
                    .split("/");
        } else {

            extractDate = sharedPreferences.getString(SharedPreferencesInformation.BIRTHDAY,
                    context.getString(R.string.birthday)).split("/");
        }

        newCalendar.set(Integer.parseInt(extractDate[2]),
                Integer.parseInt(extractDate[1]), Integer.parseInt(extractDate[0]));

        birthdayButton.setText(sharedPreferences.getString(SharedPreferencesInformation.BIRTHDAY,
                context.getString(R.string.birthday)));

        pickBirthday = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar birthDate = Calendar.getInstance();
                        birthDate.set(year, month, dayOfMonth);

                        birthdayButton.setText(dateFormatter.format(birthDate
                                .getTime()));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPreferencesInformation.BIRTHDAY, dateFormatter.format(birthDate
                                .getTime()));
                        editor.putInt(SharedPreferencesInformation.AGE, getAgeFromBirthDate(year, month, dayOfMonth));
                        editor.apply();

                        updateBodyMassIndexView(bodyMassIndexView, context);

                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH) - 1,
                newCalendar.get(Calendar.DAY_OF_MONTH));

        pickBirthday.show();
    }


    /**
     * @param context           application context
     * @param activity          corresponding activity
     * @param heightButton      button for height
     * @param sharedPreferences shared preferences
     */
    void selectHeight(final Context context, final Activity activity,
                      final TextView bodyMassIndexView,
                      final Button heightButton, final SharedPreferences sharedPreferences) {

        final String DEFAULT_VALUE = String.valueOf(165);
        final int MAXIMUM_VALUE = 300;
        final int MINIMUM_VALUE = 0;
        final NumberPicker heightPicker = new NumberPicker(activity);

        heightPicker.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String format(int i) {
                return String.format("%02d%s", i, ProfileMain.CENTIMETERS);
            }
        });

        heightPicker.setMinValue(MINIMUM_VALUE);
        heightPicker.setMaxValue(MAXIMUM_VALUE);
        heightPicker.invalidate();

        if (!sharedPreferences.contains(SharedPreferencesInformation.HEIGHT)) {

            heightPicker.setValue(Integer.parseInt(DEFAULT_VALUE));
            heightButton.setText(context.getString(R.string.height));
        } else {

            heightPicker.setValue(Integer.parseInt(sharedPreferences
                    .getString(SharedPreferencesInformation.HEIGHT, DEFAULT_VALUE)));
            heightButton.setAllCaps(false);
            heightButton.setText(sharedPreferences.getString(SharedPreferencesInformation.HEIGHT,
                    context.getString(R.string.height)).concat(ProfileMain.CENTIMETERS));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(context.getString(R.string.heightDialogTitle));

        builder.setPositiveButton(context.getString(R.string.profileDialogButtonPositive),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        heightButton.setAllCaps(false);
                        heightButton.setText(String.valueOf(heightPicker.getValue() +
                                ProfileMain.CENTIMETERS));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPreferencesInformation.HEIGHT,
                                String.valueOf(heightPicker.getValue()));
                        editor.apply();

                        updateBodyMassIndexView(bodyMassIndexView, context);
                    }
                });

        builder.setNegativeButton(context.getString(R.string.profileDialogButtonNegative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

        builder.setView(heightPicker);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * @param context           application context
     * @param activity          corresponding activity
     * @param weightButton      button for weight
     * @param sharedPreferences shared preferences
     */
    void selectWeight(final Context context, final Activity activity,
                      final TextView bodyMassIndexView,
                      final Button weightButton, final SharedPreferences sharedPreferences) {

        final String DEFAULT_VALUE = String.valueOf(65);
        final int MINIMUM_VALUE = 20;
        final int MAXIMUM_VALUE = 300;
        final NumberPicker weightPicker = new NumberPicker(activity);

        weightPicker.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String format(int i) {
                return String.format("%02d%s", i, ProfileMain.KILOS);
            }
        });

        weightPicker.setMinValue(MINIMUM_VALUE);
        weightPicker.setMaxValue(MAXIMUM_VALUE);
        weightPicker.invalidate();

        if (!sharedPreferences.contains(SharedPreferencesInformation.WEIGHT)) {

            weightPicker.setValue(Integer.parseInt(DEFAULT_VALUE));
            weightButton.setText(context.getString(R.string.weight));
        } else {

            weightPicker.setValue(Integer.parseInt(sharedPreferences
                    .getString(SharedPreferencesInformation.WEIGHT, DEFAULT_VALUE)));
            weightButton.setAllCaps(false);
            weightButton.setText(sharedPreferences.getString(SharedPreferencesInformation.WEIGHT,
                    context.getString(R.string.weight)).concat((ProfileMain.KILOS)));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(context.getString(R.string.weightDialogTitle));

        builder.setPositiveButton(context.getString(R.string.profileDialogButtonPositive),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weightButton.setAllCaps(false);
                        weightButton.setText(String.valueOf(weightPicker.getValue() +
                                ProfileMain.KILOS.toLowerCase()));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPreferencesInformation.WEIGHT,
                                String.valueOf(weightPicker.getValue()));
                        editor.apply();

                        updateBodyMassIndexView(bodyMassIndexView, context);
                    }
                });

        builder.setNegativeButton(context.getString(R.string.profileDialogButtonNegative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

        builder.setView(weightPicker);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private int getAgeFromBirthDate(int year, int month, int day) {
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        int age;

        dateOfBirth.set(year, month, day);

        age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    private void updateBodyMassIndexView(TextView bodyMassIndexView, Context context) {

        PersonalizeCalculationsUtils personalizeCalculations = new PersonalizeCalculationsUtils();
        personalizeCalculations.updateBodyMassIndexView(bodyMassIndexView, context);
    }
}
