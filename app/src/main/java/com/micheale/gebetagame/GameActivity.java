package com.micheale.gebetagame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private boolean isPlayer1Turn = true; // Player 1 starts first
    private int[] pits = new int[14];  // 14 pits (10 small + 2 large)
    private Button[] pitButtons = new Button[10];  // Store pit buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize pits with initial values (3 seeds in each small pit)
        for (int i = 0; i < pits.length; i++) {
            if (i != 6 && i != 13) {  // Skip large pits (index 6 for Player 1, index 13 for Player 2)
                pits[i] = 3; // Each small pit starts with 3 seeds
            }
        }

        // Find buttons for all small pits (index 0 to 9)
        pitButtons[0] = findViewById(R.id.pit1);
        pitButtons[1] = findViewById(R.id.pit2);
        pitButtons[2] = findViewById(R.id.pit3);
        pitButtons[3] = findViewById(R.id.pit4);
        pitButtons[4] = findViewById(R.id.pit5);
        pitButtons[5] = findViewById(R.id.pit6);
        pitButtons[6] = findViewById(R.id.pit7);
        pitButtons[7] = findViewById(R.id.pit8);
        pitButtons[8] = findViewById(R.id.pit9);
        pitButtons[9] = findViewById(R.id.pit10);

        // Set click listeners for the pits
        for (int i = 0; i < pitButtons.length; i++) {
            final int pitIndex = i;
            pitButtons[i].setOnClickListener(v -> {
                onPitClicked(pitIndex);
            });
        }
    }

    // Handle pit click (a player picks seeds from a pit)
    private void onPitClicked(int pitIndex) {
        // Ensure the player clicks only their own pits
        if (isPlayer1Turn && pitIndex < 5) {
            makeMove(pitIndex);
        } else if (!isPlayer1Turn && pitIndex >= 5 && pitIndex < 10) {
            makeMove(pitIndex);
        } else {
            Toast.makeText(this, "It's not your turn or invalid pit!", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeMove(int pitIndex) {
        // Get the number of seeds in the selected pit
        int seedsInPit = pits[pitIndex];

        // If the selected pit has seeds, start the move process
        if (seedsInPit > 0) {
            pits[pitIndex] = 0;  // Empty the selected pit

            // Distribute seeds around the board
            int currentPitIndex = pitIndex;
            while (seedsInPit > 0) {
                currentPitIndex = (currentPitIndex + 1) % pits.length;  // Circular distribution

                // Skip the large pits (index 6 for Player 1, index 13 for Player 2)
                if (currentPitIndex != 6 && currentPitIndex != 13) {
                    pits[currentPitIndex]++;
                    seedsInPit--;
                }
            }

            // Update UI (you should update the button labels to show the seed count)
            updatePitButtons();

            // Switch turns after the move
            isPlayer1Turn = !isPlayer1Turn;
        } else {
            Toast.makeText(this, "This pit is empty! Choose another one.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePitButtons() {
        // Update the button labels to show the number of seeds in each pit
        for (int i = 0; i < pitButtons.length; i++) {
            pitButtons[i].setText(String.valueOf(pits[i]));
        }
    }
}
