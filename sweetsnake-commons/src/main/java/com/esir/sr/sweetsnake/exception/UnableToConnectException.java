package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class UnableToConnectException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = 6715591274262381713L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public UnableToConnectException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public UnableToConnectException(final String message) {
        super(message);
    }

}
