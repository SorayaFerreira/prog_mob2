import android.content.Context;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataSeeder {

    // Obrigado CHAT GPT :)

    public static void popularBanco(Context context) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            CampeonatoDatabase db = CampeonatoDatabase.getInstance(context);

            // Criar jogadores
            Jogador j1 = new Jogador();
            j1.nome = "João da Silva";
            j1.nickname = "joaofifa";
            j1.email = "joao@example.com";
            j1.dataNascimento = "1990-01-01";
            j1.numeroGols = 10;
            j1.numeroAmarelos = 2;
            j1.numeroVermelhos = 1;

            Jogador j2 = new Jogador();
            j2.nome = "Maria Oliveira";
            j2.nickname = "mariagol";
            j2.email = "maria@example.com";
            j2.dataNascimento = "1992-03-15";
            j2.numeroGols = 15;
            j2.numeroAmarelos = 0;
            j2.numeroVermelhos = 0;

            // Inserir jogadores
            db.jogadorDao().inserir(j1);
            db.jogadorDao().inserir(j2);

            // Recuperar jogadores com seus IDs
            Jogador jogador1 = db.jogadorDao().buscarPorNickname("joaofifa");
            Jogador jogador2 = db.jogadorDao().buscarPorNickname("mariagol");

            // Criar partida
            Partida p1 = new Partida();
            p1.data = "2025-05-25";
            p1.idJogador1 = jogador1.idJogador;
            p1.idJogador2 = jogador2.idJogador;
            p1.placarJogador1 = 3;
            p1.placarJogador2 = 2;

            // Inserir partida
            db.partidaDao().inserir(p1);
        });
    }
}
