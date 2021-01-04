package com.example.rickandmorty.data;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmorty.characterDetail.CharacterAdapterHandler;
import com.example.rickandmorty.databinding.RowCharacterBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> implements Filterable {
    private final List<CharacterModel> characterModelList;
    private final CharacterAdapterHandler mHandler;
    private List<CharacterModel> movieListFiltered;

    public CharacterAdapter(CharacterAdapterHandler handler) {
        mHandler = handler;
        this.characterModelList = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    movieListFiltered = characterModelList;
                } else {
                    List<CharacterModel> filteredList = new ArrayList<>();
                    for (CharacterModel characterModel : filteredList) {
                        if (characterModel.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(characterModel);
                        }
                    }
                    movieListFiltered = filteredList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = movieListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieListFiltered = (ArrayList<CharacterModel>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        RowCharacterBinding rowCharacterBinding = RowCharacterBinding.inflate(li, parent, false);
        return new ViewHolder(rowCharacterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CharacterModel characterModel = characterModelList.get(position);
        holder.bindView(characterModel.getName(), characterModel.getStatus(), characterModel.getUrl_image(), Integer.toString(characterModel.getId()), characterModel.getSpecies(), characterModel.getGender());
        holder.itemView.setOnClickListener(view -> mHandler.onCharacterSelected(characterModel));
        Picasso.get().load(characterModel.getUrl_image()).into(holder.binding.imageCharacter);
    }

    @Override
    public int getItemCount() {
        return characterModelList.size();
    }

    public void setCharacterModelList(List<CharacterModel> characterModelList) {
        this.characterModelList.clear();
        this.characterModelList.addAll(characterModelList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowCharacterBinding binding;

        ViewHolder(RowCharacterBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bindView(String title, String status, String url, String id, String species, String gender) {
            binding.name.setText(title);
            binding.speciesAndStatus.setText(status);
        }
    }
}