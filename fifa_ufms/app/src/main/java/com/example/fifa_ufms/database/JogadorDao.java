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
    void inserirJogador(Jogador... jogador);
    @Update
    void atualizarJogador(Jogador... jogador);
    @Delete
    void deletarJogador(Jogador... jogador);
    @Query("SELECT * FROM Jogador")
    List<Jogador> listarTodosJogadores();
    @Query("SELECT * FROM Jogador WHERE idTime = :idTime")
    List<Jogador> listarJogadoresPorTime(int idTime);
    @Query("SELECT * FROM Jogador WHERE nickname = :nickname LIMIT 1")
    Jogador buscarPorNickname(String nickname);
    //listar todos jogadores ainda sem time
    @Query("SELECT * FROM Jogador") //WHERE idTime = 0
    List<Jogador> listarJogadoresSemTime();
    //Atribui um novo idTime para o jogador cujo idJogador for informado.
    @Query("UPDATE Jogador SET idTime = :idTime WHERE idJogador = :idJogador")
    void setarTimeJogador(int idJogador, int idTime);

    //Listar os jogos que o jogador participou e deletar
    @Query("SELECT * FROM Partida " +
            "WHERE time1 = (SELECT idTime FROM Jogador WHERE nickname = :nickname) " +
            "   OR time2 = (SELECT idTime FROM Jogador WHERE nickname = :nickname)")
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
