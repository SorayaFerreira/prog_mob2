package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.JogadoresAdapter;
import com.example.fifa_ufms.entities.Jogador;

import java.util.ArrayList;
import java.util.List;

public class JogadoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JogadoresAdapter adapter;
    private List<Jogador> jogadores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadores);

        // Botão de voltar
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_jogadores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista mock de jogadores
        jogadores = new ArrayList<>();

        jogadores.add(new Jogador(
                0,  // numeroVermelhos
                5,  // numeroAmarelos
                1281,  // numeroGols
                "1940-10-23",  // dataNascimento
                "pele@brasil.com",  // email
                "Rei",  // nickname
                "Pelé",  // nome
                1   // idTime
        ));

        jogadores.add(new Jogador(
                1,
                3,
                500,
                "1953-03-03",
                "zico@brasil.com",
                "Galinho",
                "Zico",
                2
        ));

        jogadores.add(new Jogador(
                0,
                2,
                414,
                "1976-09-22",
                "fenomeno@brasil.com",
                "Fenômeno",
                "Ronaldo",
                3
        ));

        adapter = new JogadoresAdapter(this, jogadores, jogador -> {
            Intent intent = new Intent(JogadoresActivity.this, JogadorFormActivity.class);
            intent.putExtra(JogadorFormActivity.EXTRA_ID_JOGADOR, jogador.getIdJogador());
            intent.putExtra(JogadorFormActivity.EXTRA_NOME_JOGADOR, jogador.getNome());
            intent.putExtra("nickname", jogador.getNickname());
            intent.putExtra("email", jogador.getEmail());
            intent.putExtra("dataNascimento", jogador.getDataNascimento());
            intent.putExtra("numeroGols", jogador.getNumeroGols());
            intent.putExtra("numeroAmarelos", jogador.getNumeroAmarelos());
            intent.putExtra("numeroVermelhos", jogador.getNumeroVermelhos());
            startActivity(intent);
        });


        recyclerView.setAdapter(adapter);

        // Botão "Adicionar Jogador"
        Button addButton = findViewById(R.id.button_add_jogador);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(JogadoresActivity.this, JogadorFormActivity.class);
            startActivity(intent);
        });
    }
}
