package org.grace.pokedex.entities;

import static org.grace.pokedex.utils.RequestUtils.formatPokemonId;

public class PokemonDetails {

    private String name;
    private int id;
    private int baseExperience;
    private int weight;
    private String[] types;
    private String image;

    public PokemonDetails(String name, int id, int baseExperience, int weight, String[] types) {
        this.name = name;
        this.id = id;
        this.baseExperience = baseExperience;
        this.weight = weight;
        this.types = types;
        this.image = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + formatPokemonId(id) + ".png";
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public int getWeight() {
        return weight;
    }

    public String[] getTypes() {
        return types;
    }

    public String getImage() {
        return image;
    }
}