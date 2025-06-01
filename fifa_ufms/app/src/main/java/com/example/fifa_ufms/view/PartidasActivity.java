package com.example.fifa_ufms.view;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;

public class PartidasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }
}
