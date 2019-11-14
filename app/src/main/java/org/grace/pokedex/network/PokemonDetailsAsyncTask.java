package org.grace.pokedex.network;



import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.grace.pokedex.entities.PokemonDetails;
import org.grace.pokedex.interfaces.AsyncTaskHandler;

import java.io.IOException;
import java.net.URL;

import static org.grace.pokedex.network.NetworkUtils.createUrl;
import static org.grace.pokedex.network.NetworkUtils.makeHttpRequest;

public class PokemonDetailsAsyncTask extends AsyncTask<String, Void, PokemonDetails> {

    public AsyncTaskHandler handler;

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected PokemonDetails doInBackground(String... urls) {

        URL url = createUrl(urls[0]);
        // Hacemos la petición. Ésta puede tirar una exepción.
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            return pokemonDetails(jsonResponse);
        } catch (IOException e) {
            Log.e("Download error", "Problem making the HTTP request.", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(PokemonDetails pokemonDetails) {
        super.onPostExecute(pokemonDetails);
        if (handler != null) {
            handler.onTaskEnd(pokemonDetails);
        }
    }

    private PokemonDetails pokemonDetails(String jsonStr) {
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            int baseExperience = jsonObj.getInt("base_experience");
            String name = jsonObj.getString("name");
            int weight = jsonObj.getInt("weight");
            int id = jsonObj.getInt("id");

            JSONArray jsonArray = jsonObj.getJSONArray("types");
            String[] types = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                String type = jsonArray.getJSONObject(i).getJSONObject("type").getString("name");
                types[i] = type;
            }
            PokemonDetails pokemonDetails = new PokemonDetails(name, id, baseExperience, weight, types);
            return pokemonDetails;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}