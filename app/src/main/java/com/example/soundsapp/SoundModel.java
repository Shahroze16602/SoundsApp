package com.example.soundsapp;

public class SoundModel {
    private String name;
    private int imageId;
    private int soundId;

    public SoundModel() {
    }

    public SoundModel(String name, int imageId, int soundId) {
        this.name = name;
        this.imageId = imageId;
        this.soundId = soundId;
    }

    public String getName() {
        return name;
    }

    public void setName(int id) {
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
}
