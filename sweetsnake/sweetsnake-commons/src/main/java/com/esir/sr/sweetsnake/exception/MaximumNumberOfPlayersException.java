package com.esir.sr.sweetsnake.exception;

/**
 * This class represents the exception raised when the maximum number of players per session has been reached.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class MaximumNumberOfPlayersException extends Exception
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -2046501376174911625L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new maximum number of players exception
     */
    public MaximumNumberOfPlayersException() {
        super();
    }

    /**
     * Creates a new maximum number of players exception
     * 
     * @param message
     *            The exception message
     */
    public MaximumNumberOfPlayersException(final String message) {
        super(message);
    }

}
