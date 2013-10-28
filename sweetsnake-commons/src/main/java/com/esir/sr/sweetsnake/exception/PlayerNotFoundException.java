package com.esir.sr.sweetsnake.exception;


public class PlayerNotFoundException extends Exception
{

    private static final long serialVersionUID = 4022247310885736041L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public PlayerNotFoundException() {
        super();
    }

    public PlayerNotFoundException(final String message) {
        super(message);
    }

}
