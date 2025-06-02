// /app/src/main/java/com/example/fifa_ufms/view/TimesActivity.java
package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.TimesAdapter;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.entities.Time;


import java.util.List;

public class TimesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimesAdapter adapter;
    private List<Time> timesList;

    private CampeonatoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);

        // 1) Botão de voltar
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // 2) Botão “Novo Time”
        //Button newButton = findViewById(R.id.button_new_time);
        //newButton.setOnClickListener(v -> {
            // Navegar para TimeFormActivity em modo de CADASTRO (sem extras)
           // Intent intent = new Intent(TimesActivity.this, TimeFormActivity.class);
           // startActivity(intent);
      ///  });

        // 3) Configura RecyclerView
        recyclerView = findViewById(R.id.recycler_times);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 4) Busca lista de Times do banco
        db = CampeonatoDatabase.getInstance(getApplicationContext());
        //timesList = db.timeDao().getAllTimes();

        // 5) Configura o Adapter passando o listener de clique em cada card
        adapter = new TimesAdapter(timesList, time -> {
            // Ao clicar num item da lista, navegar para TimeFormActivity em modo EDIÇÃO
            Intent intent = new Intent(TimesActivity.this, TimeFormActivity.class);
            // Passa os extras: id, nome e cor do uniforme
            intent.putExtra(TimeFormActivity.EXTRA_ID_TIME, time.getIdTime());
            intent.putExtra(TimeFormActivity.EXTRA_NOME_TIME, time.getNomeTime());
            intent.putExtra(TimeFormActivity.EXTRA_COR_UNIFORME, time.getCorUniforme());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Toda vez que a Activity voltar ao primeiro plano, re-carregamos a lista (para pegar inserções/edições recentes)
        timesList.clear();
       // timesList.addAll(db.timeDao().getAllTimes());
        adapter.notifyDataSetChanged();
    }
}
