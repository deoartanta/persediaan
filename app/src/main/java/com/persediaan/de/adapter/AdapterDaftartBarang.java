package com.persediaan.de.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelCartKonv;
import com.persediaan.de.model.ModelDaftarItem;

import java.util.ArrayList;

public class AdapterDaftartBarang extends RecyclerView.Adapter<AdapterDaftartBarang.AdapterViewHolder> {

    ArrayList<ModelDaftarItem> listItem;
    Context ctx;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterDaftartBarang(ArrayList<ModelDaftarItem> listItem, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.listItem = listItem;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdapterDaftartBarang.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_daftar_item,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDaftartBarang.AdapterViewHolder holder, int position) {

        holder.tv_nm_item.setText(""+listItem.get(position).getNm_item());
        holder.tv_nm_stn.setText(""+listItem.get(position).getNm_satuan());

    }

    @Override
    public int getItemCount() {
        return (listItem!=null)?listItem.size():0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nm_item,tv_nm_stn;
        AppCompatButton btn_edit,btn_hps;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nm_item =itemView.findViewById(R.id.tvResNmItem);
            tv_nm_stn =itemView.findViewById(R.id.tvResNmStn);

            btn_edit = itemView.findViewById(R.id.btnEditItem);
            btn_hps = itemView.findViewById(R.id.btnHpsItem);

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),itemView);
                }
            });
            btn_hps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick1(getAdapterPosition(),itemView);
                }
            });
        }
    }
}
