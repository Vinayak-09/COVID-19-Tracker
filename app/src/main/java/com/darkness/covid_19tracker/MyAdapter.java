package com.darkness.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<DistrictModel> arrayList;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<DistrictModel> arrayList, Context context){
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DistrictModel districtModel = arrayList.get(position);
        holder.name.setText(String.valueOf(districtModel.getDistrictName()));
        holder.confirmed.setText(String.valueOf(districtModel.getConfirmedCases()));
        holder.active.setText(String.valueOf(districtModel.getActivePatients()));
        holder.deceased.setText(String.valueOf(districtModel.getDeceasedPatients()));
        holder.recovered.setText(String.valueOf(districtModel.getRecoveredPatients()));
        holder.progressIndicator.setProgress((int)districtModel.getDeathRate(),true);
        holder.recoveryText.setText(String.valueOf((int)districtModel.getDeathRate())+"%");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,confirmed,active,recovered,deceased,recoveryText;
        CircularProgressIndicator progressIndicator;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            confirmed = itemView.findViewById(R.id.confirmed);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.recovered);
            deceased = itemView.findViewById(R.id.deceased);
            progressIndicator = itemView.findViewById(R.id.analysis);
            recoveryText = itemView.findViewById(R.id.recoveryText);
        }
    }
}
