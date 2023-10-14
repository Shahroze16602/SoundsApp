package com.example.soundsapp;

public class SoundModel {
    private String name;
    private int imageId;
    private int soundId;
    private String category;

    public SoundModel() {
    }

    public SoundModel(String name, int imageId, int soundId, String category) {
        this.name = name;
        this.imageId = imageId;
        this.soundId = soundId;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(int id) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
