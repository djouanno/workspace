package com.esir.sr.sweetsnake.exception;


public class BadGameSessionException extends Exception
{

    private static final long serialVersionUID = 4022247310885736041L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public BadGameSessionException() {
        super();
    }

    public BadGameSessionException(final String message) {
        super(message);
    }

}
