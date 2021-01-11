package com.thiagonunes.dailyfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static final String
            KEY_MULTIPLE_NOTES_PER_DAY_PREF = "note_per_day_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        new SettingsFragment()).commit();
    }
}