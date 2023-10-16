package com.example.soundsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundsapp.databinding.ItemCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewholder>{
    Context context;
    ArrayList<CategoryModel> categories;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public categoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new categoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewholder holder, int position) {
        CategoryModel currentItem = categories.get(position);
        holder.binding.categoryName.setText(currentItem.getCategory());
        holder.binding.categoryRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.binding.categoryRv.setAdapter(new SoundAdapter(context, currentItem.getSoundModels()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class categoryViewholder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public categoryViewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoryBinding.bind(itemView);
        }
    }
}
