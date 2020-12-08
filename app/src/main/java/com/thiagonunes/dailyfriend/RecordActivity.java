package com.thiagonunes.dailyfriend;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStore;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.thiagonunes.dailyfriend.database.RecordRepository;
import com.thiagonunes.dailyfriend.model.MyViewModelFactory;
import com.thiagonunes.dailyfriend.model.Record;
import com.thiagonunes.dailyfriend.model.RecordEditorViewModel;
import com.thiagonunes.dailyfriend.utils.Constants;
import com.thiagonunes.dailyfriend.utils.Utils;

import java.util.Objects;

import static com.thiagonunes.dailyfriend.utils.Constants.KEY_RECORD_ID;
import static com.thiagonunes.dailyfriend.utils.Constants.RECORD_TEXT;
import static com.thiagonunes.dailyfriend.utils.Constants.RECORD_TITLE;

public class RecordActivity extends AppCompatActivity {

    private int id;
    private RecordEditorViewModel mRecordEditorViewModel;
    private EditText mTitleEt, mTextEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(KEY_RECORD_ID, -1);
        }

        mTitleEt = findViewById(R.id.title_editText);
        mTextEt = findViewById(R.id.emailTextInputEditText);

        if(savedInstanceState!=null)
        {
            id = savedInstanceState.getInt(KEY_RECORD_ID,-1);
            mTitleEt.setText(savedInstanceState.getString(RECORD_TITLE,""));
            mTextEt.setText(savedInstanceState.getString(RECORD_TEXT,""));
        }

        if (id != -1) {

            mRecordEditorViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), id)).get(RecordEditorViewModel.class);

            mRecordEditorViewModel.getRecord().observe(this, record -> {
                if (record != null) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(record.date);
                    mTitleEt.setText(record.title);
                    mTextEt.setText(record.text);
                }


            });
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(String.format("New (%s)", Utils.getDateStr()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (id == -1) {
            menu.findItem(R.id.action_delete).setEnabled(false);
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int optionId = item.getItemId();

        if (optionId == R.id.action_delete) {
            if (id != -1) {
                mRecordEditorViewModel.deleteRecord(mRecordEditorViewModel.getRecord().getValue());
                getViewModelStore().clear();
                finish();
            }
            return true;
        } else if (optionId == R.id.action_save) {

            String title = mTitleEt.getText().toString();
            String text = mTextEt.getText().toString();

            if (id == -1) {
                Record record = new Record();
                record.title = title;
                record.text = text;
                record.date = Utils.getDateStr();
                record.hour = Utils.getHourStr();

                RecordRepository mRepository = new RecordRepository(getApplication());
                mRepository.insert(record);

            } else {
                Record record = mRecordEditorViewModel.getRecord().getValue();
                record.title = title;
                record.text = text;
                mRecordEditorViewModel.insert(record);
            }

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_RECORD_ID, id);
        savedInstanceState.putString(RECORD_TITLE, mTitleEt.getText().toString());
        savedInstanceState.putString(RECORD_TEXT, mTextEt.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();

    }
}