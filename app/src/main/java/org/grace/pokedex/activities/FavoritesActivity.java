package org.grace.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.grace.pokedex.R;
import org.grace.pokedex.adapters.PokemonAdapter;
import org.grace.pokedex.database.AppDatabase;
import org.grace.pokedex.entities.Pokemon;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements PokemonAdapter.ItemClickListener {

    PokemonAdapter adapter;
    RecyclerView recyclerView;
    AppDatabase database;

    List<Pokemon> favoritePokemons;
    Pokemon selectedPokemon;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_pokemon);

        database = AppDatabase.getDatabase(this);
        favoritePokemons = database.pokemonDao().getAll();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PokemonAdapter(this, favoritePokemons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(selectedPokemon != null) {
            boolean pokemonWasRemoved = database.pokemonDao().findByName(selectedPokemon.getName()) == null;
            if(pokemonWasRemoved) {
                favoritePokemons.remove(selectedPokemon);
                adapter.notifyItemRemoved(selectedPosition);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        selectedPokemon = adapter.getPokemon(position);
        selectedPosition = position;
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("URL", selectedPokemon.getUrl());
        startActivity(intent);
    }
}
