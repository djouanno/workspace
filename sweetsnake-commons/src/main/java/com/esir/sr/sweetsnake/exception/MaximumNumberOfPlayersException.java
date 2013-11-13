package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class MaximumNumberOfPlayersException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = -2046501376174911625L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public MaximumNumberOfPlayersException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public MaximumNumberOfPlayersException(final String message) {
        super(message);
    }

}
