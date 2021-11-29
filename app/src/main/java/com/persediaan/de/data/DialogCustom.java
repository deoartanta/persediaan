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

        dialog1.setView(viewInflated);
    }
    public AlertDialog.Builder getDialog(){
        return dialog1;
    }
    public View getViewInflated(){
        return viewInflated;
    }
}
