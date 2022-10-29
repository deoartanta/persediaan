package com.persediaan.de.javaKey;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class JavaKeyBoard {

    View view;
    Window window;
    InputMethodManager imm;

    public JavaKeyBoard(Window window) {
        this.window = window;
        view = this.window.getCurrentFocus();
    }

    public void setInputMethodManager(InputMethodManager imm) {
        this.imm = imm;
    }

    public JavaKeyBoard hideInput(){
        if (view!=null){
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
        return  this;
    }
    public JavaKeyBoard showInput(){
        if (window!=null){
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//            imm.showSoftInputFromInputMethod(view.getWindowToken(),0);
        }
        return  this;
    }
}
