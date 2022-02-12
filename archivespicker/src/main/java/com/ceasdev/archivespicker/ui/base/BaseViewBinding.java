package com.ceasdev.archivespicker.ui.base;

import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseViewBinding{

    public View rootView;

    public BaseViewBinding(View rootView){
        this.rootView = rootView;
        initViews();
    }

    public BaseViewBinding(LayoutInflater layoutInflater, int layout){
        this(layoutInflater.inflate(layout, null));
    }


    protected abstract void initViews();

    protected <T extends View> T find(int id){
        return rootView.findViewById(id);
    }

}
