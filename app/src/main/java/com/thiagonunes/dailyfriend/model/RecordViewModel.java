package com.thiagonunes.dailyfriend.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thiagonunes.dailyfriend.database.RecordRepository;

import java.util.List;

public class RecordViewModel extends AndroidViewModel {

    private final RecordRepository mRecordRepository;
    private final LiveData<List<Record>> mAllRecords;

    public RecordViewModel(@NonNull Application application) {
        super(application);
        mRecordRepository = new RecordRepository(application);
        mAllRecords = mRecordRepository.getAllRecords();
    }



    public LiveData<List<Record>> getAllRecords() { return mAllRecords; }

    public void insert(Record record) { mRecordRepository.insert(record); }

    public void deleteAll() {mRecordRepository.deleteAllRecords();}

    public void deleteWord(Record record) {mRecordRepository.deleteRecord(record);}

    

}