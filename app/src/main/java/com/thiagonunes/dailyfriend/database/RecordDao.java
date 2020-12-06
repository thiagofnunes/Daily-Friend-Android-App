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

    @Query("SELECT * from record_table ORDER BY id DESC")
    LiveData<List<Record>> getAllRecords();

    @Query("SELECT * from record_table where id=:idRecord LIMIT 1")
    LiveData<Record> getRecord(int idRecord);

    @Delete
    void deleteRecord(Record record);

    @Query("DELETE FROM record_table ")
    void deleteAll();


}
