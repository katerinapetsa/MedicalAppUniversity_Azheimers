package com.example.ehealth.lab.university.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.menu.GridViewAdapter;

import java.util.Objects;

/**
 * @author Stelios Maimaris
 */
public class MainActivity extends AppCompatActivity {

    private static final int VIBRATOR_MILLISECONDS = 50;
    private static Vibrator vibe;

    private boolean doubleBackToExitPressedOnce = false;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        context = this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GridView categoryView = findViewById(R.id.category);
        GridViewAdapter adapter = new GridViewAdapter(this);
        categoryView.setAdapter(adapter);

    }

    public static void vibrate() {

        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibe.vibrate(VibrationEffect.createOneShot(VIBRATOR_MILLISECONDS, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {

            //deprecated in API 26
            vibe.vibrate(VIBRATOR_MILLISECONDS);
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.exitMessage), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}