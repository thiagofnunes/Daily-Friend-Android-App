package com.thiagonunes.dailyfriend.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thiagonunes.dailyfriend.model.Record;

@Database(entities = {Record.class}, version = 3, exportSchema = false)
public abstract class RecordRoomDatabase extends RoomDatabase {

    public abstract RecordDao recordDao();

    private static RecordRoomDatabase INSTANCE;

    public static RecordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecordRoomDatabase.class, "records_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
