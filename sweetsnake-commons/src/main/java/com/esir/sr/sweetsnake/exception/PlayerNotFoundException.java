package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerNotFoundException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = 4022247310885736041L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public PlayerNotFoundException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public PlayerNotFoundException(final String message) {
        super(message);
    }

}
