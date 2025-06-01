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
import com.example.fifa_ufms.model.Jogador;

import java.util.List;

public class JogadoresAdapter extends RecyclerView.Adapter<JogadoresAdapter.JogadorViewHolder> {

    public interface OnItemEditClickListener {
        void onEditClick(Jogador jogador);
    }

    private List<Jogador> jogadores;
    private Context context;
    private OnItemEditClickListener editClickListener;

    public JogadoresAdapter(Context context, List<Jogador> jogadores, OnItemEditClickListener listener) {
        this.context = context;
        this.jogadores = jogadores;
        this.editClickListener = listener;
    }

    @NonNull
    @Override
    public JogadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jogador, parent, false);
        return new JogadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JogadorViewHolder holder, int position) {
        Jogador jogador = jogadores.get(position);
        holder.nomeTextView.setText(jogador.getNome());

        holder.editButton.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(jogador);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jogadores.size();
    }

    static class JogadorViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView nomeTextView;
        ImageButton editButton;

        public JogadorViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.image_jogador_icon);
            nomeTextView = itemView.findViewById(R.id.text_nome_jogador);
            editButton = itemView.findViewById(R.id.button_edit_jogador);
        }
    }
}
