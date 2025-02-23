package com.micheale.gebetagame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Set the title for the activity



        // Implement the Reset Stats button functionality
        Button resetButton = findViewById(R.id.buttonResetStats);
        resetButton.setOnClickListener(v -> {
            // Reset the stats here (you can reset game stats in your database or shared preferences)
            resetStats();
        });
    }

    private void resetStats() {
        // Implement your logic to reset the stats here
        // For example, you could clear shared preferences or reset variables
        // Example:
        // SharedPreferences sharedPreferences = getSharedPreferences("GameStats", MODE_PRIVATE);
        // SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.clear();
        // editor.apply();

        // For now, just show a toast message indicating that the stats have been reset
        // Toast.makeText(this, "Stats reset!", Toast.LENGTH_SHORT).show();
    }
}