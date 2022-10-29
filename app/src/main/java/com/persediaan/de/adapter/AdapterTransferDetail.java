package com.persediaan.de.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelTransferDetail;

import java.util.ArrayList;

public class AdapterTransferDetail extends RecyclerView.Adapter<AdapterTransferDetail.TransferDetailViewHolder> {
    ArrayList<ModelTransferDetail> modelTransferDetails;
    private AdapterItemClickListener adapterItemClickListener;

    public AdapterTransferDetail(ArrayList<ModelTransferDetail> modelTransferDetails) {
        this.modelTransferDetails = modelTransferDetails;
    }
    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener){
        this.adapterItemClickListener = adapterItemClickListener;
    }

    @NonNull
    @Override
    public TransferDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_transfer_detail,parent,false);
        return new TransferDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferDetailViewHolder holder, int position) {

        String nm_item = modelTransferDetails.get(position).getNm_item()+" "+
                modelTransferDetails.get(position).getQty_peng()+" "+
                modelTransferDetails.get(position).getEceran();
        holder.tv_nm_item.setText(nm_item);
        holder.tv_qty.setVisibility(View.GONE);
        holder.tv_eceran.setVisibility(View.GONE);

        holder.tv_gudang_asal.setText(modelTransferDetails.get(position).getNm_area());
        holder.tv_gudang_tujuan.setText(""+modelTransferDetails.get(position).getGudang_tujuan());
    }

    @Override
    public int getItemCount() {
        return (modelTransferDetails!=null)?modelTransferDetails.size():0;
    }

    public class TransferDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tv_gudang_tujuan,tv_gudang_asal,tv_nm_item,tv_qty,tv_eceran;
        Button btn_del;

        public TransferDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gudang_asal = itemView.findViewById(R.id.tvGudangAsal);
            tv_gudang_tujuan = itemView.findViewById(R.id.tvGudangTujuan);

            tv_nm_item = itemView.findViewById(R.id.tvDetailTransferNmItem);
            tv_qty = itemView.findViewById(R.id.tvDetailTransferQty);
            tv_eceran = itemView.findViewById(R.id.tvDetailTransferEceran);

            btn_del = itemView.findViewById(R.id.btnDetailTransferDel);
            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterItemClickListener.onItemClick(getAdapterPosition(),itemView);
                }
            });
        }
    }
}
