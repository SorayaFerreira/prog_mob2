package com.example.fifa_ufms.view;  // ajuste para o pacote correto

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.adapter.ClassificacaoAdapter;
import com.example.fifa_ufms.entities.ClassificacaoItem;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Time;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.PartidaDao;
import com.example.fifa_ufms.database.TimeDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity que exibe a tabela de classificação baseada nas Partidas cadastradas.
 */
public class ClassificacaoActivity extends AppCompatActivity {

    private RecyclerView rvClassificacao;
    private ClassificacaoAdapter adapter;
    private CampeonatoDatabase db;
    private TimeDao timeDao;
    private PartidaDao partidaDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificacao);

        rvClassificacao = findViewById(R.id.rvClassificacao);
        rvClassificacao.setLayoutManager(new LinearLayoutManager(this));
        rvClassificacao.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        // Obtém instância do banco Room
        db = CampeonatoDatabase.getInstance(this);
        timeDao = db.timeDao();
        partidaDao = db.partidaDao();

        // Carrega a classificação em background
        new CarregaClassificacaoTask().execute();
    }

     //AsyncTask para carregar a lista de ClassificacaoItem sem bloquear a UI.
    private class CarregaClassificacaoTask extends AsyncTask<Void, Void, List<ClassificacaoItem>> {
        @Override
        protected List<ClassificacaoItem> doInBackground(Void... voids) {
            // 1. Pega todos os times registrados no banco
            List<Time> todosTimes = timeDao.listarTodosTimes();
            Map<Long, ClassificacaoItem> mapaClassificacao = new HashMap<>();

            // Inicializa cada time com estatísticas zeradas
            for (Time t : todosTimes) {
                long id = t.idTime;         // supondo que idTime seja um int; convertemos para long
                String nome = t.nomeTime;   // ou getNomeTime(), dependendo de como você declarou
                ClassificacaoItem ci = new ClassificacaoItem(id, nome);
                mapaClassificacao.put(id, ci);
            }

            // 2. Pega todas as partidas cadastradas
            List<Partida> todasPartidas = partidaDao.listarTodasPartidas();

            // 3. Para cada partida, atualiza estatísticas dos dois times
            for (Partida p : todasPartidas) {
                long idTime1 = p.getTime1();
                long idTime2 = p.getTime2();
                int golsT1 = p.getPlacarTime1();
                int golsT2 = p.getPlacarTime2();

                //instancias das posicoes dos times 1 e 2
                ClassificacaoItem posT1 = mapaClassificacao.get(idTime1);
                ClassificacaoItem posT2 = mapaClassificacao.get(idTime2);

                // Se partida null, ignora esta partida (por garantia)
                if (posT1 == null || posT2 == null) continue;

                // Incrementa jogos disputados
                posT1.incrementarJogos();
                posT2.incrementarJogos();

                //incrementar gols feitos e sofridos t1
                posT1.adicionarGolsPro(golsT1);
                posT1.adicionarGolsContra(golsT2);
                //incrementar gols feitos e sofridos t2
                posT2.adicionarGolsPro(golsT2);
                posT2.adicionarGolsContra(golsT1);

                //logica de vitorias e derrotas
                //se t1 fez mais gols que t2
                if (golsT1 > golsT2) {
                    // t1 vence
                    posT1.incrementarVitorias();
                    posT2.incrementarDerrotas();
                    //se t2 fez mais gols que t1
                } else if (golsT1 < golsT2) {
                    // t2 vence
                    posT2.incrementarVitorias();
                    posT1.incrementarDerrotas();
                    //se t1 e t2 empataram
                } else {
                    // empate
                    posT1.incrementarEmpates();
                    posT2.incrementarEmpates();
                }
            }

            // 4) Converte o mapa em lista e devolve
            return new ArrayList<>(mapaClassificacao.values());
        }

        @Override
        protected void onPostExecute(List<ClassificacaoItem> classificacaoItems) {
            super.onPostExecute(classificacaoItems);
            // (Opcional) ordenar por número de vitórias (decrescente), depois por empates, etc.
            classificacaoItems.sort((o1, o2) -> {
                // Primeiro: mais vitórias
                if (o2.getVitorias() != o1.getVitorias()) {
                    return o2.getVitorias() - o1.getVitorias();
                }
                // Depois: mais empates
                if (o2.getEmpates() != o1.getEmpates()) {
                    return o2.getEmpates() - o1.getEmpates();
                }
                // Finalmente: menos derrotas
                return o1.getDerrotas() - o2.getDerrotas();
            });

            adapter = new ClassificacaoAdapter(classificacaoItems);
            rvClassificacao.setAdapter(adapter);
        }
    }
}
