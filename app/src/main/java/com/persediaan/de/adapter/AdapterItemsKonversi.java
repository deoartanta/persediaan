package com.persediaan.de.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
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

        if (listItems.get(position).isPaddingLastItem()){
            holder.container_layout.setPadding(0,0,0,100);
        }
        Log.d("19201299", "onBindViewHolder: "+listItems.get(position).isPaddingLastItem());

        holder.tvNmSatuanEdt.setText(listItems.get(position).getNm_satuan());
        holder.tvNmEceranEdt.setText(listItems.get(position).getEceran());
        holder.edtQtySatu.setText(String.valueOf(listItems.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return (listItems!=null)?listItems.size():0;
    }

    public class itemsKonversiViewHolder extends RecyclerView.ViewHolder {
        TextView tvNmItem, tvQty, tvNmSatuan, tvNmSatuanEdt, tvNmEceranEdt;
        TextInputEditText edtQtySatu, edtQtyEcer;
        ImageButton btnEdtKonversi;

        LinearLayout container_layout,
                     lyt_qty,
                     lyt_sisa;
        public itemsKonversiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNmItem = itemView.findViewById(R.id.tvNmItem);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvNmSatuan = itemView.findViewById(R.id.tvNmSatuan);
            btnEdtKonversi = itemView.findViewById(R.id.btnEdtKonversi);

            container_layout = itemView.findViewById(R.id.containerLayout);
            tvNmSatuanEdt = itemView.findViewById(R.id.tvNmSatuanEdt);
            tvNmEceranEdt = itemView.findViewById(R.id.tvNmEceranEdt);
            edtQtySatu = itemView.findViewById(R.id.edtQtySatu);
            edtQtyEcer = itemView.findViewById(R.id.edtQtyEcer);

            lyt_qty = itemView.findViewById(R.id.lytQty);
            lyt_sisa = itemView.findViewById(R.id.lytSisa);

            lyt_qty.setVisibility(View.GONE);
            lyt_sisa.setVisibility(View.GONE);

            btnEdtKonversi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listItems.get(getAdapterPosition()).setExpand(!(listItems.get(getAdapterPosition()).isExpand()));
                    if (listItems.get(getAdapterPosition()).isExpand()){
                        lyt_qty.setVisibility(View.VISIBLE);
                        lyt_sisa.setVisibility(View.VISIBLE);
                    }else {
                        lyt_qty.setVisibility(View.GONE);
                        lyt_sisa.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
