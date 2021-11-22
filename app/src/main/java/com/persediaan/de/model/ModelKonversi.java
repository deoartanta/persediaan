package com.persediaan.de.model;

public class ModelKonversi {
    String no_konversi, no_receipt, id_trans, id_purchase, nm_item, nm_area, nm_singkat, nm_satuan, eceran;
    Integer id,id_area,id_item,qty,id_satuan,harga,dikonversi,created,updated,jumlah;

    @Override
    public String toString() {
        return no_receipt;
    }

    public ModelKonversi(String no_receipt) {
        this.no_receipt = no_receipt;
    }

    public String getNo_konversi() {
        return no_konversi;
    }

    public String getNo_receipt() {
        return no_receipt;
    }
}
