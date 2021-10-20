package com.persediaan.de.model;

public class ModelPenerimaan {
    String name_penyedia,
            status,
            area,
            alamat,tgl;
    int img_bg_card;
    int img_bg_label;
    String no_penerimaan;
    int harga_total=0;
    int ppn=0;
    int color_label;
    int paddingBot=0;

    public ModelPenerimaan(
            String name_penyedia,
            String status,
            String area,
            String alamat,
            int hrg_total,
            String tgl,
            int img_bg_card,
            String no_penerimaan) {
        this.name_penyedia = name_penyedia;
        this.status = status;
        this.area = area;
        this.alamat = alamat;
        this.harga_total = hrg_total;
        this.tgl = tgl;
        this.img_bg_card = img_bg_card;
        this.no_penerimaan = no_penerimaan;
    }

    public ModelPenerimaan(
            String name_penyedia,
            String status,
            String area,
            String alamat,
            int hrg_total,
            String tgl,
            String no_penerimaan,
            int ppn,
            int color_label,
            int img_bg_card,
            int img_bg_label) {
        this.name_penyedia = name_penyedia;
        this.status = status;
        this.area = area;
        this.alamat = alamat;
        this.tgl = tgl;
        this.img_bg_card = img_bg_card;
        this.img_bg_label = img_bg_label;
        this.no_penerimaan = no_penerimaan;
        this.harga_total = hrg_total;
        this.ppn = ppn;
        this.color_label = color_label;
    }
    public ModelPenerimaan(
            String name_penyedia,
            String status,
            String area,
            String alamat,
            int hrg_total,
            String tgl,
            String no_penerimaan,
            int ppn,
            int color_label,
            int img_bg_card,
            int img_bg_label,
            int paddingBot) {
        this.name_penyedia = name_penyedia;
        this.status = status;
        this.area = area;
        this.alamat = alamat;
        this.tgl = tgl;
        this.img_bg_card = img_bg_card;
        this.img_bg_label = img_bg_label;
        this.no_penerimaan = no_penerimaan;
        this.harga_total = hrg_total;
        this.ppn = ppn;
        this.color_label = color_label;
        this.paddingBot = paddingBot;
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

    public String getNo_penerimaan() {
        return no_penerimaan;
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
                ", alamat='" + alamat + '\'' +
                ", tgl='" + tgl + '\'' +
                ", img_bg_card=" + img_bg_card +
                ", img_bg_label=" + img_bg_label +
                ", no_penerimaan='" + no_penerimaan + '\'' +
                ", harga_total=" + harga_total +
                ", ppn=" + ppn +
                ", color_label=" + color_label +
                '}';
    }
}
