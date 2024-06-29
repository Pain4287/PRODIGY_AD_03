package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton, pauseButton, resetButton;

    private Handler handler;
    private long startTime, elapsedTime;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();
        isRunning = false;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseStopwatch();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });
    }

    private void startStopwatch() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(updateTimerRunnable, 0);
            isRunning = true;
        }
    }

    private void pauseStopwatch() {
        if (isRunning) {
            handler.removeCallbacks(updateTimerRunnable);
            elapsedTime = System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    private void resetStopwatch() {
        handler.removeCallbacks(updateTimerRunnable);
        elapsedTime = 0;
        isRunning = false;
        timerTextView.setText("00:00:00.000");
    }

    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            int millis = (int) (elapsedTime % 1000);
            int seconds = (int) (elapsedTime / 1000 % 60);
            int minutes = (int) (elapsedTime / 60000 % 60);

            timerTextView.setText(String.format("%02d:%02d:%03d", minutes, seconds, millis));

            handler.postDelayed(this, 10);
        }
    };
}
