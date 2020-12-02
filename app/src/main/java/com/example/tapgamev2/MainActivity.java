package com.example.tapgamev2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View screenView;
    Button clickMe;
    int[] images;

    TextView tv_result, tv_info, tv_lose, tv_taps;

    int currentTaps = 0;
    int checkPoint=1;
    int tapsToWin=10;

    boolean gameStarted = false;
    CountDownTimer timer;
    long leftLimit = 10000L;
    long rightLimit = 60000L;

    long finishTime=10000;

    int bestResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h};

        screenView = findViewById(R.id.rview);
        clickMe = (Button) findViewById(R.id.button);

        tv_result = findViewById(R.id.tv_result);
        tv_info = findViewById(R.id.tv_info);
        tv_lose = findViewById(R.id.tv_lose);
        tv_taps = findViewById(R.id.tv_taps);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        bestResult = preferences.getInt("highScore", 0);

        tv_result.setText("Best Result " + bestResult);

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameStarted)
                {
                    tv_info.setText("Current Taps: " + (currentTaps+1));
                    tv_taps.setText("Taps required: "+ tapsToWin);
                    tv_lose.setVisibility(View.INVISIBLE);
                    currentTaps++;

                    if(currentTaps>=tapsToWin)
                    {
                        int aryLength = images.length;
                        Random random = new Random();
                        int rNum = random.nextInt(aryLength);
                        screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), images[rNum]));
                        checkPoint+=2;
                        tapsToWin*=checkPoint;
                        finishTime = (finishTime/2) * checkPoint;
                        timer.cancel();
                        startTime();
                    }
                }
                else
                {
                    gameStarted = true;
                    startTime();
                }
            }
        });

    };

    private void startTime()
    {
        timer = new CountDownTimer(finishTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long timeTillEnd = (millisUntilFinished / 1000) + 1;
                tv_result.setText("Time Remaining: " + timeTillEnd);
            }

            @Override
            public void onFinish() {
                gameStarted = false;
                tv_lose.setVisibility(View.VISIBLE);
                tv_lose.setText("Game Over\nTap button to retry!");

                if (currentTaps > bestResult) {
                    bestResult = currentTaps;
                    SharedPreferences preferences1 = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putInt("highScore", bestResult);
                    editor.apply();
                }

                tv_result.setText("Best Result: " + bestResult);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_info.setText("Start Tapping");
                        tv_taps.setText("Taps required: 10");
                        currentTaps = 0;
                        tapsToWin = 10;
                        checkPoint = 1;
                    }
                }, 0000);
            }
        }.start();
    }

}