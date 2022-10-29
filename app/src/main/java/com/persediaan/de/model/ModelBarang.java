package com.persediaan.de.model;

public class ModelBarang {
    String code_barang,
            nama_barang,
            satuan_barang;
    int qty,harga_barang;

    public ModelBarang(String code_barang, String nama_barang, String satuan_barang, int qty, int harga_barang) {
        this.code_barang = code_barang;
        this.nama_barang = nama_barang;
        this.satuan_barang = satuan_barang;
        this.qty = qty;
        this.harga_barang = harga_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getSatuan_barang() {
        return satuan_barang;
    }

    public int getQty() {
        return qty;
    }

    public int getHarga_barang() {
        return harga_barang;
    }

    public String getCode_barang() {
        return code_barang;
    }
}
