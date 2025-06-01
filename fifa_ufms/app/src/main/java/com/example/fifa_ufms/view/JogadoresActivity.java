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
import com.example.fifa_ufms.model.Jogador;

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

        // RecyclerView de jogadores
        recyclerView = findViewById(R.id.recycler_jogadores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jogadores = new ArrayList<>();
        jogadores.add(new Jogador(1, "Pelé"));
        jogadores.add(new Jogador(2, "Zico"));
        jogadores.add(new Jogador(3, "Ronaldo Fenômeno"));

        adapter = new JogadoresAdapter(this, jogadores, jogador -> {
            Intent intent = new Intent(JogadoresActivity.this, JogadorFormActivity.class);
            intent.putExtra(JogadorFormActivity.EXTRA_ID_JOGADOR, jogador.getId());
            intent.putExtra(JogadorFormActivity.EXTRA_NOME_JOGADOR, jogador.getNome());
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
