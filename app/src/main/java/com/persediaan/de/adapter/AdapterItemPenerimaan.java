package com.persediaan.de.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.data.Currency;
import com.persediaan.de.model.ModelItemBrg;

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
        Currency formatNumber = new Currency("Rp. ",".");
        double ttl_hrg=
                (double)(listItems.get(position).getHarga()*listItems.get(position).getQty());
        holder.tv_nmItem.setText(listItems.get(position).getNm_item());
        holder.tv_satuan.setText(listItems.get(position).getNm_satuan());
        holder.tv_hrg.setText("Rp. "+formatNumber.setFormatNumber((double)listItems.get(position).getHarga()));
        holder.tv_ttl_hrg.setText("Rp. "+formatNumber.setFormatNumber(ttl_hrg));
        holder.tv_qty.setText(String.valueOf(listItems.get(position).getQty()));

    }

    @Override
    public int getItemCount() {
        return (listItems!=null)?listItems.size():0;
    }

    public class itemPenerimaanViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nmItem,tv_qty,tv_satuan,tv_hrg,tv_ttl_hrg;
        ImageView imgEdit,imgHps;
        public itemPenerimaanViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nmItem = itemView.findViewById(R.id.tvNmItemPener);
            tv_qty = itemView.findViewById(R.id.tvQtyPener);
            tv_satuan = itemView.findViewById(R.id.tvSatuanPener);
            tv_hrg = itemView.findViewById(R.id.tvHrgPener);
            tv_ttl_hrg = itemView.findViewById(R.id.tvTtlHrgPener);


            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgHps = itemView.findViewById(R.id.imgHps);
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),itemView);
//                    Log.d("19201299",
//                            "onClick: "+getAdapterPosition()+", Nama Item = "+listItems.get(getAdapterPosition()).getNm_item());
                }
            });

            imgHps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick1(getAdapterPosition(),itemView);
                }
            });
        }
    }
}
