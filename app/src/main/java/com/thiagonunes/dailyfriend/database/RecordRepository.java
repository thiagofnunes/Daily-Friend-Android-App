package com.thiagonunes.dailyfriend.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.thiagonunes.dailyfriend.model.Record;

import java.util.List;

public class RecordRepository {

    private RecordDao mRecordDao;
    private LiveData<List<Record>> mAllRecords;

    public RecordRepository(Application application) {
        RecordRoomDatabase db = RecordRoomDatabase.getDatabase(application);
        mRecordDao = db.recordDao();
        mAllRecords = mRecordDao.getAllRecords();
    }

    public LiveData<List<Record>> getAllRecords() {
        return mAllRecords;
    }

    public LiveData<Record> getRecord(int id){
        return mRecordDao.getRecord(id);
    }

    public void insert (Record record) {
        new insertAsyncTask(mRecordDao).execute(record);
    }

    public void deleteRecord (Record record) {
        new deleteRecordAsyncTask(mRecordDao).execute(record);
    }

    public void deleteAllRecords () {
        new deleteAllRecordsAsyncTask(mRecordDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Record,Void,Void> {

        private RecordDao mAsyncTaskDao;

        insertAsyncTask(RecordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Record... records) {
            mAsyncTaskDao.insert(records[0]);
            return null;
        }
    }

    private static class deleteAllRecordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecordDao mAsyncTaskDao;

        deleteAllRecordsAsyncTask(RecordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteRecordAsyncTask extends AsyncTask<Record, Void, Void> {
        private RecordDao mAsyncTaskDao;

        deleteRecordAsyncTask(RecordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Record... params) {
            mAsyncTaskDao.deleteRecord(params[0]);
            return null;
        }
    }


}
