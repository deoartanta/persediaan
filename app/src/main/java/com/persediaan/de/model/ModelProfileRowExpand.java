package com.persediaan.de.model;

import java.util.ArrayList;

public class ModelProfileRowExpand {
    String row_name,result;
    int imgResource;
    boolean enable = false;
    boolean expandable;

    ArrayList<ModelProfileRowItem> modelProfileRowItems;

    public ModelProfileRowExpand(
            String row_name, int imgResource,
            ArrayList<ModelProfileRowItem> modelProfileRowItems,boolean enable) {
        this.modelProfileRowItems = modelProfileRowItems;
        this.row_name = row_name;
        this.imgResource = imgResource;
        this.enable = enable;
        expandable= false;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public ModelProfileRowExpand setResult(String result) {
        this.result = result;
        return this;
    }

    public String getResult() {
        return result;
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
