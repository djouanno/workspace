package com.esir.sr.sweetsnake.enumeration;

/**
 * This enumeration contains all the possible status for a player.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum PlayerStatus
{

    /**********************************************************************************************
     * [BLOCK] STATIC ENUMERATIONS
     **********************************************************************************************/

    /** The disconnected status */
    DISCONNECTED("disconnected"),

    /** The available status */
    AVAILABLE("available"),

    /** The invited status */
    INVITED("invited"),

    /** The ready status */
    READY("ready"),

    /** The winner status */
    WINNER("winner"),

    /** The loser status */
    LOSER("loser"),

    /** The draw status */
    DRAW("draw"),

    /** The denied status */
    DENIED("denied"),

    /** The playing status */
    PLAYING("playing");

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The status value */
    private String value = "unknown";

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new player status
     * 
     * @param _value
     *            The player status value
     */
    PlayerStatus(final String _value) {
        value = _value;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return value;
    }

}
