package com.esir.sr.sweetsnake.exception;


public class UnableToConnectException extends Exception
{

    private static final long serialVersionUID = 6715591274262381713L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public UnableToConnectException() {
        super();
    }

    public UnableToConnectException(final String message) {
        super(message);
    }

}
