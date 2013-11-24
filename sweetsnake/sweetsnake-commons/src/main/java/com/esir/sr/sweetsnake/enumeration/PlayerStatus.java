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

    /** The invited status */
    INVITED("invited"),

    /** The ready status */
    READY("ready"),

    /** The winner status */
    WINNER("winner"),

    /** The loser status */
    LOSER("loser"),

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
