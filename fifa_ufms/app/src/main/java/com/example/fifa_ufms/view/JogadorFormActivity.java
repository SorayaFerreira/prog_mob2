package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.databinding.ActivityJogadorFormBinding;
import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Time;

import java.util.concurrent.Executors;

public class JogadorFormActivity extends AppCompatActivity {

    public static final String EXTRA_ID_JOGADOR = "extra_id_jogador";
    public static final String EXTRA_NOME_JOGADOR = "extra_nome_jogador";

    private ActivityJogadorFormBinding binding;
    private int jogadorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJogadorFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();

        jogadorId = intent.getIntExtra(EXTRA_ID_JOGADOR, -1);
        String nomeJogador = intent.getStringExtra(EXTRA_NOME_JOGADOR);

        if (jogadorId != -1 && nomeJogador != null) {
            // Modo edição
            binding.titleText.setText("Editar Jogador");
            binding.buttonSave.setText("Salvar");

            // Preencher campos
            binding.edittextNomeJogador.setText(nomeJogador);
            binding.editNickname.setText(intent.getStringExtra("nickname"));
            binding.editEmail.setText(intent.getStringExtra("email"));
            binding.editDataNascimento.setText(intent.getStringExtra("dataNascimento"));
            binding.editNumeroGols.setText(String.valueOf(intent.getIntExtra("numeroGols", 0)));
            binding.editNumeroAmarelos.setText(String.valueOf(intent.getIntExtra("numeroAmarelos", 0)));
            binding.editNumeroVermelhos.setText(String.valueOf(intent.getIntExtra("numeroVermelhos", 0)));
            binding.editIdTime.setText(String.valueOf(intent.getIntExtra("idTime", 0)));

            binding.buttonSave.setOnClickListener(v -> salvarJogador(false));

        } else {
            // Modo cadastro
            binding.titleText.setText("Cadastrar Jogador");
            binding.buttonSave.setText("Cadastrar");

            binding.buttonSave.setOnClickListener(v -> salvarJogador(true));
        }
    }

    private void salvarJogador(boolean isNew) {
        String nome = binding.edittextNomeJogador.getText().toString().trim();
        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o nome do jogador", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pegar dados dos campos
        String nickname = binding.editNickname.getText().toString().trim();
        String email = binding.editEmail.getText().toString().trim();
        String dataNascimento = binding.editDataNascimento.getText().toString().trim();

        int numeroGols = parseIntOrZero(binding.editNumeroGols.getText().toString().trim());
        int numeroAmarelos = parseIntOrZero(binding.editNumeroAmarelos.getText().toString().trim());
        int numeroVermelhos = parseIntOrZero(binding.editNumeroVermelhos.getText().toString().trim());
        int idTime = parseIntOrZero(binding.editIdTime.getText().toString().trim());

        Jogador jogador = new Jogador(
                numeroVermelhos,
                numeroAmarelos,
                numeroGols,
                dataNascimento,
                email,
                nickname,
                nome,
                idTime
        );

        if (!isNew) {
            jogador.setIdJogador(jogadorId);
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            CampeonatoDatabase db = CampeonatoDatabase.getInstance(getApplicationContext());
            if (isNew) {
                db.jogadorDao().inserirJogador(jogador);
            } else {
                db.jogadorDao().atualizarJogador(jogador);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, (isNew ? "Jogador cadastrado: " : "Jogador atualizado: ") + nome, Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private int parseIntOrZero(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
