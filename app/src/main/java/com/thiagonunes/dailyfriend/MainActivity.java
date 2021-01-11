package com.thiagonunes.dailyfriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thiagonunes.dailyfriend.model.Record;
import com.thiagonunes.dailyfriend.model.RecordsListViewModel;
import com.thiagonunes.dailyfriend.recyclerview.RecordListAdapter;
import com.thiagonunes.dailyfriend.utils.Utils;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private RecordsListViewModel mRecordsListViewModel;
    private FloatingActionButton mFab;
    private Boolean mMultipleNotesPerDay;
    private View mEmtpyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        mFab = findViewById(R.id.fab);
        mEmtpyList = findViewById(R.id.emptyList);
        mFab.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            startActivity(intent);

        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RecordListAdapter adapter = new RecordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        mMultipleNotesPerDay = sharedPref.getBoolean
                (SettingsActivity.KEY_MULTIPLE_NOTES_PER_DAY_PREF, false);

        mRecordsListViewModel = ViewModelProviders.of(this).get(RecordsListViewModel.class);
        mRecordsListViewModel.getAllRecords().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                adapter.setRecordList(records);
                adapter.notifyDataSetChanged();

                if (records.isEmpty()) {
                    mEmtpyList.setVisibility(View.VISIBLE);
                } else {
                    mEmtpyList.setVisibility(View.GONE);
                }

                checkFabState(records);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkFabState(List<Record> records) {
        if (!mMultipleNotesPerDay) {
            String today = Utils.getDateStr();
            for (Record record : records) {
                if (record.date.equals(today)) {
                    disableFab();
                    break;
                }
            }
        } else {
            enableFab();
        }
    }

    private void enableFab() {
        mFab.show();
    }

    private void disableFab() {
        mFab.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this,
                    SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete_all) {
            mRecordsListViewModel.deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();

    }
}