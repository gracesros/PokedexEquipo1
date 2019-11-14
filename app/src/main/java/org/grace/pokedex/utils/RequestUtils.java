package org.grace.pokedex.utils;

public class RequestUtils {

    public static String getStringId(String url) {
        String substring = url.replace("https://pokeapi.co/api/v2/pokemon", "");
        String[] array = substring.split("/");
        return array[1];
    }

    public static String formatPokemonId(int id) {
        return formatPokemonId(String.valueOf(id));
    }

    public static String formatPokemonId(String id) {
        switch (id.length()) {
            case 0:
                return "001";
            case 1:
                return "00" + id;
            case 2:
                return "0" + id;
            default:
                return id;
        }
    }
}