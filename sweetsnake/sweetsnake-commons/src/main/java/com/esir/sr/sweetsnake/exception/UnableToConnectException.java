package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when a player was unable to connect to the server.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class UnableToConnectException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 6715591274262381713L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new unable to connect exception
     */
    public UnableToConnectException() {
        super();
    }

    /**
     * Creates a new unable to connect exception
     * 
     * @param message
     *            The exception message
     */
    public UnableToConnectException(final String message) {
        super(message);
    }

}
