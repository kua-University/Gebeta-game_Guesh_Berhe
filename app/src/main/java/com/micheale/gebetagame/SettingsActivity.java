package com.micheale.gebetagame;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    // Declare MediaPlayer instance for background music
    private MediaPlayer bgmPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Ensure you have this layout file

        // Get the Switch views for BGM and SFX
        Switch switchBGM = findViewById(R.id.switchBgm); // Ensure this matches your XML ID
        Switch switchSFX = findViewById(R.id.switchSfx); // Ensure this matches your XML ID

        // Set initial states (optional, can be stored using SharedPreferences)
        switchBGM.setChecked(true); // Set to ON by default
        switchSFX.setChecked(true); // Set to ON by default

        // Set listeners to handle when the switches are toggled
        switchBGM.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "BGM On", Toast.LENGTH_SHORT).show();
                // Code to turn on background music
                if (bgmPlayer == null) {
                    bgmPlayer = MediaPlayer.create(SettingsActivity.this, R.raw.bg_music); // Make sure you have a music file in res/raw
                    bgmPlayer.start();
                }
            } else {
                Toast.makeText(SettingsActivity.this, "BGM Off", Toast.LENGTH_SHORT).show();
                // Code to turn off background music
                if (bgmPlayer != null && bgmPlayer.isPlaying()) {
                    bgmPlayer.pause();
                    bgmPlayer.seekTo(0);  // Optionally reset the music position
                }
            }
        });

        switchSFX.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsActivity.this, "SFX On", Toast.LENGTH_SHORT).show();
                // Code to turn on sound effects (use SoundPool or any other method here)
            } else {
                Toast.makeText(SettingsActivity.this, "SFX Off", Toast.LENGTH_SHORT).show();
                // Code to turn off sound effects (stop or mute SoundPool or any other method used)
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources when the activity is destroyed
        if (bgmPlayer != null) {
            bgmPlayer.release();
            bgmPlayer = null;
        }
    }
}