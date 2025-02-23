package com.micheale.gebetagame;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SplashActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "default"; // Notification Channel ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the splash screen layout (make sure activity_splash exists)
        setContentView(R.layout.activity_splash);

        // Trigger vibration
        triggerVibration();

        // Create the notification channel for devices running Android O (API 26) and above
        createNotificationChannel();

        // Show notification
        showNotification();

        // Transition to MainMenuActivity after 3 seconds (3000 milliseconds)
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(SplashActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish(); // Close the SplashActivity
                } catch (Exception e) {
                    e.printStackTrace(); // Log any error if transition fails
                }
            }
        }, 3000); // Delay of 3 seconds
    }

    // Trigger vibration for 500ms
    private void triggerVibration() {
        try {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android O and above, use VibrationEffect
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)); // Vibrate for 500ms
            } else {
                // For older versions, use the simple vibrate method
                vibrator.vibrate(500); // Vibrate for 500ms
            }
        } catch (Exception e) {
            e.printStackTrace(); // Catch any vibration-related exceptions
        }
    }

    // Create Notification Channel (Needed for Android O and above)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Channel for default notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // Show notification
    private void showNotification() {
        try {
            // Build the notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info) // Default icon (you can replace it)
                    .setContentTitle("Welcome to Gebetagame!")
                    .setContentText("Get ready to play.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true); // Auto-cancel the notification when clicked

            // Create an intent for when the notification is clicked
            Intent intent = new Intent(this, MainMenuActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            // Get the NotificationManager
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build()); // Show the notification
        } catch (Exception e) {
            e.printStackTrace(); // Log any errors related to notification
        }
    }
}