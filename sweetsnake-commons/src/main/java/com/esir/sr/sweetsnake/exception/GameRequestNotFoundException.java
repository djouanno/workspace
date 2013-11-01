package com.esir.sr.sweetsnake.exception;


public class GameRequestNotFoundException extends Exception
{

    private static final long serialVersionUID = 1565159930012015433L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public GameRequestNotFoundException() {
        super();
    }

    public GameRequestNotFoundException(final String message) {
        super(message);
    }

}
