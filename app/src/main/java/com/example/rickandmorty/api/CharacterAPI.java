package com.example.rickandmorty.api;

import com.example.rickandmorty.data.CharactersModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharacterAPI {
    @GET("api/character")
    Call<CharactersModel> getCharacters();

    @GET("api/character")
    Call<CharactersModel> getCharactersPage(
            @Query("page") int pageIndex
    );

    @GET("character/{id}")
    Call<CharactersModel> getCharactersId();
}