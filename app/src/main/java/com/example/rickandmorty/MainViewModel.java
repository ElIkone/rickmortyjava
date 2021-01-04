package com.example.rickandmorty;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rickandmorty.api.NetworkFetcher;
import com.example.rickandmorty.data.CharacterModel;
import com.example.rickandmorty.data.CharactersModel;
import com.example.rickandmorty.local.CharacterEntry;
import com.example.rickandmorty.local.CharacterRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private final NetworkFetcher networkFetcher;
    private final MutableLiveData<List<CharacterModel>> mCharacters = new MutableLiveData<>();
    private final CharacterRepository mRepository;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private CharactersModel mLastCharactersModel = null;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterRepository(application);
        networkFetcher = new NetworkFetcher();
    }

    public LiveData<List<CharacterModel>> searchByName(String name) {
        return mRepository.getCharactersByName(name);
    }

    public LiveData<List<CharacterModel>> getCharacterLiveData() {
        return mRepository.getAllCharacters();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadInfo() {
        isLoading.setValue(true);
        int pageToFetch = 1;

        if (mLastCharactersModel != null) {
            try {
                pageToFetch = Integer.parseInt(mLastCharactersModel.info.next.substring(mLastCharactersModel.info.next.lastIndexOf("=") + 1));
            } catch (Exception e) {
                Log.e("MainViewModel", "Can't parse number " + e);
            }

            if (pageToFetch == mLastCharactersModel.info.pages + 1) {
                pageToFetch = -1;
            }
        }

        if (pageToFetch != -1) {
            networkFetcher.fetchCharacters(pageToFetch, new Callback<CharactersModel>() {
                @Override
                public void onResponse(@NotNull Call<CharactersModel> call, @NotNull Response<CharactersModel> response) {
                    if (response.body() != null) {
                        mLastCharactersModel = response.body();
                        List<CharacterModel> characters = response.body().results;

                        for (int i = 1; i < characters.size(); i++) {
                            CharacterEntry characterEntry = new CharacterEntry(characters.get(i).getId(), characters.get(i).getName(),
                                    characters.get(i).getImage(), characters.get(i).getSpecies(), characters.get(i).getStatus(),
                                    characters.get(i).getGender());
                            mRepository.insert(characterEntry);
                            isLoading.postValue(false);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CharactersModel> call, @NotNull Throwable t) {
                    isLoading.setValue(false);
                    mCharacters.postValue(null);
                }
            });
        }
    }
}