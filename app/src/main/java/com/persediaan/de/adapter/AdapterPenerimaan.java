package com.persediaan.de.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelPenerimaan;

import java.util.ArrayList;

public class AdapterPenerimaan extends RecyclerView.Adapter<AdapterPenerimaan.penerimaanBrgViewHolder> {

    ArrayList<ModelPenerimaan> datalist;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterPenerimaan(ArrayList<ModelPenerimaan> datalist, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.datalist = datalist;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdapterPenerimaan.penerimaanBrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_penerimaan,parent,false);
        return new penerimaanBrgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull penerimaanBrgViewHolder holder, int position) {
        String nm_penyedia = datalist.get(position).getNm_suplier();
        String alamat = datalist.get(position).getAlamat();
//        int qty = datalist.get(position).getQty();
        String area = datalist.get(position).getNm_area();
//        String satker = datalist.get(position).getnm_satker();
        String status = datalist.get(position).getStatus();
        int hrg_total = datalist.get(position).getHarga_total();
        String tgl = datalist.get(position).getTgl();
        String idtrans = datalist.get(position).getId_trans();
//        holder.tv_status.setTextColor(color);
//        if (datalist.get(position).isLastItem()) {
//            holder.cardLinear.setPadding(0, 0, 0, 50);
//        }

//        holder.img_background.setImageResource(img);
        holder.tv_nm_penyedia.setText(nm_penyedia);
        holder.tv_alamat.setText(alamat);
        holder.tv_area.setText(area);
        holder.tv_idtrans.setText(idtrans);
//        holder.tv_qty.setText(String.valueOf(qty)+" ITEM");
        holder.tv_status.setText(status);
//        holder.tv_status.setBackgroundResource(bg_label);
        holder.tv_hrg_total.setText(String.valueOf(hrg_total));
        holder.tv_tgl.setText(tgl);
    }

    @Override
    public int getItemCount() {
        return (datalist!=null)?datalist.size():0;
    }

    public class penerimaanBrgViewHolder extends RecyclerView.ViewHolder {
        ImageView img_background;
        TextView tv_nm_penyedia,tv_alamat,tv_area,
                tv_status,tv_hrg_total,tv_tgl,tv_qty,tv_idtrans;
        LinearLayout cardLinear;
        public penerimaanBrgViewHolder(@NonNull View itemView) {
            super(itemView);

            img_background = itemView.findViewById(R.id.imgBgCard);
            tv_nm_penyedia = itemView.findViewById(R.id.tvNamePenyedia);
            tv_alamat = itemView.findViewById(R.id.tvAlamat);
            tv_area = itemView.findViewById(R.id.tvArea);
            tv_qty = itemView.findViewById(R.id.tvQTY);
            tv_status = itemView.findViewById(R.id.tvSts);
            tv_hrg_total = itemView.findViewById(R.id.tvHrgTotal);
            tv_tgl = itemView.findViewById(R.id.tvTgl);
            tv_idtrans = itemView.findViewById(R.id.tvIdTrans);
            cardLinear = itemView.findViewById(R.id.cardLinear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(getAdapterPosition());
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),view);
                }
            });

        }
    }
}
