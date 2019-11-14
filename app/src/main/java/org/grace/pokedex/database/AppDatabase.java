package org.grace.pokedex.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.grace.pokedex.entities.Pokemon;
import org.grace.pokedex.interfaces.PokemonDao;

@Database(entities = {Pokemon.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();

    private static AppDatabase database;

    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "pokemonDB").allowMainThreadQueries().build();
        }

        return database;
    }
}