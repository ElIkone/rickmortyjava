package com.example.rickandmorty.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rickandmorty.data.CharacterModel;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM characterentry")
    LiveData<List<CharacterModel>> getAllCharacters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterEntry characterEntry);

    @Query("SELECT * FROM characterentry WHERE name LIKE '%' || :name || '%'")
    LiveData<List<CharacterModel>> getCharacterByName(String name);
}
