package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.PartidasAdapter;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.PartidaDao;
import com.example.fifa_ufms.database.TimeDao;
import com.example.fifa_ufms.entities.Partida;

import java.util.List;

public class PartidasActivity extends AppCompatActivity {

    private ListView listViewPartidas;
    private Button botaoAdd;
    private PartidasAdapter partidasAdapter;

    private PartidaDao partidaDao;
    private TimeDao timeDao;
    private CampeonatoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);

        listViewPartidas = findViewById(R.id.list_view_partidas);
        botaoAdd = findViewById(R.id.button_add_partida);

        db = Room.databaseBuilder(getApplicationContext(),
                        CampeonatoDatabase.class, "campeonato")
                .allowMainThreadQueries()
                .build();

        partidaDao = db.partidaDao();
        timeDao = db.timeDao();

        botaoAdd.setOnClickListener(v -> {
            Intent intent = new Intent(PartidasActivity.this, PartidasFormActivity.class);
            startActivity(intent);
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Partida> partidas = partidaDao.listarTodasPartidas();
        partidasAdapter = new PartidasAdapter(this, partidas, timeDao);
        listViewPartidas.setAdapter(partidasAdapter);
    }
}

