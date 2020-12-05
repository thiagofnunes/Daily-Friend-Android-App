package com.thiagonunes.dailyfriend.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "record_table")
public class Record {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "data")
    private String data;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "hour")
    private String hour;



}
