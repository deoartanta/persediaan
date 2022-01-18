package com.persediaan.de.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.javatouch.JavaHover;
import com.persediaan.de.javatouch.JavaHoverListener;
import com.persediaan.de.model.ModelDaftarItem;
import com.persediaan.de.model.ModelItemGudang;

import java.util.ArrayList;

public class AdapterItemGudang extends RecyclerView.Adapter<AdapterItemGudang.AdapterViewHolder> {

    ArrayList<ModelItemGudang> listItem;
    Context ctx;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public AdapterItemGudang(ArrayList<ModelItemGudang> listItem, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.listItem = listItem;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdapterItemGudang.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_item_gudang,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemGudang.AdapterViewHolder holder, int position) {
        holder.tv_nm_item.setText(listItem.get(position).getNm_item());
        holder.tv_stock.setText(""+listItem.get(position).getSisa()+" "+listItem.get(position).getEceran());
        holder.tv_area.setText(listItem.get(position).getNm_area());


    }

    @Override
    public int getItemCount() {
        return (listItem!=null)?listItem.size():0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nm_item,tv_stock,tv_area;
        LinearLayout linear_item_gudang;
        boolean itemClicked = false;
        CardView card_item_gudang;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nm_item =itemView.findViewById(R.id.tvResNmItem);
            tv_stock =itemView.findViewById(R.id.tvResStock);
            tv_area =itemView.findViewById(R.id.tvArea);
            linear_item_gudang =itemView.findViewById(R.id.linearItemGudang);
            card_item_gudang =itemView.findViewById(R.id.cardItemGudang);

            new JavaHover(card_item_gudang,R.color.bgcCickedSplas,
                    R.color.splashBackground).create()
                    .setJavaHoverListener(new JavaHoverListener() {
                @Override
                public void hoverIn(View view,float x) {

                }

                @Override
                public void hoverOut(View view) {

                }

                @Override
                public void hoverMove(View view, float x, float y) {

                }

                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),itemView);
                }
            });

        }
    }
}
