package com.persediaan.de.model;

public class ModelProfileRowItem {
    String row_name_item;
    int img_left;

    public ModelProfileRowItem(String row_name_item, int img_left) {
        this.row_name_item = row_name_item;
        this.img_left = img_left;
    }

    public String getRow_name_item() {
        return row_name_item;
    }

    public int getImg_left() {
        return img_left;
    }
}
