package com.persediaan.de.model;

import java.util.ArrayList;

public class ModelProfileRowExpand {
    String row_name,result;
    int typeItem,index = 0;
    int imgResource;
    boolean enable = false;
    boolean expandable;

    public static int TYPE_TEXT = 0;
    public static int TYPE_TEXT_AREA = 1;
    public static int TYPE_TEXT_BOOLEAN = 2;
    public static int TYPE_TEXT_PASSWORD = 3;

    ArrayList<ModelProfileRowItem> modelProfileRowItems;

    public ModelProfileRowExpand(int id,
            String row_name, int imgResource,
            ArrayList<ModelProfileRowItem> modelProfileRowItems,boolean enable) {
        this.modelProfileRowItems = modelProfileRowItems;
        this.row_name = row_name;
        this.imgResource = imgResource;
        this.enable = enable;
        this.index = id;
        expandable= false;
    }

    public int getID() {
        return index;
    }

    public int getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(int typeItem) {
        this.typeItem = typeItem;
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
    public ModelProfileRowExpand setTypeText(int typeText) {
        this.typeItem = typeText;
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
