package com.persediaan.de.model;

public class ModelItemGudang {
    int id,admin,id_area,id_item,qty,harga,tipe,tgl_gudang,sisa,
            id_satuan,jumlah;
    String dt_gudang,nm_item,nm_satuan,eceran,nm_area,nm_singkat;
    public ModelItemGudang(String nm_item,int sisa,String area){
        this.nm_area = area;
        this.nm_item = nm_item;
        this.sisa = sisa;
    }

    public int getId() {
        return id;
    }

    public int getTgl_gudang() {
        return tgl_gudang;
    }

    public int getSisa() {
        return sisa;
    }

    public int getAdmin() {
        return admin;
    }

    public int getId_area() {
        return id_area;
    }

    public int getId_item() {
        return id_item;
    }

    public int getQty() {
        return qty;
    }

    public int getHarga() {
        return harga;
    }

    public int getTipe() {
        return tipe;
    }

    public int getId_satuan() {
        return id_satuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getDt_gudang() {
        return dt_gudang;
    }

    public String getNm_item() {
        return nm_item;
    }

    public String getNm_satuan() {
        return nm_satuan;
    }

    public String getEceran() {
        return eceran;
    }

    public String getNm_area() {
        return nm_area;
    }

    public String getNm_singkat() {
        return nm_singkat;
    }
}
