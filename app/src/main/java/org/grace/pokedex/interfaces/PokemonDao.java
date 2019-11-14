package org.grace.pokedex.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.grace.pokedex.entities.Pokemon;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    List<Pokemon> getAll();

    @Query("SELECT * FROM pokemon WHERE id IN (:pokemonIds)")
    List<Pokemon> loadAllByIds(int[] pokemonIds);

    @Query("SELECT * FROM pokemon WHERE name LIKE :name LIMIT 1")
    Pokemon findByName(String name);

    @Insert
    void insertAll(Pokemon... pokemons);

    @Delete
    void delete(Pokemon pokemon);
}