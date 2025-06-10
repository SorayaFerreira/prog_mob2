package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
    private PartidaDao partidaDao;
    private TimeDao timeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);

        listViewPartidas = findViewById(R.id.list_view_partidas);
        Button botaoAdd = findViewById(R.id.button_add_partida);

        CampeonatoDatabase db = Room.databaseBuilder(
                        getApplicationContext(),
                        CampeonatoDatabase.class,
                        "campeonato"
                )
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

        // 1. Buscar todas as partidas
        List<Partida> partidas = partidaDao.listarTodasPartidas();

        // 2. Configurar o adapter para exibir cada partida
        PartidasAdapter partidasAdapter = new PartidasAdapter(this, partidas, timeDao);
        listViewPartidas.setAdapter(partidasAdapter);

        // 3. Registrar o clique em cada item da lista de partidas
        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Partida partidaSelecionada = (Partida) partidasAdapter.getItem(position);
                int idPartida = partidaSelecionada.getIdPartida();

                // DEBUG: exibe um Toast para confirmar o clique
                Toast.makeText(PartidasActivity.this,
                        "Clicou em partida #"+ idPartida,
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent = new Intent(PartidasActivity.this, JogadoresPartidaActivity.class);
                intent.putExtra(JogadoresPartidaActivity.EXTRA_ID_PARTIDA, idPartida);
                startActivity(intent);
            }

        });
    }
}
