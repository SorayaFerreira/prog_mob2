// /app/src/main/java/com/example/fifa_ufms/view/TimeFormActivity.java
package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.JogadoresAdapter;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.JogadorDao;
import com.example.fifa_ufms.database.TimeDao;
import com.example.fifa_ufms.databinding.ActivityTimeFormBinding;
import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Time;

import java.util.ArrayList;
import java.util.List;

public class TimeFormActivity extends AppCompatActivity {
    //info de time
    public static final String EXTRA_ID_TIME       = "extra_id_time";
    public static final String EXTRA_NOME_TIME     = "extra_nome_time";
    public static final String EXTRA_COR_UNIFORME  = "extra_cor_uniforme";

    private static final int REQUEST_ADD_JOGADOR = 1001;

    private ActivityTimeFormBinding binding;
    private List<Jogador> jogadoresList;         // lista local de jogadores que serão anexados ao time
    private JogadoresAdapter jogadorAdapter;       // adapter para exibir JavaList<Jogador> no RecyclerView
    private RecyclerView recyclerViewJogadores;

    private TimeDao timeDao;
    private JogadorDao jogadorDao;

    // Se for edição, guardamos aqui o id do time que chegou pelo Intent
    private int timeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimeFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa DAOs
        CampeonatoDatabase db = CampeonatoDatabase.getInstance(getApplicationContext());
        timeDao = db.timeDao();
        jogadorDao = db.jogadorDao();

        // Inicializa lista de jogadores (vazia por padrão)
        jogadoresList = new ArrayList<>();

        // Configura RecyclerView
        jogadorAdapter = new JogadoresAdapter(jogadoresList);
        binding.recyclerViewJogadores.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewJogadores.setAdapter(jogadorAdapter);

        // Ação do botão de voltar
        binding.backButton.setOnClickListener(v -> finish());

        // Verifica se o Intent trouxe dados para edição
        Intent intent = getIntent();
        timeId = intent.getIntExtra(EXTRA_ID_TIME, -1);
        String nomeTime = intent.getStringExtra(EXTRA_NOME_TIME);
        String corUniforme = intent.getStringExtra(EXTRA_COR_UNIFORME);

        if (timeId != -1 && nomeTime != null && corUniforme != null) {
            // Modo edição: preenche campos e carrega jogadores do DB
            binding.titleText.setText("Editar Time");
            binding.buttonSalvarTime.setText("Salvar");

            binding.inputNomeTime.setText(nomeTime);
            binding.inputCorUniforme.setText(corUniforme);

            // Carrega jogadores já vinculados ao time
            List<Jogador> deDB = jogadorDao.listarJogadoresPorTime(timeId);
            jogadoresList.clear();
            jogadoresList.addAll(deDB);
            jogadorAdapter.notifyDataSetChanged();

            binding.buttonSalvarTime.setOnClickListener(v -> {
                String novoNome = binding.inputNomeTime.getText().toString().trim();
                String novaCor = binding.inputCorUniforme.getText().toString().trim();

                if (novoNome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (novaCor.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha a cor do uniforme", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Atualiza Time no banco
                Time timeAtualizado = new Time(novoNome, novaCor);
                timeAtualizado.setIdTime(timeId);
                timeDao.atualizarTime(timeAtualizado);


                // Atualiza referencia de timeId para cada jogador
                for (Jogador j : jogadoresList) {
                    jogadorDao.setarTimeJogador(timeId, j.getIdJogador());
                }

                Toast.makeText(this, "Time atualizado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            });

        } else {
            // Modo cadastro
            binding.titleText.setText("Cadastrar Time");
            binding.buttonSalvarTime.setText("Cadastrar");

            binding.buttonSalvarTime.setOnClickListener(v -> {
                String nome = binding.inputNomeTime.getText().toString().trim();
                String cor = binding.inputCorUniforme.getText().toString().trim();

                if (nome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cor.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha a cor do uniforme", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insere novo Time no DB (retorna o id gerado)
                Time novoTime = new Time(nome, cor);
                long[] novoIdLong = timeDao.inserirTime(novoTime);
                int novoId = (int) novoIdLong[0];

                // Associa cada jogador da lista a esse novo time e insere no DB
                for (Jogador j : jogadoresList) {
                    jogadorDao.setarTimeJogador(timeId, j.getIdJogador());
                }

                Toast.makeText(this, "Time cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        // Ação do botão “Adicionar Jogador”
        binding.buttonAddJogador.setOnClickListener(v -> {
            Intent toJogadorForm = new Intent(TimeFormActivity.this, JogadorFormActivity.class);
            // Se quiser editar um jogador existente, você poderia passar o EXTRA_ID_JOGADOR, mas aqui consideramos só adição
            startActivityForResult(toJogadorForm, REQUEST_ADD_JOGADOR);
        });
    }

    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //if (requestCode == REQUEST_ADD_JOGADOR && resultCode == RESULT_OK && data != null) {
            // Supondo que JogadorFormActivity devolva um objeto Jogador via Intent:
            //Jogador novoJogador = (Jogador) data.getSerializableExtra(JogadorFormActivity.EXTRA_JOGADOR_OBJECT);
            //if (novoJogador != null) {
                // Se estiver no modo edição de Time, já podemos definir o timeId (caso queira salvar imediatamente)
                // mas aqui vamos somente armazenar na lista local para inserir depois
                //if (timeId != -1) {
                    //novoJogador.setTimeId(timeId);
                //}
                //jogadoresList.add(novoJogador);
                //jogadorAdapter.notifyItemInserted(jogadoresList.size() - 1);
            //}
        //}
    //}
}
