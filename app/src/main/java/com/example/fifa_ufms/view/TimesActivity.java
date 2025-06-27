// /app/src/main/java/com/example/fifa_ufms/view/TimesActivity.java
package com.example.fifa_ufms.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.TimesAdapter;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.entities.Time;
import com.example.fifa_ufms.view.TimeFormActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TimesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TimesAdapter adapter;
    private List<Time> timesList;
    private CampeonatoDatabase db;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);

        // 1) instancia a lista para não ser null
        timesList = new ArrayList<>();

        // 2) obtém instância do banco (Room)
        db = CampeonatoDatabase.getInstance(this);

        backButton = findViewById(R.id.back_button);

        // Botão de voltar
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // 3) configura RecyclerView e Adapter (com listener para clique)
        recyclerView = findViewById(R.id.recycler_times);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimesAdapter(
                this,
                timesList,
                new TimesAdapter.OnTimeClickListener() {
                    @Override
                    public void onTimeClick(Time time) {
                        long id = time.getIdTime(); // id long
                        // 1. Debub. Veja se este método está mesmo sendo chamado e qual ID o Time tem
                        Toast.makeText(
                                TimesActivity.this,
                                "Clique em editar: timeId = " + time.getIdTime(),
                                Toast.LENGTH_SHORT
                        ).show();
                        Log.d("TimesActivity", "onTimeClick: timeId = " + time.getIdTime());
                        // navega para TimeFormActivity em modo edição, passa o id atraves do intent
                        Intent intent = new Intent(TimesActivity.this, TimeFormActivity.class);
                        intent.putExtra(TimeFormActivity.EXTRA_ID_TIME, id);

                        startActivity(intent);
                    }
                }
        );
        recyclerView.setAdapter(adapter);

        // 4) navega em modo cadastro
        Button btnNovoTime = findViewById(R.id.button_add_time);
        btnNovoTime.setOnClickListener(v -> {
            Intent intent = new Intent(TimesActivity.this, TimeFormActivity.class);
            startActivity(intent);
        });

        // 5) carrega os dados iniciais de “times” de forma assíncrona
        loadTimesFromDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // sempre que a Activity voltar ao foco, recarregamos a lista de forma assíncrona:
        loadTimesFromDatabase();
    }

    /**
     * Método que executa a consulta em background e atualiza o adapter na UI thread.
     */
    private void loadTimesFromDatabase() {
        // Usa um Executor de thread única para não bloquear a UI
        Executors.newSingleThreadExecutor().execute(() -> {
            // 1) pega todos os times do banco (dentro de thread de background)
            final List<Time> listaTemporaria = db.timeDao().listarTodosTimes();

            // 2) volta para a main thread para atualizar o RecyclerView
            runOnUiThread(() -> {
                timesList.clear();
                timesList.addAll(listaTemporaria);
                adapter.notifyDataSetChanged();
            });
        });
    }
}

