package com.persediaan.de.model;

public class ModelDaftarItem {
    String nm_item,nm_satuan;
    String id_item;
    boolean visibility;

    public ModelDaftarItem(String nm_item, String nm_satuan,String id_item) {
        this.nm_item = nm_item;
        this.nm_satuan = nm_satuan;
        this.id_item = id_item;
        this.visibility = false;
    }

    public String getId_item() {
        return id_item;
    }

    public String getNm_item() {
        return nm_item;
    }

    public void setNm_item(String nm_item) {
        this.nm_item = nm_item;
    }

    public String getNm_satuan() {
        return nm_satuan;
    }

    public void setNm_satuan(String nm_satuan) {
        this.nm_satuan = nm_satuan;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public ModelDaftarItem setVisibility(boolean visibility) {
        this.visibility = visibility;
        return this;
    }
}
