package com.persediaan.de.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.ModelProfileRowExpand;
import com.persediaan.de.model.ModelProfileRowItem;

import java.util.ArrayList;

public class AdapterAkunSetting extends RecyclerView.Adapter<AdapterAkunSetting.akunSettingViewHolder> {

    ArrayList<ModelProfileRowExpand> datalist_expend;
    private RecyclerViewClickExpendInterface recyclerViewClickExpendInterface;
    ArrayList<ModelProfileRowExpand> datalist_item;
    boolean itemExpend=false;
    public static String STATE_EXPAND = "STATE_EXPAND";

    public AdapterAkunSetting(ArrayList<ModelProfileRowExpand> datalist, RecyclerViewClickExpendInterface recyclerViewClickInterface) {
        this.datalist_expend = datalist;
        this.recyclerViewClickExpendInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdapterAkunSetting.akunSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_profile_setting_expand,parent,false);
        return new akunSettingViewHolder(view,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull akunSettingViewHolder holder, int position) {
        itemExpend = datalist_expend.get(position).isEnable();
        System.out.println(itemExpend);
        if (itemExpend){
            ArrayList<ModelProfileRowItem>ModelArraySetting = new ArrayList<ModelProfileRowItem>();
            String row_name =datalist_expend.get(position).getRow_name();
            int img_row_left =datalist_expend.get(position).getimgResource();

            holder.img_right.setVisibility(View.VISIBLE);
            holder.recyclerViewProfileExpend.setVisibility(View.VISIBLE);
            holder.img_left.setVisibility(View.VISIBLE);
            holder.tv_row_name.setText(row_name);
            holder.img_left.setImageResource(img_row_left);
//            holder.img_right.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            ArrayList <ModelProfileRowItem> dataPeritem;
            datalist_item = new ArrayList<>();
            dataPeritem = datalist_expend.get(position).getModelProfileRowItems();
            int i= 0;
            for (ModelProfileRowItem data_Hsl_row_item : dataPeritem){
                ModelProfileRowExpand modelProfileRowExpand;
                if (data_Hsl_row_item.getRow_name_item()!="state"){
                    datalist_item.add(new ModelProfileRowExpand(
                            datalist_expend.get(position).getID(), data_Hsl_row_item.getRow_name_item(),
                            0,null,
                            false).setResult(
                            (data_Hsl_row_item.getResult())!=null?
                                    (data_Hsl_row_item.getResult())[i]:""
                            ).setTypeText(
                            (data_Hsl_row_item.getTypeText())!=null?
                                    (data_Hsl_row_item.getTypeText())[i]:0));
                }else{
                    if (data_Hsl_row_item.getRow_name_item()=="state"&&data_Hsl_row_item.getResult()[i]==
                            "true"){
                        datalist_expend.get(position).setExpandable(true);
                        data_Hsl_row_item.getResult()[i] = "false";
                    }
                    Log.d("19201299",
                            "onBindViewHolder: \nState("+data_Hsl_row_item.getRow_name_item()+" " +
                                    "result("+data_Hsl_row_item.getResult()[i]+")");
                }
                i++;
            }

            if (datalist_expend.get(position).isExpandable()){
                holder.recyclerViewProfileExpend.setVisibility(View.VISIBLE);
                holder.img_right.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }else{
                holder.recyclerViewProfileExpend.setVisibility(View.GONE);
                holder.img_right.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            }

            holder.recyclerViewProfileExpend.setLayoutManager(new LinearLayoutManager(holder.view.getContext(),
                        LinearLayoutManager.VERTICAL, false));

                holder.recyclerViewProfileExpend.setHasFixedSize(true);
                holder.recyclerViewProfileExpend.setItemAnimator(new DefaultItemAnimator());


                holder.recyclerViewProfileExpend.setAdapter(
                        new AdapterAkunSetting(datalist_item,recyclerViewClickExpendInterface)
                );
        }else{
            String name_item_row = datalist_expend.get(position).getRow_name();
            int img_left = datalist_expend.get(position).getimgResource();

            holder.tv_row_name.setText(name_item_row);
//            holder.tv_row_name.setPadding(0,0,10,0);
            holder.img_right.setVisibility(View.GONE);
            holder.img_left.setVisibility(View.VISIBLE);
            if (datalist_expend.get(position).getTypeItem()==ModelProfileRowExpand.TYPE_TEXT){
                holder.tv_result_area.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.VISIBLE);
                holder.tv_result.setText(datalist_expend.get(position).getResult());
            }else if(datalist_expend.get(position).getTypeItem()==ModelProfileRowExpand.TYPE_TEXT_AREA){
                holder.tv_result.setVisibility(View.GONE);
                holder.tv_result_area.setVisibility(View.VISIBLE);
                holder.tv_result_area.setText(datalist_expend.get(position).getResult());
            }else if(datalist_expend.get(position).getTypeItem()==ModelProfileRowExpand.TYPE_TEXT_PASSWORD){
                holder.tv_result_area.setVisibility(View.GONE);
                holder.tv_result.setVisibility(View.VISIBLE);
                holder.tv_result.setText(datalist_expend.get(position).getResult());
            }
            if (img_left==0){
                holder.img_left.setVisibility(View.GONE);
                holder.img_right.setVisibility(View.VISIBLE);
                holder.img_right.setImageResource(R.drawable.ic_baseline_create_24);
                holder.linearLayoutProfile.setBackgroundResource(0);
                holder.linearLayoutProfile.setPadding(0,0,0,0);

            }else{
                holder.img_left.setImageResource(img_left);
                if ((datalist_expend.size()-1)==position) {
                    holder.linearLayoutProfileC.setPadding(0, 0, 0, 100);
                }
            }
            holder.recyclerViewProfileExpend.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (datalist_expend!=null)?datalist_expend.size():0;
    }

    public class akunSettingViewHolder extends RecyclerView.ViewHolder {
        ImageView img_left,img_right;
        TextView tv_row_name,tv_result,tv_result_area;
        RecyclerView recyclerViewProfileExpend;
        LinearLayout linearLayoutProfile,linearLayoutProfileC;
        View view;

        public akunSettingViewHolder(@NonNull View itemView, ViewGroup parent) {
            super(itemView);

            tv_row_name = itemView.findViewById(R.id.tvRowName);
            tv_result = itemView.findViewById(R.id.tvSettingResult);
            tv_result_area = itemView.findViewById(R.id.tvSettingResultArea);
            img_right = itemView.findViewById(R.id.imgRight);
            img_left = itemView.findViewById(R.id.imgLeft);
            recyclerViewProfileExpend = itemView.findViewById(R.id.recyclerRowDetail);
            linearLayoutProfile = itemView.findViewById(R.id.linear_card_profile_expand);
            linearLayoutProfileC = itemView.findViewById(R.id.linear_card_profile_expand_container);
            view = itemView;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemExpend = datalist_expend.get(getAdapterPosition()).isEnable();
                    datalist_expend.get(getAdapterPosition()).setExpandable(!datalist_expend.get(getAdapterPosition()).isExpandable());
                    notifyItemChanged(getAdapterPosition());
                    if (itemExpend) {
                        recyclerViewClickExpendInterface.onItemClick(getAdapterPosition(), view);
                    }else{
                        if (datalist_expend.get(getAdapterPosition()).getimgResource()==0){
                            recyclerViewClickExpendInterface.onItemExpendClick(
                                    datalist_expend.get(getAdapterPosition()).getID(),
                                    getAdapterPosition(), view, true);
                        }else {
                            recyclerViewClickExpendInterface.onItemExpendClick(
                                    datalist_expend.get(getAdapterPosition()).getID(),
                                    getAdapterPosition(), view, false);
                        }
                    }
                }
            });

        }
    }
}
