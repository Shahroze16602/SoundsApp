package com.example.soundsapp;

import java.util.ArrayList;

public class CategoryModel {
    private String category;
    private ArrayList<SoundModel> soundModels;

    public CategoryModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<SoundModel> getSoundModels() {
        return soundModels;
    }

    public void setSoundModels(ArrayList<SoundModel> soundModels) {
        this.soundModels = soundModels;
    }
}
