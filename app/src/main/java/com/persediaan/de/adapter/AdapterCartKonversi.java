package com.persediaan.de.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelCartKonv;

import java.util.ArrayList;

public class AdapterCartKonversi extends RecyclerView.Adapter<AdapterCartKonversi.itemsKonversiViewHolder> {

    ArrayList<ModelCartKonv> listCart;
    Context ctx;
    RecyclerViewKonversiInterface recyclerViewKonversiInterface;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterCartKonversi(Context ctx, ArrayList<ModelCartKonv> listCart, RecyclerViewKonversiInterface recyclerViewKonversiInterface) {
        this.ctx = ctx;
        this.listCart = listCart;
        this.recyclerViewKonversiInterface = recyclerViewKonversiInterface;
    }

    @NonNull
    @Override
    public itemsKonversiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cycle_hasilkonversi,parent,false);
        return new AdapterCartKonversi.itemsKonversiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemsKonversiViewHolder holder, int position) {
        holder.tvNmItemHKonv.setText(listCart.get(position).getNm_item());
        holder.tvQtyKonversi.setText(listCart.get(position).getQty_conv().toString());
        holder.tvEceranHKonv.setText(listCart.get(position).getEceran());
    }

    @Override
    public int getItemCount() {
        return (listCart!=null)?listCart.size():0;
    }

    public class itemsKonversiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNmItemHKonv, tvQtyKonversi, tvEceranHKonv;
        Button btnDeleteCrtKonversi;

        public itemsKonversiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNmItemHKonv = itemView.findViewById(R.id.tvDetailTransferNmItem);
            tvQtyKonversi = itemView.findViewById(R.id.tvDetailTransferQty);
            tvEceranHKonv = itemView.findViewById(R.id.tvDetailTransferEceran);
            btnDeleteCrtKonversi = itemView.findViewById(R.id.btnDetailTransferDel);

            btnDeleteCrtKonversi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewKonversiInterface.DelCartKonversi(itemView, getAdapterPosition(), listCart.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
