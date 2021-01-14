package com.example.tapgamev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainMenu extends AppCompatActivity {
ImageButton button;
ImageView logo;
ImageButton credit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        logo = (ImageView) findViewById(R.id.logo);
        button = (ImageButton) findViewById(R.id.button);
        credit = (ImageButton) findViewById(R.id.credit);
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.btn);
        Music.SoundPlayer(this, R.raw.galaxy);

        if(!Music.soundOn){
            Music.player.stop();
            Music.soundOn = true;
        }
        else{
            Music.player.start();
            Music.soundOn=false;
        }

        //play button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent main = new Intent(MainMenu.this, MainActivity.class);
                startActivity(main);
            }
        });

        //credits
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent credit = new Intent (MainMenu.this, credits.class);
                startActivity(credit);
            }
        });
    }
}
