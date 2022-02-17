package com.persediaan.de.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.data.Currency;
import com.persediaan.de.model.ModelPenerimaan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterPenerimaan extends RecyclerView.Adapter<AdapterPenerimaan.penerimaanBrgViewHolder> {

    ArrayList<ModelPenerimaan> datalist;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterPenerimaan(ArrayList<ModelPenerimaan> dataKonversi,
                             RecyclerViewClickInterface recyclerViewClickInterface) {
        this.datalist = dataKonversi;
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
        if (datalist.get(position)!=null) {
            Currency formatNumber = new Currency("Rp. ", ".");
            {
                String nm_penyedia = datalist.get(position).getNm_suplier();
                String alamat = datalist.get(position).getAlasuplier();
                int ttlqty = datalist.get(position).getTtlQty();
                String area = datalist.get(position).getNm_area();
//        String satker = datalist.get(position).getnm_satker();
                int dikonversi = datalist.get(position).getDikonversi();
                int hrg_total = datalist.get(position).getHarga_total();
                int tgl = datalist.get(position).getUpdated();
                String idtrans = datalist.get(position).getId_trans();

//        holder.img_background.setImageResource(img);
                holder.tv_nm_penyedia1.setText(nm_penyedia);
                holder.tv_alamat1.setText(alamat);
                holder.tv_area1.setVisibility(View.GONE);
                holder.tv_idtrans1.setText("idtrans");
                holder.tv_qty1.setText("" + ttlqty + " ITEM");
                if (dikonversi == 1) {
                    holder.tv_status1.setText("Konversi");
                    holder.tv_status1.setBackgroundResource(R.color.BgGreen);
                } else {
                    holder.tv_status1.setText("Belum Konversi");
                    holder.tv_status1.setBackgroundResource(R.color.BgRed);
                }
                holder.tv_hrg_total1.setText(String.valueOf("Rp. " + formatNumber.setFormatNumber((double) hrg_total)));
                holder.tv_tgl1.setText((new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                        .format(
                                new Date((Long.parseLong(String.valueOf(tgl)) * 1000))
                        )).toString());
            }
        }else{
            holder.cardLinear1.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (datalist!=null)?datalist.size():0;
    }

    public class penerimaanBrgViewHolder extends RecyclerView.ViewHolder {
        ImageView img_background1;
        TextView tv_nm_penyedia1, tv_alamat1, tv_area1,
                tv_status1, tv_hrg_total1, tv_tgl1, tv_qty1, tv_idtrans1;
        LinearLayout cardLinear1;
        public penerimaanBrgViewHolder(@NonNull View itemView) {
            super(itemView);

            img_background1 = itemView.findViewById(R.id.imgBgCard);
            tv_nm_penyedia1 = itemView.findViewById(R.id.tvNamePenyedia);
            tv_alamat1 = itemView.findViewById(R.id.tvAlamat);
            tv_area1 = itemView.findViewById(R.id.tvArea);
            tv_qty1 = itemView.findViewById(R.id.tvQTY);
            tv_status1 = itemView.findViewById(R.id.tvSts);
            tv_hrg_total1 = itemView.findViewById(R.id.tvHrgTotal);
            tv_tgl1 = itemView.findViewById(R.id.tvTgl);
            tv_idtrans1 = itemView.findViewById(R.id.tvIdTrans);
            cardLinear1 = itemView.findViewById(R.id.cardLinear);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    notifyItemChanged(getAdapterPosition());
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),view);
                }
            });

        }
    }
}
