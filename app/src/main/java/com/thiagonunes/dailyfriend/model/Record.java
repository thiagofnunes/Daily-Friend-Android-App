package com.thiagonunes.dailyfriend.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "record_table")
public class Record {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "hour")
    public String hour;



}
