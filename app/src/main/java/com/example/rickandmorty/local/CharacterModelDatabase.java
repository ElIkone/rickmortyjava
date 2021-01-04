package com.example.rickandmorty.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CharacterEntry.class}, version = 6, exportSchema = false)
public abstract class CharacterModelDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static CharacterModelDatabase INSTANCE;

    public static CharacterModelDatabase getCharacterModelDatabase(Context context) {
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                CharacterModelDatabase.class, "character_database.db").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return INSTANCE;
    }

    public abstract CharacterDao characterDao();
}