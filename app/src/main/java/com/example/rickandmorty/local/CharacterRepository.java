package com.example.rickandmorty.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.rickandmorty.data.CharacterModel;

import java.util.List;

public class CharacterRepository {
    private final CharacterDao mCharacterDao;
    private final LiveData<List<CharacterModel>> mAllCharacters;

    public CharacterRepository(Application application) {
        CharacterModelDatabase db = CharacterModelDatabase.getCharacterModelDatabase(application);
        mCharacterDao = db.characterDao();
        mAllCharacters = mCharacterDao.getAllCharacters();
    }

    public LiveData<List<CharacterModel>> getAllCharacters() {
        return mAllCharacters;
    }

    public LiveData<List<CharacterModel>> getCharactersByName(String name) {
        if (name == null)
            return getAllCharacters();
        else
            return mCharacterDao.getCharacterByName(name);
    }

    public void insert(CharacterEntry characterEntry) {
        CharacterModelDatabase.databaseWriteExecutor.execute(() -> {
            mCharacterDao.insertCharacter(characterEntry);
        });
    }
}