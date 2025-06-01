package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout cardTimes = findViewById(R.id.card_times);
        cardTimes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimesActivity.class);
            startActivity(intent);
        });

        LinearLayout cardJogadores = findViewById(R.id.card_jogadores);
        cardJogadores.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JogadoresActivity.class);
            startActivity(intent);
        });

    }
}
