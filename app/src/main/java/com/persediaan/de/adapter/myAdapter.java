package com.persediaan.de.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.persediaan.de.R;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.myModel;

import java.util.ArrayList;

public class myAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ModelPenerimaan> modelArrayList;

    public myAdapter(Context context, ArrayList<ModelPenerimaan> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_penerimaan,container,false);

        ImageView img_background = view.findViewById(R.id.imgBgCard);
        TextView tv_nm_penyedia = view.findViewById(R.id.tvNamePenyedia);
        TextView tv_alamat = view.findViewById(R.id.tvAlamat);
        TextView tv_area = view.findViewById(R.id.tvArea);
        TextView tv_status = view.findViewById(R.id.tvSts);
        TextView tv_hrg_total = view.findViewById(R.id.tvHrgTotal);
        TextView tv_tgl = view.findViewById(R.id.tvTgl);

        ModelPenerimaan model = modelArrayList.get(position);

        String nm_penyedia = model.getName_penyedia();
        String alamat = model.getAlamat();
        String area = model.getArea();
        String status = model.getStatus();
        int hrg_total = model.getHarga_total();
        String tgl = model.getTgl();

//        img_background.setImageResource(img);
        tv_nm_penyedia.setText(nm_penyedia);
        tv_alamat.setText(alamat);
        tv_area.setText(area);
        tv_status.setText(status);
//        tv_status.setBackgroundResource(bg_label);
//        tv_status.setTextColor(color);
        tv_hrg_total.setText(String.valueOf(hrg_total));
        tv_tgl.setText(tgl);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "name : "
                        +nm_penyedia+"\n alamat :"+
                        alamat+"\n status : "+
                        status+"\n Harga Total :" +hrg_total, Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view,position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
