package com.example.tapgamev2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View screenView;
    ImageButton clickMe,home,sound,pause;
    int[] images;
    int[] planet;
    TextView tv_result, tv_info, tv_lose, tv_taps;

    int currentTaps = 0;
    int checkPoint=1;
    int tapsToWin=10;

    boolean gameStarted = false;
    boolean isPaused=false;

    CountDownTimer timer;

    long finishTime=10000;
    long timeEnd;

    int bestResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer bgSound = MediaPlayer.create(this, R.raw.galaxy);
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.btn);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.f, R.drawable.g, R.drawable.h};
        planet = new int[]{R.drawable.planet1, R.drawable.planet2,R.drawable.planet3,R.drawable.planet4,R.drawable.planet5,};
        screenView = findViewById(R.id.rview);

        clickMe = (ImageButton) findViewById(R.id.button);
        home = (ImageButton) findViewById(R.id.home);
        pause = (ImageButton) findViewById(R.id.pause);
        sound = (ImageButton) findViewById(R.id.sound);

        tv_result = findViewById(R.id.tv_result);
        tv_info = findViewById(R.id.tv_info);
        tv_lose = findViewById(R.id.tv_lose);
        tv_taps = findViewById(R.id.tv_taps);

        sound.setVisibility(View.INVISIBLE);
        home.setVisibility(View.INVISIBLE);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        bestResult = preferences.getInt("highScore", 0);

        tv_result.setText("Best Result\n" + bestResult);


        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Music.soundOn)
                {
                    sound.setImageResource(R.drawable.mute);
                    Music.player.pause();
                    Music.soundOn=false;
                }
                else
                {
                    sound.setImageResource(R.drawable.sound);
                    Music.player.start();
                    Music.soundOn=true;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                if(gameStarted)
                {
                    if(!isPaused)
                    {
                        isPaused=true;
                        pause();
                    }
                    else
                    {
                        isPaused=false;
                        resume();

                    }
                }
                else if(!isPaused)
                {
                    showPause();
                    isPaused=true;
                    clickMe.setEnabled(false);
                }
                else
                {
                    hidePause();
                    isPaused=false;
                    clickMe.setEnabled(true);
                }

            }
        });

        home.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(menu);
            }
        });


        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gameStarted)
                {
                    tv_info.setVisibility(View.VISIBLE);

                    tv_info.setText("Current Taps: " + (currentTaps+1));
                    tv_taps.setText("Taps required: "+ (tapsToWin));
                    tv_lose.setVisibility(View.INVISIBLE);
                    currentTaps++;

                    if(currentTaps==tapsToWin)
                    {
                        currentTaps = 0;
                        checkPoint+=1;
                        tapsToWin=(tapsToWin + (checkPoint+1)*(checkPoint+2));
                        tv_taps.setText("Taps required: "+ (tapsToWin));
                        finishTime = (checkPoint*10000)^2;
                        int aryLength = images.length;
                        Random random = new Random();
                        final int rNum = random.nextInt(aryLength);
                        int imgNo=(int) (Math.random() * 5 + 1);
                        int imgID = getResources().getIdentifier("planet"+ imgNo, "drawable", getPackageName());
                        clickMe.setImageResource(imgID);
                        clickMe.requestLayout();
                        screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), images[rNum]));
                        timer.cancel();
                        startTime(finishTime);
                    }
                }
                else
                {
                    gameStarted = true;
                    startTime(finishTime);
                    tv_lose.setVisibility(View.INVISIBLE);
                    if(gameStarted) {
                        tv_info.setVisibility(View.VISIBLE);
                        tv_taps.setVisibility(View.VISIBLE);
                        tv_info.setText("Current Taps: " + (currentTaps + 1));
                        tv_taps.setText("Taps required: " + (tapsToWin));
                        tv_lose.setVisibility(View.INVISIBLE);
                        currentTaps++;
                    }
                }
            }
        });

    };


    private void startTime(long finishTime)
    {
        timer = new CountDownTimer(finishTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeEnd = millisUntilFinished;
                long timeTillEnd = (millisUntilFinished / 1000+1);
                tv_result.setText("Time Remaining\n" + timeTillEnd);
            }


            @Override
            public void onFinish() {
                gameStarted = false;
                tv_info.setVisibility(View.INVISIBLE);
                tv_lose.setVisibility(View.VISIBLE);
                tv_lose.setText("Game Over\nTap button to retry!");

                if (currentTaps > bestResult) {
                    bestResult = currentTaps;
                    SharedPreferences preferences1 = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putInt("highScore", bestResult);
                    editor.apply();
                }

                tv_result.setText("Best Result\n" + bestResult);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_taps.setVisibility(View.INVISIBLE);
                        currentTaps = 0;
                        tapsToWin = 10;
                        checkPoint = 1;
                    }
                }, 0000);
            }
        }.start();
    }


    public void showPause()
    {
        pause.setImageResource(R.drawable.back);
        sound.setVisibility(View.VISIBLE);
        home.setVisibility(View.VISIBLE);
    }

    public void hidePause()
    {
        pause.setImageResource(R.drawable.pause);
        sound.setVisibility(View.INVISIBLE);
        home.setVisibility(View.INVISIBLE);
    }

    public void pause()
    {
        timer.cancel();
        showPause();
        clickMe.setEnabled(false);
    }

    public void resume()
    {
        startTime(timeEnd);
        hidePause();
        clickMe.setEnabled(true);
    }

}