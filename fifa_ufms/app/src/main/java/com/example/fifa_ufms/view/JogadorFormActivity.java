package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.databinding.ActivityJogadorFormBinding;

public class JogadorFormActivity extends AppCompatActivity {

    public static final String EXTRA_ID_JOGADOR = "extra_id_jogador";
    public static final String EXTRA_NOME_JOGADOR = "extra_nome_jogador";

    private ActivityJogadorFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJogadorFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ação do botão de voltar
        binding.backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        int jogadorId = intent.getIntExtra(EXTRA_ID_JOGADOR, -1);
        String nomeJogador = intent.getStringExtra(EXTRA_NOME_JOGADOR);

        if (jogadorId != -1 && nomeJogador != null) {
            // Modo edição
            binding.titleText.setText("Editar Jogador");
            binding.buttonSave.setText("Salvar");
            binding.buttonSave.setText(nomeJogador);

            binding.buttonSave.setOnClickListener(v -> {
                String nome = binding.edittextNomeJogador.getText().toString().trim();
                if (nome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do jogador", Toast.LENGTH_SHORT).show();
                    return;
                }
                // salvar no banco ou retornar resultado
                Toast.makeText(this, "Jogador atualizado: " + nome, Toast.LENGTH_SHORT).show();
                finish();
            });

        } else {
            // Modo cadastro
            binding.titleText.setText("Cadastrar Jogador");
            binding.buttonSave.setText("Cadastrar");

            binding.buttonSave.setOnClickListener(v -> {
                String nome = binding.edittextNomeJogador.getText().toString().trim();
                if (nome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do jogador", Toast.LENGTH_SHORT).show();
                    return;
                }
                // salvar no banco ou retornar resultado
                Toast.makeText(this, "Jogador cadastrado: " + nome, Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}
