package com.example.rickandmorty.characterDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rickandmorty.R;
import com.example.rickandmorty.data.CharacterModel;
import com.example.rickandmorty.databinding.DetailsFragmentBinding;
import com.squareup.picasso.Picasso;

public class CharacterDetailFragment extends Fragment {
    DetailsFragmentBinding binding;

    public static CharacterDetailFragment newInstance(CharacterModel characterModel) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", characterModel.getName());
        bundle.putString("species", characterModel.getSpecies());
        bundle.putString("image", characterModel.getUrl_image());
        bundle.putString("gender", characterModel.getGender());
        bundle.putString("status", characterModel.getStatus());

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        String data = bundle.getString("name");
        String poster = bundle.getString("image");
        String species = bundle.getString("species");
        String status = bundle.getString("status");
        String gender = bundle.getString("gender");

        binding = DetailsFragmentBinding.inflate(inflater, viewGroup, false);
        binding.name.setText(data);
        binding.species.setText(species);
        binding.gender.setText(gender);
        binding.status.setText(status);
        Picasso.get().load(poster).into((binding.image));
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.search_bar).setVisible(false);
        MenuItem menuSearch = menu.findItem(R.id.search_bar);
        menuSearch.collapseActionView();
        super.onPrepareOptionsMenu(menu);
    }
}
