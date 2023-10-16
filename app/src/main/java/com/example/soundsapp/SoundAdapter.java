package com.example.soundsapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundsapp.databinding.ItemSoundBinding;

import java.util.ArrayList;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {
    Context context;
    ArrayList<SoundModel> soundModels;
    FragmentManager fragmentManager;
    static MediaPlayer mediaPlayer;


    public SoundAdapter(Context context, ArrayList<SoundModel> soundModels) {
        this.context = context;
        this.soundModels = soundModels;
            this.fragmentManager = fragmentManager;

    }


    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound, parent, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        SoundModel currentItem = soundModels.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(currentItem.getName());
        holder.binding.imgIcon.setImageDrawable(ContextCompat.getDrawable(context, currentItem.getImageId()));
        holder.binding.getRoot().setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
            mediaPlayer = MediaPlayer.create(context, currentItem.getSoundId());
            mediaPlayer.start();
//            MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment();
//            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());

        });
    }

    @Override
    public int getItemCount() {
        return soundModels.size();
    }

    public class SoundViewHolder extends RecyclerView.ViewHolder {
        ItemSoundBinding binding;
        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSoundBinding.bind(itemView);
        }
    }
}
