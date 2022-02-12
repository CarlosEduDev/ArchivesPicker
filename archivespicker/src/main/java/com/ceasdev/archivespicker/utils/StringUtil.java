package com.ceasdev.archivespicker.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import java.text.NumberFormat;

public class StringUtil{

    public static SpannableString boldText(String textBold, String text){
        int start = text.indexOf(textBold);
        int end = start + textBold.length();
        return boldRange(start, end, text);
    }

    public static SpannableString boldRange(int startIndex, int endIndex, String text){
        return spannable(startIndex, endIndex, new StyleSpan(Typeface.BOLD), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE, text);
    }

    public static SpannableString spannable(int startIndex, int endIndex, Object what, int flags, String text){
        SpannableString ss = new SpannableString(text);
        ss.setSpan(what, startIndex, endIndex, flags);
        return ss;
    }

    public static String longToString(long number){
        return NumberFormat.getInstance().format(number);
    }

}
