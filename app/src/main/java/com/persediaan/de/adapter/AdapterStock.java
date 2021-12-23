package com.persediaan.de.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelCartKonv;
import com.persediaan.de.model.ModelStock;

import java.util.ArrayList;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.itemsStockViewHolder> {

    ArrayList<ModelStock> listStock;
    Context ctx;

    public AdapterStock(Context ctx, ArrayList<ModelStock> listStock) {
        this.ctx = ctx;
        this.listStock = listStock;
    }

    @NonNull
    @Override
    public itemsStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cycle_stock,parent,false);
        return new AdapterStock.itemsStockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemsStockViewHolder holder, int position) {
        holder.nmBarangStock.setText(listStock.get(position).getNm_item());
        holder.qtyStock.setText(""+listStock.get(position).getSisa());
        holder.nmEceranStock.setText("/"+listStock.get(position).getEceran());
        holder.nmAreaStock.setText(listStock.get(position).getNm_area());
    }

    @Override
    public int getItemCount() {
        return (listStock!=null)?listStock.size():0;
    }

    public class itemsStockViewHolder extends RecyclerView.ViewHolder {
        TextView nmBarangStock, qtyStock, nmEceranStock, nmAreaStock;

        public itemsStockViewHolder(@NonNull View itemView) {
            super(itemView);

            nmBarangStock = itemView.findViewById(R.id.nmBarangStock);
            qtyStock = itemView.findViewById(R.id.qtyStock);
            nmEceranStock = itemView.findViewById(R.id.nmEceranStock);
            nmAreaStock = itemView.findViewById(R.id.nmAreaStock);
        }
    }
}
