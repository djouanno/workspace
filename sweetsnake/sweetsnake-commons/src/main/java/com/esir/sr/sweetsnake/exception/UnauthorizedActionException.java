package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class UnauthorizedActionException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = -2852895482211047199L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public UnauthorizedActionException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public UnauthorizedActionException(final String message) {
        super(message);
    }

}
