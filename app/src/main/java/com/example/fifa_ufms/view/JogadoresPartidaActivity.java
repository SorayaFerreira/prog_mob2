package com.example.fifa_ufms.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.ParticipacaoDao;

import java.util.List;

public class JogadoresPartidaActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PARTIDA = "com.example.fifa_ufms.EXTRA_ID_PARTIDA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadores_partida);

        // 1) Referências de view
        ListView listViewJogadores = findViewById(R.id.list_view_jogadores);
        ImageButton buttonBack = findViewById(R.id.button_back_jogadores);

        // 2) Configurar botão “voltar” (para fechar esta Activity)
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // volta para a Activity anterior
            }
        });

        // 3) Recuperar o ID da partida que veio na Intent
        int idPartida = getIntent().getIntExtra(EXTRA_ID_PARTIDA, -1);
        if (idPartida == -1) {
            // Caso não tenha vindo o ID, exibimos um Toast e encerramos a Activity
            Toast.makeText(this, "ID de partida inválido.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 4) Obter instância do banco e DAO
        CampeonatoDatabase db = CampeonatoDatabase.getInstance(this);
        ParticipacaoDao participacaoDao = db.participacaoDao();

        // 5) Consultar a lista de nicknames dos jogadores que participaram desta partida
        List<String> listaNicknames = participacaoDao.listarNicknamesPorPartida(idPartida);

        // 6) Exibir no ListView com ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaNicknames
        );

        listViewJogadores.setAdapter(adapter);
    }
}
