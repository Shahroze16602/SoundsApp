package com.example.soundsapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundsapp.databinding.BottomSheetLayoutBinding;
import com.example.soundsapp.databinding.ItemSoundBinding;

import java.util.ArrayList;
import java.util.Objects;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {
    Context context;
    ArrayList<SoundModel> soundModels;
    static MediaPlayer mediaPlayer=new MediaPlayer();
    private Handler handler;
    BottomSheetLayoutBinding binding;
    boolean isOpened = false;


    public SoundAdapter(Context context, ArrayList<SoundModel> soundModels) {
        this.context = context;
        this.soundModels = soundModels;
    }


    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound, parent, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        handler = new Handler();
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
            if (!isOpened)
                showBottomSheet();
        });
    }

    @Override
    public int getItemCount() {
        return soundModels.size();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder {
        ItemSoundBinding binding;
        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSoundBinding.bind(itemView);
        }
    }
    @SuppressLint({"RestrictedApi", "QueryPermissionsNeeded"})
    private void showBottomSheet() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = BottomSheetLayoutBinding.inflate(LayoutInflater.from(dialog.getContext()));
        dialog.setContentView(binding.getRoot());
        binding.seekBar.setMax(mediaPlayer.getDuration());
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    updateSeekBar();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.btnPlay.setOnClickListener(view -> {
            if (!mediaPlayer.isPlaying()) mediaPlayer.start();
        });
        binding.btnPause.setOnClickListener(view -> mediaPlayer.pause());
        dialog.setOnCancelListener(dialogInterface -> {
            isOpened = false;
        });
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void updateSeekBar() {
        handler.postDelayed(() -> {
            if (mediaPlayer != null) {
                try {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    binding.seekBar.setProgress(currentPosition);
                    updateSeekBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 250);
    }
}
