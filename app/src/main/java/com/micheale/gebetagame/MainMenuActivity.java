package com.micheale.gebetagame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private MediaPlayer bgmPlayer;
    private boolean isBgmOn = true;
    private boolean isSfxOn = true;
    private SharedPreferences sharedPreferences;
    private int wins = 0;
    private int losses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Vibrator for button press feedback
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sharedPreferences = getSharedPreferences("GameStats", MODE_PRIVATE);
        wins = sharedPreferences.getInt("wins", 0);
        losses = sharedPreferences.getInt("losses", 0);
        isBgmOn = sharedPreferences.getBoolean("bgm", true);
        isSfxOn = sharedPreferences.getBoolean("sfx", true);

        // Buttons in Main Menu
        Button buttonPlay = findViewById(R.id.buttonPlay);
        Button buttonSettings = findViewById(R.id.buttonSettings);
        Button buttonStats = findViewById(R.id.buttonStats);

        // Background music setup
        bgmPlayer = MediaPlayer.create(this, R.raw.bg_music);
        bgmPlayer.setLooping(true);
        if (isBgmOn) bgmPlayer.start();

        // Play Button - Start Game Activity
        buttonPlay.setOnClickListener(v -> {
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            // Show Game Mode Dialog
            showGameModeDialog();
        });

        // Settings Button - Show Settings Dialog
        buttonSettings.setOnClickListener(v -> {
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            showSettingsDialog();
        });

        // Stats Button - Show Stats Dialog
        buttonStats.setOnClickListener(v -> {
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            showStatsDialog();
        });
    }

    // Show Game Mode Dialog
    private void showGameModeDialog() {
        View gameModeView = getLayoutInflater().inflate(R.layout.dialog_game_mode, null);
        Button buttonAgainstAI = gameModeView.findViewById(R.id.buttonAgainstAI);
        Button buttonTwoPlayers = gameModeView.findViewById(R.id.buttonTwoPlayers);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gameModeView)
                .setTitle("GAMEMODE");

        AlertDialog dialog = builder.create();

        // Against AI Button - Start AI Game
        buttonAgainstAI.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
            intent.putExtra("mode", "AI");
            startActivity(intent);
            dialog.dismiss();
        });

        // Two Players Button - Start Two Player Game
        buttonTwoPlayers.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
            intent.putExtra("mode", "TwoPlayers");
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }

    // Show Settings Dialog
    private void showSettingsDialog() {
        View settingsView = getLayoutInflater().inflate(R.layout.dialog_settings, null);
        Switch switchBgm = settingsView.findViewById(R.id.switchBgm);
        Switch switchSfx = settingsView.findViewById(R.id.switchSfx);

        switchBgm.setChecked(isBgmOn);
        switchSfx.setChecked(isSfxOn);

        switchBgm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isBgmOn = isChecked;
            if (isChecked) {
                if (!bgmPlayer.isPlaying()) bgmPlayer.start();
            } else {
                bgmPlayer.pause();
            }
        });

        switchSfx.setOnCheckedChangeListener((buttonView, isChecked) -> isSfxOn = isChecked);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(settingsView)
                .setTitle("Settings")
                .setPositiveButton("OK", (dialog, which) -> {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("bgm", isBgmOn);
                    editor.putBoolean("sfx", isSfxOn);
                    editor.apply();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Show Stats Dialog
    private void showStatsDialog() {
        View statsView = getLayoutInflater().inflate(R.layout.dialog_stats, null);
        TextView tvWins = statsView.findViewById(R.id.tvWins);
        TextView tvLosses = statsView.findViewById(R.id.tvLosses);
        Button buttonResetStats = statsView.findViewById(R.id.buttonResetStats);

        tvWins.setText(String.valueOf(wins));
        tvLosses.setText(String.valueOf(losses));

        buttonResetStats.setOnClickListener(v -> {
            wins = 0;
            losses = 0;
            tvWins.setText("0");
            tvLosses.setText("0");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("wins", 0);
            editor.putInt("losses", 0);
            editor.apply();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(statsView)
                .setTitle("STATS")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.release();
        }
    }
}