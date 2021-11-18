package com.persediaan.de.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelPenerimaan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterPenerimaan2 extends RecyclerView.Adapter<AdapterPenerimaan2.penerimaanBrgViewHolder> {

    ArrayList<ModelPenerimaan> datalist;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterPenerimaan2(ArrayList<ModelPenerimaan> datalist, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.datalist = datalist;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdapterPenerimaan2.penerimaanBrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_penerimaan2,parent,false);
        return new penerimaanBrgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull penerimaanBrgViewHolder holder, int position) {
        DecimalFormat kursID= (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols satuanKursID = new DecimalFormatSymbols();

        satuanKursID.setCurrencySymbol("Rp. ");
        satuanKursID.setMonetaryDecimalSeparator(',');
        satuanKursID.setGroupingSeparator('.');

        kursID.setDecimalFormatSymbols(satuanKursID);

        String nm_penyedia = datalist.get(position).getNm_suplier();
        String alamat = datalist.get(position).getAlamat();
        int jmlitem = datalist.get(position).getJml_item();
        String area = datalist.get(position).getNm_area();
        String status = datalist.get(position).getStatus();
        int hrg_total = datalist.get(position).getHarga_total();
        int tgl = datalist.get(position).getTgl_purchase();
        String idtrans = datalist.get(position).getId_purchase();
        String idpurchase = datalist.get(position).getId_purchase();
//        Integer img = datalist.get(position).getImg_bg_card();
//        Integer bg_label = datalist.get(position).getImg_bg_label();
//        int color = datalist.get(position).getColor_label();
//        holder.tv_status.setTextColor(color);
        if (datalist.get(position).isExpand()){
            holder.expandDetailpener.setVisibility(View.VISIBLE);
            holder.tv_imgExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        }else{
            holder.expandDetailpener.setVisibility(View.GONE);
            holder.tv_imgExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        }
        if ((datalist.size()-1)==position) {
            holder.cardLinear.setPadding(0, 0, 0, 130);
        }

//        holder.img_background.setImageResource(img);
        holder.tv_nm_penyedia.setText(": "+nm_penyedia);
//        holder.tv_alamat.setText(alamat);
        holder.tv_area.setText(": "+area);
        holder.tv_idtrans.setText(idtrans);

        holder.tv_nopurchase.setText(": "+idpurchase);

        holder.tv_jml_item.setText(": "+String.valueOf(jmlitem)+" ITEM");
        holder.tv_status.setText(": "+status);
//        holder.tv_status.setBackgroundResource(bg_label);
        holder.tv_hrg_total.setText(": "+String.valueOf(kursID.format(hrg_total)));
        holder.tv_tgl.setText(": "+(new SimpleDateFormat("dd MMM yyyy")
                .format(
                        new Date((Long.parseLong(String.valueOf(tgl))*1000))
                )).toString());
    }

    @Override
    public int getItemCount() {
        return (datalist!=null)?datalist.size():0;
    }

    public class penerimaanBrgViewHolder extends RecyclerView.ViewHolder {
        ImageView img_background;
        TextView tv_nm_penyedia,tv_alamat,tv_area,tv_status,tv_hrg_total,
                tv_tgl,tv_jml_item,tv_idtrans,tv_nopurchase,tv_imgExpand;
        Button btn_detail;
        LinearLayout cardLinear,expandDetailpener;
        public penerimaanBrgViewHolder(@NonNull View itemView) {
            super(itemView);

            img_background = itemView.findViewById(R.id.imgBgCard);

            tv_nm_penyedia = itemView.findViewById(R.id.tvResAdmin);
            tv_area = itemView.findViewById(R.id.tvResArea);
            tv_alamat = itemView.findViewById(R.id.tvAlamat);
            tv_status = itemView.findViewById(R.id.tvResSTS);
            tv_hrg_total = itemView.findViewById(R.id.tvResTotalHrg);
            tv_jml_item = itemView.findViewById(R.id.tvResJMLItem);
            tv_tgl = itemView.findViewById(R.id.tvResTGL);
            tv_idtrans = itemView.findViewById(R.id.tvResIdTrans);
            tv_nopurchase = itemView.findViewById(R.id.tvResPurchase);
            tv_imgExpand = itemView.findViewById(R.id.imgDetailpener);

            btn_detail = itemView.findViewById(R.id.btnShowDetail);

            cardLinear = itemView.findViewById(R.id.cardLinear);
            expandDetailpener = itemView.findViewById(R.id.linearExpandDetailContainer);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(getAdapterPosition());
                    datalist.get(getAdapterPosition()).setExpand(!datalist.get(getAdapterPosition()).isExpand());
                }
            });
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),itemView);
                }
            });

        }
    }
}
