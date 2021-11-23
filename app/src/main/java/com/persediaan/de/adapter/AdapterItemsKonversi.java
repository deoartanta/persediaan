package com.persediaan.de.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelItemsKonv;

import java.util.ArrayList;

public class AdapterItemsKonversi extends RecyclerView.Adapter<AdapterItemsKonversi.itemsKonversiViewHolder> {

    ArrayList<ModelItemsKonv> listItems;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterItemsKonversi(ArrayList<ModelItemsKonv> listItems, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.listItems = listItems;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public itemsKonversiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cycle_konversi,parent,false);
        return new AdapterItemsKonversi.itemsKonversiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemsKonversiViewHolder holder, int position) {

        holder.tvNmItem.setText(listItems.get(position).getNm_item());
        holder.tvNmSatuan.setText(listItems.get(position).getNm_satuan());
        holder.tvQty.setText(String.valueOf(listItems.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return (listItems!=null)?listItems.size():0;
    }

    public class itemsKonversiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNmItem, tvQty, tvNmSatuan;
        ImageButton btnEdtKonversi;
        public itemsKonversiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNmItem = itemView.findViewById(R.id.tvNmItem);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvNmSatuan = itemView.findViewById(R.id.tvNmSatuan);
            btnEdtKonversi = itemView.findViewById(R.id.btnEdtKonversi);
        }
    }
}