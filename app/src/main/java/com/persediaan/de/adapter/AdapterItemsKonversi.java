package com.persediaan.de.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.persediaan.de.R;
import com.persediaan.de.api.ApiKonversi;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelCartKonv;
import com.persediaan.de.model.ModelItemsKonv;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterItemsKonversi extends RecyclerView.Adapter<AdapterItemsKonversi.itemsKonversiViewHolder> {

    ArrayList<ModelItemsKonv> listItems;
    Context ctx;
    RecyclerViewKonversiInterface recyclerViewKonversiInterface;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterItemsKonversi(Context ctx, ArrayList<ModelItemsKonv> listItems, RecyclerViewKonversiInterface recyclerViewKonversiInterface) {
        this.ctx = ctx;
        this.listItems = listItems;
        this.recyclerViewKonversiInterface = recyclerViewKonversiInterface;
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
        ImageButton btnEdtKonversi, btnAddKonversi;

        LinearLayout container_layout, lyt_qty, lyt_sisa;

        public itemsKonversiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNmItem = itemView.findViewById(R.id.tvNmItem);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvNmSatuan = itemView.findViewById(R.id.tvNmSatuan);
            btnEdtKonversi = itemView.findViewById(R.id.btnEdtKonversi);
            btnAddKonversi = itemView.findViewById(R.id.btnAddKonversi);

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
                        btnEdtKonversi.setVisibility(View.GONE);
                        btnAddKonversi.setVisibility(View.VISIBLE);
                    }else {
                        lyt_qty.setVisibility(View.GONE);
                        lyt_sisa.setVisibility(View.GONE);
                    }
                }
            });

            btnAddKonversi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewKonversiInterface.AddCartKonversi(itemView, getAdapterPosition());
                }
            });
        }
    }
}
