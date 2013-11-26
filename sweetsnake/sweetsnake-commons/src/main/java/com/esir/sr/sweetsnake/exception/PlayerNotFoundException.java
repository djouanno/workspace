package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when a player is not found
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerNotFoundException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 4022247310885736041L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new player not found exception
     */
    public PlayerNotFoundException() {
        super();
    }

    /**
     * Creates a new player not found exception
     * 
     * @param message
     *            The exception message
     */
    public PlayerNotFoundException(final String message) {
        super(message);
    }

}
