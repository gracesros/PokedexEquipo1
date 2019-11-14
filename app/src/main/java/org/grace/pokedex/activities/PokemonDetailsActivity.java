package org.grace.pokedex.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.grace.pokedex.R;
import org.grace.pokedex.database.AppDatabase;
import org.grace.pokedex.entities.Pokemon;
import org.grace.pokedex.entities.PokemonDetails;
import org.grace.pokedex.interfaces.AsyncTaskHandler;
import org.grace.pokedex.network.PokemonDetailsAsyncTask;

public class PokemonDetailsActivity extends AppCompatActivity implements AsyncTaskHandler {

    ImageView image, favorite;
    TextView name, types, weight, experience, id;

    AppDatabase database;

    // Pokemon info
    String url;
    String pokemonName;
    Pokemon favoritePokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        image = findViewById(R.id.details_image);
        favorite = findViewById(R.id.details_favorite);
        name = findViewById(R.id.details_name);
        types = findViewById(R.id.detatils_type);
        weight = findViewById(R.id.detatils_weight);
        experience = findViewById(R.id.detatils_experience);
        id = findViewById(R.id.details_id);

        url = getIntent().getStringExtra("URL");

        PokemonDetailsAsyncTask pokemonDetailsAsyncTask = new PokemonDetailsAsyncTask();
        pokemonDetailsAsyncTask.handler = this;
        pokemonDetailsAsyncTask.execute(url);

        database = AppDatabase.getDatabase(this);
    }

    @Override
    public void onTaskEnd(Object result) {
        PokemonDetails details = (PokemonDetails) result;
        pokemonName = details.getName();
        Glide.with(this).load(details.getImage()).into(image);
        name.setText(details.getName());
        weight.setText("Peso: " + details.getWeight());
        experience.setText("Experiencia: "  + details.getBaseExperience());
        id.setText("ID: " + details.getId());

        String typesString = "";

        for (int i = 0; i < details.getTypes().length; i++) {
            typesString += details.getTypes()[i] + " ";
        }

        types.setText("Tipo: " + typesString);

        favoritePokemon = database.pokemonDao().findByName(details.getName());

        if (favoritePokemon != null) {
            Glide.with(this).load(R.drawable.pokeball_splash).into(favorite);
        }
    }

    public void onClickType(View view) {
            Intent intent = new Intent(this, PokemonTypeActivity.class);
            startActivity(intent);
    }

    public void onClickFavorite(View view) {
        if(favoritePokemon != null) {
            showAlert(this);
        }
        else {
            Pokemon pokemon = new Pokemon(pokemonName, url);
            database.pokemonDao().insertAll(pokemon);
            Glide.with(this).load(R.drawable.pokeball_splash).into(favorite);
        }
    }

    private void showAlert(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Seguro que quieres eliminar este pokemón de favoritos?");

        // Add the buttons
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                database.pokemonDao().delete(favoritePokemon);
                favoritePokemon = null;
                Glide.with(context).load(R.drawable.favorite_empty).into(favorite);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Show
        dialog.show();
    }
}
