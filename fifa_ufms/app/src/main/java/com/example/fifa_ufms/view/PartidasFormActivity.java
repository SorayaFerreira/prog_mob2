package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidasFormActivity extends AppCompatActivity {
    private EditText editTextData, editTextPlacar1, editTextPlacar2;
    private Spinner spinnerTime1, spinnerTime2;
    private Button buttonSalvar;

    private PartidaDao partidaDao;
    private TimeDao timeDao; // ou JogadorDao se preferir usar jogadores diretamente

    private List<Time> listaTimes;
    private int partidaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_form);

        editTextData = findViewById(R.id.editTextData);
        editTextPlacar1 = findViewById(R.id.editTextPlacarTime1);
        editTextPlacar2 = findViewById(R.id.editTextPlacarTime2);
        spinnerTime1 = findViewById(R.id.spinnerTime1);
        spinnerTime2 = findViewById(R.id.spinnerTime2);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        CampeonatoDatabase db = Room.databaseBuilder(getApplicationContext(),
                CampeonatoDatabase.class, "Campeonato").allowMainThreadQueries().build();

        partidaDao = db.partidaDao();
        timeDao = db.timeDao(); // se usar times

        carregarTimesNoSpinner();

        buttonSalvar.setOnClickListener(v -> salvarPartida());
    }

    private void carregarTimesNoSpinner() {
        listaTimes = timeDao.listarTodosTimes(); // ou jogadorDao.getAllNicknames()
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                (List) listaTimes.stream().map(t -> t.nomeTime).collect(Collectors.toList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime1.setAdapter(adapter);
        spinnerTime2.setAdapter(adapter);
    }

    private void salvarPartida() {
        String data = editTextData.getText().toString();
        int placar1 = Integer.parseInt(editTextPlacar1.getText().toString());
        int placar2 = Integer.parseInt(editTextPlacar2.getText().toString());
        int idTime1 = listaTimes.get(spinnerTime1.getSelectedItemPosition()).idTime;
        int idTime2 = listaTimes.get(spinnerTime2.getSelectedItemPosition()).idTime;

        Partida partida = new Partida();
        partida.data = data;
        partida.placarTime1 = placar1;
        partida.placarTime2 = placar2;
        partida.time1 = idTime1;
        partida.time2 = idTime2;

        partidaDao.inserirPartida(partida);

        Toast.makeText(this, "Partida salva!", Toast.LENGTH_SHORT).show();
        finish();

        finish();
    }
}
