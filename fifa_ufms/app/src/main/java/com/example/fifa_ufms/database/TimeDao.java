package com.example.fifa_ufms.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fifa_ufms.entities.Time;

import java.util.List;

@Dao
public interface TimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] inserirTime(Time... time);
    @Update
    void atualizarTime(Time... time);
    @Delete
    void deletarTime(Time... time);

    @Query("SELECT * FROM Times")
    List<Time> listarTodosTimes();
}
