package com.persediaan.de.model;

public class ModelItemsKonv {
    String no_receipt, id_purchase, nm_item, nm_area, nm_singkat, nm_satuan, eceran;
    Integer id,id_area,id_item,qty,id_satuan,harga,dikonversi,created,updated,jumlah;

    public ModelItemsKonv(
            String no_receipt, String id_purchase, String nm_item,
            String nm_area, String nm_singkat, String nm_satuan,
            String eceran, Integer id, Integer id_area, Integer id_item,
            Integer qty, Integer id_satuan, Integer harga, Integer dikonversi,
            Integer created, Integer updated, Integer jumlah
    ) {
        this.no_receipt = no_receipt;
        this.id_purchase = id_purchase;
        this.nm_item = nm_item;
        this.nm_area = nm_area;
        this.nm_singkat = nm_singkat;
        this.nm_satuan = nm_satuan;
        this.eceran = eceran;
        this.id = id;
        this.id_area = id_area;
        this.id_item = id_item;
        this.qty = qty;
        this.id_satuan = id_satuan;
        this.harga = harga;
        this.dikonversi = dikonversi;
        this.created = created;
        this.updated = updated;
        this.jumlah = jumlah;
    }

    @Override
    public String toString() {
        return "ModelItemsKonv{" +
                "no_receipt='" + no_receipt + '\'' +
                ", id_purchase='" + id_purchase + '\'' +
                ", nm_item='" + nm_item + '\'' +
                ", nm_area='" + nm_area + '\'' +
                ", nm_singkat='" + nm_singkat + '\'' +
                ", nm_satuan='" + nm_satuan + '\'' +
                ", eceran='" + eceran + '\'' +
                ", id=" + id +
                ", id_area=" + id_area +
                ", id_item=" + id_item +
                ", qty=" + qty +
                ", id_satuan=" + id_satuan +
                ", harga=" + harga +
                ", dikonversi=" + dikonversi +
                ", created=" + created +
                ", updated=" + updated +
                ", jumlah=" + jumlah +
                '}';
    }

    public String getNo_receipt() {
        return no_receipt;
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
}
