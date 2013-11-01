package com.esir.sr.sweetsnake.exception;


public class GameSessionNotFoundException extends Exception
{

    private static final long serialVersionUID = 8398924513005174981L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public GameSessionNotFoundException() {
        super();
    }

    public GameSessionNotFoundException(final String message) {
        super(message);
    }

}
