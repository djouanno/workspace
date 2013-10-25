package com.esir.sr.sweetsnake.commons.exceptions;


public class UnableToMountGameSessionException extends Exception
{

    private static final long serialVersionUID = 4022247310885736041L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public UnableToMountGameSessionException() {
        super();
    }

    public UnableToMountGameSessionException(final String message) {
        super(message);
    }

}
