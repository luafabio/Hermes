package com.example.hermes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BingAdapter extends RecyclerView.Adapter<BingAdapter.BingHolder> {

    private List<Bing> bings = new ArrayList<>();

    @NonNull
    @Override
    public BingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bing_item, viewGroup, false);

        return new BingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BingHolder bingHolder, int i) {
        Bing currentBing = bings.get(i);
        bingHolder.textViewStop.setText(String.valueOf(currentBing.getId_stop()));
        bingHolder.textViewTime.setText(String.valueOf(currentBing.getTime()));
        bingHolder.textViewStatus.setText(currentBing.getStatus());
        //Poner los datos correctos
    }

    @Override
    public int getItemCount() {
        return bings.size();
    }

    public void setBings(List<Bing> bings) {
        this.bings = bings;
        notifyDataSetChanged();
    }

    class BingHolder extends RecyclerView.ViewHolder {
        private TextView textViewStop;
        private TextView textViewTime;
        private TextView textViewStatus;

        public BingHolder(@NonNull View itemView) {
            super(itemView);

            textViewStop = itemView.findViewById(R.id.text_view_stop);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            //Poner los datos correctos

        }
    }
}
