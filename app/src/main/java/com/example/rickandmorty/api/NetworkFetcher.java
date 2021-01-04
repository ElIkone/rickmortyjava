package com.example.rickandmorty.api;

import com.example.rickandmorty.data.CharactersModel;

import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkFetcher {
    private static final String BASE_URL = "https://rickandmortyapi.com/";
    private final CharacterAPI characterAPI;

    public NetworkFetcher() {
        characterAPI = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterAPI.class);
    }

    public void fetchCharacters(int page, Callback<CharactersModel> callback) {
        if (page == 1) {
            characterAPI.getCharacters().enqueue(callback);
        } else {
            characterAPI.getCharactersPage(page).enqueue(callback);
        }
    }
}
