package com.persediaan.de.model;

public class ModelItemGudang {
    int id,admin,id_area,qty,harga,tipe,tgl_gudang,sisa,
            id_satuan,jumlah;
    String dt_gudang,nm_item,nm_satuan,eceran,nm_area,nm_singkat,id_item;
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

    public String getId_item() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public void setTgl_gudang(int tgl_gudang) {
        this.tgl_gudang = tgl_gudang;
    }

    public void setSisa(int sisa) {
        this.sisa = sisa;
    }

    public void setId_satuan(int id_satuan) {
        this.id_satuan = id_satuan;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setDt_gudang(String dt_gudang) {
        this.dt_gudang = dt_gudang;
    }

    public void setNm_item(String nm_item) {
        this.nm_item = nm_item;
    }

    public void setNm_satuan(String nm_satuan) {
        this.nm_satuan = nm_satuan;
    }

    public void setEceran(String eceran) {
        this.eceran = eceran;
    }

    public void setNm_area(String nm_area) {
        this.nm_area = nm_area;
    }

    public void setNm_singkat(String nm_singkat) {
        this.nm_singkat = nm_singkat;
    }
}
