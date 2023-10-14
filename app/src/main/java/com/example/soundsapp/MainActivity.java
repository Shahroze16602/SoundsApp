package com.example.soundsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.soundsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<CategoryModel> categoryModels = new ArrayList<>();
    CategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SoundModel ak47 = new SoundModel("AK 47", R.drawable.ak_47, R.raw.ak47_sound);
        SoundModel desertEagle = new SoundModel("Desert Eagle", R.drawable.desert_eagle, R.raw.deserteagle_sound);
        SoundModel handGrenade = new SoundModel("Hand Grenade", R.drawable.hand_grenade, R.raw.handgernade_sound);
        SoundModel kar98 = new SoundModel("Kar 98", R.drawable.kar_98, R.raw.kar98_sound);
        SoundModel uzi = new SoundModel("Uzi", R.drawable.uzi, R.raw.uzi_sound);
        ArrayList<SoundModel> gunSounds = new ArrayList<>();
        gunSounds.add(ak47);
        gunSounds.add(desertEagle);
        gunSounds.add(handGrenade);
        gunSounds.add(kar98);
        gunSounds.add(uzi);
        categoryModels.add(new CategoryModel("Guns", gunSounds));
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(this, categoryModels);
        binding.rvCategories.setAdapter(adapter);
    }
}