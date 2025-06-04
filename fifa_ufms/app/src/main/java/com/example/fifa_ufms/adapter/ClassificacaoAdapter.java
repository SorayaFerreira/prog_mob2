package com.example.fifa_ufms.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.entities.ClassificacaoItem;

import java.util.List;

public class ClassificacaoAdapter extends RecyclerView.Adapter<ClassificacaoAdapter.ClassificacaoViewHolder> {

    private List<ClassificacaoItem> lista;

    public ClassificacaoAdapter(List<ClassificacaoItem> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ClassificacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_classificacao, parent, false);
        return new ClassificacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassificacaoViewHolder holder, int position) {
        ClassificacaoItem item = lista.get(position);
        holder.tvEquipe.setText(item.getNomeTime());
        holder.tvJogos.setText(String.valueOf(item.getJogos()));
        holder.tvVitorias.setText(String.valueOf(item.getVitorias()));
        holder.tvEmpates.setText(String.valueOf(item.getEmpates()));
        holder.tvDerrotas.setText(String.valueOf(item.getDerrotas()));
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    static class ClassificacaoViewHolder extends RecyclerView.ViewHolder {
        TextView tvEquipe, tvJogos, tvVitorias, tvEmpates, tvDerrotas;

        public ClassificacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEquipe = itemView.findViewById(R.id.tvItemEquipe);
            tvJogos = itemView.findViewById(R.id.tvItemJogos);
            tvVitorias = itemView.findViewById(R.id.tvItemVitorias);
            tvEmpates = itemView.findViewById(R.id.tvItemEmpates);
            tvDerrotas = itemView.findViewById(R.id.tvItemDerrotas);
        }
    }
}
