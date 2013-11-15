package com.esir.sr.sweetsnake.enumeration;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum PlayerStatus
{

    /** The disconnected status */
    DISCONNECTED("disconnected"),

    /** The available status */
    AVAILABLE("available"),

    /** The invinting status */
    INVITING("inviting"),

    /** The invited status */
    INVITED("invited"),

    /** The present status */
    PRESENT("present"),

    /** The denied status */
    DENIED("denied"),

    /** The playing status */
    PLAYING("playing");

    /** The status value */
    private String value = "unknown";

    /**
     * 
     * @param _value
     */
    PlayerStatus(final String _value) {
        value = _value;
    }

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
