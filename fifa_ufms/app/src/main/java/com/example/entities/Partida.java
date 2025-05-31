package com.example.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Partida",
        foreignKeys = {
                @ForeignKey(entity = Time.class,
                        parentColumns = "idTime",
                        childColumns = "time1",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Time.class,
                        parentColumns = "idTime",
                        childColumns = "time2",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class Partida {
    @PrimaryKey(autoGenerate = true)
    public int idPartida;

    public String data;
    public int time1;
    public int time2;
    public int placarTime1;
    public int placarTime2;

    // Construtor para Data Seeder
    public Partida(String data, int time1, int time2, int placarTime1, int placarTime2) {
        this.data = data;
        this.time1 = time1;
        this.time2 = time2;
        this.placarTime1 = placarTime1;
        this.placarTime2 = placarTime2;
    }

    // Getters e Setters...
}
