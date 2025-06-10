package com.example.fifa_ufms.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "Times")
public class Time {
    @PrimaryKey(autoGenerate = true)
    public int idTime;
    @ColumnInfo(name = "nome_Time")
    public String nomeTime;
    @ColumnInfo(name = "cor_Uniforme")
    public String corUniforme;

    public Time(String nomeTime, String corUniforme) {
        this.nomeTime = nomeTime;
        this.corUniforme = corUniforme;
    }

    public int getIdTime() {
        return idTime;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public String getCorUniforme() {
        return corUniforme;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }


    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public void setCorUniforme(String corUniforme) {
        this.corUniforme = corUniforme;
    }
}