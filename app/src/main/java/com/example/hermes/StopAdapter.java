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
    private OnParadaListener monParadaListener;

    public StopAdapter(OnParadaListener onParadaListener){
        this.monParadaListener = onParadaListener;
    }

    @NonNull
    @Override
    public StopAdapter.StopHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.parada_item, viewGroup, false);

        return new StopHolder(itemView, monParadaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StopHolder stopHolder, int i) {
        Stop currentStop = stops.get(i);
        stopHolder.textViewStop.setText(currentStop.toString());
        stopHolder.setParada(currentStop);
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
        notifyDataSetChanged();
    }

    class StopHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewStop;
        private Stop parada;
        OnParadaListener onParadaListener;

        public StopHolder(@NonNull View itemView, OnParadaListener onParadaListener) {
            super(itemView);
            textViewStop = itemView.findViewById(R.id.nombre_parada);
            this.onParadaListener = onParadaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onParadaListener.onParadaClick(getAdapterPosition());
        }

        public void setParada(Stop parada){
            this.parada = parada;
        }
    }

    public interface OnParadaListener{
        void onParadaClick(int position);
    }
}
