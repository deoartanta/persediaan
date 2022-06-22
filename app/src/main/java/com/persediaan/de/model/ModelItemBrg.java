package com.persediaan.de.model;

public class ModelItemBrg {
int qty,id_satuan,harga,jumlah,id;
String nm_eceran,nm_satuan,nm_item,id_item;
boolean action = true;

    public ModelItemBrg(int id, String id_item, int qty, int id_satuan, int harga, int jumlah,
                        String nm_item, String nm_eceran, String nm_satuan) {
        this.id_item = id_item;
        this.qty = qty;
        this.id_satuan = id_satuan;
        this.harga = harga;
        this.jumlah = jumlah;
        this.nm_eceran = nm_eceran;
        this.nm_satuan= nm_satuan;
        this.nm_item= nm_item;
        this.id = id;
    }
    public ModelItemBrg setAction(boolean boolAction){
        this.action = boolAction;
        return this;
    }

    public boolean isAction() {
        return action;
    }

    public int getId() {
        return id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getNm_eceran() {
        return nm_eceran;
    }

    public String getNm_satuan() {
        return nm_satuan;
    }

    public String getNm_item() {
        return nm_item;
    }

    public String getId_item() {
        return id_item;
    }

    public int getQty() {
        return qty;
    }

    public int getId_satuan() {
        return id_satuan;
    }

    public int getHarga() {
        return harga;
    }

    @Override
    public String toString() {
        return "ModelItemBrg{" +
                "id_item=" + id_item +
                ", qty=" + qty +
                ", id_satuan=" + id_satuan +
                ", harga=" + harga +
                ", jumlah=" + jumlah +
                ", nm_eceran='" + nm_eceran + '\'' +
                ", nm_satuan='" + nm_satuan + '\'' +
                ", nm_item='" + nm_item + '\'' +
                '}';
    }
}
