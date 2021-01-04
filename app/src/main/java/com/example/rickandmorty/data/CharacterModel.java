package com.example.rickandmorty.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterModel implements Parcelable {
    public static final Creator<CharacterModel> CREATOR = new Creator<CharacterModel>() {
        @Override
        public CharacterModel createFromParcel(Parcel in) {
            return new CharacterModel(in);
        }

        @Override
        public CharacterModel[] newArray(int size) {
            return new CharacterModel[size];
        }
    };

    private int id;
    private String name;
    private String status;
    private String species;
    private String image;
    private String gender;

    public CharacterModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readString();
        species = in.readString();
        image = in.readString();
        gender = in.readString();
    }
    public CharacterModel(int id, String name, String gender, String status, String species, String image) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.status = status;
        this.species = species;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

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

    public String getUrl_image() {
        return image;
    }

    public void setUrl_image(String image) {
        this.image = image;
    }
}