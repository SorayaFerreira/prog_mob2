package com.example.fifa_ufms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.entities.Jogador;

import java.util.List;
public class JogadoresAdapter extends RecyclerView.Adapter<JogadoresAdapter.ViewHolder> {

    private final Context context;
    private final List<Jogador> jogadores;
    private final OnJogadorClickListener listener;

    public interface OnJogadorClickListener {
        void onJogadorClick(Jogador jogador);
    }

    public JogadoresAdapter(Context context, List<Jogador> jogadores, OnJogadorClickListener listener) {
        this.context = context;
        this.jogadores = jogadores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JogadoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jogador, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JogadoresAdapter.ViewHolder holder, int position) {
        Jogador jogador = jogadores.get(position);
        holder.textNome.setText(jogador.getNome());
        holder.textNickname.setText(jogador.getNickname());
        holder.textEmail.setText(jogador.getEmail());
        holder.textDataNascimento.setText(jogador.getDataNascimento());
        holder.textGols.setText("Gols: " + jogador.getNumeroGols());
        holder.textCartoes.setText("Amarelos: " + jogador.getNumeroAmarelos() + " | Vermelhos: " + jogador.getNumeroVermelhos());

        holder.buttonEdit.setOnClickListener(v -> listener.onJogadorClick(jogador));
    }

    @Override
    public int getItemCount() {
        return jogadores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textNickname, textEmail, textDataNascimento, textGols, textCartoes;
        ImageButton buttonEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.text_nome_jogador);
            textNickname = itemView.findViewById(R.id.text_nickname);
            textEmail = itemView.findViewById(R.id.text_email);
            textDataNascimento = itemView.findViewById(R.id.text_data_nascimento);
            textGols = itemView.findViewById(R.id.text_gols);
            textCartoes = itemView.findViewById(R.id.text_cartoes);
            buttonEdit = itemView.findViewById(R.id.button_edit_jogador);
        }
    }
}
