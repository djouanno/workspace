package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameRequestNotFoundException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = 1565159930012015433L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public GameRequestNotFoundException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public GameRequestNotFoundException(final String message) {
        super(message);
    }

}
