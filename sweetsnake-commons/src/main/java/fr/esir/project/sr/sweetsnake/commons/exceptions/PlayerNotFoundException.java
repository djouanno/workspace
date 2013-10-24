package fr.esir.project.sr.sweetsnake.commons.exceptions;


public class PlayerNotFoundException extends Exception
{

    private static final long serialVersionUID = 4022247310885736041L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public PlayerNotFoundException() {
        super();
    }

    public PlayerNotFoundException(final String message) {
        super(message);
    }

}
