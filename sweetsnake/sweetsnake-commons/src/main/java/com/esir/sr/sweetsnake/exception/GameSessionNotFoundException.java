package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSessionNotFoundException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = 8398924513005174981L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public GameSessionNotFoundException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public GameSessionNotFoundException(final String message) {
        super(message);
    }

}
