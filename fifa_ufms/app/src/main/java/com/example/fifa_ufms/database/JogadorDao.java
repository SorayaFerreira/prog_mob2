package com.example.fifa_ufms.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import com.example.fifa_ufms.entities.Jogador;
import com.example.fifa_ufms.entities.Partida;

@Dao
public interface JogadorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirJogador(Jogador... jogadores);

    @Update
    void atualizarJogador(Jogador... jogadores);

    @Delete
    void deletarJogador(Jogador... jogadores);

    @Query("SELECT * FROM Jogador")
    List<Jogador> listarTodosJogadores();

    @Query("SELECT * FROM Jogador WHERE nickname = :nickname LIMIT 1")
    Jogador buscarPorNickname(String nickname);

    @Query("SELECT * FROM Partida " +
            "WHERE time1 = (SELECT idTime FROM Jogador WHERE nickname = :nickname) " +
            "OR time2 = (SELECT idTime FROM Jogador WHERE nickname = :nickname)")
    List<Partida> buscarPartidasDoJogadorPorNickname(String nickname);

    @Transaction
    default void deletarJogadorEPartidas(String nickname, CampeonatoDatabase db) {
        Jogador jogador = buscarPorNickname(nickname);
        if (jogador != null) {
            db.partidaDao().deletarPartidasPorTime(jogador.idTime);
            deletarJogador(jogador);
        }
    }
}
