package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;

public class JogadorFormActivity extends AppCompatActivity {

    public static final String EXTRA_ID_JOGADOR = "extra_id_jogador";
    public static final String EXTRA_NOME_JOGADOR = "extra_nome_jogador";

    private EditText editTextNome;
    private Button buttonSave;
    private TextView tituloForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogador_form);

        // Referências dos elementos
        editTextNome = findViewById(R.id.edittext_nome_jogador);
        buttonSave = findViewById(R.id.button_save_jogador);
        tituloForm = findViewById(R.id.title_text);
        ImageButton backButton = findViewById(R.id.back_button);

        // Ação do botão de voltar
        backButton.setOnClickListener(v -> finish());

        // Verifica se é edição ou cadastro
        Intent intent = getIntent();
        int jogadorId = intent.getIntExtra(EXTRA_ID_JOGADOR, -1);
        String nomeJogador = intent.getStringExtra(EXTRA_NOME_JOGADOR);

        if (jogadorId != -1 && nomeJogador != null) {
            // Modo edição
            tituloForm.setText("Editar Jogador");
            buttonSave.setText("Salvar");
            editTextNome.setText(nomeJogador);

            buttonSave.setOnClickListener(v -> {
                String nome = editTextNome.getText().toString().trim();
                if (nome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do jogador", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Aqui você pode salvar no banco ou retornar resultado
                Toast.makeText(this, "Jogador atualizado: " + nome, Toast.LENGTH_SHORT).show();
                finish();
            });

        } else {
            // Modo cadastro
            tituloForm.setText("Cadastrar Jogador");
            buttonSave.setText("Cadastrar");

            buttonSave.setOnClickListener(v -> {
                String nome = editTextNome.getText().toString().trim();
                if (nome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do jogador", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Aqui você pode salvar no banco ou retornar resultado
                Toast.makeText(this, "Jogador cadastrado: " + nome, Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}
