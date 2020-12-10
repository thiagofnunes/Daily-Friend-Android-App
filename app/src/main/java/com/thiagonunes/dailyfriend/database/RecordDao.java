package com.thiagonunes.dailyfriend.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
