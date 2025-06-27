package com.example.fifa_ufms.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Partida",
        foreignKeys = {
                @ForeignKey(entity = Time.class,
                        parentColumns = "idTime",
                        childColumns = "time1"),
                @ForeignKey(entity = Time.class,
                        parentColumns = "idTime",
                        childColumns = "time2"),
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

    public Partida() {

    }
    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTime1() {
        return time1;
    }

    public void setTime1(int time1) {
        this.time1 = time1;
    }

    public int getTime2() {
        return time2;
    }

    public void setTime2(int time2) {
        this.time2 = time2;
    }

    public int getPlacarTime1() {
        return placarTime1;
    }

    public void setPlacarTime1(int placarTime1) {
        this.placarTime1 = placarTime1;
    }

    public int getPlacarTime2() {
        return placarTime2;
    }

    public void setPlacarTime2(int placarTime2) {
        this.placarTime2 = placarTime2;
    }

}
