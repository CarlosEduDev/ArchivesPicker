package com.ceasdev.archivespicker.exceptions;

public class NotFoundPreferencesException extends RuntimeException{
    public NotFoundPreferencesException(){
        super("PickerPreferences not found in intent");
    }
}
