package com.example.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.entities.Partida;

import com.example.entities.Time;

import java.util.List;

@Dao
public interface PartidaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirPartida(Partida... partida);

    @Update
    void atualizarPartida(Partida... partida);

    @Delete
    void deletarPartida(Partida... partida);

    @Query("SELECT * FROM Partida")
    List<Partida> listarTodasPartidas();

    @Query("SELECT * FROM Partida WHERE time1 = :id OR time2 = :id")
    List<Partida> buscarPorPartidaPorTime(int id);

    @Query("DELETE FROM Partida WHERE time1 = :idTime OR time2 = :idTime")
    void deletarPartidasPorTime(int idTime);
}
