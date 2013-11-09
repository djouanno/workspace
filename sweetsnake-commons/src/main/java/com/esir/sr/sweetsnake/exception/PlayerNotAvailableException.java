package com.esir.sr.sweetsnake.exception;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerNotAvailableException extends Exception
{

    /** The serial version UID */
    private static final long serialVersionUID = -574303944858420886L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public PlayerNotAvailableException() {
        super();
    }

    /**
     * 
     * @param message
     */
    public PlayerNotAvailableException(final String message) {
        super(message);
    }

}
