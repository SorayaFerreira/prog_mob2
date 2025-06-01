package com.example.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.entities.Time;

@Entity(indices = {@Index(value = {"nickname"}, unique = true)},
        foreignKeys = @ForeignKey(
                entity = Time.class,
                parentColumns = "idTime",
                childColumns = "idTime",
                onDelete = ForeignKey.CASCADE
        )
)

public class Jogador {
    @PrimaryKey(autoGenerate = true)
    public int idJogador;
    public String nome;
    public String nickname;
    public String email;
    public String dataNascimento;
    public int numeroGols;
    public int numeroAmarelos;
    public int numeroVermelhos;
    public int idTime;

    public Jogador(int numeroVermelhos, int numeroAmarelos, int numeroGols, String dataNascimento, String email, String nickname, String nome, int idTime) {
        this.numeroVermelhos = numeroVermelhos;
        this.numeroAmarelos = numeroAmarelos;
        this.numeroGols = numeroGols;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.nickname = nickname;
        this.nome = nome;
        this.idTime = idTime;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getNumeroGols() {
        return numeroGols;
    }

    public void setNumeroGols(int numeroGols) {
        this.numeroGols = numeroGols;
    }

    public int getNumeroVermelhos() {
        return numeroVermelhos;
    }

    public void setNumeroVermelhos(int numeroVermelhos) {
        this.numeroVermelhos = numeroVermelhos;
    }

    public int getNumeroAmarelos() {
        return numeroAmarelos;
    }

    public void setNumeroAmarelos(int numeroAmarelos) {
        this.numeroAmarelos = numeroAmarelos;
    }
}
