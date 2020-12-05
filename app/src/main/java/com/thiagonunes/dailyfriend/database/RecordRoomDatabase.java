package com.thiagonunes.dailyfriend.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.thiagonunes.dailyfriend.model.Record;

@Database(entities = {Record.class}, version = 1, exportSchema = false)
public abstract class RecordRoomDatabase extends RoomDatabase {

    public abstract RecordDao recordDao();

    private static RecordRoomDatabase INSTANCE;

    public static RecordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecordRoomDatabase.class, "records_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
