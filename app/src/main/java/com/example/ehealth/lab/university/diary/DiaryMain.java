package com.example.ehealth.lab.university.diary;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.main.MainActivity;

import java.util.EnumSet;
import java.util.Objects;


/**
 * @author Stavroula Kousparou
 */

public class DiaryMain extends AppCompatActivity {

    private static final int COLOR_MIDDLEGREY = Color.parseColor("#a8a8a8");
    private static final int COLOR_LIGHTBLUE = Color.parseColor("#3C515C");
    private static final int COLOR_PRIMARY_DARK = Color.parseColor("#00ccff");

    private ViewPager diaryViewPager;
    private DiaryViewPagerAdapter diaryViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;


    private boolean edit = false;

    private Button buttonNext;
    private Button buttonBack;

    // slide 2
    private RadioButton[] emotions;
    // private DiaryEmotion emotion;

    private RadioGroup menstruationGroup;
    private RadioButton menstruationButton;

    // slide 3
    private Bitmap body;
    private EnumSet<BodyRegion> bodyRegionsWeakness = EnumSet.noneOf(BodyRegion.class);

    // slide 4
    private EnumSet<DiaryMoment> diaryMoments = EnumSet.noneOf(DiaryMoment.class);
    // slide 5
    private EnumSet<DiarySymptoms> diarySymptoms = EnumSet.noneOf(DiarySymptoms.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_report);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        diaryViewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layout_dots);
        buttonNext = findViewById(R.id.btn_next);
        buttonBack = findViewById(R.id.btn_back);

        layouts = new int[]{R.layout.diary2_emotion,
                R.layout.diary3_myalzheimer,
                R.layout.diary4_moment,
                R.layout.diary5_symptoms,
                R.layout.diary6_comments};


        addBottomDots(0);

        diaryViewPagerAdapter = new DiaryViewPagerAdapter(this, layouts);
        diaryViewPager.setAdapter(diaryViewPagerAdapter);
        diaryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                buttonBack.setVisibility(View.VISIBLE);
                if (position == 0) { // slide 2
                    buttonBack.setVisibility(View.GONE);
                    stress();

                } else if (position == 1) { // slide 3
                   slide3();
                } else if (position == 2) { // slide 4
                  //  String s4 = slide4();
                  //  Toast.makeText(getApplicationContext(), s4, Toast.LENGTH_LONG).show();
                } else if (position == 3) { //slide 5
                   // String s5 = slide5();
                  //  Toast.makeText(getApplicationContext(), s5, Toast.LENGTH_LONG).show();
                } else if (position == 4) { // slide 6
                    String res = slide6();
                  //  Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                }

                if (position == layouts.length - 1) {
                    buttonNext.setText(getString(R.string.save));
                } else {
                    buttonNext.setText(getString(R.string.next));
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.vibrate();

                int current = getItem(+1);
                if (current < layouts.length) {
                    diaryViewPager.setCurrentItem(current);
                } else {
                    //na kanei save
                    //  launchHomeScreen();
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.vibrate();

                int current = getItem(-1);
                if (current >= 0) {
                    diaryViewPager.setCurrentItem(current);
                }
            }
        });


        if (savedInstanceState == null) {
            diaryViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    slide2();
                    diaryViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }


    private int getItem(int i) {
        return diaryViewPager.getCurrentItem() + i;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));  //"&#8226;" = •
            dots[i].setTextSize(35);
            dots[i].setTextColor(COLOR_MIDDLEGREY);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(COLOR_LIGHTBLUE);
    }

    private void initEmotions() {
        emotions = new RadioButton[]{
                findViewById(R.id.emotion_very_bad),
                findViewById(R.id.emotion_bad),
                findViewById(R.id.emotion_ok),
                findViewById(R.id.emotion_good),
                findViewById(R.id.emotion_very_good)};
    }


    public void slide2() {
        if (findViewById(R.id.diary2_emotion) != null) {
            initEmotions();
            // if(emotion != null){
            //     emotions[emotion.getValue()].setBackgroundColor(COLOR_PRIMARY_DARK);
            // }
        }
    }

    // For Emotion Radio Buttons
    public void selectEmotion(View view) {
        int num = 0;

        if (emotions == null) {
            initEmotions();
        }

        for (int i = 0; i < emotions.length; i++) {
            if (emotions[i].isChecked()) {
                //emotion will be deselected if it is already selected
                //   if(emotion == DiaryEmotion.valueOf(i)){
                //      emotions[i].setBackgroundColor(Color.TRANSPARENT);
                //((RadioGroup)findViewById(R.id.emotion_group)).clearCheck();
                //     emotion = null;
                //  }else{
                emotions[i].setBackgroundColor(COLOR_PRIMARY_DARK);
                num = i;
                // emotion = DiaryEmotion.valueOf(i);
                //}
            } else {
                emotions[i].setBackgroundColor(Color.TRANSPARENT);
            }
            String a = Integer.toString(num);
           // Toast.makeText(getBaseContext(), a, Toast.LENGTH_SHORT).show();

        }
    }


    private void slide3() {
        /*if (findViewById(R.id.diary3_weakness) != null) {
            initClickableBodyRegions(bodyRegionsWeakness, R.id.person, R.id.person_coloured, R.id.bodyregion_value);
        }*/
    }
/*
    /**
     * Click on body region.
     *
     * @param bodyRegions
     * @param personID         resource ID of ImageView displaying the person
     * @param personColouredID resource ID of (invisible) ImageView displaying the coloured person
     * @param valueofRegion    resource ID of Image View displaying the selected body parts
     */


/*    private void initClickableBodyRegions(final EnumSet<BodyRegion> bodyRegions, int personID, final int personColouredID, final int valueofRegion) {
        ImageView person = findViewById(personID);

        if (bodyRegions.isEmpty()) {
            ImageView img = findViewById(valueofRegion);
            this.body = Helper.overlay(this, bodyRegions);
            img.setImageBitmap(this.body);

            img.setVisibility(View.VISIBLE);
        }

        if (person != null) {
            person.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        final float x = motionEvent.getX();
                        final float y = motionEvent.getY();

                        ImageView img = findViewById(personColouredID);
                        Glide.with(DiaryMain.this)
                                .asBitmap()
                                .load(R.drawable.paindiary_person_fullbody_coloured)
                                .into(new SimpleTarget<Bitmap>(img.getWidth(), img.getHeight()) {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        int touchColor = resource.getPixel(Math.round(x), Math.round(y));
                                        BodyRegion bodyPart = getBodyRegion(touchColor);

                                        if (bodyPart != null) {
                                            ImageView img = findViewById(valueofRegion);

                                            if (!bodyRegions.contains(bodyPart)) {
                                                bodyRegions.add(bodyPart);


                                                Bitmap bitmapToAdd = BitmapFactory.decodeResource(getResources(), bodyPart.getResourceID());
                                                Bitmap value = null;
                                                if (body == null) {
                                                    value = bitmapToAdd;
                                                } else {
                                                    value = Helper.overlay(body, bitmapToAdd);
                                                }
                                                body = value;

                                                img.setImageBitmap(value);
                                                img.setVisibility(View.VISIBLE);

                                            } else {   // already selected => deselected
                                                bodyRegions.remove(bodyPart);

                                                if (bodyRegions.isEmpty() || body == null) {
                                                    body = null;
                                                    img.setVisibility(View.GONE);
                                                } else {
//                                                    Bitmap[] images = getBitmapArrayForBodyRegions(bodyRegions);
                                                    Bitmap value = Helper.overlay(DiaryMain.this, bodyRegions);
                                                    body = value;
                                                    img.setImageBitmap(value);
                                                    img.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                    return false;
                }
            });
        }
    }

    private boolean closeMatch(int color1, int color2, int tolerance) {
        if (Math.abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false;
        if (Math.abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false;
        if (Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance)
            return false;
        return true;
    }

    private BodyRegion getBodyRegion(int touchColor) {
        int tolerance = 25;
        BodyRegion bodyPart = null;
        if (closeMatch(Color.parseColor("#0000ff"), touchColor, tolerance)) {
            bodyPart = BodyRegion.BELLY_LEFT;

        } else if (closeMatch(Color.parseColor("#ff00ff"), touchColor, tolerance)) {
            bodyPart = BodyRegion.BELLY_RIGHT;

        } else if (closeMatch(Color.parseColor("#ffff00"), touchColor, tolerance)) {
            bodyPart = BodyRegion.GROIN_LEFT;

        } else if (closeMatch(Color.parseColor("#00ff00"), touchColor, tolerance)) {
            bodyPart = BodyRegion.GROIN_RIGHT;

        } else if (closeMatch(Color.parseColor("#ff7e00"), touchColor, tolerance)) {
            bodyPart = BodyRegion.THIGH_LEFT;

        } else if (closeMatch(Color.parseColor("#a774d2"), touchColor, tolerance)) {
            bodyPart = BodyRegion.THIGH_RIGHT;

        } else if (closeMatch(Color.parseColor("#147914"), touchColor, tolerance)) {
            bodyPart = BodyRegion.KNEE_LEFT;

        } else if (closeMatch(Color.parseColor("#775205"), touchColor, tolerance)) {
            bodyPart = BodyRegion.KNEE_RIGHT;

        } else if (closeMatch(Color.parseColor("#ff007e"), touchColor, tolerance)) {
            bodyPart = BodyRegion.LOWER_LEG_LEFT;

        } else if (closeMatch(Color.parseColor("#00ffff"), touchColor, tolerance)) {
            bodyPart = BodyRegion.LOWER_LEG_RIGHT;

        } else if (closeMatch(Color.parseColor("#7ec8ff"), touchColor, tolerance)) {
            bodyPart = BodyRegion.FOOT_LEFT;

        } else if (closeMatch(Color.parseColor("#173081"), touchColor, tolerance)) {
            bodyPart = BodyRegion.FOOT_RIGHT;

        } else if (closeMatch(Color.parseColor("#007ba9"), touchColor, tolerance)) {
            bodyPart = BodyRegion.CHEST_LEFT;

        } else if (closeMatch(Color.parseColor("#00ffb4"), touchColor, tolerance)) {
            bodyPart = BodyRegion.CHEST_RIGHT;

        } else if (closeMatch(Color.parseColor("#042c3a"), touchColor, tolerance)) {
            bodyPart = BodyRegion.NECK;

        } else if (closeMatch(Color.parseColor("#ff0000"), touchColor, tolerance)) {
            bodyPart = BodyRegion.HEAD;

        } else if (closeMatch(Color.parseColor("#81173d"), touchColor, tolerance)) {
            bodyPart = BodyRegion.UPPER_ARM_LEFT;

        } else if (closeMatch(Color.parseColor("#bc3b13"), touchColor, tolerance)) {
            bodyPart = BodyRegion.UPPER_ARM_RIGHT;

        } else if (closeMatch(Color.parseColor("#7e007e"), touchColor, tolerance)) {
            bodyPart = BodyRegion.LOWER_ARM_LEFT;

        } else if (closeMatch(Color.parseColor("#7e7e00"), touchColor, tolerance)) {
            bodyPart = BodyRegion.LOWER_ARM_RIGHT;

        } else if (closeMatch(Color.parseColor("#7e7e7e"), touchColor, tolerance)) {
            bodyPart = BodyRegion.HAND_LEFT;

        } else if (closeMatch(Color.parseColor("#7e7eff"), touchColor, tolerance)) {
            bodyPart = BodyRegion.HAND_RIGHT;
        }
        return bodyPart;
    }
*/

    public void stress() {

        if(findViewById(R.id.diary5_symptoms) != null) {

            SeekBar stressSeekBar = (SeekBar) findViewById(R.id.painlevel_seekbar);

            stressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int stressLevel = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    stressLevel = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //Toast.makeText(DiaryMain.this, "Seek bar progress is :" + stressLevel, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void menstruation(View v) {
        menstruationGroup = (RadioGroup) findViewById(R.id.radioSymptoms);

        int radioButtonId = menstruationGroup.getCheckedRadioButtonId();
        menstruationButton = (RadioButton) findViewById(radioButtonId);

       // Toast.makeText(getBaseContext(), menstruationButton.getText(), Toast.LENGTH_SHORT).show();

    }

/*
    public void onMomentClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        DiaryMoment moment = null;
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.morning:
                moment = DiaryMoment.MORNING;
                break;

            case R.id.before_lunch:
                moment = DiaryMoment.BEFORE_LUNCH;
                break;

            case R.id.after_lunch:
                moment = DiaryMoment.AFTER_LUNCH;
                break;

            case R.id.afternoon:
                moment = DiaryMoment.AFTERNOON;
                break;

            case R.id.evening:
                moment = DiaryMoment.EVENING;
                break;

            default:
                break;
        }
        if (checked && moment != null) {
            diaryMoments.add(moment);
        } else {
            diaryMoments.remove(moment);
        }
    }

    public String slide4() {
        String str = "";
        if (findViewById(R.id.diary4_moment) != null) {
            if (diaryMoments.contains(DiaryMoment.MORNING)) {
                ((CheckBox) findViewById(R.id.morning)).setChecked(true);
                str = str + "morning ";
            }
            if (diaryMoments.contains(DiaryMoment.BEFORE_LUNCH)) {
                ((CheckBox) findViewById(R.id.before_lunch)).setChecked(true);
                str = str + "before ";
            }
            if (diaryMoments.contains(DiaryMoment.AFTER_LUNCH)) {
                ((CheckBox) findViewById(R.id.after_lunch)).setChecked(true);
                str = str + "after ";
            }
            if (diaryMoments.contains(DiaryMoment.AFTERNOON)) {
                ((CheckBox) findViewById(R.id.afternoon)).setChecked(true);
                str = str + "afternoon ";
            }
            if (diaryMoments.contains(DiaryMoment.EVENING)) {
                ((CheckBox) findViewById(R.id.evening)).setChecked(true);
                str = str + "evening ";
            }
        }
        return str;
    }

    public void onSymptomsClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        DiarySymptoms symptom = null;
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.symptom1:
                symptom = DiarySymptoms.DROOPING_OF_EYELIDS;
                break;

            case R.id.symptom2:
                symptom = DiarySymptoms.DOUBLE_VISION;
                break;

            case R.id.symptom3:
                symptom = DiarySymptoms.SPEAKING;
                break;

            case R.id.symptom4:
                symptom = DiarySymptoms.SWALLOWING_CHEWING;
                break;

            case R.id.symptom5:
                symptom = DiarySymptoms.HEADACHE;
                break;

            case R.id.symptom6:
                symptom = DiarySymptoms.BALANCE;
                break;

            case R.id.symptom7:
                symptom = DiarySymptoms.BREATHING;
                break;

            case R.id.symptom8:
                symptom = DiarySymptoms.CONCENTRATION_MEMORY;
                break;

            default:
                break;
        }
        if (checked && symptom != null) {
            diarySymptoms.add(symptom);
        } else {
            diarySymptoms.remove(symptom);
        }
    }

    public String slide5() {
        String str = "";
        if (findViewById(R.id.diary5_symptoms) != null) {
            if (diarySymptoms.contains(DiarySymptoms.DROOPING_OF_EYELIDS)) {
                ((CheckBox) findViewById(R.id.symptom1)).setChecked(true);
                str = str + "1 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.DOUBLE_VISION)) {
                ((CheckBox) findViewById(R.id.symptom2)).setChecked(true);
                str = str + "2 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.SPEAKING)) {
                ((CheckBox) findViewById(R.id.symptom3)).setChecked(true);
                str = str + "3 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.SWALLOWING_CHEWING)) {
                ((CheckBox) findViewById(R.id.symptom4)).setChecked(true);
                str = str + "4 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.HEADACHE)) {
                ((CheckBox) findViewById(R.id.symptom5)).setChecked(true);
                str = str + "5 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.BALANCE)) {
                ((CheckBox) findViewById(R.id.symptom6)).setChecked(true);
                str = str + "6 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.BREATHING)) {
                ((CheckBox) findViewById(R.id.symptom7)).setChecked(true);
                str = str + "7 ";
            }
            if (diarySymptoms.contains(DiarySymptoms.CONCENTRATION_MEMORY)) {
                ((CheckBox) findViewById(R.id.symptom8)).setChecked(true);
                str = str + "8 ";
            }
        }
        return str;
    }
    */


    public String slide6() {


        return null;

    }


}
