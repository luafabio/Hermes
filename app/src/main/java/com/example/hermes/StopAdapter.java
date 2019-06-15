package com.example.hermes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.hermes.Stop;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.StopHolder>{

    private List<Stop> stops = new ArrayList<>();

    @NonNull
    @Override
    public StopAdapter.StopHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.parada_item, viewGroup, false);

        return new StopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StopHolder stopHolder, int i) {
        Stop currentStop = stops.get(i);
        stopHolder.textViewStop.setText(String.valueOf(currentStop.getName()));
        stopHolder.textViewStopNumber.setText(String.valueOf(currentStop.getNum_stop()));
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
        notifyDataSetChanged();
    }

    class StopHolder extends RecyclerView.ViewHolder {
        private TextView textViewStop;
        private TextView textViewStopNumber;

        public StopHolder(@NonNull View itemView) {
            super(itemView);

            textViewStop = itemView.findViewById(R.id.nombre_parada);
            textViewStopNumber = itemView.findViewById(R.id.id_parada);
        }
    }
}
