package com.example.rickandmorty.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CharacterEntry {
    @PrimaryKey
    private int id;
    private String name;
    private String status;
    private String species;
    private String image;
    private String gender;

    public CharacterEntry(int id, String name, String image, String species, String status, String gender) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.species = species;
        this.status = status;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
