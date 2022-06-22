package com.persediaan.de.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AdapterPenerimaan2 extends RecyclerView.Adapter<AdapterPenerimaan2.penerimaanBrgViewHolder> {

    ArrayList<ModelPenerimaan> datalist;
    boolean isDataPenerimaan;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterPenerimaan2(ArrayList<ModelPenerimaan> datalist,boolean isDataPenerimaan,
                              RecyclerViewClickInterface recyclerViewClickInterface) {
        this.datalist = datalist;
        this.isDataPenerimaan = isDataPenerimaan;
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
//        DecimalFormat kursID= (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols satuanKursID = new DecimalFormatSymbols();
//
//        satuanKursID.setCurrencySymbol("Rp. ");
//        satuanKursID.setMonetaryDecimalSeparator(',');
//        satuanKursID.setGroupingSeparator('.');
//
//        kursID.setDecimalFormatSymbols(satuanKursID);
        if (isDataPenerimaan){
            if (position>0){
                //        Card Penerimaan 1
                Log.d("19201299", "<<58>>is Penerimaan Data "+isDataPenerimaan);
                holder.cardLinear2.setVisibility(View.GONE);
                holder.cardLinear1.setVisibility(View.VISIBLE);
                {
                    if (datalist.get(position) != null) {
                        Currency formatNumber = new Currency("Rp. ", ".");
                        {
                            String nm_penyedia = datalist.get(position).getNm_suplier();
                            String alamat = datalist.get(position).getAlasuplier();
                            int ttlqty = datalist.get(position).getTtlQty();
                            int dikonversi = datalist.get(position).getDikonversi();
                            int hrg_total = datalist.get(position).getHarga_total();
                            int tgl = datalist.get(position).getTgl_purchase();

                            holder.tv_nm_penyedia1.setText(nm_penyedia);
                            holder.tv_alamat1.setText(alamat);
                            holder.tv_area1.setVisibility(View.GONE);
                            holder.tv_idtrans1.setText("idtrans");
                            holder.tv_qty1.setText("" + ttlqty + " ITEM");
                            if (dikonversi == 1) {
                                holder.tv_status1.setText("Konversi");
                                holder.tv_status.setText("kv1");
                                holder.tv_status1.setBackgroundResource(R.color.BgGreen);
                            } else {
                                holder.tv_status1.setText("Belum Konversi");
                                holder.tv_status.setText("kv0");
                                holder.tv_status1.setBackgroundResource(R.color.BgRed);
                            }
                            holder.tv_hrg_total1.setText(String.valueOf("Rp. " + formatNumber.setFormatNumber((double) hrg_total)));
                            holder.tv_tgl1.setText((new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                                    .format(
                                            new Date((Long.parseLong(String.valueOf(tgl)) * 1000))
                                    )).toString());
                        }
                    } else {
                        holder.cardLinear1.setVisibility(View.INVISIBLE);
                    }
                }
            }else {
                //        Card penerimaan 2
                Log.d("19201299", "<<96>>is Penerimaan Data "+isDataPenerimaan);
                holder.cardLinear2.setVisibility(View.VISIBLE);
                holder.cardLinear1.setVisibility(View.GONE);
                {
                    Currency formatNumber = new Currency("Rp. ", ".");

                    String nm_penyedia = datalist.get(0).getNm_suplier();
                    String alamat = datalist.get(0).getAlamat();
                    int jmlitem = datalist.get(0).getJml_item();
                    String area = datalist.get(0).getNm_area();
                    String ala_supplier = datalist.get(0).getAlasuplier();
                    String status = datalist.get(0).getStatus();
                    int hrg_total = datalist.get(0).getHarga_total();
                    int tgl = datalist.get(0).getTgl_purchase();
                    String idtrans = datalist.get(0).getId_purchase();
                    String idpurchase = datalist.get(0).getId_purchase();

                    if (datalist.get(position).isExpand()) {
                        holder.expandDetailpener.setVisibility(View.VISIBLE);
                        holder.tv_imgExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    } else {
                        holder.expandDetailpener.setVisibility(View.GONE);
                        holder.tv_imgExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }

                    holder.tv_nm_penyedia.setText("" + nm_penyedia);
                    holder.tv_alaSupplier.setText(ala_supplier);
                    holder.tv_area.setText("" + area);
                    holder.tv_idtrans.setText(idtrans);

                    holder.tv_nopurchase.setText("" + idpurchase);

                    holder.tv_jml_item.setText("" + String.valueOf(jmlitem) + " ITEM");
                    holder.tv_status.setText("" + status);

                    holder.tv_hrg_total.setText("Rp. " + formatNumber.setFormatNumber((double) hrg_total));
                    holder.tv_tgl.setText((new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                            .format(
                                    new Date((Long.parseLong(String.valueOf(tgl)) * 1000))
                            )).toString());
                }
            }

        }else {
//        Card Penerimaan 1
            Log.d("19201299", "<<141>>is Penerimaan Data "+isDataPenerimaan);
            holder.cardLinear2.setVisibility(View.GONE);
            {
                holder.cardLinear1.setVisibility(View.VISIBLE);
                if (datalist.get(position) != null) {
                    Currency formatNumber = new Currency("Rp. ", ".");
                    {
                        String nm_penyedia = datalist.get(position).getNm_suplier();
                        String alamat = datalist.get(position).getAlasuplier();
                        String area = datalist.get(position).getNm_area();
                        int ttlqty = datalist.get(position).getTtlQty();
                        int dikonversi = datalist.get(position).getDikonversi();
                        int hrg_total = datalist.get(position).getHarga_total();
                        int tgl = datalist.get(position).getUpdated();

                        holder.tv_nm_penyedia1.setText(nm_penyedia);
                        holder.tv_alamat1.setText(alamat);
                        holder.tv_area1.setVisibility(View.GONE);
                        holder.tv_idtrans1.setText("idtrans");
                        holder.tv_area1.setText(area);
                        holder.tv_qty1.setText("" + ttlqty + " ITEM");
                        if (dikonversi == 1) {
                            holder.tv_status1.setText("Konversi");
                            holder.tv_status.setText("kv1");
                            holder.tv_status1.setBackgroundResource(R.color.BgGreen);
                        } else {
                            holder.tv_status1.setText("Belum Konversi");
                            holder.tv_status.setText("kv0");
                            holder.tv_status1.setBackgroundResource(R.color.BgRed);
                        }
                        holder.tv_hrg_total1.setText(String.valueOf("Rp. " + formatNumber.setFormatNumber((double) hrg_total)));
                        holder.tv_tgl1.setText((new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                                .format(
                                        new Date((Long.parseLong(String.valueOf(tgl)) * 1000))
                                )).toString());
                    }
                } else {
                    holder.cardLinear1.setVisibility(View.INVISIBLE);
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        int datalistSize = (datalist!=null)?datalist.size():0;
        return datalistSize;
    }

    public class penerimaanBrgViewHolder extends RecyclerView.ViewHolder {
        ImageView img_background1;
        TextView tv_nm_penyedia,tv_area,tv_status,tv_hrg_total,
                tv_tgl,tv_jml_item,tv_idtrans,tv_nopurchase,tv_imgExpand,
                tv_alaSupplier;
        Button btn_detail;

        TextView tv_nm_penyedia1,tv_alamat1,tv_area1,
                tv_status1,tv_hrg_total1,tv_tgl1,tv_qty1,tv_idtrans1;

        LinearLayout cardLinear1,cardLinear2,expandDetailpener;
        public penerimaanBrgViewHolder(@NonNull View itemView) {
            super(itemView);
            cardLinear1 = itemView.findViewById(R.id.cardLinear);
            cardLinear2 = itemView.findViewById(R.id.cardLinear2);
            //            Data Konversi
            {
                img_background1 = itemView.findViewById(R.id.imgBgCard);
                tv_nm_penyedia1 = itemView.findViewById(R.id.tvNamePenyedia);
                tv_alamat1 = itemView.findViewById(R.id.tvAlamat);
                tv_area1 = itemView.findViewById(R.id.tvArea);
                tv_qty1 = itemView.findViewById(R.id.tvQTY);
                tv_status1 = itemView.findViewById(R.id.tvSts);
                tv_hrg_total1 = itemView.findViewById(R.id.tvHrgTotal);
                tv_tgl1 = itemView.findViewById(R.id.tvTgl);
                tv_idtrans1 = itemView.findViewById(R.id.tvIdTrans);
            }
            //            Data Penerimaan
            {
                tv_nm_penyedia = itemView.findViewById(R.id.tvResSupplier);
                tv_area = itemView.findViewById(R.id.tvResArea);
                tv_alaSupplier = itemView.findViewById(R.id.tvResAlaSupplier);
                tv_status = itemView.findViewById(R.id.tvResSTS);
                tv_hrg_total = itemView.findViewById(R.id.tvResTotalHrg);
                tv_jml_item = itemView.findViewById(R.id.tvResJMLItem);
                tv_tgl = itemView.findViewById(R.id.tvResTGL);
                tv_idtrans = itemView.findViewById(R.id.tvResIdTrans);
                tv_nopurchase = itemView.findViewById(R.id.tvResNmItem);
                tv_imgExpand = itemView.findViewById(R.id.imgDetailpener);

                btn_detail = itemView.findViewById(R.id.btnShowDetail);
                expandDetailpener = itemView.findViewById(R.id.linearExpandDetailContainer);
                if (getAdapterPosition()<1){
                    btn_detail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recyclerViewClickInterface.onItemClick(getAdapterPosition(), itemView);
                        }
                    });
                }
            }
            cardLinear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isDataPenerimaan) {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition()-1, itemView);
                    }else{
                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), itemView);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isDataPenerimaan){
                        if (getAdapterPosition()<1){
                            notifyItemChanged(getAdapterPosition());
                            datalist.get(getAdapterPosition()).setExpand(!datalist.get(getAdapterPosition()).isExpand());
                        }
//                    }else{
//                        recyclerViewClickInterface.onItemClick(getAdapterPosition(), itemView);
                    }
                }
            });

        }
    }
}
