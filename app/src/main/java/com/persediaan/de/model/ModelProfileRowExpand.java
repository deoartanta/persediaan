package com.persediaan.de.model;

import java.util.ArrayList;

public class ModelProfileRowExpand {
    String row_name;
    int imgResource;
    boolean enable = false;

    ArrayList<ModelProfileRowItem> modelProfileRowItems;

    public ModelProfileRowExpand(
            String row_name, int imgResource,
            ArrayList<ModelProfileRowItem> modelProfileRowItems,boolean enable) {
        this.modelProfileRowItems = modelProfileRowItems;
        this.row_name = row_name;
        this.imgResource = imgResource;
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getRow_name() {
        return row_name;
    }

    public int getimgResource() {
        return imgResource;
    }

    public ArrayList<ModelProfileRowItem> getModelProfileRowItems() {
        return modelProfileRowItems;
    }
}
