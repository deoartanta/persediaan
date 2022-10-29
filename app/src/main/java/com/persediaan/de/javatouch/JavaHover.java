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
    int m = 0;
    JavaHoverListener javaHoverListener;
    boolean itemClicked;

    public JavaHover(View viewHover, @DrawableRes int hover_in, @DrawableRes int hover_out) {
        this.view = viewHover;
        this.viewToBackground=view;
        this.hover_in = hover_in;
        this.hover_out = hover_out;
    }
    public JavaHover create(){
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view1) {
//                javaHoverListener.hoverMove(view,0,0);
//                return true;
//            }
//        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = 0,y=0;
                float x1=0,y1=0;
                m++;
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:

//                        viewToBackground.setBackgroundResource(hover_in);
//                        x=motionEvent.getX()+(motionEvent.getX()*0.2f);
//                        y=motionEvent.getX()+(motionEvent.getY()*0.2f);
//                        x1=motionEvent.getX()-(motionEvent.getX()*0.2f);
//                        y1=motionEvent.getX()-(motionEvent.getY()*0.2f);
//                        itemClicked = true;
//                        if (javaHoverListener!=null){
//                            javaHoverListener.hoverIn(view,motionEvent.getX());
//                            javaHoverListener.hoverMove(view,m,motionEvent.getX());
//                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if ((motionEvent.getX()>x||motionEvent.getY()>y)||
//                                (motionEvent.getX()<x1||motionEvent.getY()<y1)){
//                            if (itemClicked){
//                                viewToBackground.setBackgroundResource(hover_out);
//                            }else {
//                                viewToBackground.setBackgroundResource(hover_in);
//                            }
//                            if (javaHoverListener!=null){
//                                javaHoverListener.hoverMove(view,m,m);
//                            }
//                            itemClicked = false;
//                        }
                        break;
                    case MotionEvent.ACTION_UP:
//                        viewToBackground.setBackgroundResource(hover_out);
//                        if (itemClicked){
                            if (javaHoverListener!=null){
                                javaHoverListener.onClick(view);
                            }
//                        }
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
