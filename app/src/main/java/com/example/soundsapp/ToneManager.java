package com.example.soundsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ToneManager {
    public static void writeMp3file(String name, Listener listener) {
        File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        if (!outputDir.exists()) outputDir.mkdir();
        File outputFile = new File(outputDir, name + ".mp3");
        listener.onWrite(outputFile);
    }

    public static void setRingtone(Context context, SoundModel soundModel) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        if (!Settings.System.canWrite(context)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            context.startActivity(intent);
            return;
        }
        writeMp3file(soundModel.getName(), outputFile -> {
            InputStream inputStream = context.getResources().openRawResource(soundModel.getSoundId());
            try {
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int length;

                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.close();

                Uri ringtoneUri = Uri.fromFile(outputFile);
                RingtoneManager.setActualDefaultRingtoneUri(
                        context,
                        RingtoneManager.TYPE_RINGTONE,
                        ringtoneUri
                );

                Toast.makeText(context, "Ringtone set successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAG", e.getMessage(), e);
                Toast.makeText(context, "Failed to set ringtone", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static void setNotificationSound(Context context, SoundModel soundModel) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        if (!Settings.System.canWrite(context)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            context.startActivity(intent);
            return;
        }
        try {
            File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            File outputFile = new File(outputDir, soundModel.getName() + ".mp3");

            InputStream inputStream = context.getResources().openRawResource(soundModel.getSoundId());
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            Uri notificationSoundUri = Uri.fromFile(outputFile);
            RingtoneManager.setActualDefaultRingtoneUri(
                    context,
                    RingtoneManager.TYPE_NOTIFICATION,
                    notificationSoundUri
            );

            Toast.makeText(context, "Notification sound set successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", e.getMessage(), e);
            Toast.makeText(context, "Failed to set notification sound", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setAlarmSound(Context context, SoundModel soundModel) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }

        if (!Settings.System.canWrite(context)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            context.startActivity(intent);
            return;
        }
        try {
            File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            File outputFile = new File(outputDir, soundModel.getName() + ".mp3");

            InputStream inputStream = context.getResources().openRawResource(soundModel.getSoundId());
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            Uri alarmSoundUri = Uri.fromFile(outputFile);
            RingtoneManager.setActualDefaultRingtoneUri(
                    context,
                    RingtoneManager.TYPE_ALARM,
                    alarmSoundUri
            );

            Toast.makeText(context, "Alarm sound set successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to set alarm sound", Toast.LENGTH_SHORT).show();
        }
    }
}
