package com.persediaan.de.javatouch;

import android.view.View;

public interface JavaHoverListener {
    void hoverIn(View view,float x);
    void hoverOut(View view);
    void onClick(View view);

    void hoverMove(View view, float x, float y);
}
