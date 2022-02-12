package com.ceasdev.archivespicker.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import androidx.core.content.ContextCompat;

public class ResourceUtil {

    public static Drawable getDrawable(Context context, int id){
        return ContextCompat.getDrawable(context, id);
    }

    public static int getColor(Context context, int id){
        return ContextCompat.getColor(context, id);
    }

    public static int getDip(Context context, int value){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static String getString(Context context, int id){
        return context.getResources().getString(id);
    }
}
