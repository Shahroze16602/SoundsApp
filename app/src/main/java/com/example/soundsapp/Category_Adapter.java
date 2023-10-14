package com.example.soundsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.Category_viewholder>{
    Context context;
    @NonNull
    @Override
    public Category_Adapter.Category_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new Category_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.Category_viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class Category_viewholder extends RecyclerView.ViewHolder {
        TextView Category_Name;

        public Category_viewholder(@NonNull View itemView) {
            super(itemView);
            Category_Name = itemView.findViewById(R.id.category_name);

        }
    }
}
