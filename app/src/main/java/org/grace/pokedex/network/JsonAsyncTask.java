package org.grace.pokedex.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.grace.pokedex.entities.Pokemon;
import org.grace.pokedex.interfaces.AsyncTaskHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.grace.pokedex.network.NetworkUtils.createUrl;
import static org.grace.pokedex.network.NetworkUtils.makeHttpRequest;

 public class JsonAsyncTask extends AsyncTask<Void, Void, List<Pokemon>> {

    public AsyncTaskHandler handler;

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Pokemon> doInBackground(Void... voids) {

        URL url = createUrl("https://pokeapi.co/api/v2/pokemon?offset=0&limit=151");
        // Hacemos la petición. Ésta puede tirar una exepción.
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            return listapokemon(jsonResponse);
        } catch (IOException e) {
            Log.e("Download error", "Problem making the HTTP request.", e);
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Pokemon> pokemonList) {
        super.onPostExecute(pokemonList);
        if (handler != null) {
            handler.onTaskEnd(pokemonList);
        }
    }

     private List<Pokemon> listapokemon(String jsonStr) {
         try {
             JSONObject jsonObj = new JSONObject(jsonStr);
             JSONArray jsonArray = jsonObj.getJSONArray("results");
             ArrayList<Pokemon> pokemonShortList = new ArrayList<>();
             for (int i = 0; i < jsonArray.length(); i++) {
                 String url = jsonArray.getJSONObject(i).getString("url");
                 String name = jsonArray.getJSONObject(i).getString("name");
                 pokemonShortList.add(new Pokemon(name, url));
             }
             return pokemonShortList;
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return null;
     }
}