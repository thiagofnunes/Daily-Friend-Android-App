package com.thiagonunes.dailyfriend.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thiagonunes.dailyfriend.database.RecordRepository;

import java.util.List;

public class RecordsListViewModel extends AndroidViewModel {

    private final RecordRepository mRecordRepository;
    private final LiveData<List<Record>> mAllRecords;

    public RecordsListViewModel(@NonNull Application application) {
        super(application);
        mRecordRepository = new RecordRepository(application);
        mAllRecords = mRecordRepository.getAllRecords();
    }

    public LiveData<List<Record>> getAllRecords() { return mAllRecords; }

    public void deleteAll() {mRecordRepository.deleteAllRecords();}

    public void deleteRecord(Record record) {mRecordRepository.deleteRecord(record);}



}

