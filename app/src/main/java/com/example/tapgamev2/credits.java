package com.example.tapgamev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class credits extends AppCompatActivity {
ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        backButton= (ImageButton) findViewById(R.id.back);
        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.btn);
        backButton.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                buttonSound.start();
                Intent menu = new Intent(credits.this, MainMenu.class);
                startActivity(menu);
            }
        });
    }

}
