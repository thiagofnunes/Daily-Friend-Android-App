package com.thiagonunes.dailyfriend.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "feelings")
    public String feelings;
}
