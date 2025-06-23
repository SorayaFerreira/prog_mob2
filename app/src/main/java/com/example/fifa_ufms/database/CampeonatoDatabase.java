package com.example.fifa_ufms.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Time;
import com.example.fifa_ufms.entities.Participacao;

@Database(entities = {Jogador.class, Time.class, Partida.class, Participacao.class}, version = 2)
public abstract class CampeonatoDatabase extends RoomDatabase {
    private static volatile CampeonatoDatabase INSTANCE;

    public abstract JogadorDao jogadorDao();
    public abstract TimeDao timeDao();
    public abstract PartidaDao partidaDao();
    public abstract ParticipacaoDao participacaoDao();

    public static CampeonatoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CampeonatoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CampeonatoDatabase.class,
                            "campeonato"
                    )
                            .fallbackToDestructiveMigration() // <- adiciona aqui
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
