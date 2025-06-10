package com.example.fifa_ufms.adapter;
// /app/src/main/java/com/example/fifa_ufms/adapter/TimesAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.entities.Time;
import com.example.fifa_ufms.view.TimesActivity;

import java.util.List;

/**
 * Adapter para exibir a lista de Times.
 * Recebe um callback onTimeClick que será chamado quando o usuário tocar em um item para editar.
 */
public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.TimeViewHolder> {

    public interface OnTimeClickListener {
        void onTimeClick(Time time);
    }

    private final List<Time> timesList;
    private final OnTimeClickListener listener;

    public TimesAdapter(TimesActivity timesActivity, List<Time> timesList, OnTimeClickListener listener) {
        this.timesList = timesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        Time time = timesList.get(position);
        holder.bind(time, listener);
    }

    @Override
    public int getItemCount() {
        return timesList.size();
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView textNomeTime;
        TextView textCorUniforme;

        TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            textNomeTime = itemView.findViewById(R.id.text_team_name);
            textCorUniforme = itemView.findViewById(R.id.text_team_color);
        }

        void bind(final Time time, final OnTimeClickListener listener) {
            textNomeTime.setText(time.getNomeTime());
            textCorUniforme.setText(time.getCorUniforme());

            // configura o clique no “card”
            itemView.setOnClickListener(v -> listener.onTimeClick(time));
        }
    }
}

