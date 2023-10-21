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
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundsapp.databinding.BottomSheetLayoutBinding;
import com.example.soundsapp.databinding.ItemSoundBinding;

import java.util.ArrayList;
import java.util.Objects;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {
    Context context;
    ArrayList<SoundModel> soundModels;
    static MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler;
    BottomSheetLayoutBinding binding;
    static boolean isOpened = false;


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
        SoundModel currentItem = soundModels.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(currentItem.getName());
        holder.binding.imgIcon.setImageDrawable(ContextCompat.getDrawable(context, currentItem.getImageId()));
        holder.binding.getRoot().setOnClickListener(view -> {
            if (!isOpened) {
                showBottomSheet(holder.getAdapterPosition());
                isOpened = true;
            }
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
    private void showBottomSheet(int position) {
        final SoundModel[] currentItem = {soundModels.get(position)};
        final int[] currentPosition = {position};
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
        handler = new Handler();
        mediaPlayer = MediaPlayer.create(context, currentItem[0].getSoundId());
        mediaPlayer.setLooping(false);
        MediaPlayer.OnCompletionListener onCompletionListener = mediaPlayer -> {
            if (!binding.switchReplay.isChecked()) {
                binding.playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_play_arrow_24));
                binding.txtPlayPause.setText("Play");
            }
        };
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        Dialog dialog = new Dialog(context);
        binding = BottomSheetLayoutBinding.inflate(LayoutInflater.from(dialog.getContext()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding.seekBar.setMax(mediaPlayer.getDuration());
        binding.tvName.setText(currentItem[0].getName());
        dialog.setContentView(binding.getRoot());
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
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                binding.txtPlayPause.setText("Play");
                binding.playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_play_arrow_24));
            } else {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    binding.txtPlayPause.setText("Pause");
                    binding.playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_pause_24));
                } else {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            }
        });
        binding.btnNext.setOnClickListener(view -> {
            if (currentPosition[0] < soundModels.size() - 1) {
                currentItem[0] = soundModels.get(currentPosition[0] + 1);
                currentPosition[0] += 1;
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, currentItem[0].getSoundId());
                mediaPlayer.setOnCompletionListener(onCompletionListener);
                mediaPlayer.setLooping(false);
                binding.switchReplay.setChecked(false);
                binding.seekBar.setMax(mediaPlayer.getDuration());
                binding.tvName.setText(currentItem[0].getName());
                binding.playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_play_arrow_24));
                binding.txtPlayPause.setText("Play");
            } else {
                Toast.makeText(context, "No more sounds", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnPrevious.setOnClickListener(view -> {
            if (currentPosition[0] > 0) {
                currentItem[0] = soundModels.get(currentPosition[0] - 1);
                currentPosition[0] -= 1;
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, currentItem[0].getSoundId());
                mediaPlayer.setOnCompletionListener(onCompletionListener);
                mediaPlayer.setLooping(false);
                binding.switchReplay.setChecked(false);
                binding.seekBar.setMax(mediaPlayer.getDuration());
                binding.tvName.setText(currentItem[0].getName());
                binding.playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_play_arrow_24));
                binding.txtPlayPause.setText("Play");
            } else {
                Toast.makeText(context, "No more sounds", Toast.LENGTH_SHORT).show();
            }
        });
        binding.switchReplay.setOnCheckedChangeListener((compoundButton, b) -> mediaPlayer.setLooping(b));
        binding.btnRepeat.setOnClickListener(view -> binding.switchReplay.setChecked(!binding.switchReplay.isChecked()));
        binding.btnSetAs.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, binding.imgPopUp);
            popupMenu.inflate(R.menu.set_as_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.opt_ringtone) {
                    ToneManager.setRingtone(context, currentItem[0]);
                } else if (id == R.id.opt_alarm) {
                    ToneManager.setAlarmSound(context, currentItem[0]);
                } else if (id == R.id.opt_notification) {
                    ToneManager.setNotificationSound(context, currentItem[0]);
                }
                return true;
            });
            popupMenu.show();
        });
        dialog.setOnCancelListener(dialogInterface -> {
            isOpened = false;
            mediaPlayer.stop();
        });
        dialog.setOnDismissListener(dialogInterface -> {
            isOpened = false;
            mediaPlayer.stop();
        });
        dialog.show();
        updateSeekBar();
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
        }, 100);
    }
}
