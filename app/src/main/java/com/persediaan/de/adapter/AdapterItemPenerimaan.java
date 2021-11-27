package com.persediaan.de.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelItemBrg;
import com.persediaan.de.model.ModelItemsKonv;

import java.util.ArrayList;

public class AdapterItemPenerimaan extends RecyclerView.Adapter<AdapterItemPenerimaan.itemPenerimaanViewHolder> {

    ArrayList<ModelItemBrg> listItems;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterItemPenerimaan(
            ArrayList<ModelItemBrg> listItems,
            RecyclerViewClickInterface recyclerViewClickInterface) {
        this.listItems = listItems;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public itemPenerimaanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_penerimaan_item,parent,false);
        return new AdapterItemPenerimaan.itemPenerimaanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemPenerimaanViewHolder holder, int position) {
        holder.tv_nmItem.setText(listItems.get(position).getNm_item());
        holder.tv_nmItem.setText(listItems.get(position).getNm_satuan());
        holder.tv_nmItem.setText(listItems.get(position).getNm_eceran());

    }

    @Override
    public int getItemCount() {
        return (listItems!=null)?listItems.size():0;
    }

    public class itemPenerimaanViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nmItem,tv_qty,tv_satuan,tv_hrg;
        ImageView imgEdit;
        public itemPenerimaanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nmItem = itemView.findViewById(R.id.tvNmItemPener);
            tv_qty = itemView.findViewById(R.id.tvQTY);
            tv_satuan = itemView.findViewById(R.id.tvSatuanPener);
            tv_hrg = itemView.findViewById(R.id.tvHrgPener);

            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("19201299", "onClick: "+getAdapterPosition());
                }
            });
        }
    }
}
