package com.persediaan.de.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.data.Currency;
import com.persediaan.de.model.ModelLapTransfer;
import com.persediaan.de.model.ModelTransferDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterLapTransfer extends RecyclerView.Adapter<AdapterLapTransfer.LapTransferViewHolder>{
    ArrayList<ModelLapTransfer> modelLapTransfers;
    private AdapterItemClickListener adapterItemClickListener;

    public AdapterLapTransfer(ArrayList<ModelLapTransfer> modelLapTransfers) {
        this.modelLapTransfers = modelLapTransfers;
    }

    @NonNull
    @Override
    public LapTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_transfer_detail,parent,false);
        return new LapTransferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LapTransferViewHolder holder, int position) {
        Currency formatNumber = new Currency("Rp. ",".");
        double ttl_hrg=(double) modelLapTransfers.get(position).getTotalHarga();
        String tgl = new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                .format(
                        new Date((Long.parseLong(String.valueOf(modelLapTransfers.get(position).getTglTransfer())) * 1000))
                );
        String nm_item = modelLapTransfers.get(position).getDt_pengeluaran()+"        "+tgl+"\n\n"+
                         "        Rp. "+formatNumber.setFormatNumber(ttl_hrg)+
                         " ("+modelLapTransfers.get(position).getTotalItem()+" item)";
        holder.tv_nm_item.setText(nm_item);
        holder.tv_qty.setVisibility(View.GONE);
        holder.tv_eceran.setVisibility(View.GONE);

        holder.tv_gudang_asal.setText(modelLapTransfers.get(position).getNm_area());
        holder.tv_gudang_tujuan.setText(""+ modelLapTransfers.get(position).getNmGudangTujuan());
    }

    @Override
    public int getItemCount() {
        return (modelLapTransfers !=null)? modelLapTransfers.size():0;
    }

    public class LapTransferViewHolder extends RecyclerView.ViewHolder {
        TextView tv_gudang_tujuan,tv_gudang_asal,tv_nm_item,tv_qty,tv_eceran;
        ContextCompat contextCompat;
        Button btn_del;
        public LapTransferViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_gudang_asal = itemView.findViewById(R.id.tvGudangAsal);
            tv_gudang_tujuan = itemView.findViewById(R.id.tvGudangTujuan);

            tv_nm_item = itemView.findViewById(R.id.tvDetailTransferNmItem);
            tv_qty = itemView.findViewById(R.id.tvDetailTransferQty);
            tv_eceran = itemView.findViewById(R.id.tvDetailTransferEceran);

            btn_del = itemView.findViewById(R.id.btnDetailTransferDel);
            btn_del.setText("Detail");
            btn_del.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.splashBackground));

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    adapterItemClickListener.onItemClick(getAdapterPosition(),itemView);
                }
            });
        }
    }
}
