package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.JogadorDao;
import com.example.fifa_ufms.database.PartidaDao;
import com.example.fifa_ufms.database.TimeDao;
import com.example.fifa_ufms.database.ParticipacaoDao;
import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Participacao;
import com.example.fifa_ufms.entities.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PartidasFormActivity extends AppCompatActivity {
    private EditText editTextData, editTextPlacar1, editTextPlacar2;
    private Spinner spinnerTime1, spinnerTime2;
    private Button buttonSalvar;

    private PartidaDao partidaDao;
    private ParticipacaoDao participacoesDao;
    private JogadorDao jogadorDao;
    private TimeDao timeDao;

    private List<Time> listaTimes;
    private Partida partidaParaEditar = null; // Armazena a partida em modo de edição

    public static final String EXTRA_ID_PARTIDA = "com.example.fifa_ufms.EXTRA_ID_PARTIDA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_form);

        // Inicialização dos componentes da UI
        editTextData = findViewById(R.id.editTextData);
        editTextPlacar1 = findViewById(R.id.editTextPlacarTime1);
        editTextPlacar2 = findViewById(R.id.editTextPlacarTime2);
        spinnerTime1 = findViewById(R.id.spinnerTime1);
        spinnerTime2 = findViewById(R.id.spinnerTime2);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        // Conexão com o banco de dados
        CampeonatoDatabase db = Room.databaseBuilder(getApplicationContext(),
                CampeonatoDatabase.class, "campeonato").allowMainThreadQueries().build();

        partidaDao = db.partidaDao();
        participacoesDao = db.participacaoDao();
        jogadorDao = db.jogadorDao();
        timeDao = db.timeDao();

        // Carrega a lista de times para os Spinners
        carregarTimesNoSpinner();

        // Verifica se a activity foi iniciada para editar uma partida existente
        int idPartida = getIntent().getIntExtra(EXTRA_ID_PARTIDA, -1);
        if (idPartida != -1) {
            buttonSalvar.setText("Atualizar");
            partidaParaEditar = partidaDao.buscaPorId(idPartida);
            if (partidaParaEditar != null) {
                popularFormulario(partidaParaEditar);
            }
        }

        buttonSalvar.setOnClickListener(v -> salvarPartida());
    }

    private void popularFormulario(Partida partida) {
        editTextData.setText(partida.data);
        editTextPlacar1.setText(String.valueOf(partida.placarTime1));
        editTextPlacar2.setText(String.valueOf(partida.placarTime2));

        spinnerTime1.setSelection(getPosicaoDoTime(partida.time1));
        spinnerTime2.setSelection(getPosicaoDoTime(partida.time2));
    }

    private int getPosicaoDoTime(int idTime) {
        for (int i = 0; i < listaTimes.size(); i++) {
            if (listaTimes.get(i).idTime == idTime) {
                return i;
            }
        }
        return 0; // Retorna o primeiro item como padrão se não encontrar
    }

    private void carregarTimesNoSpinner() {
        listaTimes = timeDao.listarTodosTimes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                listaTimes.stream().map(t -> t.nomeTime).collect(Collectors.toList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime1.setAdapter(adapter);
        spinnerTime2.setAdapter(adapter);
    }

    private void salvarPartida() {
        // Validação básica para campos vazios
        if (editTextData.getText().toString().isEmpty() ||
                editTextPlacar1.getText().toString().isEmpty() ||
                editTextPlacar2.getText().toString().isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String data = editTextData.getText().toString();
        int placar1 = Integer.parseInt(editTextPlacar1.getText().toString());
        int placar2 = Integer.parseInt(editTextPlacar2.getText().toString());
        int idTime1 = listaTimes.get(spinnerTime1.getSelectedItemPosition()).idTime;
        int idTime2 = listaTimes.get(spinnerTime2.getSelectedItemPosition()).idTime;
        String nome1 = listaTimes.get(spinnerTime1.getSelectedItemPosition()).nomeTime;
        String nome2 = listaTimes.get(spinnerTime2.getSelectedItemPosition()).nomeTime;

        if (idTime1 == idTime2) {
            Toast.makeText(this, "Os times devem ser diferentes!", Toast.LENGTH_SHORT).show();
            return;
        }

        Partida partida = new Partida();
        partida.data = data;
        partida.placarTime1 = placar1;
        partida.placarTime2 = placar2;
        partida.time1 = idTime1;
        partida.time2 = idTime2;

        if (partidaParaEditar == null) { // Modo de Criação
            long idNovaPartida = partidaDao.inserirPartida(partida);
            atualizarParticipacoes((int) idNovaPartida, idTime1, idTime2);
            Toast.makeText(this, "Partida salva!", Toast.LENGTH_SHORT).show();
        } else { // Modo de Atualização
            partida.idPartida = partidaParaEditar.idPartida;
            partidaDao.atualizarPartida(partida); // Método a ser adicionado no DAO
            // Deleta participações antigas e cria as novas
            //participacoesDao.deletarParticipacoesPorPartida(partida.idPartida); // Método a ser adicionado no DAO
            //atualizarParticipacoes(partida.idPartida, idTime1, idTime2);
            Toast.makeText(this, "Partida atualizada!", Toast.LENGTH_SHORT).show();
        }

        try {
            SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
            Calendar inicio = Calendar.getInstance();
            inicio.setTime(formato.parse(partida.getData()));
            inicio.set(Calendar.HOUR_OF_DAY, 18); // inventei a hora :)
            inicio.set(Calendar.MINUTE, 0);

            Calendar fim = (Calendar) inicio.clone();
            fim.add(Calendar.MINUTE, 90);

            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.putExtra(CalendarContract.Events.TITLE, "Partida: " + nome1 + " vs " + nome2);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, inicio.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fim.getTimeInMillis());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Jogo do campeonato Fifa UFMS. Placar: " + nome1 + " " + partida.placarTime1 + " x " + nome2 + " " + partida.placarTime2);

            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar evento", Toast.LENGTH_SHORT).show();
        }

        finish(); // Retorna para a tela anterior
    }

    private void atualizarParticipacoes(int idPartida, int idTime1, int idTime2) {
        List<Participacao> participacoes = new ArrayList<>();
        List<Jogador> jogadoresTime1 = jogadorDao.listarJogadoresPorTime(idTime1);
        List<Jogador> jogadoresTime2 = jogadorDao.listarJogadoresPorTime(idTime2);

        for (Jogador jogador : jogadoresTime1) {
            Participacao p = new Participacao();
            p.idJogador = jogador.getIdJogador();
            p.idPartida = idPartida;
            p.idTime = jogador.getIdTime();
            participacoes.add(p);
        }
        for (Jogador jogador : jogadoresTime2) {
            Participacao p = new Participacao();
            p.idJogador = jogador.getIdJogador();
            p.idPartida = idPartida;
            p.idTime = jogador.getIdTime();
            participacoes.add(p);
        }
        participacoesDao.inserirParticipacoes(participacoes);
    }}

