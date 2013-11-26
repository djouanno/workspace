package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when a requested player is not available.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerNotAvailableException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -574303944858420886L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new player not available exception
     */
    public PlayerNotAvailableException() {
        super();
    }

    /**
     * Creates a new player not available exception
     * 
     * @param message
     *            The exception message
     */
    public PlayerNotAvailableException(final String message) {
        super(message);
    }

}
