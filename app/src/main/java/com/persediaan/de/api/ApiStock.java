package com.persediaan.de.api;

public class ApiStock {
    String dt_gudang;
    String nm_item;
    String nm_satuan;
    String eceran;
    String nm_area;
    String nm_singkat;
    int id;
    int admin;
    int id_area;
    int id_item;
    int qty;
    int harga;
    int tipe;
    int tgl_gudang;
    int sisa;
    int id_satuan;
    int jumlah;

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

    public int getId() {
        return id;
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

    public int getTgl_gudang() {
        return tgl_gudang;
    }

    public int getSisa() {
        return sisa;
    }

    public int getId_satuan() {
        return id_satuan;
    }

    public int getJumlah() {
        return jumlah;
    }
}
