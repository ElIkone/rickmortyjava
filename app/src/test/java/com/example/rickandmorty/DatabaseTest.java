package com.example.rickandmorty;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.example.rickandmorty.local.CharacterDao;
import com.example.rickandmorty.local.CharacterEntry;
import com.example.rickandmorty.local.CharacterModelDatabase;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {
    private CharacterDao userDao;
    private CharacterModelDatabase db;

    @Before
    @Config(sdk = 28)
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, CharacterModelDatabase.class).allowMainThreadQueries().build();
        userDao = db.characterDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertData() throws Exception {
        CharacterEntry characterEntry = new CharacterEntry(1,"benjamin","dead","human","male", "test");
        userDao.insertCharacter(characterEntry);
    }

    @Test
    public void insertDataAndGetInfo() throws Exception {
        CharacterEntry characterEntry = new CharacterEntry(1,"benjamin","https://rickandmortyapi.com/api/character/avatar/361.jpeg","human","dead", "male");
        CharacterEntry characterEntry2 = new CharacterEntry(2,"rick","https://rickandmortyapi.com/api/character/avatar/361.jpeg","human","alive", "male");
        userDao.insertCharacter(characterEntry);
        userDao.insertCharacter(characterEntry2);
        userDao.getAllCharacters();
    }

    @Test
    public void deleteCharacterById() throws Exception {
        CharacterEntry characterEntry = new CharacterEntry(1,"benjamin","https://rickandmortyapi.com/api/character/avatar/361.jpeg","human","dead", "male");
        userDao.deleteByUserId(characterEntry.getId());
    }
}
