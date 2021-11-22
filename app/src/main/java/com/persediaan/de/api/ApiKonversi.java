package com.persediaan.de.api;

public class ApiKonversi {
    private String no_konversi, no_receipt, id_trans, id_purchase, nm_item, nm_area, nm_singkat, nm_satuan, eceran, id_detail;
    private Integer id,id_area,id_item,qty,id_satuan,harga,dikonversi,created,updated,jumlah;

    public String getId_detail() {
        return id_detail;
    }

    public String getId_purchase() {
        return id_purchase;
    }

    public String getNm_item() {
        return nm_item;
    }

    public String getNm_area() {
        return nm_area;
    }

    public String getNm_singkat() {
        return nm_singkat;
    }

    public String getNm_satuan() {
        return nm_satuan;
    }

    public String getEceran() {
        return eceran;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_area() {
        return id_area;
    }

    public Integer getId_item() {
        return id_item;
    }

    public Integer getQty() {
        return qty;
    }

    public Integer getId_satuan() {
        return id_satuan;
    }

    public Integer getHarga() {
        return harga;
    }

    public Integer getDikonversi() {
        return dikonversi;
    }

    public Integer getCreated() {
        return created;
    }

    public Integer getUpdated() {
        return updated;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public String getNo_konversi(){
        return no_konversi;
    }

    public String getNo_receipt(){
        return no_receipt;
    }

    public String getId_trans() {
        return id_trans;
    }
}
