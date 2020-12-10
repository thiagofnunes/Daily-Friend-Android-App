package com.thiagonunes.dailyfriend.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thiagonunes.dailyfriend.database.RecordRepository;

import java.util.List;

public class RecordEditorViewModel extends AndroidViewModel {

    private final RecordRepository mRecordRepository;
    private final LiveData<Record> mRecord;

    public RecordEditorViewModel(@NonNull Application application, int id) {
        super(application);
        mRecordRepository = new RecordRepository(application);
        mRecord = mRecordRepository.getRecord(id);    }

    public LiveData<Record> getRecord() { return mRecord; }

    public void insert(Record record) { mRecordRepository.insert(record); }

    public void deleteRecord(Record record) {mRecordRepository.deleteRecord(record);}



}


