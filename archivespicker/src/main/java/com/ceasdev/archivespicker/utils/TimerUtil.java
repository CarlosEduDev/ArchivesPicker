package com.ceasdev.archivespicker.utils;

import java.text.DateFormat;

public class TimerUtil{

    public static String formatString(long time){
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(time);
    }
    
}
