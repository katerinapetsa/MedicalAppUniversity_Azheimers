package com.example.ehealth.lab.university.profile.information;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.TimePicker;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;
import com.example.ehealth.lab.university.main.SharedPreferencesInformation;
import com.example.ehealth.lab.university.medications.AddMed;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * @author Stavroula Kousparou
 * @author Stelios Maimaris
 */

public class ProfileMain extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME_TAG = "Name";
    private static final String GENDER_TAG = "Gender";
    private static final String Date_Of_Birth_TAG = "Date of Birth";
    private static final String HEIGHT_TAG = "Height";
    private static final String WEIGHT_TAG = "Weight";

    static final String CENTIMETERS = "  cm";
    static final String KILOS = "  kg";

    private static final int[] images = {R.mipmap.mg1, R.mipmap.mg2, R.mipmap.mg3};
    private static final boolean LOOP_IMAGES_ANIMATION = true;

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView imageAnimation;
    private TextView profileName, bodyMassIndex;
    private Button genderButton, birthdayButton, weightButton, heightButton;
    private SharedPreferences profileSharedPreferences;
    private TextView dateOfDiagnosis, nextAppointment;

    private DatePickerDialog.OnDateSetListener mDateSetListenerDiagnosis;
    private Calendar diagnosis_date_cal = new GregorianCalendar();

    private DatePickerDialog.OnDateSetListener mDateSetListenerNextAppointment;
    private Calendar next_appointment_cal = new GregorianCalendar();

    // the dates that the user select
    private String date_dignosis;
    private String date_appointment;

    // date of diagnosis
    private int dday;
    private int dmonth;
    private int dyear;

    // date of next appointment
    private int nyear;
    private int nmonth;
    private int nday;


    private static final int COLOR_PRIMARY_DARK = Color.parseColor("#142E3C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_information_main);

        declareAllWidgets();

        PersonalizeCalculationsUtils personalizeCalculations = new PersonalizeCalculationsUtils();
        personalizeCalculations.updateBodyMassIndexView(bodyMassIndex, getApplicationContext());

        changeImagesForProfile(imageAnimation, images, 0, LOOP_IMAGES_ANIMATION);
        new PersonalInformationAsyncTask(this).execute();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.personalData));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dateOfDiagnosis = findViewById(R.id.dateOfDiagnosisText);
        nextAppointment = findViewById(R.id.nextAppointmentText);

        // First date of personal report
        dateOfDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal1 = Calendar.getInstance();
                int d_year = cal1.get(Calendar.YEAR);
                int d_month = cal1.get(Calendar.MONTH);
                int d_day = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerDiagnosis, d_year, d_month, d_day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //The month in calendar starts from the zero, that's why we plus 1 for the print
        mDateSetListenerDiagnosis = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                dday = day1;
                dmonth = month1;
                dyear = year1;

                diagnosis_date_cal.set(year1, month1, day1);
                month1 = month1 + 1;

                date_dignosis = day1 + "/" + month1 + "/" + year1;
                dateOfDiagnosis.setText(date_dignosis);


            }
        };


        //Last Date of report
        nextAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal1 = Calendar.getInstance();
                int from_year = cal1.get(Calendar.YEAR);
                int from_month = cal1.get(Calendar.MONTH);
                int from_day = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        view.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerNextAppointment, from_year, from_month, from_day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //The month in calendar starts from the zero, that's why we plus 1 for the print
        mDateSetListenerNextAppointment = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                nday = day1;
                nmonth = month1;
                nyear = year1;

                next_appointment_cal.set(year1, month1, day1);
                month1 = month1 + 1;

                date_appointment = day1 + "/" + month1 + "/" + year1;
                nextAppointment.setText(date_appointment);
            }
        };

    }


    @Override
    public void onClick(View view) {

        MainActivity.vibrate();
        ProfileDialogBoxUtils profileUtils = new ProfileDialogBoxUtils();

        switch (view.getTag().toString()) {

            case NAME_TAG:
                profileUtils.changeProfileName(getApplicationContext(), this,
                        profileName, profileSharedPreferences);
                break;

            case GENDER_TAG:
                profileUtils.selectGender(getApplicationContext(), this, bodyMassIndex, genderButton, profileSharedPreferences);
                break;

            case Date_Of_Birth_TAG:
                profileUtils.selectBirthday(getApplicationContext(), this, bodyMassIndex, birthdayButton, profileSharedPreferences);
                break;

            case HEIGHT_TAG:
                profileUtils.selectHeight(getApplicationContext(), this, bodyMassIndex, heightButton, profileSharedPreferences);
                break;

            case WEIGHT_TAG:
                profileUtils.selectWeight(getApplicationContext(), this, bodyMassIndex, weightButton, profileSharedPreferences);
                break;
        }
    }

    private void declareAllWidgets() {

        profileSharedPreferences = getSharedPreferences(SharedPreferencesInformation.MY_PROFILE_INFORMATION,
                Context.MODE_PRIVATE);
        //sharedpreferences.edit().clear().commit();

        //Set toolbar title
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ImageView editProfileName = findViewById(R.id.editProfileName);

        imageAnimation = findViewById(R.id.expandedImage);
        profileName = findViewById(R.id.profileName);
        bodyMassIndex = findViewById(R.id.bodyMassIndex);
        genderButton = findViewById(R.id.genderButton);
        birthdayButton = findViewById(R.id.birthdayButton);
        weightButton = findViewById(R.id.weightButton);
        heightButton = findViewById(R.id.heightButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);

        profileName.setOnClickListener(this);
        profileName.setTag(NAME_TAG);
        editProfileName.setOnClickListener(this);
        editProfileName.setTag(NAME_TAG);

        genderButton.setOnClickListener(this);
        genderButton.setTag(GENDER_TAG);

        birthdayButton.setOnClickListener(this);
        birthdayButton.setTag(Date_Of_Birth_TAG);

        weightButton.setOnClickListener(this);
        weightButton.setTag(WEIGHT_TAG);

        heightButton.setOnClickListener(this);
        heightButton.setTag(HEIGHT_TAG);
    }


    //imageView <-- The View which displays the images
    //images[] <-- Holds R references to the images to display
    //imageIndex <-- index of the first image to show in images[]
    //forever <-- If equals true then after the last image it starts all over
    // again with the first image resulting in an infinite loop. You have been warned.
    private void changeImagesForProfile(final ImageView imageAnimation, final int images[],
                                        final int imageIndex, final boolean forever) {

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        //Visible or invisible by default - this will apply when the animation ends
        imageAnimation.setVisibility(View.INVISIBLE);
        imageAnimation.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageAnimation.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    changeImagesForProfile(imageAnimation, images, imageIndex + 1,
                            forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        changeImagesForProfile(imageAnimation, images, 0, true);
                        //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }

    private static class PersonalInformationAsyncTask extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

        private WeakReference<ProfileMain> activityReference;
        private SharedPreferences sharedInformation;

        // only retain a weak reference to the activity
        PersonalInformationAsyncTask(ProfileMain context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            ArrayList<String> informationSharedPreferences = new ArrayList<>();
            sharedInformation = activityReference.get().
                    getSharedPreferences(SharedPreferencesInformation.MY_PROFILE_INFORMATION
                            , Context.MODE_PRIVATE);

            informationSharedPreferences.add(sharedInformation.getString(SharedPreferencesInformation.PROFILE_NAME,
                    activityReference.get().getString(R.string.profileName)));
            informationSharedPreferences.add(sharedInformation.getString(SharedPreferencesInformation.GENDER,
                    activityReference.get().getString(R.string.gender)));
            informationSharedPreferences.add(sharedInformation.getString(SharedPreferencesInformation.BIRTHDAY,
                    activityReference.get().getString(R.string.birthday)));
            informationSharedPreferences.add(sharedInformation.getString(SharedPreferencesInformation.HEIGHT,
                    activityReference.get().getString(R.string.height)));
            informationSharedPreferences.add(sharedInformation.getString(SharedPreferencesInformation.WEIGHT,
                    activityReference.get().getString(R.string.weight)));

            return informationSharedPreferences;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {

            // get a reference to the activity if it is still there
            ProfileMain activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            TextView profileName = activity.findViewById(R.id.profileName);
            Button genderButton = activity.findViewById(R.id.genderButton);
            Button birthdayButton = activity.findViewById(R.id.birthdayButton);
            Button weightButton = activity.findViewById(R.id.weightButton);
            Button heightButton = activity.findViewById(R.id.heightButton);

            profileName.setText(strings.get(0));
            genderButton.setText(strings.get(1));
            birthdayButton.setText(strings.get(2));

            if (sharedInformation.contains(SharedPreferencesInformation.HEIGHT)) {
                heightButton.setAllCaps(false);
                heightButton.setText(strings.get(3).concat(CENTIMETERS));
            } else
                heightButton.setText(strings.get(3));

            if (sharedInformation.contains(SharedPreferencesInformation.WEIGHT)) {
                weightButton.setAllCaps(false);
                weightButton.setText(strings.get(4).concat(KILOS));
            } else weightButton.setText(strings.get(4));
        }
    }
}