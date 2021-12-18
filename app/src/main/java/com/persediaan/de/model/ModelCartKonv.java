package com.persediaan.de.model;

public class ModelCartKonv {
    String dt_conv, id_receipt, nm_item, nm_area, nm_singkat, nm_satuan, eceran, id_trans, note;
    Integer id, id_area, id_item, qty_asal, qty_conv, id_satuan, harga, updated, jumlah, admin, tgl_trans;
    boolean expand = false,
            paddingLastItem = false;

    public ModelCartKonv(
            Integer id,
            String dt_conv,
            Integer id_area,
            String id_receipt,
            Integer id_item,
            Integer qty_asal,
            Integer qty_conv,
            Integer id_satuan,
            Integer harga,
            Integer updated,
            String nm_item,
            String nm_area,
            String nm_singkat,
            String nm_satuan,
            Integer jumlah,
            String eceran,
            String id_trans,
            Integer admin,
            String note,
            Integer tgl_trans){
        this.id = id;
        this.dt_conv = dt_conv;
        this.id_area = id_area;
        this.id_receipt = id_receipt;
        this.id_item = id_item;
        this.qty_asal = qty_asal;
        this.qty_conv = qty_conv;
        this.id_satuan = id_satuan;
        this.harga = harga;
        this.updated = updated;
        this.nm_item = nm_item;
        this.nm_area = nm_area;
        this.nm_singkat = nm_singkat;
        this.nm_satuan = nm_satuan;
        this.jumlah = jumlah;
        this.eceran = eceran;
        this.id_trans = id_trans;
        this.admin = admin;
        this.note = note;
        this.tgl_trans = tgl_trans;
    }

    public ModelCartKonv setPaddingLastItem(boolean paddingLastItem) {
        this.paddingLastItem = paddingLastItem;
        return this;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isExpand() {
        return expand;
    }

    public boolean isPaddingLastItem() {
        return paddingLastItem;
    }

    @Override
    public String toString() {
        return "{" +
                "dt_conv='" + dt_conv + '\'' +
                ", id_receipt='" + id_receipt + '\'' +
                ", nm_item='" + nm_item + '\'' +
                ", nm_area='" + nm_area + '\'' +
                ", nm_singkat='" + nm_singkat + '\'' +
                ", nm_satuan='" + nm_satuan + '\'' +
                ", eceran='" + eceran + '\'' +
                ", id_trans='" + id_trans + '\'' +
                ", note='" + note + '\'' +
                ", id=" + id +
                ", id_area=" + id_area +
                ", id_item=" + id_item +
                ", qty_asal=" + qty_asal +
                ", qty_conv=" + qty_conv +
                ", id_satuan=" + id_satuan +
                ", harga=" + harga +
                ", updated=" + updated +
                ", jumlah=" + jumlah +
                ", admin=" + admin +
                ", tgl_trans=" + tgl_trans +
                '}';
    }

    public String getDt_conv() {
        return dt_conv;
    }

    public String getId_receipt() {
        return id_receipt;
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

    public String getId_trans() {
        return id_trans;
    }

    public String getNote() {
        return note;
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

    public Integer getQty_asal() {
        return qty_asal;
    }

    public Integer getQty_conv() {
        return qty_conv;
    }

    public Integer getId_satuan() {
        return id_satuan;
    }

    public Integer getHarga() {
        return harga;
    }

    public Integer getUpdated() {
        return updated;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public Integer getAdmin() {
        return admin;
    }

    public Integer getTgl_trans() {
        return tgl_trans;
    }
}
