package com.persediaan.de.model;

public class ModelPenerimaan {
    String name_penyedia,status,area,
            alamat,tgl,id_trans,no_purchase;
    int img_bg_card,img_bg_label,harga_total=0,
            ppn=0,color_label,paddingBot=0,qty;
    boolean lastItem = false;
    boolean expand = false;

    public ModelPenerimaan(
            String name_penyedia,String status,String area,
            String alamat,int qty,int hrg_total,String tgl,
            String id_trans,String no_purchase,int ppn,int color_label,
            int img_bg_card,int img_bg_label,boolean lastItem) {

        this.name_penyedia = name_penyedia;this.status = status;
        this.area = area;this.alamat = alamat;this.tgl = tgl;
        this.img_bg_card = img_bg_card;this.img_bg_label = img_bg_label;
        this.id_trans = id_trans;this.no_purchase = no_purchase;
        this.qty = qty;this.harga_total = hrg_total;this.ppn = ppn;
        this.color_label = color_label;this.lastItem = lastItem;
    }

    public String getNo_purchase() {
        return no_purchase;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isExpand() {
        return expand;
    }

    public int getPaddingBot() {
        return paddingBot;
    }

    public int getHarga_total() {
        return harga_total;
    }

    public int getPpn() {
        return ppn;
    }

    public void setHarga_total(int harga_total) {
        this.harga_total = harga_total;
    }

    public void setPpn(int ppn) {
        this.ppn = ppn;
    }

    public void setImg_bg_label(int img_bg_label) {
        this.img_bg_label = img_bg_label;
    }

    public void setColor_label(int color_label) {
        this.color_label = color_label;
    }

    public String getName_penyedia() {
        return name_penyedia;
    }

    public String getStatus() {
        return status;
    }

    public String getArea() {
        return area;
    }

    public int getQty() {
        return qty;
    }

    public boolean isLastItem() {
        return lastItem;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTgl() {
        return tgl;
    }

    public int getImg_bg_card() {
        return img_bg_card;
    }

    public int getImg_bg_label() {
        return img_bg_label;
    }

    public String getId_Trans() {
        return id_trans;
    }

    public int getColor_label() {
        return color_label;
    }

    @Override
    public String toString() {
        return "ModelPenerimaan{" +
                "name_penyedia='" + name_penyedia + '\'' +
                ", status='" + status + '\'' +
                ", area='" + area + '\'' +
                ", qty='" + qty + '\'' +
                ", alamat='" + alamat + '\'' +
                ", tgl='" + tgl + '\'' +
                ", img_bg_card=" + img_bg_card +
                ", img_bg_label=" + img_bg_label +
                ", id_trans='" + id_trans + '\'' +
                ", harga_total=" + harga_total +
                ", ppn=" + ppn +
                ", color_label=" + color_label +
                ", LastItem=" + lastItem +
                '}';
    }
}
