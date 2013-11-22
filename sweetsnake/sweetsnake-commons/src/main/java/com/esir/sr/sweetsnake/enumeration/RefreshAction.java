package com.esir.sr.sweetsnake.enumeration;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum RefreshAction
{

    /** The add action */
    ADD("add"),

    /** The move action */
    MOVE("move"),

    /** The remove action */
    REMOVE("remove");

    /** */
    private String value;

    /**
     * 
     * @param _value
     */
    private RefreshAction(final String _value) {
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
