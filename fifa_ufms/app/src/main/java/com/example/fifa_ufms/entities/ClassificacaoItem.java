package com.example.fifa_ufms.entities;


public class ClassificacaoItem {
    private long timeId;
    private String nomeTime;

    private int jogos;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsPro;
    private int golsContra;

    public ClassificacaoItem(long timeId, String nomeTime) {
        this.timeId = timeId;
        this.nomeTime = nomeTime;
        this.jogos = 0;
        this.vitorias = 0;
        this.empates = 0;
        this.derrotas = 0;
        this.golsPro = 0;
        this.golsContra = 0;
    }
    public ClassificacaoItem(Time time) {
        this.timeId = time.getIdTime();
        this.nomeTime = time.getNomeTime();

    }

    // getters e setters

    public long getTimeId() {
        return timeId;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public int getJogos() {
        return jogos;
    }

    public void incrementarJogos() {
        this.jogos++;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void incrementarVitorias() {
        this.vitorias++;
    }

    public int getEmpates() {
        return empates;
    }

    public void incrementarEmpates() {
        this.empates++;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void incrementarDerrotas() {
        this.derrotas++;
    }

    public int getGolsPro() {
        return golsPro;
    }

    public void adicionarGolsPro(int g) {
        this.golsPro += g;
    }

    public int getGolsContra() {
        return golsContra;
    }

    public void adicionarGolsContra(int g) {
        this.golsContra += g;
    }

    public int getSaldoDeGols() {
        return this.golsPro - this.golsContra;
    }

    public int getPontos() {
        // Padrão futebol: vitória = 3, empate = 1, derrota = 0
        return this.vitorias * 3 + this.empates;
    }
}
