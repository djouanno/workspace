package com.esir.sr.sweetsnake.exception;


public class PlayerNotAvailableException extends Exception
{

    private static final long serialVersionUID = -574303944858420886L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public PlayerNotAvailableException() {
        super();
    }

    public PlayerNotAvailableException(final String message) {
        super(message);
    }

}
