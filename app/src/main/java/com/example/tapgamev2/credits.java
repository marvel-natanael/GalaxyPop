package com.example.tapgamev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        backButton.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(credits.this, MainMenu.class);
                startActivity(menu);
            }
        });
    }

}
