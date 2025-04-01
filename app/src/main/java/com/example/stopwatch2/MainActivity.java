package com.example.stopwatch2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean isRunning;

    private TextView timerText;
    private EditText hourInput, minuteInput, secondInput;
    private Button startTimerBtn, pauseTimerBtn, resetTimerBtn;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Stopwatch
        chronometer = findViewById(R.id.chronometer);
        Button startButton = findViewById(R.id.startButton);
        Button pauseButton = findViewById(R.id.pauseButton);
        Button resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(view -> startChronometer());
        pauseButton.setOnClickListener(view -> pauseChronometer());
        resetButton.setOnClickListener(view -> resetChronometer());

        // Timer
        timerText = findViewById(R.id.timerText);
        hourInput = findViewById(R.id.hourInput);
        minuteInput = findViewById(R.id.minuteInput);
        secondInput = findViewById(R.id.secondInput);
        startTimerBtn = findViewById(R.id.startTimer);
        pauseTimerBtn = findViewById(R.id.pauseTimer);
        resetTimerBtn = findViewById(R.id.resetTimer);

        startTimerBtn.setOnClickListener(view -> startTimer());
        pauseTimerBtn.setOnClickListener(view -> pauseTimer());
        resetTimerBtn.setOnClickListener(view -> resetTimer());
    }

    // Stopwatch Functions
    private void startChronometer() {
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            isRunning = true;
        }
    }

    private void pauseChronometer() {
        if (isRunning) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
        }
    }

    private void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        chronometer.stop();
        isRunning = false;
    }

    // Timer Functions
    private void startTimer() {
        if (!timerRunning) {
            int hours = Integer.parseInt(hourInput.getText().toString());
            int minutes = Integer.parseInt(minuteInput.getText().toString());
            int seconds = Integer.parseInt(secondInput.getText().toString());

            timeLeftInMillis = (hours * 3600000) + (minutes * 60000) + (seconds * 1000);

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerText();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    timerText.setText("00:00:00");
                }
            }.start();
            timerRunning = true;
        }
    }

    private void pauseTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerText.setText("00:00:00");
        hourInput.setText("");
        minuteInput.setText("");
        secondInput.setText("");
        timerRunning = false;
    }

    private void updateTimerText() {
        int hours = (int) (timeLeftInMillis / 3600000);
        int minutes = (int) ((timeLeftInMillis % 3600000) / 60000);
        int seconds = (int) ((timeLeftInMillis % 60000) / 1000);

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(timeFormatted);
    }
}
