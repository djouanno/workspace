package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when a game session is not found.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSessionNotFoundException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 8398924513005174981L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new game session not found exception
     */
    public GameSessionNotFoundException() {
        super();
    }

    /**
     * Creates a new game session not found exception
     * 
     * @param message
     *            The exception message
     */
    public GameSessionNotFoundException(final String message) {
        super(message);
    }

}
