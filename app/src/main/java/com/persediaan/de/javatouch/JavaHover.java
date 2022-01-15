package com.persediaan.de.javatouch;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

import com.persediaan.de.R;

public class JavaHover {
    View view;
    View viewToBackground;
    int hover_in;
    int hover_out;
    JavaHoverListener javaHoverListener;
    boolean itemClicked;

    public JavaHover(View viewHover, @DrawableRes int hover_in, @DrawableRes int hover_out) {
        this.view = viewHover;
        this.viewToBackground=view;
        this.hover_in = hover_in;
        this.hover_out = hover_out;
    }
    public JavaHover create(){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:

//                        viewToBackground.setBackgroundResource(hover_in);
                        itemClicked = true;
                        if (javaHoverListener!=null){
                            javaHoverListener.hoverIn(view);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (itemClicked){
                            viewToBackground.setBackgroundResource(hover_out);
                        }else {
                            viewToBackground.setBackgroundResource(hover_in);
                        }
                        if (javaHoverListener!=null){
                            javaHoverListener.hoverMove(view);
                        }
                        itemClicked = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        viewToBackground.setBackgroundResource(hover_out);
                        if (itemClicked){
                            javaHoverListener.onClick(view);
                        }
                        if (javaHoverListener!=null){
                            javaHoverListener.hoverOut(view);
                        }
                        itemClicked = false;
                }
                return true;
            }
        });
        return this;
    }

    public View getView() {
        return view;
    }

    public View getViewFromBackground() {
        return viewToBackground;
    }

    public void setViewToBackground(View viewToBackground) {
        this.viewToBackground = viewToBackground;
    }

    public int getHover_in() {
        return hover_in;
    }

    public void setHover_in(int hover_in) {
        this.hover_in = hover_in;
    }

    public int getHover_out() {
        return hover_out;
    }

    public void setHover_out(int hover_out) {
        this.hover_out = hover_out;
    }

    public void setJavaHoverListener(JavaHoverListener javaHoverListener) {
        this.javaHoverListener = javaHoverListener;
    }
}
