package com.shockn745.data;

/**
 * @author Kempenich Florian
 */
public class IllegalSaveException extends IllegalStateException{
    public IllegalSaveException(String detailMessage) {
        super(detailMessage);
    }
}
