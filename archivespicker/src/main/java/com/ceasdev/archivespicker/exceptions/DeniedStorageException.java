package com.ceasdev.archivespicker.exceptions;

public class DeniedStorageException extends RuntimeException{
    public DeniedStorageException(){
        super("No access to storage");
    }
}
