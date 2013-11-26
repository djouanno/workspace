package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when a player tried to perform an unauthorized action.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class UnauthorizedActionException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -2852895482211047199L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new unauthorized action exception
     */
    public UnauthorizedActionException() {
        super();
    }

    /**
     * Creates a new unauthorized action exception
     * 
     * @param message
     *            The exception message
     */
    public UnauthorizedActionException(final String message) {
        super(message);
    }

}
