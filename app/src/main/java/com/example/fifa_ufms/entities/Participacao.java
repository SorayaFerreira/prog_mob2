package com.example.fifa_ufms.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Participacoes",
        foreignKeys = {
                @ForeignKey(entity = Jogador.class,
                        parentColumns = "idJogador",
                        childColumns = "idJogador",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Partida.class,
                        parentColumns = "idPartida",
                        childColumns = "idPartida",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {
                @Index("idPartida"),
                @Index("idJogador")
        }
)

public class Participacao {

    @PrimaryKey(autoGenerate = true)
    public int idParticipacao;

    public int idJogador;
    public int idPartida;
    public int idTime;

    // Campos adicionais opcionais
    public boolean titular;
    public int minutosJogados;
}