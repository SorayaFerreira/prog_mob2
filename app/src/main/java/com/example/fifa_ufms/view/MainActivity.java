package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Time;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inserir time padrão
        Executors.newSingleThreadExecutor().execute(() -> {
            CampeonatoDatabase db = CampeonatoDatabase.getInstance(getApplicationContext());

            if (db.timeDao().listarTodosTimes().isEmpty()) {
                Time time = new Time("UFMS", "Branco e Azul");
                db.timeDao().inserirTime(time);

                Time time2 = new Time("UEMS", "Preto e Vermelho");
                db.timeDao().inserirTime(time2);

                // Inserir jogadores
                Jogador jogador1 = new Jogador(
                        0, 1, 5, "2001-04-15", "caio@ufms.br", "Caio", "Caio", 1, "android.resource://com.example.fifa_ufms/drawable/ic_user"
                );
                Jogador jogador2 = new Jogador(
                        1, 0, 3, "2000-10-30", "txas@ufms.br", "Txas", "Texeira", 1, "android.resource://com.example.fifa_ufms/drawable/ic_user"
                );
                Jogador jogador3 = new Jogador(
                        0, 2, 4, "2002-02-28", "Sora@uems.br", "Sora", "Soraya", 2, "android.resource://com.example.fifa_ufms/drawable/ic_user"
                );
                Jogador jogador4 = new Jogador(
                        1, 1, 2, "1999-11-11", "Capi@uems.br", "Capi", "Capivara", 2, "android.resource://com.example.fifa_ufms/drawable/ic_user"
                );

                db.jogadorDao().inserirJogador(jogador1);
                db.jogadorDao().inserirJogador(jogador2);
                db.jogadorDao().inserirJogador(jogador3);
                db.jogadorDao().inserirJogador(jogador4);

                // Inserir partida
                Partida partida = new Partida("2025-06-01", 1, 2, 3, 2);
                db.partidaDao().inserirPartida(partida);
            }
        });

        // Card para Times
        LinearLayout cardTimes = findViewById(R.id.card_times);
        cardTimes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimesActivity.class);
            startActivity(intent);
        });

        // Card para Jogadores
        LinearLayout cardJogadores = findViewById(R.id.card_jogadores);
        cardJogadores.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JogadoresActivity.class);
            startActivity(intent);
        });

        // Card para Partidas
        LinearLayout cardPartidas = findViewById(R.id.card_partidas);
        cardPartidas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PartidasActivity.class);
            startActivity(intent);
        });
        // Card para Classificação
        LinearLayout cardClassificacao = findViewById(R.id.card_classificacao);
        cardClassificacao.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClassificacaoActivity.class);
            startActivity(intent);
        });
    }
}
