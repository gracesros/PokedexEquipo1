package org.grace.pokedex.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static org.grace.pokedex.utils.RequestUtils.formatPokemonId;
import static org.grace.pokedex.utils.RequestUtils.getStringId;

@Entity
public class Pokemon {

    @PrimaryKey()
    @NonNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "image")
    private String image;

    public Pokemon() {
    }

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;

        this.id = getStringId(url);

        this.image = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + formatPokemonId(id) + ".png";
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }


    }

