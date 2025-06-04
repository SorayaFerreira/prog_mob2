package com.example.fifa_ufms.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import com.example.fifa_ufms.R;
import com.example.fifa_ufms.database.CampeonatoDatabase;
import com.example.fifa_ufms.database.PartidaDao;
import com.example.fifa_ufms.database.TimeDao;
import com.example.fifa_ufms.entities.Partida;
import com.example.fifa_ufms.entities.Time;
import com.example.fifa_ufms.view.PartidasFormActivity;

import java.util.List;

public class PartidasAdapter extends BaseAdapter {
    private final Context context;
    private final List<Partida> partidas;
    private final TimeDao timeDao;

    public PartidasAdapter(Context context, List<Partida> partidas, TimeDao timeDao) {
        this.context = context;
        this.partidas = partidas;
        this.timeDao = timeDao;
    }

    @Override
    public int getCount() {
        return partidas.size();
    }

    @Override
    public Object getItem(int position) {
        return partidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return partidas.get(position).idPartida;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_partida, parent, false);
        }

        TextView textData = convertView.findViewById(R.id.textData);
        TextView textTimes = convertView.findViewById(R.id.textTimes);
        TextView textPlacar = convertView.findViewById(R.id.textPlacar);
        ImageButton btnDelete = convertView.findViewById(R.id.button_delete);

        Partida partida = partidas.get(position);
        Time t1 = timeDao.buscarPorId(partida.time1);
        Time t2 = timeDao.buscarPorId(partida.time2);

        textData.setText("Data: " + partida.data);
        textTimes.setText("Times: " + t1.nomeTime + " vs " + t2.nomeTime);
        textPlacar.setText("Placar: " + partida.placarTime1 + " x " + partida.placarTime2);

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja excluir esta partida?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        CampeonatoDatabase db = Room.databaseBuilder(context,
                                        CampeonatoDatabase.class, "campeonato")
                                .allowMainThreadQueries()
                                .build();

                        PartidaDao partidaDao = db.partidaDao();
                        partidaDao.deletarPartida(partida);

                        partidas.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Partida deletada", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Não", null)
                    .show();
        });

        ImageButton buttonEdit = convertView.findViewById(R.id.button_edit_partida);

        buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, PartidasFormActivity.class);
            intent.putExtra("ID_PARTIDA", partida.idPartida);
            Toast.makeText(context, "Editar partida ID: " + partida.idPartida, Toast.LENGTH_SHORT).show();
            context.startActivity(intent);

        });


        return convertView;
    }

}
