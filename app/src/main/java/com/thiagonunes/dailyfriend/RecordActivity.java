package com.thiagonunes.dailyfriend;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.thiagonunes.dailyfriend.model.MyViewModelFactory;
import com.thiagonunes.dailyfriend.model.RecordEditorViewModel;

import static com.thiagonunes.dailyfriend.utils.Constants.KEY_RECORD_ID;

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
        mTextEt = findViewById(R.id.text_editText);

        if (id != -1) {

            RecordEditorViewModel mRecordEditorViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this.getApplication(), id)).get(RecordEditorViewModel.class);

            mRecordEditorViewModel.getRecord().observe(this, record -> {

                mTitleEt.setText(String.format("%s -> %s", record.date, record.title));
                mTextEt.setText(record.text);


            });
        }
    }
}