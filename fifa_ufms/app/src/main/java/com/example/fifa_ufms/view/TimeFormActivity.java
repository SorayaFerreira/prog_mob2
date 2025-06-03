// /app/src/main/java/com/example/fifa_ufms/view/TimeFormActivity.java
package com.example.fifa_ufms.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.JogadoresNoTimeAdapter;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.JogadorDao;
import com.example.fifa_ufms.database.TimeDao;
import com.example.fifa_ufms.databinding.ActivityTimeFormBinding;
import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Time;

import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import java.util.List;

public class TimeFormActivity extends AppCompatActivity {
    //variaveis para spinner
    private Spinner spinnerJogadores;
    private List<Jogador> listaJogadoresSemTime = new ArrayList<>();
    private ArrayAdapter<Jogador> spinnerAdapter;
    //private Jogador mJogadorSelecionado; // Jogador selecionado no Spinner
    //info de time
    public static final String EXTRA_ID_TIME       = "extra_id_time";
    public static final String EXTRA_NOME_TIME     = "extra_nome_time";
    public static final String EXTRA_COR_UNIFORME  = "extra_cor_uniforme";

    private static final int REQUEST_ADD_JOGADOR = 1001;

    private ActivityTimeFormBinding binding;
    private List<Jogador> listaJogadoresAtuais;         // lista local de jogadores que serão anexados ao time
    private JogadoresNoTimeAdapter timeAdapter;       // adapter para exibir JavaList<Jogador> no RecyclerView

    private TimeDao timeDao;
    private JogadorDao jogadorDao;
    //placeholder
    private final Jogador placeholder = new Jogador("— Adicione jogadores —");

    // Se edição, id do time que chegou pelo Intent
    private int timeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimeFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //1. Inicializa DAOs
        CampeonatoDatabase db = CampeonatoDatabase.getInstance(getApplicationContext());
        timeDao = db.timeDao();
        jogadorDao = db.jogadorDao();

        //2. Inicializa lista de jogadores (vazia por padrão)
        listaJogadoresAtuais = new ArrayList<>();

        // 3. Criar um dummy “placeholder” Jogador:
        placeholder.setIdJogador(-1);
        listaJogadoresSemTime.add(0, placeholder);


        //3. Configura RecyclerView
        // jogadorAdapter = new JogadoresAdapter(jogadoresList);
        // 2) INICIALIZA O RECYCLERVIEW E SEU ADAPTER PRIMEIRO
        RecyclerView recyclerViewJogadoresDoTime = findViewById(R.id.recyclerViewJogadoresNoTime);
        recyclerViewJogadoresDoTime.setLayoutManager(new LinearLayoutManager(this));
        timeAdapter = new JogadoresNoTimeAdapter(this, listaJogadoresAtuais);
        recyclerViewJogadoresDoTime.setAdapter(timeAdapter);

        //4. Referenciar o Spinner do layout
        spinnerJogadores = findViewById(R.id.spinnerJogadoresSemTime);

        //4. Configurar adaptador vazio para o Spinner pra não quebrar se a lista estiver vazia)
        spinnerAdapter = new ArrayAdapter<Jogador>(
                this,
                android.R.layout.simple_spinner_item,
                listaJogadoresSemTime
        ) {
            @Override
            public boolean isEnabled(int position) {
                // Desabilita o placeholder
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);   // placeholder em cinza
                } else {
                    tv.setTextColor(Color.BLACK);  // jogadores “reais” em preto
                }
                return view;
            }
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // This is the “selected” view that the Spinner shows when closed
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                if (position == 0) {
                    // If the Spinner is “showing” the placeholder, render it in gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJogadores.setAdapter(spinnerAdapter);

        // 5. Listener de seleção, agora ignorando o primeiro callback e o item “placeholder”
        spinnerJogadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Esse campo garante que o primeiro evento (quando o Spinner é renderizado) seja ignorado
            private boolean isFirstCall = true;

            @Override
            public void onItemSelected(
                    AdapterView<?> parent, View view, int position, long id
            ) {
                // Ignora o primeiro callback automático do Spinner
                if (isFirstCall) {
                    isFirstCall = false;
                    return;
                }

                // Se o placeholder estiver na posição 0, não faz nada
                if (position == 0) {
                    return;
                }

                // Busca o Jogador selecionado (posição > 0 já é um Jogador “real”)
                Jogador selecionado = (Jogador) parent.getItemAtPosition(position);
                if (selecionado != null && !listaJogadoresAtuais.contains(selecionado)) {
                    // Adiciona na lista de jogadores do time e notifica o adapter de RecyclerView
                    listaJogadoresAtuais.add(selecionado);
                    timeAdapter.notifyItemInserted(listaJogadoresAtuais.size() - 1);

                    // Remove esse jogador da lista de “sem time” e atualiza o Spinner
                    listaJogadoresSemTime.remove(selecionado);
                    spinnerAdapter.notifyDataSetChanged();
                    spinnerJogadores.setSelection(0);
                }

                Toast.makeText(
                        TimeFormActivity.this,
                        "Você adicionou: " + selecionado.getNickname(),
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Não é necessário tratar nada aqui
            }
        });


        //7. carrega consulta
        carregarJogadoresSemTime();

        //8. Ação do botão de voltar
        binding.backButton.setOnClickListener(v -> finish());

        //9. Verifica se o Intent trouxe dados para edição
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

            // Carrega jogadores já vinculados ao time (essa parte fica na main thread pois é apenas leitura simples)
            List<Jogador> deDB = jogadorDao.listarJogadoresPorTime(timeId);
            listaJogadoresAtuais.clear();
            listaJogadoresAtuais.addAll(deDB);
            timeAdapter.notifyDataSetChanged();

            binding.buttonSalvarTime.setOnClickListener(v -> {
                String novoNome = binding.inputNomeTime.getText().toString().trim();
                String novaCor  = binding.inputCorUniforme.getText().toString().trim();

                if (novoNome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (novaCor.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha a cor do uniforme", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria o objeto Time atualizado
                Time timeAtualizado = new Time(novoNome, novaCor);
                timeAtualizado.setIdTime(timeId);

                // Executa TODO em background
                new Thread(() -> {
                    // 1) Atualiza Time
                    timeDao.atualizarTime(timeAtualizado);

                    // 2) Atualiza referência de timeId para cada jogador no banco
                    for (Jogador j : listaJogadoresAtuais) {
                        jogadorDao.setarTimeJogador(timeId, j.getIdJogador());
                    }

                    // 3) Volta para a UI para mostrar Toast e finalizar Activity
                    runOnUiThread(() -> {
                        Toast.makeText(TimeFormActivity.this, "Time atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();
            });

        } else {
            // Modo cadastro
            binding.titleText.setText("Cadastrar Time");
            binding.buttonSalvarTime.setText("Cadastrar");

            binding.buttonSalvarTime.setOnClickListener(v -> {
                String nome = binding.inputNomeTime.getText().toString().trim();
                String cor  = binding.inputCorUniforme.getText().toString().trim();

                if (nome.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha o nome do time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cor.isEmpty()) {
                    Toast.makeText(this, "Por favor, preencha a cor do uniforme", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria o objeto Time a ser inserido
                Time novoTime = new Time(nome, cor);

                // Executa TODO em background
                new Thread(() -> {
                    // 1) Insere novo Time e obtém o array de IDs (Room retorna long[])
                    long[] novoIdLong = timeDao.inserirTime(novoTime);
                    int novoId = (int) novoIdLong[0]; // pega o id gerado

                    // 2) Associa cada jogador da lista a esse novo time
                    for (Jogador j : listaJogadoresAtuais) {
                        jogadorDao.setarTimeJogador(novoId, j.getIdJogador());
                    }

                    // 3) Volta para a UI para mostrar Toast e finalizar Activity
                    runOnUiThread(() -> {
                        Toast.makeText(TimeFormActivity.this, "Time cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();
            });
        }


        // Ação do botão “Adicionar Jogador”
        //binding.buttonAddJogador.setOnClickListener(v -> {
            //Intent toJogadorForm = new Intent(TimeFormActivity.this, JogadorFormActivity.class);
            // Se quiser editar um jogador existente, você poderia passar o EXTRA_ID_JOGADOR, mas aqui consideramos só adição
            //startActivityForResult(toJogadorForm, REQUEST_ADD_JOGADOR);
        //});
    }
    private void carregarJogadoresSemTime() {
        new Thread(() -> {
            final List<Jogador> todosSemTime = jogadorDao.listarJogadoresSemTime();

            runOnUiThread(() -> {
                listaJogadoresSemTime.clear();

                // 1) re-insere o placeholder em índice 0
                listaJogadoresSemTime.add(placeholder);

                // 2) depois, adiciona todos os jogadores “de verdade”
                listaJogadoresSemTime.addAll(todosSemTime);

                spinnerAdapter.notifyDataSetChanged();
                spinnerJogadores.setSelection(0);
            });
        }).start();
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
