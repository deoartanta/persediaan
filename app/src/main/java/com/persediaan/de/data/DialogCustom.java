package com.persediaan.de.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.persediaan.de.R;

public class DialogCustom {

    Context ctx;
    ViewGroup viewGroup;

    AlertDialog.Builder dialog1;
    View viewInflated;

    int layoutView,drawableicon;

    public DialogCustom(Context ctx, ViewGroup viewGroup, int layoutView, int drawableicon) {
        this.ctx = ctx;
        this.viewGroup = viewGroup;
        this.layoutView = layoutView;
        this.drawableicon = drawableicon;
    }

    public void setCustomDialog(){
        viewInflated = LayoutInflater.from(ctx).inflate(
                layoutView, viewGroup,
                false);
        dialog1 = new AlertDialog.Builder(ctx);
        dialog1.setIcon(drawableicon);
    }
    public void setView(View view){
        dialog1.setView(view);
    }
    public void setTitle(String title){
        dialog1.setTitle(title);
    }
    public AlertDialog.Builder getDialog(){
        return dialog1;
    }
    public View getViewInflated(){
        return viewInflated;
    }
}
