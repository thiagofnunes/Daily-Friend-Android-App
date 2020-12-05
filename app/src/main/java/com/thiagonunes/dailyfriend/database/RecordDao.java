package com.thiagonunes.dailyfriend.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.thiagonunes.dailyfriend.model.Record;

import java.util.List;

@Dao
public interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Record record);

    @Query("SELECT * from Constants.record_table ORDER BY id DESC")
    LiveData<List<Record>> getAllRecords();

    @Delete
    void deleteRecord(Record record);

    @Query("DELETE FROM Constants.record_table ")
    void deleteAll();


}
