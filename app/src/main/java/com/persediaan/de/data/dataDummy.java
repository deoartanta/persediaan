package com.persediaan.de.data;

import com.persediaan.de.R;
import com.persediaan.de.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dataDummy {
    HashMap<ModelPenerimaan,List<ModelBarang>> modelDataPenerimaan;
    ModelBarang modelBarang;
    ModelPenerimaan modelPenerimaan;
    ArrayList<ModelPenerimaan> modelPenerimaanList ;

    private String name_penyedia[]={
            "Shohib","Silvana","Doni Prasetyo"
    };
    private String status[]={
            "Belum","Sudah","Sudah"
    };
    private String area[]={
            "Malang","Probolinggo","Pasuruan"
    };
    private String alamat[]={
            "Jl. Cempaka no.27","Jl. Sudimoro VI no.3","Jl. Ahmad yani no.63"
    };
    private int hrg_total[]={
            600000,200000,400000
    };
    private String tgl[]={
            "28 September 2021","28 September 2021","30 September 2021"
    };
    private String no_penerimaan[]={
            "001","002","003"
    };
    private int ppn[]={
            0,0,0
    };
    private ModelBarang barang[]={
      new ModelBarang("57","Kertas A4","Box",5,100000),
      new ModelBarang("58","Kertas A3","Box",7,100000),
      new ModelBarang("59","Mie","Box",10,300000),
      new ModelBarang("60","Polpen","Box",10,10000)
    };
    List<ModelBarang> getDataBarang(int position){
        List<ModelBarang> dataBarangList=null;
      switch (position){
          case 0:
              dataBarangList.add(barang[0]);
              dataBarangList.add(barang[1]);
              dataBarangList.add(barang[2]);
              dataBarangList.add(barang[3]);
              break;
          case 1:
              dataBarangList.add(barang[0]);
              dataBarangList.add(barang[1]);
              break;
          case 2:
              dataBarangList.add(barang[0]);
              dataBarangList.add(barang[1]);
              dataBarangList.add(barang[3]);
              break;
      }
      return  dataBarangList;
    };

    private void setModelDataPenerimaan(ModelPenerimaan penerimaan,
                                        List<ModelBarang> barang) {
        modelDataPenerimaan.put(penerimaan,barang);
    }

    public HashMap<ModelPenerimaan, List<ModelBarang>> getModelDataPenerimaan() {

        for (int position=0;position<name_penyedia.length;position++){
            modelPenerimaan = new ModelPenerimaan(
                    name_penyedia[position],
                    status[position],
                    area[position],
                    alamat[position],
                    hrg_total[position],
                    tgl[position],
                    R.drawable.ic_bubble_chart_24,"00"+(position+1));
            if (status[position]=="Belum"){
                modelPenerimaan.setColor_label(R.color.white);
                modelPenerimaan.setImg_bg_label(R.drawable.ic_bg_label_red_1);
            }else{
                modelPenerimaan.setColor_label(R.color.colorBgGreen);
                modelPenerimaan.setImg_bg_label(R.drawable.ic_bg_label_green);
            }

            setModelDataPenerimaan(modelPenerimaan,getDataBarang(position));
        }
        return modelDataPenerimaan;
    }
    public ArrayList<ModelPenerimaan> getPenerimaan(){
        modelPenerimaanList = new ArrayList<ModelPenerimaan>();
        for (int i= 0;i<name_penyedia.length;i++){
            modelPenerimaan = new ModelPenerimaan(
                    name_penyedia[i],
                    status[i],
                    area[i],
                    alamat[i],hrg_total[i],
                    tgl[1],R.drawable.ic_bubble_chart_24,"00"+(i+1)
            );
            if (status[i]=="Belum"){
                modelPenerimaan.setColor_label(R.color.white);
                modelPenerimaan.setImg_bg_label(R.drawable.ic_bg_label_red_1);
            }else{
                modelPenerimaan.setColor_label(R.color.colorBgGreen);
                modelPenerimaan.setImg_bg_label(R.drawable.ic_bg_label_green);
            }
            if (modelPenerimaan!=null){
                modelPenerimaanList.add(modelPenerimaan);
            }
            System.out.println(modelPenerimaan.toString());
        }
        return modelPenerimaanList;
    }

}
