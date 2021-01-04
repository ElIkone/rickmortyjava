package com.example.rickandmorty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.characterDetail.CharacterAdapterHandler;
import com.example.rickandmorty.characterDetail.CharacterDetailFragment;
import com.example.rickandmorty.data.CharacterAdapter;
import com.example.rickandmorty.data.CharacterModel;
import com.example.rickandmorty.databinding.MainFragmentBinding;

public class MainFragment extends Fragment implements CharacterAdapterHandler {
    MainFragmentBinding binding;
    LinearLayoutManager mLayoutManager;
    MainViewModel viewModel;
    boolean checksearchaAvailable = false;
    boolean searching = false;
    private CharacterAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        mAdapter = new CharacterAdapter(this);
        binding = MainFragmentBinding.inflate(inflater, viewGroup, false);
        View view = binding.getRoot();
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        binding.charactersRv.setLayoutManager(mLayoutManager);
        binding.charactersRv.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getCharacterLiveData().observe(getActivity(), characters -> {
            if (characters != null) {
                mAdapter.setCharacterModelList(characters);
            }
        });

        viewModel.loadInfo();

        viewModel.getIsLoading().observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.charactersRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mLayoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                    loadMoreItems();
                }
            }
        });
    }

    private void loadNextPage() {
        if (!checksearchaAvailable && !searching)
            viewModel.loadInfo();
    }

    private void loadMoreItems() {
        loadNextPage();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(sv);

        sv.setOnCloseListener(() -> {
            searching = false;
            viewModel.searchByName(null);
            return true;
        });

        sv.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
                    checksearchaAvailable = hasFocus;
                    searching = true;
                }
        );

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searching = true;
                viewModel.searchByName(s).observe(getActivity(), characters -> {
                    if (characters != null) {
                        mAdapter.setCharacterModelList(characters);
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onCharacterSelected(CharacterModel characterModel) {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("mainFragment");
        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment);
        Fragment characterDetailFragment = CharacterDetailFragment.newInstance(characterModel);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_layout, characterDetailFragment).addToBackStack(null).commit();
    }
}