package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when a game request is not found.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameRequestNotFoundException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 1565159930012015433L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new game request not found exception
     */
    public GameRequestNotFoundException() {
        super();
    }

    /**
     * Creates a new game request not found exception
     * 
     * @param message
     *            The exception message
     */
    public GameRequestNotFoundException(final String message) {
        super(message);
    }

}
