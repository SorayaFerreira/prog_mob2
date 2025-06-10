package com.example.fifa_ufms.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Transaction;

import com.example.fifa_ufms.entities.Participacao;

import java.util.List;

@Dao
public interface ParticipacaoDao {

    @Insert
    void inserirParticipacoes(List<Participacao> participacoes);



    @Query("SELECT Jogador.nickname FROM Participacoes " +
            "INNER JOIN Jogador ON Participacoes.idJogador = Jogador.idJogador " +
            "WHERE Participacoes.idPartida = :partidaId")
    List<String> listarNicknamesPorPartida(int partidaId);

    @Delete
    void deletarParticipacao(Participacao participacao);
}
