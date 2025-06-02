package com.example.fifa_ufms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.entities.Jogador;

import java.util.List;

public class JogadoresNoTimeAdapter extends RecyclerView.Adapter<JogadoresNoTimeAdapter.JogadoresNoTimeViewHolder> {

    private Context context;
    private List<Jogador> jogadores;

    // Construtor: recebe Context e lista de jogadores a serem exibidos.
    public JogadoresNoTimeAdapter(Context context, List<Jogador> jogadores) {
        this.context = context;
        this.jogadores = jogadores;
    }

    @NonNull
    @Override
    public JogadoresNoTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout item_jogador.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_jogador, parent, false);
        return new JogadoresNoTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JogadoresNoTimeViewHolder holder, int position) {
        Jogador jogador = jogadores.get(position);
        holder.bind(jogador);
    }

    @Override
    public int getItemCount() {
        return (jogadores != null) ? jogadores.size() : 0;
    }

     //metodo que atualiza a lista em caso de alteracoes
    public void atualizarLista(List<Jogador> novaLista) {
        this.jogadores = novaLista;
        notifyDataSetChanged();
    }

    //ViewHolder interno: faz o bind de cada Jogador ao layout.
    static class JogadoresNoTimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;

        public JogadoresNoTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeJogador);
        }

        public void bind(Jogador jogador) {
            // o nome do jogador é o texto a ser exibido
            tvNome.setText(jogador.getNome());
        }
    }
}
